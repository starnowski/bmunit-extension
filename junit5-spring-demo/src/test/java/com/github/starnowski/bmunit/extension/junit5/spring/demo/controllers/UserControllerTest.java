package com.github.starnowski.bmunit.extension.junit5.spring.demo.controllers;

import com.github.starnowski.bmunit.extension.junit5.spring.demo.dto.UserDto;
import com.github.starnowski.bmunit.extension.junit5.spring.demo.repositories.UserRepository;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.jboss.byteman.contrib.bmunit.BMUnitConfig;
import org.jboss.byteman.contrib.bmunit.WithByteman;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.starnowski.bmunit.extension.junit5.spring.demo.util.DemoTestUtils.CLEAR_DATABASE_SCRIPT_PATH;
import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.createJoin;
import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.joinWait;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@WithByteman
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = CLEAR_DATABASE_SCRIPT_PATH,
        config = @SqlConfig(transactionMode = ISOLATED),
        executionPhase = BEFORE_TEST_METHOD)
@Sql(value = CLEAR_DATABASE_SCRIPT_PATH,
        config = @SqlConfig(transactionMode = ISOLATED),
        executionPhase = AFTER_TEST_METHOD)
@EnableAsync
public class UserControllerTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetup.verbose(ServerSetupTest.ALL)).withConfiguration(new GreenMailConfiguration().withUser("no.such.mail@gmail.com", "no.such.password"));
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    @DisplayName("Should send mail message after correct user sign-up and wait until the async operation which is e-mail sending is complete")
    @ParameterizedTest(name = "{index}. expected e-mail is {0}")
    @ValueSource(strings = {"szymon.doe@nosuch.domain.com", "john.doe@gmail.com","marry.doe@hotmail.com", "jack.black@aws.eu" })
    @BMUnitConfig(verbose = true, bmunitVerbose = true)
    @BMRules(rules = {
            @BMRule(name = "signal thread waiting for mutex \"UserControllerTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation\"",
                    targetClass = "com.github.starnowski.bmunit.extension.junit5.spring.demo.services.MailService",
                    targetMethod = "handleNewUserEvent(com.github.starnowski.bmunit.extension.junit5.spring.demo.util.NewUserEvent)",
                    targetLocation = "AT EXIT",
                    action = "joinEnlist(\"UserControllerTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation\")")
    })
    public void testShouldCreateNewUserAndSendMailMessageInAsyncOperation(String expectedEmail) throws IOException, URISyntaxException, MessagingException {
        // given
        assertThat(userRepository.findByEmail(expectedEmail)).isNull();
        UserDto dto = new UserDto().setEmail(expectedEmail).setPassword("XXX");
        RestTemplate restTemplate = new RestTemplate();
        createJoin("UserControllerTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation", 1);
        assertEquals(0, greenMail.getReceivedMessages().length);

        // when
        UserDto responseEntity = restTemplate.postForObject(new URI("http://localhost:" + port + "/users/"), (Object) dto, UserDto.class);
        joinWait("UserControllerTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation", 1, 15000);

        // then
        assertThat(userRepository.findByEmail(expectedEmail)).isNotNull();
        assertThat(greenMail.getReceivedMessages().length).isEqualTo(1);
        assertThat(greenMail.getReceivedMessages()[0].getSubject()).contains("New user");
        assertThat(greenMail.getReceivedMessages()[0].getAllRecipients()[0].toString()).contains(expectedEmail);
    }
}
package com.github.starnowski.bmunit.extension.junit5.spring.demo.controllers;

import com.github.starnowski.bmunit.extension.junit5.spring.demo.dto.UserDto;
import com.github.starnowski.bmunit.extension.junit5.spring.demo.repositories.UserRepository;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.jboss.byteman.contrib.bmunit.BMUnitConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

//@WithByteman
//@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = CLEAR_DATABASE_SCRIPT_PATH,
        config = @SqlConfig(transactionMode = ISOLATED),
        executionPhase = BEFORE_TEST_METHOD)
@Sql(value = CLEAR_DATABASE_SCRIPT_PATH,
        config = @SqlConfig(transactionMode = ISOLATED),
        executionPhase = AFTER_TEST_METHOD)
@EnableAsync
public class UserControllerTest {

//    @Rule
//    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP);
    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension();
    @Autowired
    UserRepository userRepository;
    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    @Test
//    @org.junit.Test
    @BMUnitConfig(verbose = true, bmunitVerbose = true)
    @BMRules(rules = {
            @BMRule(name = "signal thread waiting for mutex \"UserControllerTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation\"",
                    targetClass = "com.github.starnowski.bmunit.extension.junit5.spring.demo.services.MailService",
                    targetMethod = "handleNewUserEvent(com.github.starnowski.bmunit.extension.junit5.spring.demo.util.NewUserEvent)",
                    targetLocation = "AT EXIT",
                    action = "joinEnlist(\"UserControllerTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation\")")
    })
    public void testShouldCreateNewUserAndSendMailMessageInAsyncOperation() throws IOException, URISyntaxException, MessagingException {
        // given
        String expectedEmail = "szymon.doe@nosuch.domain.com";
        assertThat(userRepository.findByEmail(expectedEmail)).isNull();
        UserDto dto = new UserDto().setEmail(expectedEmail).setPassword("XXX");
        RestTemplate restTemplate = new RestTemplate();
//        createJoin("UserControllerTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation", 1);
        assertEquals(0, greenMail.getReceivedMessages().length);

        // when
        UserDto responseEntity = restTemplate.postForObject(new URI("http://localhost:" + port + "/users/"), (Object) dto, UserDto.class);
//        joinWait("UserControllerTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation", 1, 15000);

        // then
        assertThat(userRepository.findByEmail(expectedEmail)).isNotNull();
        assertThat(greenMail.getReceivedMessages().length).isEqualTo(1);
        assertThat(greenMail.getReceivedMessages()[0].getSubject()).contains("New user");
        assertThat(greenMail.getReceivedMessages()[0].getAllRecipients()[0].toString()).contains(expectedEmail);
    }

}
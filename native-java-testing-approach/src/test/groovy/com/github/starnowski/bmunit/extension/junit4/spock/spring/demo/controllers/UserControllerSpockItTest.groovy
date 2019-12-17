package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.controllers

import com.github.starnowski.bmunit.extension.junit4.rule.BMUnitMethodRule
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.dto.UserDto
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.repositories.UserRepository
import com.icegreen.greenmail.junit.GreenMailRule
import com.icegreen.greenmail.util.ServerSetupTest
import org.jboss.byteman.contrib.bmunit.BMRule
import org.jboss.byteman.contrib.bmunit.BMRules
import org.jboss.byteman.contrib.bmunit.BMUnitConfig
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlGroup
import spock.lang.Specification
import spock.lang.Unroll

import javax.mail.MessagingException

import static com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.DemoTestUtils.CLEAR_DATABASE_SCRIPT_PATH
import static com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.DemoTestUtils.GROOVY_TEST_ASYNC_OPERATION_TIMEOUT
import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.createJoin
import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.joinWait
import static org.assertj.core.api.Assertions.assertThat
import static org.junit.Assert.assertEquals
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED

@SqlGroup([@Sql(value = CLEAR_DATABASE_SCRIPT_PATH,
        config = @SqlConfig(transactionMode = ISOLATED),
        executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = CLEAR_DATABASE_SCRIPT_PATH,
                config = @SqlConfig(transactionMode = ISOLATED),
                executionPhase = AFTER_TEST_METHOD)])
@EnableAsync
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerSpockItTest extends Specification {

    @Rule
    public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule()
    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP)

    @Autowired
    UserRepository userRepository
    @Autowired
    TestRestTemplate restTemplate
    @LocalServerPort
    private int port

    @Unroll
    @BMUnitConfig(verbose = true, bmunitVerbose = true)
    @BMRules(rules = [
            @BMRule(name = "signal thread waiting for mutex \"UserControllerSpockItTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation\"",
                    targetClass = "com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.services.MailService",
                    targetMethod = "handleNewUserEvent(com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.NewUserEvent)",
                    targetLocation = "AT EXIT",
                    action = "joinEnlist(\"UserControllerSpockItTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation\")")]
    )
    def "should send mail message and wait until async operation is completed"() throws IOException, MessagingException {
        given:
            assertThat(userRepository.findByEmail(expectedEmail)).isNull();
            UserDto dto = new UserDto().setEmail(expectedEmail).setPassword("XXX");
            createJoin("UserControllerSpockItTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation", 1);
            assertEquals(0, greenMail.getReceivedMessages().length);

        when:
            UserDto responseEntity = restTemplate.postForObject(new URI("http://localhost:" + port + "/users"), (Object) dto, UserDto.class);
            joinWait("UserControllerSpockItTest.shouldCreateNewUserAndSendMailMessageInAsyncOperation", 1, GROOVY_TEST_ASYNC_OPERATION_TIMEOUT);

        then:
            assertThat(userRepository.findByEmail(expectedEmail)).isNotNull();
            assertThat(greenMail.getReceivedMessages().length).isEqualTo(1);
            assertThat(greenMail.getReceivedMessages()[0].getSubject()).contains("New user");
            assertThat(greenMail.getReceivedMessages()[0].getAllRecipients()[0].toString()).contains(expectedEmail);

        where:
            expectedEmail   << ["szymon.doe@nosuch.domain.com", "john.doe@gmail.com","marry.doe@hotmail.com", "jack.black@aws.eu"]
    }
}

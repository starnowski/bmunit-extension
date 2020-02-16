package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.controllers

import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.concurrent.IApplicationCountDownLatch
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.dto.UserDto
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.repositories.UserRepository
import com.icegreen.greenmail.junit.GreenMailRule
import com.icegreen.greenmail.util.ServerSetupTest
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
import static org.assertj.core.api.Assertions.assertThat
import static org.junit.Assert.assertEquals
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
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
@SpringBootTest(webEnvironment= RANDOM_PORT)
class UserControllerSpockItTest extends Specification {

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP)

    @Autowired
    UserRepository userRepository
    @Autowired
    TestRestTemplate restTemplate
    @LocalServerPort
    private int port
    @Autowired
    private IApplicationCountDownLatch applicationCountDownLatch

    def cleanup() {
        applicationCountDownLatch.mailServiceClearCountDownLatchForHandleNewUserEventMethod()
    }

    @Unroll
    def "should send mail message and wait until async operation is completed, the test e-mail is #expectedEmail"() throws IOException, MessagingException {
        given:
            assertThat(userRepository.findByEmail(expectedEmail)).isNull()
            UserDto dto = new UserDto().setEmail(expectedEmail).setPassword("XXX")
            applicationCountDownLatch.mailServiceResetCountDownLatchForHandleNewUserEventMethod()
            assertEquals(0, greenMail.getReceivedMessages().length)

        when:
            restTemplate.postForObject(new URI("http://localhost:" + port + "/users"), (Object) dto, UserDto.class)
            applicationCountDownLatch.mailServiceWaitForCountDownLatchInHandleNewUserEventMethod(15000)

        then:
            assertThat(userRepository.findByEmail(expectedEmail)).isNotNull()
            assertThat(greenMail.getReceivedMessages().length).isEqualTo(1)
            assertThat(greenMail.getReceivedMessages()[0].getSubject()).contains("New user")
            assertThat(greenMail.getReceivedMessages()[0].getAllRecipients()[0].toString()).contains(expectedEmail)

        where:
            expectedEmail   << ["szymon.doe@nosuch.domain.com", "john.doe@gmail.com","marry.doe@hotmail.com", "jack.black@aws.eu"]
    }
}

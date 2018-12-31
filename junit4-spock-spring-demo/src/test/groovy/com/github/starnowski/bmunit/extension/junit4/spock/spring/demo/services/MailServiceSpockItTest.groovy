package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.services

import com.github.starnowski.bmunit.extension.junit4.rule.BMUnitMethodRule
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.dto.UserDto
import com.icegreen.greenmail.junit.GreenMailRule
import com.icegreen.greenmail.util.ServerSetupTest
import org.jboss.byteman.contrib.bmunit.BMRule
import org.jboss.byteman.contrib.bmunit.BMRules
import org.jboss.byteman.contrib.bmunit.BMUnitConfig
import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlGroup
import spock.lang.Specification

import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

import static com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.DemoTestUtils.CLEAR_DATABASE_SCRIPT_PATH
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
@SpringBootTest
class MailServiceSpockItTest extends Specification {

    @Rule
    public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule()
    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP)

    @Autowired
    MailService tested

    @BMUnitConfig(verbose = true, bmunitVerbose = true)
    @BMRules(rules = [
        @BMRule(name = "signal thread waiting for mutex \"MailServiceItTest.shouldSendMailMessageAndWaitForMailAsyncOperationComplete\"",
                targetClass = "com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.services.MailService",
                targetMethod = "handleNewUserEvent(com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.NewUserEvent)",
                targetLocation = "AT EXIT",
                action = "joinEnlist(\"MailServiceItTest.shouldSendMailMessageAndWaitForMailAsyncOperationComplete\")")]
    )
    def "should send mail message and wait until async operation is completed"() throws IOException, MessagingException {
        given:
            String verificationHash = UUID.randomUUID().toString();
            String expectedRecipient = "john.doe@gmail.com";
            UserDto dto = new UserDto();
            dto.setEmail(expectedRecipient);
            createJoin("MailServiceItTest.shouldSendMailMessageAndWaitForMailAsyncOperationComplete", 1);
            assertEquals(0, greenMail.getReceivedMessages().length);

        when:
            tested.sendMessageToNewUser(dto, verificationHash);
            int mailWithNewSendingStatusBeforeAsyncOperationCount = greenMail.getReceivedMessages().length;
            joinWait("MailServiceItTest.shouldSendMailMessageAndWaitForMailAsyncOperationComplete", 1, 5000);

        then:
            assertThat(mailWithNewSendingStatusBeforeAsyncOperationCount).isZero();
            assertThat(greenMail.getReceivedMessages().length).isEqualTo(1);
            assertThat((String) greenMail.getReceivedMessages()[0].getContent()).contains(verificationHash);
            assertThat(greenMail.getReceivedMessages()[0].getSubject()).contains("New user");
            assertThatMailContainsSingleRecipientis(greenMail.getReceivedMessages()[0], expectedRecipient);
    }

    private void assertThatMailContainsSingleRecipientis(MimeMessage mimeMessage, String recipient) throws MessagingException {
        assertThat(mimeMessage.getAllRecipients()).hasSize(1);
        assertThat(mimeMessage.getAllRecipients()[0].toString()).contains(recipient);
    }
}

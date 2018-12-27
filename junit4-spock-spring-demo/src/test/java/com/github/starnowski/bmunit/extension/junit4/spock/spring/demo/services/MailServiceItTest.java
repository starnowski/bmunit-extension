package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.services;

import com.github.starnowski.bmunit.extension.junit4.rule.BMUnitMethodRule;
import com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.dto.UserDto;
import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.jboss.byteman.contrib.bmunit.BMUnitConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.UUID;

import static com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.DemoTestUtils.CLEAR_DATABASE_SCRIPT_PATH;
import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = CLEAR_DATABASE_SCRIPT_PATH,
        config = @SqlConfig(transactionMode = ISOLATED),
        executionPhase = BEFORE_TEST_METHOD)
@Sql(value = CLEAR_DATABASE_SCRIPT_PATH,
        config = @SqlConfig(transactionMode = ISOLATED),
        executionPhase = AFTER_TEST_METHOD)
@EnableAsync
public class MailServiceItTest {
    @Rule
    public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule();
    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.SMTP_IMAP);

    @Autowired
    private MailService tested;

    @Test
    @BMUnitConfig(verbose = true, bmunitVerbose = true)
    @BMRules(rules = {
            @BMRule(name = "suspend temporary async operation",
                    targetClass = "com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.services.MailService",
                    targetMethod = "handleNewUserEvent(com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.NewUserEvent)",
                    targetLocation = "AT ENTRY",
                    action = "rendezvous(\"MailServiceItTest.shouldSendSingleMailInAsyncOperation\")")
            ,
            @BMRule(name = "signal thread waiting for mutex \"MailServiceItTest.shouldSendSingleMailInAsyncOperation\"",
                    targetClass = "com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.services.MailService",
                    targetMethod = "handleNewUserEvent(com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util.NewUserEvent)",
                    targetLocation = "AT EXIT",
                    action = "joinEnlist(\"MailServiceItTest.shouldSendSingleMailInAsyncOperation\")")
    })
    public void shouldSendSingleMailInAsyncOperation() throws IOException, MessagingException {
        // given
        greenMail.reset();
        String verificationHash = UUID.randomUUID().toString();
        UserDto dto = new UserDto();
        dto.setEmail("simon.bimbo@gmail.com");
        createJoin("MailServiceItTest.shouldSendSingleMailInAsyncOperation", 1);
        createRendezvous("MailServiceItTest.shouldSendSingleMailInAsyncOperation", 2);
        assertEquals(0, greenMail.getReceivedMessages().length);

        // when
        tested.sendMessageToNewUser(dto, verificationHash);
        int mailWithNewSendingStatusBeforeAsyncOperationCount = greenMail.getReceivedMessages().length;
        rendezvous("MailServiceItTest.shouldSendSingleMailInAsyncOperation");
        joinWait("MailServiceItTest.shouldSendSingleMailInAsyncOperation", 1, 5000);

        // then
        assertThat(mailWithNewSendingStatusBeforeAsyncOperationCount).isZero();
        assertThat(greenMail.getReceivedMessages().length).isEqualTo(1);
        assertThat((String) greenMail.getReceivedMessages()[0].getContent()).contains(verificationHash);
        assertThat(greenMail.getReceivedMessages()[0].getSubject()).contains("New user");
    }
}
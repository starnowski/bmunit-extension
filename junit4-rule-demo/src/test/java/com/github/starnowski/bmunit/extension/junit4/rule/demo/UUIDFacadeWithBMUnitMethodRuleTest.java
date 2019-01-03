package com.github.starnowski.bmunit.extension.junit4.rule.demo;

import com.github.starnowski.bmunit.extension.junit4.rule.BMUnitMethodRule;
import org.jboss.byteman.contrib.bmunit.BMRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UUIDFacadeWithBMUnitMethodRuleTest {

    @Rule
    public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule();

    @Test
    @BMRule(name = "UUIDFacadeWithBMUnitRunnerTest# should return constant value for non deterministic method",
            targetClass = "com.github.starnowski.bmunit.extension.junit4.rule.demo.UUIDFacade",
            targetMethod = "randomUUIDString()",
            targetLocation = "AT EXIT",
            action = "$! = \"XXXX\"")
    public void shouldReturnConstantValueForNonDeterministicMethod()
    {
        // when
        String result = UUIDFacade.randomUUIDString();

        // then
        assertEquals("XXXX", result);
    }
}

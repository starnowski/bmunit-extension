package com.github.starnowski.bmunit.extension.junit4.rule.demo;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(BMUnitRunner.class)
public class UUIDFacadeWithBMUnitRunnerTest {

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
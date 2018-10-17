package st.bmunit.extension.junit4.rule;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.jboss.byteman.contrib.bmunit.BMRules;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static st.bmunit.extension.junit4.rule.Demo.returnOne;
import static st.bmunit.extension.junit4.rule.Demo.returnZero;

public class DemoWithBMUnitMethodRuleTest {

    @Rule
    public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule();

    @Test
    public void shouldReturnZeroWithoutBytecodeModification() {
        // when
        int result = returnZero();

        // then
        assertEquals(0, result);
    }

    @Test
    @BMRule(name = "should return one after bytecode modification",
            targetClass = "st.bmunit.extension.junit4.rule.Demo",
            targetMethod = "returnZero",
            targetLocation = "AT EXIT",
            action = "$! = 1")
    public void shouldReturnOneAfterBytecodeModification() {
        // when
        int result = returnZero();

        // then
        assertEquals(1, result);
    }

    @Test
    public void sumOfResultShouldEqualOneWithoutBytecodeModification() {

        // when
        int result = returnZero() + returnOne();

        // then
        assertEquals(1, result);
    }

    @Test
    @BMRules(rules = {
            @BMRule(name = "method st.bmunit.extension.junit4.rule.Demo#returnZero should return one after bytecode modification",
                    targetClass = "st.bmunit.extension.junit4.rule.Demo",
                    targetMethod = "returnZero",
                    targetLocation = "AT EXIT",
                    action = "$! = 1"),
            @BMRule(name = "method st.bmunit.extension.junit4.rule.Demo#returnOne should return two after bytecode modification",
                    targetClass = "st.bmunit.extension.junit4.rule.Demo",
                    targetMethod = "returnOne",
                    targetLocation = "AT EXIT",
                    action = "$! = 2")})
    public void sumOfResultShouldEqualThreeAfterBytecodeModification() {

        // when
        int result = returnZero() + returnOne();

        // then
        assertEquals(3, result);
    }
}

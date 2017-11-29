package st.bmunit.extension;

import org.jboss.byteman.contrib.bmunit.BMRule;
import org.junit.Rule;
import org.junit.Test;
import st.bmunit.extension.util.IncrementIntValueThread;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static st.bmunit.extension.BMUnitUtils.createJoin;
import static st.bmunit.extension.BMUnitUtils.joinWait;

public class IncrementIntValueThreadWithBMUnitMethodRule {

    @Rule
    public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule();

    @Test
    @BMRule(name = "should w ait until all threads completed",
            targetClass = "st.bmunit.extension.util.IncrementIntValueThread",
            targetMethod = "run",
            targetLocation = "AT EXIT",
            action = "joinEnlist(\"shouldWaitUntilAllThreadsCompleted\")")
    public void shouldWaitUntilAllThreadsCompleted()
    {
        // given
        int expectedCount = 30;
        AtomicInteger atomicInteger = new AtomicInteger(0);
        createJoin("shouldWaitUntilAllThreadsCompleted", expectedCount);

        // when
        for (int i = 0; i < expectedCount; i++)
        {
            new IncrementIntValueThread(atomicInteger).start();
        }
        joinWait("shouldWaitUntilAllThreadsCompleted", expectedCount);

        // then
        assertEquals(expectedCount, atomicInteger.get());
    }
}

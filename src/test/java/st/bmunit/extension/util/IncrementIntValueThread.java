package st.bmunit.extension.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IncrementIntValueThread extends Thread{

    private final AtomicInteger integerValue;


    public IncrementIntValueThread(AtomicInteger integerValue) {
        this.integerValue = integerValue;
        setDaemon(true);
    }

    @Override
    public void run() {
        integerValue.incrementAndGet();
    }
}

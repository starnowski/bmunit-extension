package com.github.starnowski.bmunit.extension.utils

import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.*

class BMUnitUtilsRendezvousMethodsTest extends Specification {

    static final int MAX_THREAD_NUMBER = 5
    static ExecutorService exec = Executors.newFixedThreadPool(MAX_THREAD_NUMBER,
            new ThreadFactory() {
                Thread newThread(Runnable r) {
                    Thread t = Executors.defaultThreadFactory().newThread(r);
                    t.setDaemon(true);
                    return t;
                }
            })

    @Unroll
    def "should create #expectedRendezvous with expected number of threads #expectedThreadsNumber" ()
    {
        given:
            boolean rendezvousCreated = createRendezvous(expectedRendezvous, expectedThreadsNumber)
            int daemonThreadsCount = expectedThreadsNumber - 1
            Runnable[] threads = new Runnable[daemonThreadsCount]
            for (int i = 0; i < daemonThreadsCount; i++)
            {
                threads[i] = new RendezvousConsumer(expectedRendezvous, expectedThreadsNumber)
                exec.execute(threads[i])
            }
            int arrivedThreadsBeforeMainThredReachRendezvous = 0
            int arrivedThreadsAfterMainThredReachRendezvous = 0
            boolean rendezvousNotReached
            boolean rendezvousReached

        when:
            arrivedThreadsBeforeMainThredReachRendezvous = getRendezvous(expectedRendezvous, expectedThreadsNumber)
            rendezvousNotReached = !isRendezvous(expectedThreadsNumber, expectedThreadsNumber)
            rendezvous(expectedThreadsNumber)
            for (int i = 0; i < daemonThreadsCount; i++)
            {
                threads[i].join()
            }
            arrivedThreadsAfterMainThredReachRendezvous = getRendezvous(expectedRendezvous, expectedThreadsNumber)
            rendezvousReached = isRendezvous(expectedThreadsNumber, expectedThreadsNumber)

        then:
            rendezvousCreated

        and: "number of arrived threads should be less then expected threads number before main thread reaches  rendezvous"
            arrivedThreadsBeforeMainThredReachRendezvous < expectedThreadsNumber

        and: "static method #isRendezvous(Object, int) should return false before main thread reaches  rendezvous"
            rendezvousNotReached

        and: "number of arrived threads should be equal to expected threads number after main thread reaches  rendezvous"
            arrivedThreadsAfterMainThredReachRendezvous == expectedThreadsNumber

        and: "static method #isRendezvous(Object, int) should return true after main thread reaches  rendezvous"
            rendezvousReached

        where:
            expectedRendezvous                  |   expectedThreadsNumber
            "BMUnitUtilsRendezvousMethodsTest1" |   2
//            "BMUnitUtilsRendezvousMethodsTest2" |   2
//            "BMUnitUtilsRendezvousMethodsTest3" |   3
//            "BMUnitUtilsRendezvousMethodsTest4" |   3
//            "BMUnitUtilsRendezvousMethodsTest5" |   5
//            "BMUnitUtilsRendezvousMethodsTest6" |   4
    }

    class RendezvousConsumer extends Thread {

        private final String rendezvousIdentifier
        private final int expectedThreads

        RendezvousConsumer(String rendezvousIdentifier, int expectedThreads) {
            this.rendezvousIdentifier = rendezvousIdentifier
            this.expectedThreads = expectedThreads
        }

        void run() {
            rendezvous(rendezvousIdentifier, expectedThreads)
        }
    }
}

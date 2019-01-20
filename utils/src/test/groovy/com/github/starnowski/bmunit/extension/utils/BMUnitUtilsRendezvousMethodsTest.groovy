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
                    Thread t = Executors.defaultThreadFactory().newThread(r)
                    t.setDaemon(true)
                    return t
                }
            })

    @Unroll
    def "should create rendezvous with identifier #expectedRendezvous and expected number of threads #expectedThreadsNumber" ()
    {
        given:
            boolean rendezvousCreated = createRendezvous(expectedRendezvous, expectedThreadsNumber)
            int daemonThreadsCount = expectedThreadsNumber - 1
            Runnable[] threads = new Runnable[daemonThreadsCount]
            for (int i = 0; i < daemonThreadsCount; i++)
            {
                threads[i] = new RendezvousConsumer(expectedRendezvous)
                exec.execute(threads[i])
            }
            int arrivedThreadsBeforeMainThredReachRendezvous
            int arrivedThreadsAfterMainThredReachRendezvous
            boolean rendezvousNotReached
            boolean rendezvousReached

        when:
            arrivedThreadsBeforeMainThredReachRendezvous = getRendezvous(expectedRendezvous, expectedThreadsNumber)
            rendezvousNotReached = isRendezvous(expectedRendezvous, expectedThreadsNumber)
            rendezvous(expectedRendezvous)
            for (int i = 0; i < daemonThreadsCount; i++)
            {
                threads[i].join()
            }
            arrivedThreadsAfterMainThredReachRendezvous = getRendezvous(expectedRendezvous, expectedThreadsNumber)
            rendezvousReached = !isRendezvous(expectedRendezvous, expectedThreadsNumber)

        then:
            rendezvousCreated

        and: "number of arrived threads should be less then expected threads number before main thread reaches  rendezvous"
            arrivedThreadsBeforeMainThredReachRendezvous < expectedThreadsNumber

        and: "static method #isRendezvous(Object, int) should return true before main thread reaches  rendezvous"
            rendezvousNotReached

        and: "static method #getRendezvous(Object, int) should return minus one for deleted rendezvous after main thread reaches rendezvous"
            arrivedThreadsAfterMainThredReachRendezvous == -1

        and: "static method #isRendezvous(Object, int) should return false for deleted rendezvous  after main thread reaches  rendezvous"
            rendezvousReached

        where:
            expectedRendezvous                  |   expectedThreadsNumber
            "BMUnitUtilsRendezvousMethodsTest1" |   2
            "BMUnitUtilsRendezvousMethodsTest2" |   2
            "BMUnitUtilsRendezvousMethodsTest3" |   3
            "BMUnitUtilsRendezvousMethodsTest4" |   3
            "BMUnitUtilsRendezvousMethodsTest5" |   5
            "BMUnitUtilsRendezvousMethodsTest6" |   4
    }

    class RendezvousConsumer extends Thread {

        private final String rendezvousIdentifier

        RendezvousConsumer(String rendezvousIdentifier) {
            this.rendezvousIdentifier = rendezvousIdentifier
        }

        void run() {
            rendezvous(rendezvousIdentifier)
        }
    }
}

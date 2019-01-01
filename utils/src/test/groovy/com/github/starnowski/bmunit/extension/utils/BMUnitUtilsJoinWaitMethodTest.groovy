package com.github.starnowski.bmunit.extension.utils

import spock.lang.Specification

class BMUnitUtilsJoinWaitMethodTest extends Specification {

    def "should suspend thread when expected 'joiner' exists and other thread reached joiner" ()
    {
        given:
            def expectedJoiner = "TEST_1"
            BMUnitUtils.createJoin(expectedJoiner, 1)
            Runnable runnable = {
                BMUnitUtils.joinEnlist(expectedJoiner)
            }
            Thread t = new Thread(runnable)
            t.setDaemon(true)
            t.start()

        when:
            def threadWasSuspended = BMUnitUtils.joinWait(expectedJoiner, 1)

        then:
            threadWasSuspended
    }

    def "should not suspend thread when expected 'joiner' not exists" ()
    {
        given:
            def expectedJoiner = "TEST_2"

        when:
            def threadWasSuspended = BMUnitUtils.joinWait(expectedJoiner, 1)

        then:
            !threadWasSuspended
    }

    def "should suspend thread with expected timeout when expected 'joiner' exists and other thread reached joiner" ()
    {
        given:
            def expectedJoiner = "TEST_3"
            def expectedTimeout = 1000
            BMUnitUtils.createJoin(expectedJoiner, 1)
            Runnable runnable = {
                BMUnitUtils.joinEnlist(expectedJoiner)
            }
            Thread t = new Thread(runnable)
            t.setDaemon(true)
            t.start()
            t.join()

        when:
            def threadWasSuspended = BMUnitUtils.joinWait(expectedJoiner, 1, expectedTimeout)

        then:
            threadWasSuspended
    }

    def "should throw runtime exception when trying to suspend non existing 'joiner' with expected timeout"()
    {
        given:
            def expectedJoiner = "TEST_4"
            def expectedTimeout = 1000
            BMUnitUtils.createJoin(expectedJoiner, 1)

        when:
            BMUnitUtils.joinWait(expectedJoiner, 1, expectedTimeout)

        then:
            def ex = thrown(RuntimeException)
            ex
    }
}

package com.github.starnowski.bmunit.extension.utils

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.createCounter
import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.readCounter

class BMUnitUtilsCounterMethodTest extends Specification {

    def "should create counter with zero value" ()
    {
        given:
            def counterName = "BMUnitUtilsCounterMethodTest#1"

        when:
            def result = createCounter(counterName)

        then:
            result
            readCounter(counterName) == 0
    }

    @Unroll
    def "should create counter '#counterName' with value #expectedValue" ()
    {
        when:
            def result = createCounter(counterName, expectedValue)

        then:
            result
            readCounter(counterName) == expectedValue

        where:
            counterName                         |   expectedValue
            "BMUnitUtilsCounterMethodTest#2_1"  |   5
            "BMUnitUtilsCounterMethodTest#2_2"  |   1
            "BMUnitUtilsCounterMethodTest#2_3"  |   7
            "BMUnitUtilsCounterMethodTest#2_4"  |   2
    }

    @Unroll
    def "should increment counter '#counterName' with initial value #initValue by one" ()
    {
        given:
            createCounter(counterName, initValue)

        when:
        def result = BMUnitUtils.incrementCounter(counterName)

        then:
            result
            readCounter(counterName) == (initValue + 1)

        where:
            counterName                         |   initValue
            "BMUnitUtilsCounterMethodTest#3_1"  |   8
            "BMUnitUtilsCounterMethodTest#3_2"  |   3
            "BMUnitUtilsCounterMethodTest#3_3"  |   7
            "BMUnitUtilsCounterMethodTest#3_4"  |   1
    }
}

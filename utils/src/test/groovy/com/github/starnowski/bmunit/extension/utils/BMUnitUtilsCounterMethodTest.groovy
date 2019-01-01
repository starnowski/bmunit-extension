package com.github.starnowski.bmunit.extension.utils

import spock.lang.Specification
import spock.lang.Unroll

import static com.github.starnowski.bmunit.extension.utils.BMUnitUtils.*

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
        def result = incrementCounter(counterName)

        then:
            result == (initValue + 1)
            readCounter(counterName) == (initValue + 1)

        where:
            counterName                         |   initValue
            "BMUnitUtilsCounterMethodTest#3_1"  |   8
            "BMUnitUtilsCounterMethodTest#3_2"  |   3
            "BMUnitUtilsCounterMethodTest#3_3"  |   7
            "BMUnitUtilsCounterMethodTest#3_4"  |   1
    }

    @Unroll
    def "should increment counter '#counterName' with initial value #initValue by #addedValue" ()
    {
        given:
            createCounter(counterName, initValue)

        when:
            def result = incrementCounter(counterName, addedValue)

        then:
            result == (initValue + addedValue)
            readCounter(counterName) == (initValue + addedValue)

        where:
            counterName                         |   initValue   |   addedValue
            "BMUnitUtilsCounterMethodTest#4_1"  |   8           |   4
            "BMUnitUtilsCounterMethodTest#4_2"  |   3           |   7
            "BMUnitUtilsCounterMethodTest#4_3"  |   7           |   5
            "BMUnitUtilsCounterMethodTest#4_4"  |   1           |   6
    }

    @Unroll
    def "should delete counter '#counterName' with initial value #initValue" ()
    {
        given:
            createCounter(counterName, initValue)

        when:
            def result = deleteCounter(counterName)

        then:
            result

        and: "return zero when counter not exists"
            readCounter(counterName) == 0

        where:
            counterName                         |   initValue
            "BMUnitUtilsCounterMethodTest#5_1"  |   8
            "BMUnitUtilsCounterMethodTest#5_2"  |   3
            "BMUnitUtilsCounterMethodTest#5_3"  |   7
            "BMUnitUtilsCounterMethodTest#5_4"  |   1
    }

    @Unroll
    def "should return false when during trying to delete counter which not exists" ()
    {
        when:
            def result = deleteCounter(counterName)

        then:
            !result

        where:
            counterName                         |   initValue
            "BMUnitUtilsCounterMethodTest#6_1"  |   8
            "BMUnitUtilsCounterMethodTest#6_2"  |   3
            "BMUnitUtilsCounterMethodTest#6_3"  |   7
            "BMUnitUtilsCounterMethodTest#6_4"  |   1
    }

    @Unroll
    def "should decrement counter '#counterName' with initial value #initValue by one" ()
    {
        given:
            createCounter(counterName, initValue)

        when:
            def result = decrementCounter(counterName)

        then:
            result == (initValue - 1)
            readCounter(counterName) == (initValue - 1)

        where:
            counterName                         |   initValue
            "BMUnitUtilsCounterMethodTest#7_1"  |   8
            "BMUnitUtilsCounterMethodTest#7_2"  |   3
            "BMUnitUtilsCounterMethodTest#7_3"  |   7
            "BMUnitUtilsCounterMethodTest#7_4"  |   1
    }
}

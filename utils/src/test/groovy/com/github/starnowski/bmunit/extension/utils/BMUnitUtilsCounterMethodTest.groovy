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
    def "should create counter #counterName with value #expectedValue" ()
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
}

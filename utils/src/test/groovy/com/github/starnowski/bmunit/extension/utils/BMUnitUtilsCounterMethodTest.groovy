package com.github.starnowski.bmunit.extension.utils

import spock.lang.Specification

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
}

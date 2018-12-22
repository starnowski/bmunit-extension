package com.github.starnowski.bmunit.extension.utils

import spock.lang.Specification

class BMUnitUtilsJoinEnlistMethodTest extends Specification {

    def "should join to 'joiner' list when 'joiner' exists" ()
    {
        given:
            def expectedJoiner = "joinEnlistTest1"
            BMUnitUtils.createJoin(expectedJoiner, 1)

        when:
            def threadJoined = BMUnitUtils.joinEnlist(expectedJoiner)

        then:
            threadJoined
    }

    def "should not join to 'joiner' list when 'joiner' not exists" ()
    {
        given:
            def expectedJoiner = "joinEnlistTest2"

        when:
            def threadJoined = BMUnitUtils.joinEnlist(expectedJoiner)

        then:
            !threadJoined
    }
}

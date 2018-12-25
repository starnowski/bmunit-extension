package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util

import com.github.starnowski.bmunit.extension.junit4.rule.BMUnitMethodRule
import org.junit.Rule
import spock.lang.Specification

class RandomHasGeneratorTest extends Specification {

    @Rule
    public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule();

    def tested = new RandomHasGenerator()


}

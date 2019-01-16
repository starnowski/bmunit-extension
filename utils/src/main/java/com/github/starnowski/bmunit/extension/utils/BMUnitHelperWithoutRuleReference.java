package com.github.starnowski.bmunit.extension.utils;

import org.jboss.byteman.rule.helper.Helper;

class BMUnitHelperWithoutRuleReference extends Helper{
    BMUnitHelperWithoutRuleReference() {
        super(null);
    }

    @Override
    public String toString() {
        return "BMUnitHelperWithoutRuleReference";
    }
}

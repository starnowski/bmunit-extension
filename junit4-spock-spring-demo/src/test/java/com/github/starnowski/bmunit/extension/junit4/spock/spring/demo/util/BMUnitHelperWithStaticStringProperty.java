package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util;

import org.jboss.byteman.rule.Rule;
import org.jboss.byteman.rule.helper.Helper;

public class BMUnitHelperWithStaticStringProperty extends Helper {

    private static String staticStringProperty;

    protected BMUnitHelperWithStaticStringProperty(Rule rule) {
        super(rule);
    }

    public String getStaticStringProperty() {
        return staticStringProperty;
    }

    public static void setStaticStringProperty(String staticStringProperty) {
        BMUnitHelperWithStaticStringProperty.staticStringProperty = staticStringProperty;
    }
}

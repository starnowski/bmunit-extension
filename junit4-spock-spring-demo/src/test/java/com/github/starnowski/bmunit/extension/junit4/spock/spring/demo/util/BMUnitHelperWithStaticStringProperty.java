package com.github.starnowski.bmunit.extension.junit4.spock.spring.demo.util;

import com.github.starnowski.bmunit.extension.utils.BMUnitHelperWithoutRuleReference;

public class BMUnitHelperWithStaticStringProperty extends BMUnitHelperWithoutRuleReference {

    private static String staticStringProperty;

    public String getStaticStringProperty() {
        return staticStringProperty;
    }

    public static void setStaticStringProperty(String staticStringProperty) {
        BMUnitHelperWithStaticStringProperty.staticStringProperty = staticStringProperty;
    }
}

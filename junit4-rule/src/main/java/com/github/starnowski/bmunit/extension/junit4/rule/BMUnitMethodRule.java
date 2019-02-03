/**
 *     Bmunit-extension, implementation of junit4 rule for library BMUnit.
 *     Copyright (C) 2019  Szymon Tarnowski
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 */
package com.github.starnowski.bmunit.extension.junit4.rule;

import org.jboss.byteman.contrib.bmunit.*;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.lang.reflect.Method;

public class BMUnitMethodRule implements MethodRule{

    private final BMRunnerUtil BMRunnerUtil = new BMRunnerUtil();

    public Statement apply(Statement statement, FrameworkMethod method, Object target) {
        statement = this.addMethodSingleRuleLoader(statement, method, target.getClass());
        statement = this.addMethodMultiRuleLoader(statement, method, target.getClass());
        statement = this.addMethodConfigLoader(statement, method, target.getClass());
        return statement;
    }

    private Statement addMethodSingleRuleLoader(final Statement statement, FrameworkMethod method, final Class testClass) {
        BMRule annotation = method.getAnnotation(BMRule.class);
        if (annotation == null) {
            return statement;
        } else {
            final String name = method.getName();
            BMRunnerUtil var10000 = this.BMRunnerUtil;
            final String script = org.jboss.byteman.contrib.bmunit.BMRunnerUtil.constructScriptText(new BMRule[]{annotation});
            return new Statement() {
                public void evaluate() throws Throwable {
                    BMUnit.loadScriptText(testClass, name, script);

                    try {
                        statement.evaluate();
                    } finally {
                        BMUnit.unloadScriptText(testClass, name);
                    }

                }
            };
        }
    }

    private Statement addMethodMultiRuleLoader(final Statement statement, FrameworkMethod method, final Class testClass) {
        BMRules annotation = method.getAnnotation(BMRules.class);
        if (annotation == null) {
            return statement;
        } else {
            final String name = method.getName();
            BMRunnerUtil var10000 = this.BMRunnerUtil;
            final String script = org.jboss.byteman.contrib.bmunit.BMRunnerUtil.constructScriptText(annotation.rules());
            return new Statement() {
                public void evaluate() throws Throwable {
                    BMUnit.loadScriptText(testClass, name, script);

                    try {
                        statement.evaluate();
                    } finally {
                        BMUnit.unloadScriptText(testClass, name);
                    }

                }
            };
        }
    }

    private Statement addMethodConfigLoader(final Statement statement, FrameworkMethod method, final Class testClass) {
        final BMUnitConfig classAnnotation = (BMUnitConfig) testClass.getAnnotation(BMUnitConfig.class);
        final BMUnitConfig annotation = method.getAnnotation(BMUnitConfig.class);
        final Method testMethod = method.getMethod();
        return new Statement() {
            public void evaluate() throws Throwable {
                BMUnitConfigState.pushConfigurationState(classAnnotation, testClass);
                BMUnitConfigState.pushConfigurationState(annotation, testMethod);

                try {
                    statement.evaluate();
                } finally {
                    BMUnitConfigState.popConfigurationState(testMethod);
                    BMUnitConfigState.popConfigurationState(testClass);
                }

            }
        };
    }
}

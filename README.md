# bmunit-extension

[![Build Status](https://travis-ci.org/starnowski/bmunit-extension.svg?branch=master)](https://travis-ci.org/starnowski/bmunit-extension)
[![codecov](https://codecov.io/gh/starnowski/bmunit-extension/branch/master/graph/badge.svg)](https://codecov.io/gh/starnowski/bmunit-extension)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.starnowski.bmunit.extension/junit4-rule.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.starnowski.bmunit.extension%22%20AND%20a:%22junit4-rule%22)
![Java Native Mechanisms Approach CI](https://github.com/starnowski/bmunit-extension/workflows/Java%20Native%20Mechanisms%20Approach%20CI/badge.svg)

Support of junit4 rule for the [bmunit](https://developer.jboss.org/wiki/BMUnitUsingBytemanWithJUnitOrTestNGFromMavenAndAnt) library.

* [Introduction](#introduction)
* [How to attach project](#how-to-attach-project)
  * [Dependencies for "junit4-rule"](#dependencies-for-junit4-rule)
  * [Dependencies for "utils"](#dependencies-for-utils)
  * [Build project](#build-project)
* [How to use project](#how-to-use-project)
  * [How to use "junit4-rule" module](#how-to-use-junit4-rule-module)
  * [How to use  "utils" module](#how-to-use-utils-module)
  * [Demonstrational tests](#demonstrational-tests)
* [Other maven repositories](https://github.com/starnowski/bmunit-extension/wiki/Maven-repositories)
* [Issues](#issues)

# Introduction
Project contains junit4 rule which allows integration with [byteman](http://byteman.jboss.org/) framework and use it in junit and spock tests.
Enables to use bmunit library in test executed by SpringRunner and Spock tests runner.
It also contains types which allows to operate on mechanisms like counters, rendezvous, joins in the tests methods.
Those types can also be used with the JUnit 5 library. Please read [how to use the "utils" module](#how-to-use-utils-module).

[how-to-attach-project]: #how-to-attach-project
# How to attach project

[dependencies-for-junit4-rule]: #dependencies-for-junit4-rule
## Dependencies for "junit4-rule"
```xml
        <dependency>
            <groupId>com.github.starnowski.bmunit.extension</groupId>
            <artifactId>junit4-rule</artifactId>
            <version>1.0.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.byteman</groupId>
            <artifactId>byteman-bmunit</artifactId>
            <version>3.0.10</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.byteman</groupId>
            <artifactId>byteman</artifactId>
            <version>3.0.10</version>
            <scope>test</scope>
        </dependency>
```

[dependencies-for-utils]: #dependencies-for-utils
## Dependencies for "utils"
```xml
        <dependency>
            <groupId>com.github.starnowski.bmunit.extension</groupId>
            <artifactId>utils</artifactId>
            <version>1.0.1</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.byteman</groupId>
            <artifactId>byteman</artifactId>
            <version>3.0.10</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.byteman</groupId>
            <artifactId>byteman-submit</artifactId>
            <version>3.0.10</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.jboss.byteman</groupId>
            <artifactId>byteman-install</artifactId>
            <version>3.0.10</version>
            <scope>test</scope>
        </dependency>
```

[build-project]: #build-project 
## Build project
Build parent project with maven
```sh
mvn clean install
```

[how-to-use-project]: #how-to-use-project
# How to use project

## How to use "junit4-rule" module  
To have ability to use anotations " org.jboss.byteman.contrib.bmunit.BMUnitConfig", "org.jboss.byteman.contrib.bmunit.BMRule" and "org.jboss.byteman.contrib.bmunit.BMRules" add the object of "com.github.starnowski.bmunit.extension.junit4.rule.BMUnitMethodRule" type into the test class.

```java
import com.github.starnowski.bmunit.extension.junit4.rule.BMUnitMethodRule;
import org.junit.Rule;

...

@Rule
public BMUnitMethodRule bmUnitMethodRule = new BMUnitMethodRule();

```

>Project contains few modules with [tests](#demonstrational-tests) which demonstrates how to use the BMUnitMethodRule rule with junit4 tests and spock framework tests:

## How to use "utils" module 

Module "utils" contains object type "com.github.starnowski.bmunit.extension.utils.BMUnitUtils" which contains static methods. Methods allows to operate on mechanism like counters, rendezvous, joins. Thanks to this methods you can manipulate on those objects from tests level
The module was also tested for compatibility with the Byteman in version 4.10.0. It means that the module can be used together with the JUnit 5 library.
>The project contains few modules with [tests](#demonstrational-tests) which demonstrates how to use static methods of type BMUnitUtils with JUnit 4, JUnit5 tests and Spock framework tests

[demonstrational-tests]: #demonstrational-tests
## Demonstrational tests
* Module "junit4-rule-demo" contains use case which demonstrate how use BMUnitMethodRule without BMUnitRunner
  * [JUnit4 test without any runner](https://github.com/starnowski/bmunit-extension/blob/master/junit4-rule-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/rule/demo/UUIDFacadeWithBMUnitMethodRuleTest.java)
* Module "junit4-rule-utils-demo" contains use cases which demonstrate how use BMUnitMethodRule without BMUnitRunner. It shows also usage of components from module "utils"
  * [JUnit4 test without any runner and which use "joins" and "counters" mechanisms](https://github.com/starnowski/bmunit-extension/blob/master/junit4-rule-utils-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/rule/utils/demo/IncrementIntValueThreadWithBMUnitMethodRuleTest.java)
* Module "junit4-spock-spring-demo" contains use cases for junit and spock framework tests which use integration with spring framework
  * [JUnit4 test with SpringRunner which use restTemplate component and "joins" mechanism](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/controllers/UserControllerTest.java)
  * [JUnit4 tests with SpringRunner which use "joins" and "rendezvous" mechanisms to test asynchronous operations](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/services/MailServiceItTest.java)
  * [Spock test which use BMUnitMethodRule to override static method](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/groovy/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/util/RandomHashGeneratorTest.groovy)
  * [Spock test with SpringRunner which use "joins" mechanism](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/groovy/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/services/MailServiceSpockItTest.groovy)
  * [Spock test with SpringRunner which use restTemplate component and "joins" mechanism](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/groovy/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/controllers/UserControllerSpockItTest.groovy)
  * [JUnit5 test which use restTemplate component and "joins" mechanism](https://github.com/starnowski/bmunit-extension/blob/master/junit5-spring-demo/src/test/java/com/github/starnowski/bmunit/extension/junit5/spring/demo/controllers/UserControllerTest.java)

# Issues
* If you facing any issues related with project usage please check on [wiki page](https://github.com/starnowski/bmunit-extension/wiki/FAQ
) if there is already a solution for your problem
* Any new issues please report in GitHub site

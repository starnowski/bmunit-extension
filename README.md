# bmunit-extension

[![Build Status](https://travis-ci.org/starnowski/bmunit-extension.svg?branch=master)](https://travis-ci.org/starnowski/bmunit-extension)

Support of junit4 test rule to bmunit framework.

* [Introduction](#introduction)
* [How to attach project](#how-to-attach-project)
  * [Build project](#build-project)
  * [Dependencies for "junit4-rule"](#dependencies-for-junit4-rule)
  * [Dependencies for "utils"](#dependencies-for-utils)
* [How to use project](#how-to-use-project)
  * [How to use "junit4-rule" module](#how-to-use-junit4-rule-module)
  * [How to use  "utils" module](#how-to-use-utils-module)
  * [Demonstrational tests](#demonstrational-tests)

# Introduction
Project contains junit4 rule which allows integration with byteman framework and use it in junit and spock tests.
Enables to use bmunit library in test executed by SpringRunner and Spock tests runner.
It also contains types which allows to operate on mechanisms like counters, rendezvous, joins in the tests methods.

[how-to-attach-project]: #how-to-attach-project
# How to attach project
[build-project]: #build-project 
## Build project
Build parent project with maven
```sh
mvn clean install
```

[dependencies-for-junit4-rule]: #dependencies-for-junit4-rule
## Dependencies for "junit4-rule"
```xml
        <dependency>
            <groupId>com.github.starnowski.bmunit.extension</groupId>
            <artifactId>junit4-rule</artifactId>
            <version>0.2.0-SNAPSHOT</version>
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
>The module "com.github.starnowski.bmunit.extension:junit4-rule" is not deployed to any maven public repository yet.
This means that you have to localy [build project](#build-project) by your own. 

[dependencies-for-utils]: #dependencies-for-utils
## Dependencies for "utils"
```xml
        <dependency>
            <groupId>com.github.starnowski.bmunit.extension</groupId>
            <artifactId>utils</artifactId>
            <version>0.2.0-SNAPSHOT</version>
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
>The module "com.github.starnowski.bmunit.extension:utils" is not deployed to any maven public repository yet.
This means that you have to localy [build project](#build-project) by your own. 

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

>Project contains few modules with [tests](#demonstrational-tests) which demonstrates how to use static methods of type BMUnitUtils with junit4 tests and spock framework tests:

[demonstrational-tests]: #demonstrational-tests
## Demonstrational tests
* Module "junit4-rule-demo" contains use case which demonstrate how use BMUnitMethodRule without BMUnitRunner
  * [Junit4 test without any runner](https://github.com/starnowski/bmunit-extension/blob/master/junit4-rule-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/rule/demo/UUIDFacadeWithBMUnitMethodRuleTest.java)
* Module "junit4-rule-utils-demo" contains use cases which demonstrate how use BMUnitMethodRule without BMUnitRunner. It shows also usage of components from module "utils"
  * [Junit4 test without any runner and which use "joins" and "counters" mechanisms](https://github.com/starnowski/bmunit-extension/blob/master/junit4-rule-utils-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/rule/utils/demo/IncrementIntValueThreadWithBMUnitMethodRuleTest.java)
* Module "junit4-spock-spring-demo" contains use cases for junit and spock framework tests which use integration with spring framework
  * [Junit4 test with SpringRunner which use restTemplate component and "joins" mechanism](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/controllers/UserControllerTest.java)
  * [Junit4 tests with SpringRunner which use "joins" and "rendezvous" mechanisms to test asynchronous operations](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/services/MailServiceItTest.java)
  * [Spock test which use BMUnitMethodRule to override static method](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/groovy/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/util/RandomHashGeneratorTest.groovy)
  * [Spock test with SpringRunner which use "joins" mechanism](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/groovy/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/services/MailServiceSpockItTest.groovy)
  * [Spock test with SpringRunner which use restTemplate component and "joins" mechanism](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/groovy/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/controllers/UserControllerSpockItTest.groovy)

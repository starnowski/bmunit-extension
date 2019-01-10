# bmunit-extension

[![Build Status](https://travis-ci.org/starnowski/bmunit-extension.svg?branch=master)](https://travis-ci.org/starnowski/bmunit-extension)

Support of junit4 test rule to bmunit framework.

* [How to attach project](#attach-project)
  * [Build project](#build-project)
  * [Dependencies for "junit4-rule"](#dependencies-for-junit4-rule)
  * [Dependencies for "utils"](#dependencies-for-utils)
* [How to use project](#use-project)
  * [How to use "junit4-rule" module](#how-to-use-junit4-rule-module)
  * [How to use  "utils" module](#how-to-use-utils-module)
  * [Demonstrational tests](#demonstrational-tests)

Project contains junit4 rule which allows integration with byteman framework and use it in junit and spock tests.
Enables to use bmunit library in test executed by SpringRunner and Spock tests runner.

[attach-project]: #attach-project
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

[use-project]: #use-project
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

Project contains few modules with [tests](#demonstrational-tests) which demonstrates how to use the BMUnitMethodRule rule with junit4 tests and spock framework tests:

## How to use "utils" module 

Module "utils" contains two components:
* Object type "com.github.starnowski.bmunit.extension.utils.BMUnitHelperWithoutRuleReference" which extends type "org.jboss.byteman.rule.helper.Helper" which doesn't require  the rule object for initialize
* Object type "com.github.starnowski.bmunit.extension.utils.BMUnitUtils" which contains static methods. Methods allows to operate on mechanism like counters, rendezvous, joins. Thanks to this methods you can manipulate on those objects from tests level

[demonstrational-tests]: #demonstrational-tests
## Demonstrational tests
* [Junit4 test without any runner (junit4-rule)](https://github.com/starnowski/bmunit-extension/blob/master/junit4-rule-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/rule/demo/UUIDFacadeWithBMUnitMethodRuleTest.java)
* [Junit4 test with SpringRunner which use restTemplate component and "joins" mechanism (junit4-rule, utils)](https://github.com/starnowski/bmunit-extension/blob/master/junit4-spock-spring-demo/src/test/java/com/github/starnowski/bmunit/extension/junit4/spock/spring/demo/controllers/UserControllerTest.java)
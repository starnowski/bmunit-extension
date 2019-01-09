# bmunit-extension

[![Build Status](https://travis-ci.org/starnowski/bmunit-extension.svg?branch=master)](https://travis-ci.org/starnowski/bmunit-extension)

Support of junit4 test rule to bmunit framework.

[Build project](#build-project)

[Dependencies for "junit4-rule"](#dependencies-for-junit4-rule)

[Dependencies for "utils"](#dependencies-for-utils)

Project contains junit4 rule which allows integration with byteman framework and use it in junit and spock tests.
Enables to use bmunit library in test executed by SpringRunner and Spock tests runner.

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
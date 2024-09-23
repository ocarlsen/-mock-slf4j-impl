# mock-slf4j-impl

[![Maven Central](https://img.shields.io/maven-central/v/com.ocarlsen.test/mock-slf4j-impl.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.ocarlsen.test/mock-slf4j-impl)
[![Build](https://github.com/ocarlsen/mock-slf4j-impl/actions/workflows/build.yml/badge.svg)](https://github.com/ocarlsen/mock-slf4j-impl/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_mock-slf4j-impl&metric=alert_status)](https://sonarcloud.io/dashboard?id=ocarlsen_mock-slf4j-impl)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=ocarlsen_mock-slf4j-impl&metric=coverage)](https://sonarcloud.io/dashboard?id=ocarlsen_mock-slf4j-impl)

This library is useful for testing your logging. 
It is an SLF4J 2.0.x implementation with mock Loggers backed by Mockito.
It uses the new [Service Loader](https://www.slf4j.org/faq.html#changesInVersion200) mechanism.

(If you still need old SLF4J 1.x implementation with `StaticLoggerBinder`,
it is available as version [1.2.1](https://repo.maven.apache.org/maven2/com/ocarlsen/test/mock-slf4j-impl/1.2.1/).)


Visit the [GitHub Pages](https://ocarlsen.github.io/mock-slf4j-impl/) site for more.

## Dependency Information

### Maven

```xml
    <dependency>
        <groupId>com.ocarlsen.test</groupId>
        <artifactId>mock-slf4j-impl</artifactId>
        <version>2.0.1-SNAPSHOT</version>
        <scope>test</scope>
    </dependency>
```

### Gradle

    compile 'com.ocarlsen.test:mock-slf4j-impl:2.0.1-SNAPSHOT'

## Example Code

Consider the class you wish to test:

```java
    class MyLoggingClass {

        private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

        public void loggingMethod() {
            logger.debug("this is a debug message");
            logger.info("this is an info message");
            logger.warn("this is a warn message");
            logger.error("this is an error message");
        }
    }
```

To confirm the log events, get the mock from the factory and test like this:

```java
    @Test
    public void testLogging() {

        // Given
        final MyLoggingClass loggingInstance = new MyLoggingClass();

        // When
        loggingInstance.loggingMethod();

        // Then
        final Logger mockLogger = LoggerFactory.getLogger("MyLoggingClass");

        final InOrder inOrder = inOrder(mockLogger);
        inOrder.verify(mockLogger).debug("this is a debug message");
        inOrder.verify(mockLogger).info("this is an info message");
        inOrder.verify(mockLogger).warn("this is a warn message");
        inOrder.verify(mockLogger).error("this is an error message");
        inOrder.verifyNoMoreInteractions();
    }
```

This example is demonstrated in
[ExampleTest](https://github.com/ocarlsen/mock-slf4j-impl/blob/develop/src/test/java/com/ocarlsen/test/ExampleTest.java).


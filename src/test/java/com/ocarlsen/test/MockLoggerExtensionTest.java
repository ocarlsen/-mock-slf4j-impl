package com.ocarlsen.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.lang.System.LoggerFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionConfigurationException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class MockLoggerExtensionTest {

    @Mock
    private ExtensionContext extensionContext;
    @Mock
    private org.junit.platform.commons.logging.Logger extensionLogger;

    private MockLoggerExtension extension;
    private Logger firstLogger;
    private Logger secondLogger;

    @BeforeEach
    void setUp() {
        var loggerFactory = new MockLoggerFactory();
        extension = new MockLoggerExtension(loggerFactory);
        firstLogger = loggerFactory.getLogger("first");
        secondLogger = loggerFactory.getLogger("second");
    }

    @DisplayName("Initialize a logger finder on \"before all\" step")
    @Test
    void initLoggerFinderOnBeforeAll() {
        assertDoesNotThrow(() -> new MockLoggerExtension());
    }

    @DisplayName("Unknown logger finder")
    @Test
    void unknownLoggerFinder() {
        try (var loggerFactory = mockStatic(LoggerFactory.class)) {
            loggerFactory.when(LoggerFactory::getILoggerFactory).thenReturn(mock(ILoggerFactory.class));

            var exception = assertThrows(ExtensionConfigurationException.class, MockLoggerExtension::getMockLoggerFactory);

            assertEquals("The logger factory is not a MockLoggerFactory", exception.getMessage());
        }
    }

    @DisplayName("Clean and reset loggers after each test")
    @Test
    void resetLoggersAfterEachTest() {
        firstLogger.info("test message");
        secondLogger.info("another test message");

        assertDoesNotThrow(() -> extension.afterEach(extensionContext));

        verifyNoInteractions(extensionContext);
        verifyNoInteractions(firstLogger);
        verifyNoInteractions(secondLogger);
    }

    @DisplayName("Clean and reset loggers before each test")
    @Test
    void resetLoggersBeforeEachTest() {
        firstLogger.info("test message");
        secondLogger.info("another test message");

        assertDoesNotThrow(() -> extension.beforeEach(extensionContext));

        verifyNoInteractions(extensionContext);
        verifyNoInteractions(firstLogger);
        verifyNoInteractions(secondLogger);
    }
}
package com.ocarlsen.test;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionConfigurationException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

public class MockLoggerExtension implements AfterEachCallback, BeforeEachCallback {

    private final MockLoggerFactory loggerFactory;

    static MockLoggerFactory getMockLoggerFactory() {
        var loggerFactory = LoggerFactory.getILoggerFactory();
        if (loggerFactory instanceof MockLoggerFactory) {
            return (MockLoggerFactory) loggerFactory;
        } else {
            throw new ExtensionConfigurationException("The logger factory is not a MockLoggerFactory");
        }
    }

    public MockLoggerExtension() {
        this(getMockLoggerFactory());
    }

    MockLoggerExtension(MockLoggerFactory loggerFactory) {
        this.loggerFactory = loggerFactory;
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        loggerFactory.cleanAndResetMockLoggers();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        loggerFactory.cleanAndResetMockLoggers();
    }
}

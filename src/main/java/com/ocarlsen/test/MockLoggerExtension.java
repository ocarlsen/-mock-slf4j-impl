package com.ocarlsen.test;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionConfigurationException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

/**
 * jUnit extension to clean and reset mock loggers. Clean and reset mock loggers before and after tests. You are still
 * able to run tasks in {@link org.junit.jupiter.api.BeforeEach} and {@link org.junit.jupiter.api.AfterEach}.
 */
public class MockLoggerExtension implements AfterEachCallback, BeforeEachCallback {

    private final MockLoggerFactory loggerFactory;

    /**
     * Create an extension.
     */
    public MockLoggerExtension() {
        this(getMockLoggerFactory());
    }

    MockLoggerExtension(MockLoggerFactory loggerFactory) {
        this.loggerFactory = loggerFactory;
    }

    static MockLoggerFactory getMockLoggerFactory() {
        var loggerFactory = LoggerFactory.getILoggerFactory();
        if (loggerFactory instanceof MockLoggerFactory) {
            return (MockLoggerFactory) loggerFactory;
        } else {
            throw new ExtensionConfigurationException("The logger factory is not a MockLoggerFactory");
        }
    }

    /**
     * Clean and reset mock loggers after tests. You are still able to run tasks in
     * {@link org.junit.jupiter.api.AfterEach}.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        loggerFactory.cleanAndResetMockLoggers();
    }

    /**
     * Clean and reset mock loggers before tests. You are still able to run tasks in
     * {@link org.junit.jupiter.api.BeforeEach}.
     *
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        loggerFactory.cleanAndResetMockLoggers();
    }
}

package com.ocarlsen.test;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * http://www.slf4j.org/faq.html#slf4j_compatible
 */
public class MockLoggerFactory implements ILoggerFactory {

    private final Map<String, Logger> nameLoggerMap = new ConcurrentHashMap<>();

    @Override
    public Logger getLogger(final String name) {
        return nameLoggerMap.computeIfAbsent(name, key -> mock(Logger.class));
    }

    /**
     * Clean and reset mock loggers. This can be useful when loggers are reused in tests.
     */
    void cleanAndResetMockLoggers() {
        nameLoggerMap.forEach((loggerName, logger) -> {
            clearInvocations(logger);
            reset(logger);
        });
    }
}

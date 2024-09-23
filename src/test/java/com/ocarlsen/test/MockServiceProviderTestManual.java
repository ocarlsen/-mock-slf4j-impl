package com.ocarlsen.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * Useful test to walk through with debugger and confirm {@link MockServiceProvider} is loaded.
 */
@SuppressWarnings("NewClassNamingConvention")
public class MockServiceProviderTestManual {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().getClass().getSimpleName());

    @Test
    public void serviceLoader() {
        LOGGER.debug("this is a debug message");
        LOGGER.info("this is an info message");
        LOGGER.warn("this is a warn message");
        LOGGER.error("this is an error message");
    }
}
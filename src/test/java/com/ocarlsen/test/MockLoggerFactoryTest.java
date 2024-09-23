package com.ocarlsen.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MockLoggerFactoryTest {

    private final MockLoggerFactory loggerFactory = new MockLoggerFactory();

    @Test
    public void getLogger() {
        final String name = "abc";
        final Logger logger1 = loggerFactory.getLogger(name);
        assertThat(logger1, is(notNullValue()));

        // Confirm its a mock.
        final String msg = "This is a test";
        doNothing().when(logger1).info(msg);
        logger1.info(msg);
        verify(logger1).info(msg);
        verifyNoMoreInteractions(logger1);

        // Confirm same instance each time.
        final Logger logger2 = loggerFactory.getLogger(name);
        assertThat(logger2, is(sameInstance(logger1)));
    }
}
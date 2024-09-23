package com.ocarlsen.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

/**
 * This class was useful in reproducing {@link java.util.ConcurrentModificationException}s
 * before the {@link MockLoggerFactory} was using a {@link java.util.concurrent.ConcurrentHashMap}.
 */
public class ConcurrencyTest {

    private static final int LENGTH = 10;
    private static final boolean USE_LETTERS = true;
    private static final boolean USE_NUMBERS = false;

    private final List<Exception> exceptions = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();

    @BeforeEach
    public void initializeSlf4J() {
        LoggerFactory.getLogger("init");
    }

    @Test
    public void testConcurrency() throws Exception {

        for (int i = 0; i < 100; i++) {
            final String loggerName = RandomStringUtils.random(LENGTH, USE_LETTERS, USE_NUMBERS);
            final Thread t = buildThreadToGetLogger(loggerName);
            t.start();
        }

        // Wait for all threads to finish.
        for (final Thread t : threads) {
            t.join();
        }

        assertThat(exceptions, is(empty()));
    }

    private Thread buildThreadToGetLogger(final String loggerName) {
        final Thread t = new Thread(() -> {
            try {
                LoggerFactory.getLogger(loggerName);
            } catch (final Exception e) {
                exceptions.add(e);
            }
        });

        // Keep track.
        threads.add(t);

        return t;
    }

}

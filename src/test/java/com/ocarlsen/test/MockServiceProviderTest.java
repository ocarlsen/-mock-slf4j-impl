package com.ocarlsen.test;

import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.IMarkerFactory;
import org.slf4j.spi.MDCAdapter;

import static java.util.Objects.isNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class MockServiceProviderTest {

  public MockServiceProvider serviceProvider;

  @BeforeEach
  public void setUp() {
    serviceProvider = new MockServiceProvider();
  }

  @Test
  public void getLoggerFactory() {
    serviceProvider.initialize();

    assertThat(serviceProvider.getLoggerFactory(), is(instanceOf(MockLoggerFactory.class)));
  }

  @Test
  public void getMarkerFactory() {
    serviceProvider.initialize();

    assertThat(serviceProvider.getMarkerFactory(), is(instanceOf(IMarkerFactory.class)));
  }

  @Test
  public void getMDCAdapter() {
    serviceProvider.initialize();

    assertThat(serviceProvider.getMDCAdapter(), is(instanceOf(MDCAdapter.class)));
  }

  @Test
  public void getRequestedApiVersion() {
    assertThat(serviceProvider.getRequestedApiVersion(), startsWith("2.0"));
  }

  @Test
  public void initialize() {
    assumeTrue(isNull(serviceProvider.getLoggerFactory()));
    assumeTrue(isNull(serviceProvider.getMarkerFactory()));
    assumeTrue(isNull(serviceProvider.getMDCAdapter()));

    serviceProvider.initialize();

    assertNotNull(serviceProvider.getLoggerFactory());
    assertNotNull(serviceProvider.getMarkerFactory());
    assertNotNull(serviceProvider.getMDCAdapter());
  }
}

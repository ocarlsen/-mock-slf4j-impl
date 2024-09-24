package com.ocarlsen.test;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MDCAdapter;
import org.slf4j.spi.SLF4JServiceProvider;

/**
 * The mock logger provider.
 */
public class MockServiceProvider implements SLF4JServiceProvider {

  /**
   * Declare the version of the SLF4J API this implementation is compiled against. The value of this
   * field is modified with each major release.
   */
  public static final String REQUESTED_API_VERSION = "2.0"; // !final

  private MockLoggerFactory loggerFactory;
  private IMarkerFactory markerFactory;
  private MDCAdapter contextAdapter;

  @Override
  public ILoggerFactory getLoggerFactory() {
    return loggerFactory;
  }

  @Override
  public IMarkerFactory getMarkerFactory() {
    return markerFactory;
  }

  @Override
  public MDCAdapter getMDCAdapter() {
    return contextAdapter;
  }

  @Override
  public String getRequestedApiVersion() {
    return REQUESTED_API_VERSION;
  }

  @Override
  public void initialize() {
    loggerFactory = new MockLoggerFactory();
    markerFactory = new BasicMarkerFactory();
    contextAdapter = new BasicMDCAdapter();
  }

}

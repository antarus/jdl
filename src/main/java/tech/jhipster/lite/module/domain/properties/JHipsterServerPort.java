package tech.jhipster.lite.module.domain.properties;

import tech.jhipster.lite.error.domain.Assert;

public class JHipsterServerPort {

  private static final int DEFAULT_PORT = 8080;

  private final int serverPort;

  public JHipsterServerPort(Integer port) {
    serverPort = buildPort(port);

    Assert.field("serverPort", serverPort).min(1).max(65_535);
  }

  private int buildPort(Integer port) {
    if (port == null) {
      return DEFAULT_PORT;
    }

    return port.intValue();
  }

  public int get() {
    return serverPort;
  }

  public String stringValue() {
    return String.valueOf(serverPort);
  }
}

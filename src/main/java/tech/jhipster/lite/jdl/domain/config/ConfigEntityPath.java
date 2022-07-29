package tech.jhipster.lite.jdl.domain.config;

import org.apache.commons.lang3.StringUtils;

public record ConfigEntityPath(String entityPath) {
  private static final String DEFAULT_ENTITY_PATH = "infrastructure.secondary.entity";
  public static final ConfigEntityPath DEFAULT = new ConfigEntityPath(DEFAULT_ENTITY_PATH);

  public ConfigEntityPath(String entityPath) {
    this.entityPath = buildDefaultEntityPath(entityPath);
  }

  private String buildDefaultEntityPath(String basePackage) {
    if (StringUtils.isBlank(basePackage)) {
      return DEFAULT_ENTITY_PATH;
    }

    return basePackage.replace('/', '.');
  }

  public String get() {
    return entityPath();
  }

  public String path() {
    return entityPath().replace('.', '/');
  }
}

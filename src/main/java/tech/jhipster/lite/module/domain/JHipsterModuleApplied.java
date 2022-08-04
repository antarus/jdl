package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

import java.time.Instant;

public record JHipsterModuleApplied(JHipsterModuleProperties properties, JHipsterModuleSlug slug, Instant time) {
  public JHipsterModuleApplied {
    Assert.notNull("properties", properties);
    Assert.notNull("slug", slug);
    Assert.notNull("time", time);
  }
}

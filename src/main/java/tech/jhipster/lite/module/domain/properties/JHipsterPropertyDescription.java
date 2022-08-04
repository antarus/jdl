package tech.jhipster.lite.module.domain.properties;

import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.error.domain.Assert;

import java.util.Optional;

public record JHipsterPropertyDescription(String description) {
  public JHipsterPropertyDescription {
    Assert.field("description", description).notBlank().maxLength(100);
  }

  public static Optional<JHipsterPropertyDescription> of(String description) {
    return Optional.ofNullable(description).filter(StringUtils::isNotBlank).map(JHipsterPropertyDescription::new);
  }

  public String get() {
    return description();
  }
}

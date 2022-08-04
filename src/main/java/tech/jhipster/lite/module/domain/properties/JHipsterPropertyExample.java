package tech.jhipster.lite.module.domain.properties;

import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.error.domain.Assert;

import java.util.Optional;

public record JHipsterPropertyExample(String example) {
  public JHipsterPropertyExample {
    Assert.notBlank("example", example);
  }

  public static Optional<JHipsterPropertyExample> of(String example) {
    return Optional.ofNullable(example).filter(StringUtils::isNotBlank).map(JHipsterPropertyExample::new);
  }

  public String get() {
    return example();
  }
}

package tech.jhipster.lite.common.domain;

import java.util.Optional;

public class OptionalUtils {

  private OptionalUtils() {}

  public static Optional<String> notBlank(String value) {
    if (value == null || value.isBlank()) {
      return Optional.empty();
    }
    return Optional.of(value);
  }
}

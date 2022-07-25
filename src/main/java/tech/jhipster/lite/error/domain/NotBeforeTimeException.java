package tech.jhipster.lite.error.domain;

import java.time.Instant;

public class NotBeforeTimeException extends AssertionException {

  private NotBeforeTimeException(String message) {
    super(message);
  }

  public static NotBeforeTimeException notStrictlyBefore(String fieldName, Instant other) {
    return new NotBeforeTimeException(
      new StringBuilder()
        .append("Time in \"")
        .append(fieldName)
        .append("\" must be strictly before ")
        .append(other)
        .append(" but wasn't")
        .toString()
    );
  }

  public static NotBeforeTimeException notBefore(String fieldName, Instant other) {
    return new NotBeforeTimeException(
      new StringBuilder().append("Time in \"").append(fieldName).append("\" must be before ").append(other).append(" but wasn't").toString()
    );
  }
}

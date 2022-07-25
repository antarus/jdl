package tech.jhipster.lite.error.domain;

import java.time.Instant;

public class NotAfterTimeException extends AssertionException {

  private NotAfterTimeException(String message) {
    super(message);
  }

  public static NotAfterTimeException notStrictlyAfter(String fieldName, Instant other) {
    return new NotAfterTimeException(
      new StringBuilder()
        .append("Time in \"")
        .append(fieldName)
        .append("\" must be strictly after ")
        .append(other)
        .append(" but wasn't")
        .toString()
    );
  }

  public static NotAfterTimeException notAfter(String fieldName, Instant other) {
    return new NotAfterTimeException(
      new StringBuilder().append("Time in \"").append(fieldName).append("\" must be after ").append(other).append(" but wasn't").toString()
    );
  }
}

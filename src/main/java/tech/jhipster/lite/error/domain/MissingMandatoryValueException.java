package tech.jhipster.lite.error.domain;

public class MissingMandatoryValueException extends AssertionException {

  private MissingMandatoryValueException(String message) {
    super(message);
  }

  public static MissingMandatoryValueException forBlankValue(String field) {
    return new MissingMandatoryValueException(defaultMessage(field, "blank"));
  }

  public static MissingMandatoryValueException forNullValue(String field) {
    return new MissingMandatoryValueException(defaultMessage(field, "null"));
  }

  public static MissingMandatoryValueException forEmptyValue(String field) {
    return new MissingMandatoryValueException(defaultMessage(field, "empty"));
  }

  private static String defaultMessage(String field, String reason) {
    return new StringBuilder()
      .append("The field \"")
      .append(field)
      .append("\" is mandatory and wasn't set")
      .append(" (")
      .append(reason)
      .append(")")
      .toString();
  }
}

package tech.jhipster.lite.error.domain;

public class StringTooShortException extends AssertionException {

  private StringTooShortException(StringTooShortExceptionBuilder builder) {
    super(builder.message());
  }

  public static StringTooShortExceptionBuilder builder() {
    return new StringTooShortExceptionBuilder();
  }

  static class StringTooShortExceptionBuilder {

    private String value;
    private int minLength;
    private String field;

    private StringTooShortExceptionBuilder() {}

    StringTooShortExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    StringTooShortExceptionBuilder value(String value) {
      this.value = value;

      return this;
    }

    StringTooShortExceptionBuilder minLength(int minLength) {
      this.minLength = minLength;

      return this;
    }

    private String message() {
      return new StringBuilder()
        .append("The value in field \"")
        .append(field)
        .append("\" must be at least ")
        .append(minLength)
        .append(" long but was only ")
        .append(value.length())
        .toString();
    }

    public StringTooShortException build() {
      return new StringTooShortException(this);
    }
  }
}

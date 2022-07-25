package tech.jhipster.lite.error.domain;

public class NumberValueTooHighException extends AssertionException {

  private NumberValueTooHighException(NumberValueTooHighExceptionBuilder builder) {
    super(builder.message());
  }

  public static NumberValueTooHighExceptionBuilder builder() {
    return new NumberValueTooHighExceptionBuilder();
  }

  public static class NumberValueTooHighExceptionBuilder {

    private String field;
    private String maxValue;
    private String value;

    public NumberValueTooHighExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    public NumberValueTooHighExceptionBuilder maxValue(String maxValue) {
      this.maxValue = maxValue;

      return this;
    }

    public NumberValueTooHighExceptionBuilder value(String value) {
      this.value = value;

      return this;
    }

    public String message() {
      return new StringBuilder()
        .append("Value of field \"")
        .append(field)
        .append("\" must be at most ")
        .append(maxValue)
        .append(" but was ")
        .append(value)
        .toString();
    }

    public NumberValueTooHighException build() {
      return new NumberValueTooHighException(this);
    }
  }
}

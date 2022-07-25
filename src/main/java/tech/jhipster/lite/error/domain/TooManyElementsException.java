package tech.jhipster.lite.error.domain;

public class TooManyElementsException extends AssertionException {

  public TooManyElementsException(TooManyElementsExceptionBuilder builder) {
    super(builder.message());
  }

  public static TooManyElementsExceptionBuilder builder() {
    return new TooManyElementsExceptionBuilder();
  }

  public static class TooManyElementsExceptionBuilder {

    private String field;
    private int maxSize;
    private int size;

    public TooManyElementsExceptionBuilder field(String field) {
      this.field = field;

      return this;
    }

    public TooManyElementsExceptionBuilder maxSize(int maxSize) {
      this.maxSize = maxSize;

      return this;
    }

    public TooManyElementsExceptionBuilder size(int size) {
      this.size = size;

      return this;
    }

    public String message() {
      return new StringBuilder()
        .append("Size of collection \"")
        .append(field)
        .append("\" must be at most ")
        .append(maxSize)
        .append(" but was ")
        .append(size)
        .toString();
    }

    public TooManyElementsException build() {
      return new TooManyElementsException(this);
    }
  }
}

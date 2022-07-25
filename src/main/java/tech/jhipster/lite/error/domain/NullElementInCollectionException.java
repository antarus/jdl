package tech.jhipster.lite.error.domain;

public class NullElementInCollectionException extends AssertionException {

  public NullElementInCollectionException(String field) {
    super(message(field));
  }

  private static String message(String field) {
    return new StringBuilder().append("The field \"").append(field).append("\" contains a null element").toString();
  }
}

package tech.jhipster.lite.module.domain.javaproperties;

import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;

import java.util.Collection;
import java.util.List;

public record PropertyValue(Collection<String> values) {
  public PropertyValue(String[] values) {
    this(List.of(values));
  }

  public PropertyValue(Collection<String> values) {
    Assert.field("values", values).noNullElement();

    this.values = JHipsterCollections.immutable(values);
  }

  public Collection<String> get() {
    return values();
  }
}

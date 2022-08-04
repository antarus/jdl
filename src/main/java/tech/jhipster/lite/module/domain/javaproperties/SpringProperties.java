package tech.jhipster.lite.module.domain.javaproperties;

import tech.jhipster.lite.error.domain.Assert;

import java.util.Collection;

public record SpringProperties(Collection<SpringProperty> properties) {
  public SpringProperties {
    Assert.field("properties", properties).notNull().noNullElement();
  }

  public Collection<SpringProperty> get() {
    return properties();
  }
}

package tech.jhipster.lite.entity.domain.field;

import tech.jhipster.lite.error.domain.Assert;

public record FieldName(String name) {
    public FieldName {
        Assert.field("name", name).noWhitespace().maxLength(50);
    }

    public String get() {
        return name();
    }
}

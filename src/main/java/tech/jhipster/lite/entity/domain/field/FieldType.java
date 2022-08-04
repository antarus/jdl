package tech.jhipster.lite.entity.domain.field;

import tech.jhipster.lite.error.domain.Assert;

public record FieldType(String type) {
    public FieldType {
        Assert.field("type", type).noWhitespace();
    }

    public String get() {
        return type();
    }
}

package tech.jhipster.lite.jdl.domain.entity.field;

import tech.jhipster.lite.error.domain.Assert;

public record FieldType(String type) {
    public FieldType {
        Assert.field("type", type).noWhitespace();
    }

    public String get() {
        return type();
    }
}

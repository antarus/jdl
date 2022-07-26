package tech.jhipster.lite.jdl.domain.entity.field;

import tech.jhipster.lite.error.domain.Assert;

public record FieldValidation(String name) {
    public FieldValidation {
        Assert.field("name", name);
    }

    public String get() {
        return name();
    }
}

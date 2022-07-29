package tech.jhipster.lite.jdl.domain.entity.field;

import tech.jhipster.lite.error.domain.Assert;

public class FieldValidation {
    private String name;

    public FieldValidation(String name) {
        Assert.field("name", name);
        this.name = name;
    }

    public String name() {
        return name;
    }

    public String get() {
        return name();
    }
}

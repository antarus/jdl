package tech.jhipster.lite.jdl.domain.entity;

import tech.jhipster.lite.error.domain.Assert;

public record EntityName(String name) {
    public EntityName {
        Assert.field("name", name).noWhitespace().maxLength(50);
    }

    public String get() {
        return name();
    }
}

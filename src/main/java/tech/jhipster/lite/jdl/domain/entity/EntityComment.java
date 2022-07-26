package tech.jhipster.lite.jdl.domain.entity;

import tech.jhipster.lite.error.domain.Assert;

public record EntityComment(String comment) {
    public EntityComment {
        Assert.field("comment", comment).notBlank();
    }

    public String get() {
        return comment();
    }
}

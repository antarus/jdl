package tech.jhipster.lite.jdl.domain.entity.field;

import tech.jhipster.lite.error.domain.Assert;

public record FieldComment(String comment) {
    public FieldComment {
        Assert.field("comment", comment).notBlank();
    }

    public String get() {
        return comment();
    }
}

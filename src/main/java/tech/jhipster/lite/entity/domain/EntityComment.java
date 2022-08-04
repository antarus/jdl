package tech.jhipster.lite.entity.domain;

import tech.jhipster.lite.common.domain.EntityUtils;
import tech.jhipster.lite.error.domain.Assert;

public record EntityComment(String comment) {
    public EntityComment(String comment) {
        this.comment = cleanComment(comment);
    }

    private String cleanComment(String comment) {
        Assert.field("comment", comment).notBlank();
        return EntityUtils.cleanComment(comment);
    }
    public String get() {
        return comment();
    }
}

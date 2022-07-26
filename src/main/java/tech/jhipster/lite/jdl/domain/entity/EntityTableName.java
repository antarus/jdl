package tech.jhipster.lite.jdl.domain.entity;

import tech.jhipster.lite.error.domain.Assert;

public record EntityTableName(String tableName) {
    public EntityTableName {
        Assert.field("tableName", tableName).noWhitespace().maxLength(50);
    }

    public String get() {
        return tableName();
    }
}

package tech.jhipster.lite.jdl.domain.entity;

import tech.jhipster.lite.error.domain.Assert;

public record EntityPackage(String packag) {
    public EntityPackage {
        Assert.field("packag", packag).noWhitespace();
    }

    public String get() {
        return packag();
    }

    public String path() {
        return packag().replace('.', '/');
    }
}

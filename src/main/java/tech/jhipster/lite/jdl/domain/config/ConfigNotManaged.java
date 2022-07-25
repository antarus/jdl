package tech.jhipster.lite.jdl.domain.config;

import tech.jhipster.lite.error.domain.Assert;

public record ConfigNotManaged(String name) {
    public ConfigNotManaged {
        Assert.notNull("name", name);
    }

    public String get() {
        return name();
    }
}

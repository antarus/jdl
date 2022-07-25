package tech.jhipster.lite.jdl.domain.config;

import tech.jhipster.lite.error.domain.Assert;

public record ConfigBaseName(String baseName) {
    public ConfigBaseName {
        Assert.notNull("buildTool", baseName);
    }

    public String get() {
        return baseName();
    }
}

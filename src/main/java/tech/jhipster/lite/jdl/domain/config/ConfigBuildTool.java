package tech.jhipster.lite.jdl.domain.config;

import tech.jhipster.lite.error.domain.Assert;

public record ConfigBuildTool(String buildTool) {
    public ConfigBuildTool {
        Assert.notNull("buildTool", buildTool);
    }

    public String get() {
        return buildTool();
    }
}

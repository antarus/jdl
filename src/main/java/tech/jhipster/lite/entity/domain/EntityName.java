package tech.jhipster.lite.entity.domain;

import org.springframework.util.StringUtils;
import tech.jhipster.lite.error.domain.Assert;

public record EntityName(String name) {
    public EntityName {
        Assert.field("name", name).noWhitespace().maxLength(50);
        name = StringUtils.capitalize(name);
    }

    public String get() {
        return name();
    }
}

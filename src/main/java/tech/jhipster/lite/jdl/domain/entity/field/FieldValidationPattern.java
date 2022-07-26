package tech.jhipster.lite.jdl.domain.entity.field;

import tech.jhipster.lite.error.domain.Assert;

import java.util.regex.Pattern;

public record FieldValidationPattern(String name, Pattern pattern) {
    public FieldValidationPattern {
        Assert.field("name", name).notBlank();
        Assert.notNull("pattern", pattern);
    }


}

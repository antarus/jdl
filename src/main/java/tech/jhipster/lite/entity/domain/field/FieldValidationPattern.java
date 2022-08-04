package tech.jhipster.lite.entity.domain.field;

import tech.jhipster.lite.error.domain.Assert;

import java.util.regex.Pattern;

public class FieldValidationPattern extends FieldValidation{
    private Pattern pattern;
    public FieldValidationPattern(String name, Pattern pattern) {
        super(name);
        Assert.notNull("pattern", pattern);
        this.pattern = pattern;
    }
    public Pattern pattern() {
        return pattern;
    }
}

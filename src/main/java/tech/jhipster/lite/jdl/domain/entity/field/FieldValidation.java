package tech.jhipster.lite.jdl.domain.entity.field;

import tech.jhipster.lite.error.domain.Assert;

import java.util.Objects;

public class FieldValidation {

    public static FieldValidation REQUIRED= new FieldValidation("required");
    public static FieldValidation UNIQUE= new FieldValidation("unique");

    private String name;

    public FieldValidation(String name) {
        Assert.field("name", name);
        this.name = name;
    }

    public String name() {
        return name;
    }

    public String get() {
        return name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldValidation)) return false;
        FieldValidation that = (FieldValidation) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

package tech.jhipster.lite.jdl.domain.entity.field;

import tech.jhipster.lite.error.domain.Assert;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

public class EntityField {

    public static FieldBuilder fieldBuilder() {
        return new FieldBuilder();
    }
    private FieldName name;
    private List<Modifier> modifiers;
    private FieldComment comment;
    private FieldType type;
    private List<FieldValidation>validations;
    private List<FieldValidator>validators;

private boolean isRequired;
private boolean hasValidator;
private boolean hasValidation;

    public boolean isRequired() {
        return isRequired;
    }

    public boolean hasValidator() {
        return hasValidator;
    }

    public boolean hasValidation() {
        return hasValidation;
    }

    public FieldName getName() {
        return name;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public FieldComment getComment() {
        return comment;
    }

    public FieldType getType() {
        return type;
    }

    public List<FieldValidation> getValidations() {
        return validations;
    }

    public List<FieldValidator> getValidators() {
        return validators;
    }

    @Override
    public String toString() {
        return "EntityField{" +
                "name=" + name +
                ", modifiers=" + modifiers +
                ", comment=" + comment +
                ", type=" + type +
                ", validations=" + validations +
                ", validators=" + validators +
                ", isRequired=" + isRequired +
                ", hasValidator=" + hasValidator +
                ", hasValidation=" + hasValidation +
                '}';
    }

    public static final class FieldBuilder {
        private FieldName name;
        private FieldType type;
        private final List<Modifier> modifiers = new ArrayList<>();
        private FieldComment comment;
        private final List<FieldValidation> validations = new ArrayList<>();
        private final List<FieldValidator> validators = new ArrayList<>();

        private FieldBuilder() {
        }

        public FieldBuilder name(FieldName name) {
            this.name = name;
            return this;
        }
        public FieldBuilder addModifiers(Modifier modifiers) {
            Assert.notNull("modifiers", modifiers);
            this.modifiers.add(modifiers);
            return this;
        }

        public FieldBuilder comment(FieldComment comment) {
            this.comment = comment;
            return this;
        }
        public FieldBuilder type(FieldType type) {
            this.type = type;
            return this;
        }
        public FieldBuilder addValidation(FieldValidation validation) {
            Assert.notNull("validation", validation);
            this.validations.add(validation);
            return this;
        }

        public FieldBuilder addValidator(FieldValidator validator) {
            Assert.notNull("validator", validator);
            this.validators.add(validator);
            return this;
        }

        public EntityField build() {
            EntityField entityField = new EntityField();
            entityField.name = this.name;
            entityField.validators = this.validators;
            entityField.hasValidator = !this.validators.isEmpty();
            entityField.hasValidation = !this.validations.isEmpty();
            entityField.isRequired = this.validations.stream().anyMatch(f-> f.equals(FieldValidation.REQUIRED));
            entityField.comment = this.comment;
            entityField.type = this.type;
            if (this.modifiers.isEmpty()){
                this.modifiers.add(Modifier.PRIVATE);
            }
            entityField.modifiers = this.modifiers;
            entityField.validations = this.validations;
            return entityField;
        }
    }
}

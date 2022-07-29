package tech.jhipster.lite.jdl.infrastructure.primary;

import tech.jhipster.lite.jdl.antlr.JdlBaseVisitor;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.config.ConfigBuildTool;
import tech.jhipster.lite.jdl.domain.entity.field.*;

import java.util.regex.Pattern;

public class EntityFieldVisitor {

    public static class EntityFieldJdl extends JdlBaseVisitor<Field> {
        @Override
        public Field visitField(JdlParser.FieldContext ctx) {
            Field.FieldBuilder fieldBuilder = Field.fieldBuilder();
            fieldBuilder.name(new FieldName(ctx.IDENTIFIER(0).getText()));
            if (ctx.comment() != null) {
                String comment = ctx.comment().getText();
                fieldBuilder.comment(new FieldComment(comment));
            }
            if (ctx.FIELD_TYPE() != null) {
                fieldBuilder.type(new FieldType(ctx.FIELD_TYPE().getText()));
            } else {
                fieldBuilder.type(new FieldType(ctx.IDENTIFIER(1).getText()));
            }
            if (ctx.minmax() != null && !ctx.minmax().isEmpty()) {
                ctx.minmax().stream().forEach(minmaxContext -> {
                    fieldBuilder.addValidator( new FieldValidatorVisitor().visitMinmax(minmaxContext));
                });
            }
            if (ctx.validation() != null && !ctx.validation().isEmpty()) {
                ctx.validation().stream().forEach(validationContext -> {
                    fieldBuilder.addValidation( new FieldValidationVisitor().visitValidation(validationContext));
                });
            }

            return fieldBuilder.build();
        }


    }

    private static class FieldValidatorVisitor extends JdlBaseVisitor<FieldValidator> {
        @Override
        public FieldValidator visitMinmax(JdlParser.MinmaxContext ctx) {
            if (ctx.minValidator() != null) {
                String name = ctx.minValidator().getText();
                String value = ctx.minValidator().NATURAL_NUMBER().getText();
                return new FieldValidator(name, Integer.getInteger(value));
            }
            if (ctx.maxValidator() != null) {
                String name = ctx.maxValidator().getText();
                String value = ctx.maxValidator().NATURAL_NUMBER().getText();
                return new FieldValidator(name, Integer.getInteger(value));
            }
            throw new RuntimeException("No mimn Max validator");
        }
    }

    private static class FieldValidationVisitor extends JdlBaseVisitor<FieldValidation> {
        @Override
        public FieldValidation visitValidation(JdlParser.ValidationContext ctx) {
            if (ctx.validatorPattern() != null) {
                String name = ctx.validatorPattern().getText();

                String pattern = ctx.validatorPattern().getText();
                pattern = pattern.substring(1, pattern.length() - 1);// Remove LAPAREN AND RPAREN
                return new FieldValidationPattern(name, Pattern.compile(pattern));
            } else {
                return new FieldValidation(ctx.getText());
            }

        }

    }

    private static class BuildToolVisitor extends JdlBaseVisitor<ConfigBuildTool> {
        @Override
        public ConfigBuildTool visitBuildTool(JdlParser.BuildToolContext ctx) {
            return new ConfigBuildTool(ctx.getText());
        }
    }
}
package tech.jhipster.lite.jdl.infrastructure.primary;

import tech.jhipster.lite.jdl.antlr.JdlBaseVisitor;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.config.ConfigBuildTool;
import tech.jhipster.lite.jdl.domain.entity.field.*;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class EntityFieldVisitor {

    public static class EntityFieldJdl extends JdlBaseVisitor<EntityField> {
        @Override
        public EntityField visitEntityField(JdlParser.EntityFieldContext ctx) {
            EntityField.FieldBuilder fieldBuilder = EntityField.fieldBuilder();
            if (ctx.IDENTIFIER().isEmpty()){
                return new EntityField();
            }

            fieldBuilder.name(new FieldName(ctx.IDENTIFIER(0).getText()));
            if (ctx.comment() != null &&  !ctx.comment().isEmpty()) {

                String comment = ctx.comment().stream()
                        .map(JdlParser.CommentContext::getText)
                        .collect(Collectors.joining("\n"));
                fieldBuilder.comment(new FieldComment(comment));
            }
            if (ctx.FIELD_TYPE_BLOB() != null) {
                fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_BLOB().getText()));
            } else if (ctx.FIELD_TYPE_NUMBER() != null) {
                fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_NUMBER().getText()));
            } else if (ctx.FIELD_TYPE_TIME() != null) {
                fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_TIME().getText()));
            } else if (ctx.FIELD_TYPE_STRING() != null) {
                fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_STRING().getText()));
            } else if (ctx.FIELD_TYPE_OTHER() != null) {
                fieldBuilder.type(new FieldType(ctx.FIELD_TYPE_OTHER().getText()));
            } else {
                fieldBuilder.type(new FieldType(ctx.IDENTIFIER(1).getText()));
            }
            if (ctx.minMaxByteValidator() != null && !ctx.minMaxByteValidator().isEmpty()) {
                ctx.minMaxByteValidator().stream().forEach(minmaxContext -> {
                    fieldBuilder.addValidator(new FieldValidatorVisitor().visitMinMaxByteValidator(minmaxContext));
                });
            }
            else if (ctx.minMaxNumberValidator() != null && !ctx.minMaxNumberValidator().isEmpty()) {
                ctx.minMaxNumberValidator().stream().forEach(minmaxContext -> {
                    fieldBuilder.addValidator(new FieldValidatorVisitor().visitMinMaxNumberValidator(minmaxContext));
                });
            }
            else if (ctx.minMaxStringValidator() != null && !ctx.minMaxStringValidator().isEmpty()) {
                ctx.minMaxStringValidator().stream().forEach(minmaxContext -> {
                    fieldBuilder.addValidator(new FieldValidatorVisitor().visitMinMaxStringValidator(minmaxContext));
                });
            }

            if (ctx.validation() != null && !ctx.validation().isEmpty()) {
                ctx.validation().stream().forEach(validationContext -> {
                    fieldBuilder.addValidation(new FieldValidationVisitor().visitValidation(validationContext));
                });
            } else if (ctx.validatorPattern() != null && !ctx.validatorPattern().isEmpty()) {
                ctx.validatorPattern().stream().forEach(validationContext -> {
                    fieldBuilder.addValidation(new FieldValidationVisitor().visitValidatorPattern(validationContext));
                });
            }

            return fieldBuilder.build();
        }
    }

    private static class FieldValidatorVisitor extends JdlBaseVisitor<FieldValidator> {
        @Override
        public FieldValidator visitMinMaxByteValidator(JdlParser.MinMaxByteValidatorContext ctx) {

            String name = ctx.getChild(0).getText();
            String value = ctx.NATURAL_NUMBER().getText();
            return new FieldValidator(name, Integer.parseInt(value));
        }

        @Override
        public FieldValidator visitMinMaxNumberValidator(JdlParser.MinMaxNumberValidatorContext ctx) {
            String name = ctx.getChild(0).getText();
            String value = ctx.NATURAL_NUMBER().getText();
            return new FieldValidator(name, Integer.parseInt(value));
        }

        @Override
        public FieldValidator visitMinMaxStringValidator(JdlParser.MinMaxStringValidatorContext ctx) {
            String name = ctx.getChild(0).getText();
            String value = ctx.NATURAL_NUMBER().getText();
            return new FieldValidator(name, Integer.parseInt(value));
        }
    }

    private static class FieldValidationVisitor extends JdlBaseVisitor<FieldValidation> {
        @Override
        public FieldValidation visitValidation(JdlParser.ValidationContext ctx) {
            System.out.println(ctx.getText());
//            if (ctx.validatorPattern() != null) {
//                String name = ctx.validatorPattern().getText();
//
//                String pattern = ctx.validatorPattern().getText();
//                pattern = pattern.substring(1, pattern.length() - 1);// Remove LAPAREN AND RPAREN
//                return new FieldValidationPattern(name, Pattern.compile(pattern));
//            } else {
//                return new FieldValidation(ctx.getText());
//            }
            return new FieldValidation(ctx.getText());
        }

    }

    private static class BuildToolVisitor extends JdlBaseVisitor<ConfigBuildTool> {
        @Override
        public ConfigBuildTool visitBuildTool(JdlParser.BuildToolContext ctx) {
            return new ConfigBuildTool(ctx.getText());
        }
    }
}
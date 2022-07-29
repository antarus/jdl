package tech.jhipster.lite.jdl.infrastructure.primary;

import tech.jhipster.lite.jdl.antlr.JdlBaseVisitor;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.config.ConfigBaseName;
import tech.jhipster.lite.jdl.domain.config.ConfigBasePackage;
import tech.jhipster.lite.jdl.domain.config.ConfigBuildTool;
import tech.jhipster.lite.jdl.domain.entity.EntityTableName;
import tech.jhipster.lite.jdl.domain.entity.field.Field;
import tech.jhipster.lite.jdl.domain.entity.field.FieldComment;
import tech.jhipster.lite.jdl.domain.entity.field.FieldName;
import tech.jhipster.lite.jdl.domain.entity.field.FieldType;

public class EntityFieldVisitor {

    public static class EntityFieldJdl extends JdlBaseVisitor<Field> {
        @Override
        public Field visitField(JdlParser.FieldContext ctx) {
            Field.FieldBuilder fieldBuilder = Field.fieldBuilder();
            fieldBuilder.name(new FieldName(ctx.IDENTIFIER(0).getText()));
            if (ctx.comment()!=null){
                fieldBuilder.comment(new FieldComment(ctx.comment().getText()));
            }
            if (ctx.FIELD_TYPE()!=null){
                fieldBuilder.type(new FieldType(ctx.FIELD_TYPE().getText()));
            } else {
                fieldBuilder.type(new FieldType(ctx.IDENTIFIER(1).getText()));
            }
            if (ctx.minmax()!= null){
            }


            return fieldBuilder.build();
        }


    }

    private static class BaseNameVisitor extends JdlBaseVisitor<ConfigBaseName> {
        @Override
        public ConfigBaseName visitBaseName(JdlParser.BaseNameContext ctx) {
            return new ConfigBaseName(ctx.label().getText());
        }
    }

    private static class PackageNameVisitor extends JdlBaseVisitor<ConfigBasePackage> {
        @Override
        public ConfigBasePackage visitPackageName(JdlParser.PackageNameContext ctx) {
            return new ConfigBasePackage(ctx.label().getText());
        }
    }


    private static class BuildToolVisitor extends JdlBaseVisitor<ConfigBuildTool> {
        @Override
        public ConfigBuildTool visitBuildTool(JdlParser.BuildToolContext ctx) {
            return new ConfigBuildTool(ctx.getText());
        }
    }
}
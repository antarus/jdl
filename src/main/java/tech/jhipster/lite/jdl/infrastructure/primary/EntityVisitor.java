package tech.jhipster.lite.jdl.infrastructure.primary;

import tech.jhipster.lite.jdl.antlr.JdlBaseVisitor;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.config.ConfigApp;
import tech.jhipster.lite.jdl.domain.config.ConfigBaseName;
import tech.jhipster.lite.jdl.domain.config.ConfigBasePackage;
import tech.jhipster.lite.jdl.domain.config.ConfigBuildTool;
import tech.jhipster.lite.jdl.domain.entity.Entity;
import tech.jhipster.lite.jdl.domain.entity.EntityName;
import tech.jhipster.lite.jdl.domain.entity.EntityTableName;

public class EntityVisitor {

    public static class EntityVisitorJdl extends JdlBaseVisitor<Entity> {
        @Override
        public Entity visitEntity(JdlParser.EntityContext ctx) {
            Entity.EntityBuilder entityBuilder = Entity.entityBuilder();
            entityBuilder.name(new EntityName(ctx.IDENTIFIER().getText()));
            String tableName = ctx.IDENTIFIER().getText();
            if (ctx.entityTableName() != null) {
                if (ctx.entityTableName().IDENTIFIER() != null) {
                    tableName = ctx.entityTableName().IDENTIFIER().getText();
                }
            }
            entityBuilder.tableName(new EntityTableName(tableName));
            System.out.println("tableName : " + tableName);

            return entityBuilder.build();
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
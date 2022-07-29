package tech.jhipster.lite.jdl.infrastructure.primary;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.jdl.antlr.JdlBaseVisitor;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.config.ConfigApp;
import tech.jhipster.lite.jdl.domain.config.ConfigBaseName;
import tech.jhipster.lite.jdl.domain.config.ConfigBasePackage;
import tech.jhipster.lite.jdl.domain.config.ConfigBuildTool;
import tech.jhipster.lite.jdl.domain.entity.Entity;
import tech.jhipster.lite.jdl.domain.entity.EntityName;
import tech.jhipster.lite.jdl.domain.entity.EntityPackage;
import tech.jhipster.lite.jdl.domain.entity.EntityTableName;
import tech.jhipster.lite.jdl.domain.entity.field.Field;

public class EntityVisitor {
    public static class EntityVisitorJdl extends JdlBaseVisitor<Entity> {

        private static ConfigApp configApp;
        private EntityVisitorJdl() {
        }
        public EntityVisitorJdl(ConfigApp configApp) {
            Assert.notNull("ConfigApp",configApp);
            this.configApp=configApp;
        }

        @Override
        public Entity visitEntity(JdlParser.EntityContext ctx) {
            Entity.EntityBuilder entityBuilder = Entity.entityBuilder();
            entityBuilder.name(new EntityName(ctx.IDENTIFIER().getText()));
            String packag = configApp.getConfigBasePackage().get();
            String entityBase=configApp.getConfigEntityPath().get();
            packag=packag.concat("."+entityBase);
            entityBuilder.packag(new EntityPackage(packag));
            String tableName = ctx.IDENTIFIER().getText();
            if (ctx.entityTableName() != null) {
                if (ctx.entityTableName().IDENTIFIER() != null) {
                    tableName = ctx.entityTableName().IDENTIFIER().getText();
                }
            }
            entityBuilder.tableName(new EntityTableName(tableName));
            System.out.println("tableName : " + tableName);
            ctx.entityBody().field().stream().forEach( fc -> {
                EntityFieldVisitor.EntityFieldJdl fieldVisitor = new EntityFieldVisitor.EntityFieldJdl();
                entityBuilder.addField(fieldVisitor.visitField(fc));
            });

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
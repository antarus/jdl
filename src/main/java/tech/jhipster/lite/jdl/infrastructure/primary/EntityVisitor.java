package tech.jhipster.lite.jdl.infrastructure.primary;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.jdl.antlr.JdlBaseVisitor;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.config.ConfigBaseName;
import tech.jhipster.lite.jdl.domain.config.ConfigBasePackage;
import tech.jhipster.lite.jdl.domain.config.ConfigBuildTool;
import tech.jhipster.lite.jdl.domain.entity.*;
import tech.jhipster.lite.jdl.domain.jdl.JdlApplication;

public class EntityVisitor {
    public static class EntityVisitorJdl extends JdlBaseVisitor<Entity> {

        private final JdlApplication.JdlApplicationBuilder jdlApplicationBuilder;

        public EntityVisitorJdl(JdlApplication.JdlApplicationBuilder jdlApplicationBuilder) {
            Assert.notNull("jdlApplicationBuilder",jdlApplicationBuilder);
            this.jdlApplicationBuilder = jdlApplicationBuilder;
        }

        public void visitEntityContext(JdlParser.EntityContext ctx) {
            Entity.EntityBuilder entityBuilder = Entity.entityBuilder();
            entityBuilder.name(new EntityName(ctx.IDENTIFIER().getText()));
            String packag = jdlApplicationBuilder.getConfig().getConfigBasePackage().get();
            String entityBase=jdlApplicationBuilder.getConfig().getConfigEntityPath().get();
            packag=packag.concat("."+entityBase);
            entityBuilder.packag(new EntityPackage(packag));
            if (ctx.beforeEntity()!= null){
                BeforeEntityVisitor beforeEntityVisitor = new BeforeEntityVisitor(entityBuilder);
                beforeEntityVisitor.visitBeforeEntity(ctx.beforeEntity());
            }
            String tableName = ctx.IDENTIFIER().getText();
            if (ctx.entityTableName() != null) {
                if (ctx.entityTableName().IDENTIFIER() != null) {
                    tableName = ctx.entityTableName().IDENTIFIER().getText();
                }
            }
            entityBuilder.tableName(new EntityTableName(tableName));
            System.out.println("tableName : " + tableName);
            EntityFieldVisitor.EntityFieldJdl fieldVisitor = new EntityFieldVisitor.EntityFieldJdl();
            ctx.entityBody().entityField().stream().forEach( fc -> {

                entityBuilder.addField(fieldVisitor.visitEntityField(fc));
            });

            jdlApplicationBuilder.addEntity(entityBuilder.build());
        }


    }
    private static class BeforeEntityVisitor extends JdlBaseVisitor<Entity.EntityBuilder > {
        private Entity.EntityBuilder entityBuilder;
        public BeforeEntityVisitor(Entity.EntityBuilder entityBuilder ) {
            Assert.notNull("entityBuilder", entityBuilder);
            this.entityBuilder = entityBuilder;
        }

        @Override
        public Entity.EntityBuilder  visitBeforeEntity(JdlParser.BeforeEntityContext ctx) {
            if (ctx.comment()!= null){
                this.entityBuilder.comment(new EntityComment(ctx.comment().getText()));
            }
            if (ctx.annotation() != null && ctx.annotation().size()>0) {
                ctx.annotation().stream().forEach(annotationContext -> {
                    if (annotationContext.getText().contains("service") ||
                            annotationContext.getText().contains("paginate") ||
                            annotationContext.label() != null
                    ) {
                        System.out.println("TODO , manage Option by annotation " + annotationContext.getText());
                    }
//                    System.out.println(annotationContext.getText());
//                    System.out.println(annotationContext.label());
//                    System.out.println(annotationContext.IDENTIFIER());
                });
            }
            return entityBuilder;
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
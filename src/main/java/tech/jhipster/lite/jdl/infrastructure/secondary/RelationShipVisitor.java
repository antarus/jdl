package tech.jhipster.lite.jdl.infrastructure.secondary;

import tech.jhipster.lite.jdl.antlr.JdlBaseVisitor;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.entity.domain.relation.EntityRelation;
import tech.jhipster.lite.entity.domain.relation.RelationDescribe;
import tech.jhipster.lite.jdl.domain.JdlApplication;

public class RelationShipVisitor {
    public static class RelationShipVisitorJdl extends JdlBaseVisitor<EntityRelation> {

        private final JdlApplication.JdlApplicationBuilder jdlApplicationBuilder;

        public RelationShipVisitorJdl(JdlApplication.JdlApplicationBuilder jdlApplicationBuilder) {
            this.jdlApplicationBuilder = jdlApplicationBuilder;
        }

        public void visitRelations(JdlParser.RelationshipContext ctx) {
            ctx.relationShipBody().relation().forEach(r -> {
                this.jdlApplicationBuilder.addRelationShip(this.visitRelation(r, ctx.RELATION_SHIP_TYPE().getText()));
            });
        }


        public EntityRelation visitRelation(JdlParser.RelationContext ctx, String type) {
            EntityRelation.EntityRelationBuilder builder = EntityRelation.entityRelationShipBuilder();
            builder.type(type)
                    .from(extracteRelationDescription(ctx.relationFrom().relationDescribe()))
                    .to(extracteRelationDescription(ctx.relationTo().relationDescribe()));
            return builder.build();
        }

        private  RelationDescribe.RelationDescribeBuilder extracteRelationDescription(JdlParser.RelationDescribeContext ctx) {
            RelationDescribe.RelationDescribeBuilder builder = RelationDescribe.relationDescribeBuilder();
            if (ctx.comment() != null) {
                builder.comment(ctx.comment().getText());
            }
            if (ctx.REQUIRED() != null) {
                builder.isInjectedFieldRequired(true);
            }
            if (ctx.IDENTIFIER() != null) {
                builder.source(ctx.IDENTIFIER().getText());
            }
            if (ctx.identifierProperty() != null) {
                if ( ctx.identifierProperty().IDENTIFIER().size()==1){
                    builder.injectedField(ctx.identifierProperty().IDENTIFIER(0).getText());
                }
                if ( ctx.identifierProperty().IDENTIFIER().size()==2){
                    builder.displayField(ctx.identifierProperty().IDENTIFIER(1).getText());
                }
            }
            return builder;
        }
    }
}
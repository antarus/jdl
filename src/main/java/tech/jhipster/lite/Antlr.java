package tech.jhipster.lite;

import org.jboss.forge.roaster.model.source.JavaClassSource;
import tech.jhipster.lite.jdl.domain.config.ConfigApp;
import tech.jhipster.lite.jdl.domain.entity.Entity;
import tech.jhipster.lite.jdl.domain.jdl.JdlApplication;
import tech.jhipster.lite.jdl.infrastructure.primary.ConfigListenerJdl;

import tech.jhipster.lite.jdl.antlr.JdlLexer;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import tech.jhipster.lite.jdl.infrastructure.primary.DebugListenerJdl;
import tech.jhipster.lite.jdl.infrastructure.primary.EntityVisitor;
import tech.jhipster.lite.jdl.infrastructure.primary.RelationShipVisitor;

import java.io.IOException;
import java.io.InputStream;

public class Antlr {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Antlr.class.getResourceAsStream("/jdl1.jdl");
        JdlLexer lexer = new JdlLexer(CharStreams.fromStream(inputStream));
        JdlApplication.JdlApplicationBuilder jdlApplication = JdlApplication.jdlApplilcationBuilder();

        jdlApplication.config(listenerMode(new JdlParser(new CommonTokenStream(lexer))));
        inputStream = Antlr.class.getResourceAsStream("/jdl1.jdl");
        lexer = new JdlLexer(CharStreams.fromStream(inputStream));
        visitorMode(new JdlParser(new CommonTokenStream(lexer)), jdlApplication);

    }

    private static ConfigApp listenerMode(JdlParser parser) throws IOException {
        ConfigListenerJdl configListener = new ConfigListenerJdl();
        parser.addParseListener(configListener);

        parser.addParseListener(new DebugListenerJdl());
        JdlParser.File_Context fc = parser.file_();
        System.out.println("Not Managed");
        configListener.getConfigApp().getNotManaged().forEach(System.out::println);
        return configListener.getConfigApp();
    }

    private static void visitorMode(JdlParser parser,  JdlApplication.JdlApplicationBuilder jdlApplicationBuilder) throws IOException {
//        ConfigVisitor.ConfigVisitorJdl configVisitor = new ConfigVisitor.ConfigVisitorJdl();

        RelationShipVisitor.RelationShipVisitorJdl relationShipVisitor = new RelationShipVisitor.RelationShipVisitorJdl(jdlApplicationBuilder);
        EntityVisitor.EntityVisitorJdl entityVisitor = new EntityVisitor.EntityVisitorJdl(jdlApplicationBuilder);
        
        JdlParser.File_Context fc = parser.file_();

        fc.relationship().stream().forEach(relationShipVisitor::visitRelations);
        fc.entity().stream().forEach(entityVisitor::visitEntityContext);

        JdlApplication jdlApp = jdlApplicationBuilder.build();
        System.out.println(jdlApp);

//        ConfigApp config = configVisitor.visitConfig(fc.application(0).applicationbody().config(0));
//        System.out.println(config);

        Entity entity = jdlApp.getEntities().stream().findFirst().get();
//
        JavaClassSource javaClass = GeneratorWithRoaster.generateBaseEntity(entity);
        GeneratorWithRoaster.updateEntityToEntityJpa(javaClass, entity);

//        System.out.println(entity);
//        fc.application().forEach(identifier -> System.out.println(identifier.getText()));
//        fc.enumType().forEach(identifier -> System.out.println(identifier.getText()));
//        System.out.println(parser.buildTool().toString());
    }
}

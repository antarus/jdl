package tech.jhipster.lite;

import tech.jhipster.lite.jdl.domain.config.ConfigApp;
import tech.jhipster.lite.jdl.infrastructure.primary.ConfigListenerJdl;

import tech.jhipster.lite.jdl.antlr.JdlLexer;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import tech.jhipster.lite.jdl.infrastructure.primary.ConfigVisitor;
import tech.jhipster.lite.jdl.infrastructure.primary.DebugListenerJdl;

import java.io.IOException;
import java.io.InputStream;

public class Antlr {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Antlr.class.getResourceAsStream("/jdl1.jdl");
        JdlLexer lexer = new JdlLexer(CharStreams.fromStream(inputStream));
        listenerMode(new JdlParser(new CommonTokenStream(lexer)));

        inputStream = Antlr.class.getResourceAsStream("/jdl1.jdl");
        lexer = new JdlLexer(CharStreams.fromStream(inputStream));
        visitorMode(new JdlParser(new CommonTokenStream(lexer)));

    }

    private static void listenerMode(JdlParser parser) throws IOException {
        ConfigListenerJdl configListener = new ConfigListenerJdl();
        parser.addParseListener(configListener);

        parser.addParseListener(new DebugListenerJdl());
        JdlParser.File_Context fc = parser.file_();
        System.out.println("Not Managed");
        configListener.getConfigApp().getNotManaged().forEach(System.out::println);
    }

    private static void visitorMode(JdlParser parser) throws IOException {
        ConfigVisitor.ConfigVisitorJdl configVisitor = new ConfigVisitor.ConfigVisitorJdl();
        JdlParser.File_Context fc = parser.file_();
        ConfigApp config = configVisitor.visitConfig(fc.application(0).applicationbody().config(0));
        System.out.println(config);
//        fc.application().forEach(identifier -> System.out.println(identifier.getText()));
//        fc.enumType().forEach(identifier -> System.out.println(identifier.getText()));
//        System.out.println(parser.buildTool().toString());
    }
}

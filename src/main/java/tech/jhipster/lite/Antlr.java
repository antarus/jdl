package tech.jhipster.lite;

import org.antlr.v4.runtime.tree.ParseTreeListener;
import tech.jhipster.lite.jdl.ConfigListenerJdl;

import tech.jhipster.lite.jdl.DebugListenerJdl;
import tech.jhipster.lite.jdl.antlr.JdlLexer;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;

public class Antlr {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Antlr.class.getResourceAsStream("/jdl1.jdl");
        JdlLexer lexer = new JdlLexer(CharStreams.fromStream(inputStream));
        JdlParser parser = new JdlParser(new CommonTokenStream(lexer));

        ConfigListenerJdl configListener = new  ConfigListenerJdl();
        parser.addParseListener(configListener);
//        parser.addParseListener(new DebugListenerJdl());
// Start parsing
        JdlParser.File_Context fc= parser.file_();
        System.out.println(configListener.getConfigApp());

//        fc.application().forEach(identifier -> System.out.println(identifier.getText()));
//        fc.enumType().forEach(identifier -> System.out.println(identifier.getText()));
//        System.out.println(parser.buildTool().toString());
    }

}

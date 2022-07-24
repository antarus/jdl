package com;

import com.jdl.ListenerJdl;

import com.jdl.antlr.JdlLexer;
import com.jdl.antlr.JdlParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.io.InputStream;

public class Antlr {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = Antlr.class.getResourceAsStream("/jdl1.jdl");
        JdlLexer lexer = new JdlLexer(CharStreams.fromStream(inputStream));
        JdlParser parser = new JdlParser(new CommonTokenStream(lexer));
        parser.addParseListener(new ListenerJdl());

// Start parsing
        JdlParser.File_Context fc= parser.file_();
//        fc.application().forEach(identifier -> System.out.println(identifier.getText()));
//        fc.enumType().forEach(identifier -> System.out.println(identifier.getText()));
//        System.out.println(parser.baseName().toString());
    }

}

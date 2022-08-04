package tech.jhipster.lite.jdl.infrastructure.secondary;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import tech.jhipster.lite.jdl.antlr.JdlLexer;
import tech.jhipster.lite.jdl.antlr.JdlParser;
import tech.jhipster.lite.jdl.domain.JdlApplication;
import tech.jhipster.lite.jdl.domain.config.ConfigApp;

import java.io.FileInputStream;
import java.io.IOException;

public class FileSytemJdlRepository implements JdlRepository {


    @Override
    public JdlApplication load(String file) {
        try {
            FileInputStream fileStream = new FileInputStream(file);

            JdlLexer lexer = new JdlLexer(CharStreams.fromStream(fileStream));
            JdlApplication.JdlApplicationBuilder builder = JdlApplication.jdlApplilcationBuilder();
            builder.config(listenerMode(new JdlParser(new CommonTokenStream(lexer))));
            fileStream = new FileInputStream(file);
            lexer = new JdlLexer(CharStreams.fromStream(fileStream));
            visitorMode(new JdlParser(new CommonTokenStream(lexer)), builder);
            return builder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ConfigApp listenerMode(JdlParser parser) throws IOException {
        ConfigListenerJdl configListener = new ConfigListenerJdl();
        parser.addParseListener(configListener);

//        parser.addParseListener(new DebugListenerJdl());
        JdlParser.File_Context fc = parser.file_();
//        System.out.println("Not Managed");
//        configListener.getConfigApp().getNotManaged().forEach(System.out::println);
        return configListener.getConfigApp();
    }

    private void visitorMode(JdlParser parser, JdlApplication.JdlApplicationBuilder jdlApplicationBuilder) throws IOException {

        RelationShipVisitor.RelationShipVisitorJdl relationShipVisitor = new RelationShipVisitor.RelationShipVisitorJdl(jdlApplicationBuilder);
        EntityVisitor.EntityVisitorJdl entityVisitor = new EntityVisitor.EntityVisitorJdl(jdlApplicationBuilder);

        JdlParser.File_Context fc = parser.file_();

        fc.relationship().stream().forEach(relationShipVisitor::visitRelations);
        fc.entity().stream().forEach(entityVisitor::visitEntityContext);

    }

}

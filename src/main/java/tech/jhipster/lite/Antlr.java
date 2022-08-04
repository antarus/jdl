package tech.jhipster.lite;

import tech.jhipster.lite.entity.domain.EntityGenerator;
import tech.jhipster.lite.entity.domain.EntityRepository;
import tech.jhipster.lite.entity.infrastructure.secondary.FileSystemEntityGenerator;
import tech.jhipster.lite.entity.domain.Entity;
import tech.jhipster.lite.jdl.domain.JdlApplication;
import tech.jhipster.lite.jdl.infrastructure.secondary.*;

import java.io.IOException;

public class Antlr {
    public static void main(String[] args) throws IOException {

        FileSytemJdlRepository fileSytemJdlRepository = new FileSytemJdlRepository();



        JdlApplication jdlApp = fileSytemJdlRepository.load("/home/antarus/dev/antarus/jdl/src/main/resources/jdl1.jdl");

        Entity entity = jdlApp.getEntities().stream().findFirst().get();

        EntityRepository entityRepository = new FileSystemEntityGenerator( jdlApp);
        EntityGenerator generator = new EntityGenerator(entityRepository);
        generator.generate(entity);

    }

}

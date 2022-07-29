package tech.jhipster.lite;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import tech.jhipster.lite.jdl.domain.entity.Entity;

import java.io.IOException;
import java.io.Serializable;

public class GeneratorWithRoaster {
    public static void main(String[] args) throws IOException {


        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setPackage("com.company.example").setName("Person");

        javaClass.addInterface(Serializable.class);
        javaClass.addField()
                .setName("serialVersionUID")
                .setType("long")
                .setLiteralInitializer("1L")
                .setPrivate()
                .setStatic(true)
                .setFinal(true);

        javaClass.addProperty(Integer.class, "id").setMutable(false);
        javaClass.addProperty(String.class, "firstName");
        javaClass.addProperty("String", "lastName").removeAccessor().removeMutator();
        javaClass.addField().setName("test").setType("Person").setPrivate().addAnnotation("test");
        javaClass.getField("lastName").addAnnotation("test");
        javaClass.addMethod()
                .setConstructor(true)
                .setPublic()
                .setBody("this.id = id;")
                .addParameter(Integer.class, "id");

        System.out.println(javaClass.toString());
    }

    public static void generateEntity(Entity entity){
        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        javaClass.setPackage(entity.getPackag().get()).setName(entity.getName().get());
        entity.getFields().stream().forEach(field -> {
            javaClass.addProperty(field.getType().get(), field.getName().get());
        });

        System.out.println(javaClass.toString());
    }
}

package tech.jhipster.lite;

import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.Method;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.util.StringUtils;
import tech.jhipster.lite.common.domain.EntityUtils;
import tech.jhipster.lite.jdl.domain.entity.Entity;
import tech.jhipster.lite.jdl.domain.entity.field.EntityField;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

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

    public static JavaClassSource generateBaseEntity(Entity entity) {
        boolean generateFluent=true;
        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        if (entity.getComment() != null) {
            javaClass.getJavaDoc().setText(entity.getComment().get());
        }
        javaClass.setPackage(entity.getPackag().get()).setName(entity.getName().get());

//        tableAnnotation= tableAnnotation.setName("Table").removeAllValues().setStringValue("d=d");
//        tableAnnotation.s
//        tableAnnotation.addAnnotationValue("blog");
        entity.getFields().stream().forEach(field -> {
            var property = javaClass.addProperty(field.getType().get(), field.getName().get());
            if (field.getComment() != null) {
                property.getField().getJavaDoc().setFullText(field.getComment().get());
            }
            if (generateFluent){
                MethodSource fluentMethod =javaClass.addMethod()
                        .setName(field.getName().get())
                        .setReturnType(entity.getName().get())
                        .setPublic();
                fluentMethod.addParameter(field.getType().get(),field.getName().get());
                String body = """
                                this.set{{PROPERTY}}({{PARAMETER}});
                                return this;
                        """.replace("{{PROPERTY}}", StringUtils.capitalize(field.getName().get())).replace("{{PARAMETER}}",field.getName().get());
                fluentMethod.setBody(body);
            }

        });
        return javaClass;
//        System.out.println("");
//        System.out.println("Generate Entity 1");
//        System.out.println("");
//        System.out.println(javaClass.toString());
    }

    public static void updateEntityToEntityJpa(JavaClassSource javaClass, Entity entity) {


//        javaClass.addImport("javax.persistence.Table");
        javaClass.addImport("javax.persistence.*");
        javaClass.addAnnotation("Entity");
        AnnotationSource annotationTable = javaClass.addAnnotation().setLiteralValue("name", "\"blog\"").setName("Table");
        javaClass.addImport("org.hibernate.annotations.CacheConcurrencyStrategy");
        javaClass.addAnnotation("org.hibernate.annotations.Cache").setLiteralValue("usage", "CacheConcurrencyStrategy.READ_WRITE");

        javaClass.getFields().stream().forEach(field -> {
            Optional<EntityField> optEntityField = entity.getFields().stream().filter(f -> f.getName().get().equals(field.getName())).findFirst();
            if (!optEntityField.isPresent()) {
                throw new RuntimeException("field manquant :" + field.getName());
            }
            EntityField entityField = optEntityField.get();
//            @Column(name = "name", nullable = false)

            AnnotationSource fieldAnnotation = field.addAnnotation().setLiteralValue("name", EntityUtils.addQuote(field.getName()));
            if (entityField.isRequired()) {
                fieldAnnotation.setLiteralValue("nullable", "false");
            }
            fieldAnnotation.setName("Column");

        });

        System.out.println("");
        System.out.println("Generate Entity 1");
        System.out.println("");
        System.out.println(javaClass.toString());
    }
}

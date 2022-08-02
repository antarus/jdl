package tech.jhipster.lite;

import com.google.common.base.CaseFormat;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.util.StringUtils;
import tech.jhipster.lite.common.domain.EntityUtils;
import tech.jhipster.lite.jdl.domain.entity.Entity;
import tech.jhipster.lite.jdl.domain.entity.field.EntityField;
import tech.jhipster.lite.jdl.domain.entity.field.FieldName;
import tech.jhipster.lite.jdl.domain.entity.field.FieldType;
import tech.jhipster.lite.jdl.domain.jdl.JdlApplication;

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

    public static JavaClassSource generateBaseEntity(Entity entity, JdlApplication jdlApp ) {

        final JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
        if (entity.getComment() != null) {
            javaClass.getJavaDoc().setText(entity.getComment().get());
        }
        javaClass.setPackage(entity.getPackag().get()).setName(entity.getName().get());

        entity.getFields().forEach(field -> {
            var property = javaClass.addProperty(field.getType().get(), field.getName().get());
            if (field.getComment() != null) {
                property.getField().getJavaDoc().setFullText(field.getComment().get());
            }
            if (field.hasValidator()) {
                javaClass.addImport("javax.validation.constraints.*");
                field.getValidators().forEach(fieldValidator -> {
                    AnnotationSource<JavaClassSource> fieldAnnotation = property.getField().addAnnotation();
                    switch (fieldValidator.name()) {
                        case "minlength", "min", "minbytes" ->
                                fieldAnnotation.setLiteralValue("min", String.valueOf(fieldValidator.value())).setName("Size");
                        case "maxlength", "max", "maxbytes" ->
                                fieldAnnotation.setLiteralValue("max", String.valueOf(fieldValidator.value())).setName("Size");

                        default -> throw new RuntimeException("Validator not mananged yet " + fieldValidator.name());
                    }
                });
            }
            if (field.hasValidation()) {
                field.getValidations().forEach(fieldValidation -> {
                    AnnotationSource<JavaClassSource> fieldAnnotation = property.getField().addAnnotation();
                    if ("required".equals(fieldValidation.name())) {
                        fieldAnnotation.setName("NotNull");
                    } else {
                        throw new RuntimeException("Validator not mananged yet " + fieldValidation.name());
                    }
                });
            }
            generateFluent(jdlApp,entity, javaClass, field);
        });
        return javaClass;
    }

    private static void generateFluent(JdlApplication jdlApp, Entity entity, JavaClassSource javaClass, EntityField field) {
        if (jdlApp.getConfig().getFluentMethod().get()) {
            MethodSource<JavaClassSource> fluentMethod = javaClass.addMethod()
                    .setName(field.getName().get())
                    .setReturnType(entity.getName().get())
                    .setPublic();
            fluentMethod.addParameter(field.getType().get(), field.getName().get());
            String body = """
                            this.set{{PROPERTY}}({{PARAMETER}});
                            return this;
                    """.replace("{{PROPERTY}}", StringUtils.capitalize(field.getName().get())).replace("{{PARAMETER}}", field.getName().get());
            fluentMethod.setBody(body);
        }
    }

    public static void updateEntityToEntityJpa(JavaClassSource javaClass, Entity entity, JdlApplication jdlApp) {


//        javaClass.addImport("javax.persistence.Table");
        javaClass.addImport("javax.persistence.*");
        javaClass.addAnnotation("Entity");
        String tableName = entity.getTableName().get();
        if (tableName == null) {
            tableName = EntityUtils.addQuote(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entity.getName().get()));
        }
        javaClass.addAnnotation().setLiteralValue("name", tableName).setName("Table");
        javaClass.addImport("org.hibernate.annotations.CacheConcurrencyStrategy");
        javaClass.addAnnotation("org.hibernate.annotations.Cache").setLiteralValue("usage", "CacheConcurrencyStrategy.READ_WRITE");

        javaClass.getFields().forEach(field -> {
            Optional<EntityField> optEntityField = entity.getFields().stream().filter(f -> f.getName().get().equals(field.getName())).findFirst();
            if (optEntityField.isEmpty()) {
                throw new RuntimeException("field manquant :" + field.getName());
            }
            EntityField entityField = optEntityField.get();

            AnnotationSource<JavaClassSource> fieldAnnotation = field.addAnnotation().setLiteralValue("name", EntityUtils.addQuote(field.getName()));
            if (entityField.isRequired()) {
                fieldAnnotation.setLiteralValue("nullable", "false");
            }
            fieldAnnotation.setName("Column");

        });
        EntityField.FieldBuilder fieldIdBuilder = EntityField.fieldBuilder();
        fieldIdBuilder.name(new FieldName("id")).type(new FieldType("Long"));
        EntityField entityFieldId =fieldIdBuilder.build();
        FieldSource<JavaClassSource> idField = javaClass.addProperty(entityFieldId.getType().get(), entityFieldId.getName().get()).getField();
        idField.addAnnotation("Id");
        idField.addAnnotation().setLiteralValue("strategy", "GenerationType.SEQUENCE").setLiteralValue("generator", "sequenceGenerator").setName("GeneratedValue");
        idField.addAnnotation().setLiteralValue("name", EntityUtils.addQuote("sequenceGenerator")).setName("SequenceGenerator");
        idField.addAnnotation().setLiteralValue("name", EntityUtils.addQuote("id")).setName("Column");

        generateFluent(jdlApp,entity,javaClass,entityFieldId);


        jdlApp.geRelationShip().stream().filter(entityRelation -> entity.getName().get().equals(entityRelation.getFrom().source())).forEach(entityRelation -> {
            EntityField.FieldBuilder fieldRelationBuilder = EntityField.fieldBuilder();
            fieldRelationBuilder.name(new FieldName(entityRelation.getTo().source())).type(new FieldType(entityRelation.getFrom().injectedField()));
            EntityField entityFieldRelation =fieldRelationBuilder.build();
            FieldSource<JavaClassSource> relation = javaClass.addProperty(entityFieldRelation.getType().get(), entityFieldRelation.getName().get()).getField();
            relation.addAnnotation(entityRelation.getType().relationName());

            generateFluent(jdlApp,entity,javaClass,entityFieldRelation);

        });


        javaClass.addMethod()
                .setName("equals")
                .setReturnType(boolean.class)
                .setPublic()
                .setBody("""
                            if (this == o) {
                                return true;
                            }
                            if (!(o instanceof {{CLASS}})) {
                                return false;
                            }
                            return id != null && id.equals((({{CLASS}}) o).id);
                        """.replace("{{CLASS}}", entity.getName().get())).addAnnotation("Override");

        javaClass.addMethod()
                .setName("hashCode")
                .setReturnType(int.class)
                .setPublic()
                .setBody("""
                            // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
                            return getClass().hashCode();
                        """).addAnnotation("Override");


        var importList = javaClass.getImports().stream().distinct().toList();
        importList.forEach(i -> javaClass.removeImport(i).addImport(i));

        System.out.println("");
        System.out.println("Generate Entity 1");
        System.out.println("");
        System.out.println(javaClass.toString());
    }
}

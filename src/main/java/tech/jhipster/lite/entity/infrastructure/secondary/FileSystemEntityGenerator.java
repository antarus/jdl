package tech.jhipster.lite.entity.infrastructure.secondary;

import com.google.common.base.CaseFormat;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.AnnotationSource;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import tech.jhipster.lite.common.domain.EntityUtils;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.entity.domain.Entity;
import tech.jhipster.lite.entity.domain.EntityRepository;
import tech.jhipster.lite.entity.domain.field.EntityField;
import tech.jhipster.lite.entity.domain.field.FieldName;
import tech.jhipster.lite.entity.domain.field.FieldType;
import tech.jhipster.lite.entity.domain.relation.EntityRelation;
import tech.jhipster.lite.jdl.domain.config.ConfigApp;
import tech.jhipster.lite.jdl.domain.JdlApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;

@Repository
public class FileSystemEntityGenerator implements EntityRepository {

private final ConfigApp config;
private final Collection<EntityRelation> relations;
    public static final String LF = "\n";
    public FileSystemEntityGenerator(JdlApplication jdlApp) {
        this.config = jdlApp.getConfig();
        this.relations = jdlApp.geRelationShip();
    }
    public FileSystemEntityGenerator(ConfigApp config, Collection<EntityRelation> relations) {
        this.config = config;
        this.relations = relations;
    }
    public void generate(Entity entity){
        var base = this.generateBaseEntity(entity);
        this.updateEntityToEntityJpa(base,entity);

        String content = base.toString();
        System.out.println(content);

        Path pathLastFolder =
                this.config.getProjectFolder().filePath(entity.getPackag().path());

        Path pathEntity = Paths.get(pathLastFolder.toString(), entity.getName().get().concat(".java"));
        try {

            Files.createDirectories(pathLastFolder);
            System.out.println("generate entity to " + pathEntity.toString());
            FileUtils.write(pathEntity.toString(), content, LF);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public  JavaClassSource generateBaseEntity(Entity entity) {

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
            generateFluent(entity, javaClass, field);
        });
        return javaClass;
    }

    private  void generateFluent( Entity entity, JavaClassSource javaClass, EntityField field) {
        if (this.config.getFluentMethod().get()) {
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

    public void updateEntityToEntityJpa(JavaClassSource javaClass, Entity entity) {


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

        generateFluent(entity,javaClass,entityFieldId);


        this.relations.stream().filter(entityRelation -> entity.getName().get().equals(entityRelation.getFrom().source())).forEach(entityRelation -> {
            EntityField.FieldBuilder fieldRelationBuilder = EntityField.fieldBuilder();
            fieldRelationBuilder.name(new FieldName(entityRelation.getTo().source())).type(new FieldType(entityRelation.getFrom().injectedField()));
            EntityField entityFieldRelation =fieldRelationBuilder.build();
            FieldSource<JavaClassSource> relation = javaClass.addProperty(entityFieldRelation.getType().get(), entityFieldRelation.getName().get()).getField();
            relation.addAnnotation(entityRelation.getType().relationName());

            generateFluent(entity,javaClass,entityFieldRelation);

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


    }
}

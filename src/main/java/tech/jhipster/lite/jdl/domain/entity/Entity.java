package tech.jhipster.lite.jdl.domain.entity;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.jdl.domain.config.ConfigApp;
import tech.jhipster.lite.jdl.domain.entity.field.*;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Entity {
    public static EntityBuilder entityBuilder() {
        return new EntityBuilder();
    }

    private EntityName name;
    private List<Field> fields;
    private EntityComment comment;
    private EntityTableName tableName;

    @Override
    public String toString() {
        return "Entity{" +
                "name=" + name +
                ", fields=" + fields +
                ", comment=" + comment +
                ", tableName=" + tableName +
                '}';
    }

    public static final class EntityBuilder {
        private EntityName name;
        private final List<Field> fields=new ArrayList<>();
        private EntityComment comment;
        private EntityTableName tableName;

        private EntityBuilder() {
        }

        public static EntityBuilder anEntity() {
            return new EntityBuilder();
        }

        public EntityBuilder name(EntityName name) {
            Assert.notNull("name", name);
            this.name = name;
            return this;
        }

        public EntityBuilder addFields(Field field) {
            Assert.notNull("field", field);
            this.fields.add(field);
            return this;
        }

        public EntityBuilder comment(EntityComment comment) {
            Assert.notNull("comment", comment);
            this.comment = comment;
            return this;
        }

        public EntityBuilder tableName(EntityTableName tableName) {
            Assert.notNull("tableName", tableName);
            this.tableName = tableName;
            return this;
        }

        public Entity build() {
            Entity entity = new Entity();
            entity.tableName = this.tableName;
            entity.fields = this.fields;
            entity.comment = this.comment;
            entity.name = this.name;
            return entity;
        }
    }
}

package tech.jhipster.lite.jdl.domain.entity;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.jdl.domain.entity.field.*;

import java.util.ArrayList;
import java.util.List;

public class Entity {
    public static EntityBuilder entityBuilder() {
        return new EntityBuilder();
    }

    private EntityName name;

    private EntityPackage packag;
    private List<EntityField> entityFields;
    private EntityComment comment;
    private EntityTableName tableName;

    public EntityName getName() {
        return name;
    }

    public EntityPackage getPackag() {
        return packag;
    }

    public List<EntityField> getFields() {
        return entityFields;
    }

    public EntityComment getComment() {
        return comment;
    }

    public EntityTableName getTableName() {
        return tableName;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "name=" + name +
                ", package=" + packag +
                ", fields=" + entityFields +
                ", comment=" + comment +
                ", tableName=" + tableName +
                '}';
    }

    public static final class EntityBuilder {
        private EntityName name;
        private EntityPackage packag;
        private final List<EntityField> entityFields =new ArrayList<>();
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

        public EntityBuilder addField(EntityField entityField) {
            Assert.notNull("field", entityField);
            this.entityFields.add(entityField);
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
        public EntityBuilder packag(EntityPackage packag) {
            Assert.notNull("packag", packag);
            this.packag = packag;
            return this;
        }
        public Entity build() {
            Entity entity = new Entity();
            if (this.packag==null){
                this.packag=new EntityPackage("");
            }
            entity.tableName = this.tableName;
            entity.entityFields = this.entityFields;
            entity.comment = this.comment;
            entity.name = this.name;
            entity.packag = this.packag;
            return entity;
        }
    }
}

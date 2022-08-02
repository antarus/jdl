package tech.jhipster.lite.jdl.domain.entity.relation;

import tech.jhipster.lite.error.domain.Assert;

public class EntityRelation {
    public static EntityRelationBuilder entityRelationShipBuilder() {
        return new EntityRelationBuilder();
    }

    private RelationShip type;
    private RelationDescribe from;
    private RelationDescribe to;

    private EntityRelation() {

    }

    public RelationShip getType() {
        return type;
    }

    public RelationDescribe getFrom() {
        return from;
    }

    public RelationDescribe getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "EntityRelation{" +
                "type=" + type +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    public static final class EntityRelationBuilder {
        private RelationShip type;
        private RelationDescribe.RelationDescribeBuilder from;
        private RelationDescribe.RelationDescribeBuilder to;

        private EntityRelationBuilder() {
        }

        public static EntityRelationBuilder anEntityRelation() {
            return new EntityRelationBuilder();
        }

        public EntityRelationBuilder type(String type) {
            Assert.notBlank("type", type);
            this.type = RelationShip.valueOfRelation(type);
            return this;
        }

        public EntityRelationBuilder from(RelationDescribe.RelationDescribeBuilder from) {
            this.from = from;
            return this;
        }

        public EntityRelationBuilder to(RelationDescribe.RelationDescribeBuilder to) {
            this.to = to;
            return this;
        }

        public EntityRelation build() {
            EntityRelation entityRelation = new EntityRelation();
            if (this.from.getInjectedField() == null) {
                this.from.injectedField(this.to.getSource());
            }
            if (this.to.getInjectedField() == null) {
                this.to.injectedField(this.from.getSource());
            }
            entityRelation.from = this.from.build();
            entityRelation.to = this.to.build();
            entityRelation.type = this.type;
            return entityRelation;
        }
    }
}


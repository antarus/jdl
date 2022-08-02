package tech.jhipster.lite.jdl.domain.jdl;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.jdl.domain.config.ConfigApp;
import tech.jhipster.lite.jdl.domain.entity.Entity;
import tech.jhipster.lite.jdl.domain.entity.relation.EntityRelation;

import java.util.ArrayList;
import java.util.Collection;

public class JdlApplication {
    public static JdlApplication.JdlApplicationBuilder jdlApplilcationBuilder() {
        return new JdlApplication.JdlApplicationBuilder();
    }

    private ConfigApp config;
    private Collection<Entity> entities;
    private Collection<EntityRelation> relationShip;

    public ConfigApp getConfig() {
        return config;
    }

    public Collection<Entity> getEntities() {
        return entities;
    }

    @Override
    public String toString() {
        return "JdlApplication{" +
                "config=" + config +
                ", entities=" + entities +
                ", entityRelationShip=" + relationShip +
                '}';
    }

    public Collection<EntityRelation> geRelationShip() {

        return relationShip;
    }

    public static final class JdlApplicationBuilder {
        private ConfigApp config;
        private final Collection<Entity> entities = new ArrayList<>();
        private final Collection<EntityRelation> relationShips= new ArrayList<>();

        public ConfigApp getConfig() {
            return config;
        }

        public Collection<Entity> getEntities() {
            return entities;
        }

        public Collection<EntityRelation> getRelationShips() {
            return relationShips;
        }

        @Override
        public String toString() {
            return "JdlApplicationBuilder{" +
                    "config=" + config +
                    ", entities=" + entities +
                    ", relationShips=" + relationShips +
                    '}';
        }

        private JdlApplicationBuilder() {
        }

        public JdlApplicationBuilder config(ConfigApp config) {
            Assert.notNull("entities", entities);
            this.config = config;
            return this;
        }

        public JdlApplicationBuilder addEntity(Entity entities) {
            Assert.notNull("entities", entities);
            this.entities.add(entities);
            return this;
        }

        public JdlApplicationBuilder addRelationShip(EntityRelation relationShip) {
            Assert.notNull("relationShip", relationShip);
            this.relationShips.add(relationShip);
            return this;
        }

        public JdlApplication build() {
            JdlApplication jdlApplication = new JdlApplication();
            jdlApplication.config = this.config;
            jdlApplication.entities = this.entities;

            jdlApplication.relationShip = this.relationShips;
            return jdlApplication;
        }
    }
}

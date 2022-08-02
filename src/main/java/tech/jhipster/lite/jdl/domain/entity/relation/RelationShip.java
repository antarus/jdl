package tech.jhipster.lite.jdl.domain.entity.relation;

import java.util.Arrays;

public enum RelationShip {

    ONE_TO_ONE("OneToOne"),
    ONE_TO_MANY("OneToMany"),
    MANY_TO_ONE("ManyToOne"),
    MANY_TO_MANY("ManyToMany");

    private final String relationName;

    RelationShip(String relationName) {
        this.relationName = relationName;
    }

    public String relationName() {
        return relationName;
    }

    public static RelationShip valueOfRelation(String relationName) throws IllegalArgumentException {
        RelationShip relation = Arrays.stream(RelationShip.values())
                .filter(val -> val.relationName().equals(relationName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to resolve RelationShip: " + relationName));

        return relation;
    }
}

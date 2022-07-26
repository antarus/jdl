package tech.jhipster.lite.jdl.domain.entity;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.jdl.domain.entity.field.*;

import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Entity {

    private EntityName name;
    private List<Field> fields;
    private EntityComment comment;
    private EntityTableName tableName;
}

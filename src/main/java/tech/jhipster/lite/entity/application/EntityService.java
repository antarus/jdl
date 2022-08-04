package tech.jhipster.lite.entity.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.entity.domain.Entity;
import tech.jhipster.lite.entity.domain.EntityGenerator;
import tech.jhipster.lite.entity.domain.EntityRepository;

@Service
public class EntityService {

    private final EntityGenerator entityGenerator;

    public EntityService(EntityRepository entityRepository) {
        this.entityGenerator = new EntityGenerator(entityRepository);
    }

    public void generate(Entity entity){
        this.entityGenerator.generate(entity);
    }
}

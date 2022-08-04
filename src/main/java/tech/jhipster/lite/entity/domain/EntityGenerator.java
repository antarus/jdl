package tech.jhipster.lite.entity.domain;

public class EntityGenerator {
    private EntityRepository entityRepository;

    public EntityGenerator(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }
    public void generate(Entity entity){
    this.entityRepository.generate(entity);
    }
}

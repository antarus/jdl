package tech.jhipster.lite.jdl.infrastructure.secondary;

import tech.jhipster.lite.jdl.domain.JdlApplication;

public interface JdlRepository {
    JdlApplication load(String file);
}

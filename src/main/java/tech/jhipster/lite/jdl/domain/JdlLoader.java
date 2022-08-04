package tech.jhipster.lite.jdl.domain;

import tech.jhipster.lite.jdl.infrastructure.secondary.JdlRepository;

public class JdlLoader {
    private final JdlRepository jdlRepository;

    public JdlLoader(JdlRepository jdlRepository) {
        this.jdlRepository = jdlRepository;
    }

    public JdlApplication load(String filename) {
       return this.jdlRepository.load(filename);
    }
}

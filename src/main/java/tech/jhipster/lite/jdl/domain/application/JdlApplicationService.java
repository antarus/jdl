package tech.jhipster.lite.jdl.domain.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.jdl.domain.JdlLoader;
import tech.jhipster.lite.jdl.infrastructure.secondary.JdlRepository;

@Service
public class JdlApplicationService {

    private final JdlRepository jdlRepository;
    private final JdlLoader jdlLoader;

    public JdlApplicationService(JdlRepository jdlRepository) {
        this.jdlRepository = jdlRepository;
        this.jdlLoader= new JdlLoader(this.jdlRepository);
    }

}

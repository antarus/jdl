package tech.jhipster.lite.jdl.domain.config;

public record ConfigFluentMethod(boolean fluentMethod) {

    public boolean get() {
        return fluentMethod();
    }

}

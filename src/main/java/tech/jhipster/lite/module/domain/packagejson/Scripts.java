package tech.jhipster.lite.module.domain.packagejson;

import tech.jhipster.lite.common.domain.JHipsterCollections;

import java.util.Collection;
import java.util.stream.Stream;

public record Scripts(Collection<Script> scripts) {
  public Scripts(Collection<Script> scripts) {
    this.scripts = JHipsterCollections.immutable(scripts);
  }

  public boolean isEmpty() {
    return scripts().isEmpty();
  }

  public Stream<Script> stream() {
    return scripts().stream();
  }
}

package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.common.domain.JHipsterCollections;

import java.util.Collection;
import java.util.stream.Stream;

public record FilesToDelete(Collection<JHipsterProjectFilePath> files) {
  public FilesToDelete(Collection<JHipsterProjectFilePath> files) {
    this.files = JHipsterCollections.immutable(files);
  }

  public Stream<JHipsterProjectFilePath> stream() {
    return files().stream();
  }
}

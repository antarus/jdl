package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.common.domain.JHipsterCollections;

import java.util.Collection;

public record TemplatedFiles(Collection<TemplatedFile> files) {
  public TemplatedFiles(Collection<TemplatedFile> files) {
    this.files = JHipsterCollections.immutable(files);
  }

  public Collection<TemplatedFile> get() {
    return files();
  }
}

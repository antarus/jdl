package tech.jhipster.lite.module.domain.properties;

import tech.jhipster.lite.error.domain.Assert;

import java.nio.file.Path;
import java.nio.file.Paths;

public record JHipsterProjectFolder(String folder) {
  public JHipsterProjectFolder {
    Assert.notBlank("folder", folder);
  }

  public Path filePath(String file) {
    Assert.notNull("file", file);

    return Paths.get(folder(), file);
  }

  public String get() {
    return folder();
  }
}

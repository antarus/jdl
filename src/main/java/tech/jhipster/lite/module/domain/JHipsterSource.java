package tech.jhipster.lite.module.domain;

import org.apache.commons.io.FilenameUtils;
import tech.jhipster.lite.error.domain.Assert;

import java.nio.file.Path;

public class JHipsterSource {

  private static final String MUSTACHE_EXTENSION = ".mustache";

  private final Path source;

  JHipsterSource(Path source) {
    Assert.notNull("source", source);

    this.source = source;
  }

  public Path get() {
    return source;
  }

  public JHipsterSource template(String file) {
    Assert.notBlank("file", file);

    if (isTemplate(file)) {
      return file(file);
    }

    return file(file + MUSTACHE_EXTENSION);
  }

  public JHipsterSource append(String element) {
    return file(element);
  }

  public JHipsterSource file(String file) {
    return new JHipsterSource(source.resolve(file));
  }

  public String extension() {
    String filename = source.getFileName().toString();

    if (isTemplate(filename)) {
      return findExtension(filename.substring(0, filename.length() - MUSTACHE_EXTENSION.length()));
    }

    return findExtension(filename);
  }

  private String findExtension(String filename) {
    return "." + FilenameUtils.getExtension(filename);
  }

  public boolean isNotTemplate() {
    return !isTemplate(source.getFileName().toString());
  }

  private boolean isTemplate(String filename) {
    return filename.endsWith(MUSTACHE_EXTENSION);
  }
}

package tech.jhipster.lite.module.domain.packagejson;

import tech.jhipster.lite.common.domain.JHipsterCollections;

import java.util.Collection;
import java.util.stream.Stream;

public record PackageJsonDependencies(Collection<PackageJsonDependency> dependencies) {
  public PackageJsonDependencies(Collection<PackageJsonDependency> dependencies) {
    this.dependencies = JHipsterCollections.immutable(dependencies);
  }

  public boolean isEmpty() {
    return dependencies().isEmpty();
  }

  public Stream<PackageJsonDependency> stream() {
    return dependencies().stream();
  }
}

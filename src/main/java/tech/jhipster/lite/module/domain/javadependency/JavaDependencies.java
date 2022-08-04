package tech.jhipster.lite.module.domain.javadependency;

import tech.jhipster.lite.error.domain.Assert;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JavaDependencies {

  public static final JavaDependencies EMPTY = new JavaDependencies(null);

  private final Map<DependencyId, JavaDependency> dependencies;

  public JavaDependencies(Collection<JavaDependency> dependencies) {
    this.dependencies = buildDependencies(dependencies);
  }

  private Map<DependencyId, JavaDependency> buildDependencies(Collection<JavaDependency> dependencies) {
    if (dependencies == null) {
      return Map.of();
    }

    return dependencies.stream().collect(Collectors.toUnmodifiableMap(JavaDependency::id, Function.identity()));
  }

  public Optional<JavaDependency> get(DependencyId id) {
    Assert.notNull("id", id);

    return Optional.ofNullable(dependencies.get(id));
  }
}

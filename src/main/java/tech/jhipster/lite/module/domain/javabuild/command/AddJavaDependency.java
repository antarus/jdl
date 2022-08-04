package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyType;

import java.util.Collection;
import java.util.Optional;

public sealed interface AddJavaDependency permits AddDirectJavaDependency, AddJavaDependencyManagement {
  JavaDependency dependency();

  default Optional<VersionSlug> version() {
    return dependency().version();
  }

  default JavaDependencyScope scope() {
    return dependency().scope();
  }

  default boolean optional() {
    return dependency().optional();
  }

  default DependencyId dependencyId() {
    return dependency().id();
  }

  default Optional<JavaDependencyType> dependencyType() {
    return dependency().type();
  }

  default Collection<DependencyId> exclusions() {
    return dependency().exclusions();
  }
}

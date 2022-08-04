package tech.jhipster.lite.module.domain.javabuild.command;

import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public record JavaBuildCommands(Collection<JavaBuildCommand> commands) {
  public static final JavaBuildCommands EMPTY = new JavaBuildCommands(List.of());

  public JavaBuildCommands(Collection<JavaBuildCommand> commands) {
    this.commands = JHipsterCollections.immutable(commands);
  }

  public JavaBuildCommands merge(JavaBuildCommands other) {
    Assert.notNull("other", other);

    List<JavaBuildCommand> mergedCommands = new ArrayList<>();
    mergedCommands.addAll(commands());
    mergedCommands.addAll(other.commands());

    return new JavaBuildCommands(mergedCommands);
  }

  public boolean isEmpty() {
    return get().isEmpty();
  }

  public Collection<JavaBuildCommand> get() {
    return commands();
  }
}

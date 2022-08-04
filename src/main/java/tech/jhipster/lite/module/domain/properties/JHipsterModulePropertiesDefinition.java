package tech.jhipster.lite.module.domain.properties;

import tech.jhipster.lite.error.domain.Assert;

import java.util.*;
import java.util.stream.Stream;

import static tech.jhipster.lite.module.domain.properties.JHipsterModulePropertyDefinition.*;

public class JHipsterModulePropertiesDefinition {

  private static final Comparator<JHipsterModulePropertyDefinition> DEFINITION_COMPARATOR = Comparator
    .comparing(JHipsterModulePropertyDefinition::order)
    .thenComparing(definition -> definition.key().get());

  public static final JHipsterModulePropertiesDefinition EMPTY = builder().build();

  private final Set<JHipsterModulePropertyDefinition> definitions;

  private JHipsterModulePropertiesDefinition(JHipsterModulePropertiesDefinitionBuilder builder) {
    definitions = buildDefinitions(builder);
  }

  private Set<JHipsterModulePropertyDefinition> buildDefinitions(JHipsterModulePropertiesDefinitionBuilder builder) {
    Set<JHipsterModulePropertyDefinition> result = new TreeSet<>(DEFINITION_COMPARATOR);
    result.addAll(builder.definitions);

    return Collections.unmodifiableSet(result);
  }

  public static JHipsterModulePropertiesDefinitionBuilder builder() {
    return new JHipsterModulePropertiesDefinitionBuilder();
  }

  public Collection<JHipsterModulePropertyDefinition> get() {
    return definitions;
  }

  public Stream<JHipsterModulePropertyDefinition> stream() {
    return definitions.stream();
  }

  public static class JHipsterModulePropertiesDefinitionBuilder {

    private final Collection<JHipsterModulePropertyDefinition> definitions = new ArrayList<>();

    private JHipsterModulePropertiesDefinitionBuilder() {}

    public JHipsterModulePropertiesDefinitionBuilder addBasePackage() {
      return add(basePackageProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addProjectName() {
      return add(projectNameProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addProjectBaseName() {
      return add(projectBaseNameProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addServerPort() {
      return add(serverPortProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder addIndentation() {
      return add(indentationProperty());
    }

    public JHipsterModulePropertiesDefinitionBuilder add(JHipsterModulePropertyDefinition propertyDefinition) {
      Assert.notNull("propertyDefinition", propertyDefinition);

      definitions.add(propertyDefinition);

      return this;
    }

    public JHipsterModulePropertiesDefinition build() {
      return new JHipsterModulePropertiesDefinition(this);
    }
  }
}

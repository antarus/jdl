package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModuleContext.JHipsterModuleContextBuilder;
import tech.jhipster.lite.module.domain.JHipsterModuleFiles.JHipsterModuleFilesBuilder;
import tech.jhipster.lite.module.domain.JHipsterModulePreActions.JHipsterModulePreActionsBuilder;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuildplugin.JHipsterModuleJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuildplugin.JHipsterModuleJavaBuildPlugin.JHipsterModuleJavaBuildPluginBuilder;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin.JavaBuildPluginGroupIdBuilder;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JHipsterModuleJavaDependencies;
import tech.jhipster.lite.module.domain.javadependency.JHipsterModuleJavaDependencies.JHipsterModuleJavaDependenciesBuilder;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency.JavaDependencyGroupIdBuilder;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.module.domain.javaproperties.*;
import tech.jhipster.lite.module.domain.javaproperties.JHipsterModuleSpringProperties.JHipsterModuleSpringPropertiesBuilder;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson.JHipsterModulePackageJsonBuilder;
import tech.jhipster.lite.module.domain.packagejson.PackageName;
import tech.jhipster.lite.module.domain.packagejson.ScriptCommand;
import tech.jhipster.lite.module.domain.packagejson.ScriptKey;
import tech.jhipster.lite.module.domain.postaction.JHipsterModulePostActions;
import tech.jhipster.lite.module.domain.postaction.JHipsterModulePostActions.JHipsterModulePostActionsBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.*;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleMandatoryReplacements.JHipsterModuleMandatoryReplacementsBuilder;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleOptionalReplacements.JHipsterModuleOptionalReplacementsBuilder;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.always;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.notContainingReplacement;

public class JHipsterModule {

  public static final String LINE_BREAK = "\n";

  private final JHipsterProjectFolder projectFolder;
  private final JHipsterModuleFiles files;
  private final JHipsterModuleMandatoryReplacements mandatoryReplacements;
  private final JHipsterModuleOptionalReplacements optionalReplacements;
  private final JHipsterModuleContext context;
  private final JHipsterModuleJavaDependencies javaDependencies;
  private final JHipsterModuleJavaBuildPlugin javaBuildPlugins;
  private final JHipsterModulePackageJson packageJson;
  private final JHipsterModulePreActions preActions;
  private final JHipsterModulePostActions postActions;
  private final SpringProperties springProperties;

  private JHipsterModule(JHipsterModuleBuilder builder) {
    projectFolder = builder.projectFolder;

    files = builder.files.build();
    mandatoryReplacements = builder.mandatoryReplacements.build();
    optionalReplacements = builder.optionalReplacements.build();
    context = builder.context.build();
    javaDependencies = builder.javaDependencies.build();
    javaBuildPlugins = builder.javaBuildPlugins.build();
    packageJson = builder.packageJson.build();
    preActions = builder.preActions.build();
    postActions = builder.postActions.build();
    springProperties = buildSpringProperties(builder);
  }

  private SpringProperties buildSpringProperties(JHipsterModuleBuilder builder) {
    List<SpringProperty> properties = builder.springProperties.entrySet().stream().flatMap(toProperties()).toList();

    return new SpringProperties(properties);
  }

  private Function<Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder>, Stream<SpringProperty>> toProperties() {
    return properties -> properties.getValue().build().properties().entrySet().stream().map(toProperty(properties));
  }

  private Function<Entry<PropertyKey, PropertyValue>, SpringProperty> toProperty(
    Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> properties
  ) {
    return property ->
      SpringProperty
        .builder(properties.getKey().type())
        .key(property.getKey())
        .value(property.getValue())
        .profile(properties.getKey().profile())
        .build();
  }

  public static JHipsterModuleBuilder moduleBuilder(JHipsterModuleProperties properties) {
    return new JHipsterModuleBuilder(properties);
  }

  public static JavaDependencyGroupIdBuilder javaDependency() {
    return JavaDependency.builder();
  }

  public static JavaDependencyVersion javaDependencyVersion(String slug, String version) {
    return new JavaDependencyVersion(slug, version);
  }

  public static DependencyId dependencyId(String groupId, String artifactId) {
    return new DependencyId(groupId(groupId), artifactId(artifactId));
  }

  public static JavaBuildPluginGroupIdBuilder javaBuildPlugin() {
    return JavaBuildPlugin.builder();
  }

  public static JHipsterSource from(String source) {
    Assert.notBlank("source", source);

    return new JHipsterSource(Paths.get("/generator", source));
  }

  public static JHipsterProjectFilePath path(String path) {
    return new JHipsterProjectFilePath(path);
  }

  public static JHipsterDestination to(String destination) {
    return new JHipsterDestination(destination);
  }

  public static JHipsterDestination toSrcMainJava() {
    return JHipsterDestination.SRC_MAIN_JAVA;
  }

  public static JHipsterDestination toSrcMainDocker() {
    return JHipsterDestination.SRC_MAIN_DOCKER;
  }

  public static JHipsterDestination toSrcTestJava() {
    return JHipsterDestination.SRC_TEST_JAVA;
  }

  public static GroupId groupId(String groupId) {
    return new GroupId(groupId);
  }

  public static ArtifactId artifactId(String artifactId) {
    return new ArtifactId(artifactId);
  }

  public static VersionSlug versionSlug(String versionSlug) {
    return new VersionSlug(versionSlug);
  }

  public static TextReplacer text(String text) {
    return new TextReplacer(always(), text);
  }

  public static RegexReplacer regex(String regex) {
    return new RegexReplacer(always(), regex);
  }

  public static FileStartReplacer fileStart() {
    return new FileStartReplacer(notContainingReplacement());
  }

  public static TextNeedleBeforeReplacer lineBeforeText(String needle) {
    return new TextNeedleBeforeReplacer(notContainingReplacement(), needle);
  }

  public static RegexNeedleBeforeReplacer lineBeforeRegex(String regex) {
    return new RegexNeedleBeforeReplacer(notContainingReplacement(), Pattern.compile(regex, Pattern.MULTILINE));
  }

  public static RegexNeedleAfterReplacer lineAfterRegex(String regex) {
    return new RegexNeedleAfterReplacer(notContainingReplacement(), Pattern.compile(regex, Pattern.MULTILINE));
  }

  public static PropertyKey propertyKey(String key) {
    return new PropertyKey(key);
  }

  public static PropertyValue propertyValue(String... values) {
    return new PropertyValue(values);
  }

  public static SpringProfile springProfile(String profile) {
    return new SpringProfile(profile);
  }

  public static DocumentationTitle documentationTitle(String title) {
    return new DocumentationTitle(title);
  }

  public static ScriptKey scriptKey(String key) {
    return new ScriptKey(key);
  }

  public static ScriptCommand scriptCommand(String command) {
    return new ScriptCommand(command);
  }

  public static PackageName packageName(String packageName) {
    return new PackageName(packageName);
  }

  public JHipsterProjectFolder projectFolder() {
    return projectFolder;
  }

  public Indentation indentation() {
    return context.indentation();
  }

  public TemplatedFiles templatedFiles() {
    List<TemplatedFile> templatedFiles = files
      .filesToAdd()
      .stream()
      .map(file -> TemplatedFile.builder().file(file).context(context).build())
      .toList();

    return new TemplatedFiles(templatedFiles);
  }

  public FilesToDelete filesToDelete() {
    return files.filesToDelete();
  }

  public JHipsterModuleMandatoryReplacements mandatoryReplacements() {
    return mandatoryReplacements;
  }

  public JHipsterModuleOptionalReplacements optionalReplacements() {
    return optionalReplacements;
  }

  public JHipsterModuleJavaDependencies javaDependencies() {
    return javaDependencies;
  }

  public JHipsterModuleJavaBuildPlugin javaBuildPlugins() {
    return javaBuildPlugins;
  }

  public JHipsterModulePackageJson packageJson() {
    return packageJson;
  }

  public JHipsterModulePreActions preActions() {
    return preActions;
  }

  public JHipsterModulePostActions postActions() {
    return postActions;
  }

  public SpringProperties springProperties() {
    return springProperties;
  }

  public static class JHipsterModuleBuilder {

    private static final String PROFILE = "profile";

    private final JHipsterModuleShortcuts shortcuts;
    private final JHipsterProjectFolder projectFolder;
    private final JHipsterModuleProperties properties;
    private final JHipsterModuleContextBuilder context;
    private final JHipsterModuleFilesBuilder files = JHipsterModuleFiles.builder(this);
    private final JHipsterModuleMandatoryReplacementsBuilder mandatoryReplacements = JHipsterModuleMandatoryReplacements.builder(this);
    private final JHipsterModuleOptionalReplacementsBuilder optionalReplacements = JHipsterModuleOptionalReplacements.builder(this);
    private final JHipsterModuleJavaDependenciesBuilder javaDependencies = JHipsterModuleJavaDependencies.builder(this);
    private final JHipsterModuleJavaBuildPluginBuilder javaBuildPlugins = JHipsterModuleJavaBuildPlugin.builder(this);
    private final JHipsterModulePackageJsonBuilder packageJson = JHipsterModulePackageJson.builder(this);
    private final JHipsterModulePreActionsBuilder preActions = JHipsterModulePreActions.builder(this);
    private final JHipsterModulePostActionsBuilder postActions = JHipsterModulePostActions.builder(this);
    private final Map<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> springProperties = new HashMap<>();

    private JHipsterModuleBuilder(JHipsterModuleProperties properties) {
      Assert.notNull("properties", properties);

      this.projectFolder = properties.projectFolder();
      this.properties = properties;
      context = JHipsterModuleContext.builder(this);
      shortcuts = new JHipsterModuleShortcuts(this);
    }

    JHipsterModuleProperties properties() {
      return properties;
    }

    public JHipsterModuleBuilder documentation(DocumentationTitle title, JHipsterSource source) {
      shortcuts.documentation(title, source);

      return this;
    }

    public JHipsterModuleBuilder readmeSection(String section) {
      shortcuts.readmeSection(section);

      return this;
    }

    public JHipsterModuleBuilder startupCommand(String startupCommand) {
      shortcuts.startupCommand(startupCommand);

      return this;
    }

    public JHipsterModuleContextBuilder context() {
      return context;
    }

    public JHipsterModuleFilesBuilder files() {
      return files;
    }

    public JHipsterModuleMandatoryReplacementsBuilder mandatoryReplacements() {
      return mandatoryReplacements;
    }

    public JHipsterModuleBuilder springTestLogger(String name, LogLevel level) {
      shortcuts.springTestLogger(name, level);

      return this;
    }

    public JHipsterModuleBuilder springMainLogger(String name, LogLevel level) {
      shortcuts.springMainLogger(name, level);

      return this;
    }

    public JHipsterModuleBuilder integrationTestExtension(String extensionClass) {
      shortcuts.integrationTestExtension(extensionClass);

      return this;
    }

    public JHipsterModuleOptionalReplacementsBuilder optionalReplacements() {
      return optionalReplacements;
    }

    public JHipsterModuleJavaDependenciesBuilder javaDependencies() {
      return javaDependencies;
    }

    public JHipsterModuleJavaBuildPluginBuilder javaBuildPlugins() {
      return javaBuildPlugins;
    }

    public JHipsterModulePackageJsonBuilder packageJson() {
      return packageJson;
    }

    public JHipsterModulePreActionsBuilder preActions() {
      return preActions;
    }

    public JHipsterModulePostActionsBuilder postActions() {
      return postActions;
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties() {
      return springMainProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springMainBootstrapProperties() {
      return springMainBootstrapProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springMainBootstrapProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(
        new PropertiesKey(profile, SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(
        new PropertiesKey(profile, SpringPropertyType.MAIN_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties() {
      return springTestProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springTestBootstrapProperties() {
      return springProperties.computeIfAbsent(
        new PropertiesKey(SpringProfile.DEFAULT, SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(
        new PropertiesKey(profile, SpringPropertyType.TEST_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    String packagePath() {
      return properties.basePackage().path();
    }

    Indentation indentation() {
      return properties.indentation();
    }

    public JHipsterModule build() {
      return new JHipsterModule(this);
    }
  }

  private static record PropertiesKey(SpringProfile profile, SpringPropertyType type) {}
}

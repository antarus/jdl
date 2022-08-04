package tech.jhipster.lite.module.domain.properties;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public record JHipsterProjectBaseName(String name) {
  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
  private static final String DEFAULT_NAME = "jhipster";

  public static final JHipsterProjectBaseName DEFAULT = new JHipsterProjectBaseName(DEFAULT_NAME);

  public JHipsterProjectBaseName(String name) {
    this.name = buildName(name);
  }

  private String buildName(String name) {
    if (StringUtils.isBlank(name)) {
      return DEFAULT_NAME;
    }

    assertValidName(name);

    return name;
  }

  private void assertValidName(String name) {
    if (!NAME_PATTERN.matcher(name).matches()) {
      throw new InvalidProjectBaseNameException();
    }
  }

  public String get() {
    return name();
  }

  public String uncapitalized() {
    return StringUtils.uncapitalize(name());
  }

  public String capitalized() {
    return StringUtils.capitalize(name());
  }

  public String kebabCase() {
    return StringUtils.uncapitalize(name()).replaceAll("([A-Z])", "-$1").toLowerCase();
  }
}

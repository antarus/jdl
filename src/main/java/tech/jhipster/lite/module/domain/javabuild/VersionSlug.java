package tech.jhipster.lite.module.domain.javabuild;

import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.error.domain.Assert;

import java.util.Optional;

public record VersionSlug(String slug) {
  public static final String SUFFIX = ".version";

  private static final String VARIABLE_SLUG_START = "${";
  private static final String VARIABLE_SLUG_END = "}";

  public VersionSlug(String slug) {
    Assert.notBlank("slug", slug);

    this.slug = buildSlug(slug);
  }

  private static String buildSlug(String slug) {
    if (isVariable(slug)) {
      return removeSuffix(cleanVariable(slug));
    }

    return removeSuffix(slug);
  }

  private static String cleanVariable(String slug) {
    return slug.substring(VARIABLE_SLUG_START.length(), slug.length() - VARIABLE_SLUG_END.length());
  }

  private static boolean isVariable(String slug) {
    return slug.startsWith(VARIABLE_SLUG_START) && slug.endsWith(VARIABLE_SLUG_END);
  }

  private static String removeSuffix(String slug) {
    if (slug.endsWith(SUFFIX)) {
      return slug.substring(0, slug.length() - SUFFIX.length());
    }

    return slug;
  }

  public static Optional<VersionSlug> of(String versionSlug) {
    return Optional.ofNullable(versionSlug).filter(StringUtils::isNotBlank).map(VersionSlug::new);
  }

  public String mavenVariable() {
    return VARIABLE_SLUG_START + propertyName() + VARIABLE_SLUG_END;
  }

  public String propertyName() {
    return slug() + SUFFIX;
  }
}

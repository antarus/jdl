package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;

import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public record RegexReplacer(ReplacementCondition condition, Pattern pattern) implements ElementReplacer {
  public RegexReplacer(ReplacementCondition condition, String regex) {
    this(condition, buildPattern(regex));
  }

  private static Pattern buildPattern(String regex) {
    Assert.notBlank("regex", regex);

    try {
      return Pattern.compile(regex);
    } catch (PatternSyntaxException e) {
      throw new GeneratorException("Can't compile regex " + regex + ": " + e.getMessage(), e);
    }
  }

  public RegexReplacer {
    Assert.notNull("condition", condition);
    Assert.notNull("pattern", pattern);
  }

  @Override
  public boolean notMatchIn(String content) {
    return !pattern().matcher(content).find();
  }

  @Override
  public BiFunction<String, String, String> replacement() {
    return (content, replacement) -> pattern().matcher(content).replaceAll(replacement);
  }

  @Override
  public String searchMatcher() {
    return pattern().pattern();
  }
}

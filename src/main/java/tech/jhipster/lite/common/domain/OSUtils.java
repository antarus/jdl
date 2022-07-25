package tech.jhipster.lite.common.domain;

public class OSUtils {

  private OSUtils() {}

  public static boolean isWindows() {
    return System.getProperty("os.name").toLowerCase().contains("win");
  }
}

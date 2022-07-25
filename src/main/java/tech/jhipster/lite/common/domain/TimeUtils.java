package tech.jhipster.lite.common.domain;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

  private TimeUtils() {}

  public static String getDateString(Clock clock) {
    return getDateString(clock, 0);
  }

  public static String getDateString(Clock clock, int plusSecond) {
    LocalDateTime localDateTime = LocalDateTime.now(clock).plusSeconds(plusSecond);
    return localDateTime.format(DATE_TIME_FORMATTER);
  }
}

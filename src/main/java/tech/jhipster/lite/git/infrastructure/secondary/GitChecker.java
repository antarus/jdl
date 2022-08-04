package tech.jhipster.lite.git.infrastructure.secondary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.jhipster.lite.common.domain.Generated;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Generated(reason = "Highly dependant of execution environment")
class GitChecker {

  private static final Logger log = LoggerFactory.getLogger(GitChecker.class);

  boolean hasGit() {
    try {
      Process process = new ProcessBuilder("git", "--version").start();

      if (process.waitFor(5, TimeUnit.SECONDS)) {
        return true;
      }
    } catch (IOException e) {
      log.debug("Error executing git command: {}", e.getMessage(), e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();

      log.debug("Error executing git command: {}", e.getMessage(), e);
    }

    return false;
  }
}

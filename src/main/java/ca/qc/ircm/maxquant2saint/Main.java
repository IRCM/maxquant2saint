package ca.qc.ircm.maxquant2saint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Main class.
 */
@SpringBootApplication
public class Main {
  /**
   * Starts application.
   *
   * @param args
   *          arguments
   */
  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Main.class, args);
    MainService service = context.getBean(MainService.class);
    service.main(args);
  }
}

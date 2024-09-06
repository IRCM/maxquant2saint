package ca.qc.ircm.maxquant2saint;

import static ca.qc.ircm.maxquant2saint.Constants.messagePrefix;
import static ca.qc.ircm.maxquant2saint.MainPane.TITLE;

import java.util.Locale;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;

/**
 * Main class.
 */
@SpringBootApplication
public class Main extends AbstractSpringBootJavafxApplication {
  private static final String MESSAGE_PREFIX = messagePrefix(MainPane.class);

  /**
   * Sets preloader and calls {@link javafx.application.Application#launch(String...)} to start the
   * application.
   *
   * @param args
   *          command line arguments
   */
  public static void main(String[] args) {
    System.setProperty("javafx.preloader", SplashScreen.class.getName());
    launch(args);
    /*
    ApplicationContext context = SpringApplication.run(Main.class, args);
    MainService service = context.getBean(MainService.class);
    service.main(args);
     */
  }

  /**
   * Starts main application.
   *
   * @param stage
   *          the primary stage for this application, onto which the application scene can be set.
   *          Applications may create other stages, if needed, but they will not be primary stages.
   */
  @Override
  public void start(Stage stage) {
    MessageSource messageSource = applicationContext.getBean(MessageSource.class);
    MainPane layout = applicationContext.getBean(MainPane.class);
    Scene scene = new Scene(layout);
    scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
    stage.setScene(scene);
    stage.setTitle(messageSource.getMessage(MESSAGE_PREFIX + TITLE, null, Locale.getDefault()));
    notifyPreloader(new ApplicationStarted());
    stage.show();
  }
}

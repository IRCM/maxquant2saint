package ca.qc.ircm.maxquant2saint.gui;

import static ca.qc.ircm.maxquant2saint.Constants.messagePrefix;

import java.util.Locale;
import javafx.application.Preloader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Splash screen shown before application starts.
 */
public class SplashScreen extends Preloader {
  /**
   * ID of splash screen.
   */
  public static final String SPLASH_SCREEN = "splash";
  /**
   * Key to message for message label text.
   */
  public static final String MESSAGE = "message";
  /**
   * Key to message for error dialog content text.
   */
  public static final String ERROR_CONTENT = "error.content";
  private static final String MESSAGE_PREFIX = messagePrefix(SplashScreen.class);
  private static final Logger logger = LoggerFactory.getLogger(SplashScreen.class);
  /**
   * Layout of splash screen.
   */
  protected HBox layout = new HBox();
  /**
   * Message to show in splash screen.
   */
  protected Label message = new Label();
  /**
   * To get messages.
   */
  private MessageSource messageSource;

  public SplashScreen() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("messages");
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setAlwaysUseMessageFormat(true);
    this.messageSource = messageSource;
  }

  /**
   * Show splash screen.
   * 
   * @param stage
   *          the primary stage for this application, onto which the application scene can be set.
   *          Applications may create other stages, if needed, but they will not be primary stages.
   */
  @Override
  public void start(Stage stage) {
    layout.getChildren().add(message);
    layout.setId(SPLASH_SCREEN);
    layout.setCursor(Cursor.WAIT);
    message.setText(messageSource.getMessage(MESSAGE_PREFIX + MESSAGE, null, Locale.getDefault()));
    Scene scene = new Scene(layout);
    scene.getStylesheets().add(getClass().getResource("/splashscreen.css").toExternalForm());
    stage.initStyle(StageStyle.UNDECORATED);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Closes splash screen if application started successfully.
   * 
   * @param info
   *          the application-generated notification
   */
  @Override
  public void handleApplicationNotification(PreloaderNotification info) {
    if (info instanceof ApplicationStarted) {
      layout.getScene().getWindow().hide();
    }
  }

  /**
   * Shows error {@link Alert} containing error message.
   * 
   * @param info
   *          the error notification describing the cause of this error
   *
   * @return true
   */
  @Override
  public boolean handleErrorNotification(ErrorNotification info) {
    logger.error("Could not start application", info.getCause());
    Alert alert =
        new Alert(Alert.AlertType.ERROR, messageSource.getMessage(MESSAGE_PREFIX + ERROR_CONTENT,
            new Object[] { info.getDetails() }, Locale.getDefault()));
    alert.showAndWait();
    return true;
  }
}

package ca.qc.ircm.maxquant2saint;

import static ca.qc.ircm.maxquant2saint.Constants.messagePrefix;
import static ca.qc.ircm.maxquant2saint.SplashScreen.ERROR_CONTENT;
import static ca.qc.ircm.maxquant2saint.SplashScreen.MESSAGE;
import static ca.qc.ircm.maxquant2saint.SplashScreen.SPLASH_SCREEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.qc.ircm.maxquant2saint.test.config.TestFxTestAnnotations;
import java.util.Locale;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.scene.Cursor;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@TestFxTestAnnotations
@ExtendWith(ApplicationExtension.class)
public class SplashScreenTest {
  private static final String MESSAGE_PREFIX = messagePrefix(SplashScreen.class);
  private SplashScreen splashScreen;
  private Stage stage;
  @Autowired
  private MessageSource messageSource;

  @Start
  private void start(Stage stage) throws Exception {
    System.out.println(messageSource);
    System.out
        .println(messageSource.getMessage(MESSAGE_PREFIX + MESSAGE, null, Locale.getDefault()));
    this.stage = new Stage();
    splashScreen = new SplashScreen();
    splashScreen.init();
    splashScreen.start(this.stage);
  }

  @Test
  public void styles(FxRobot robot) {
    assertTrue(stage.isShowing());
    assertEquals(StageStyle.UNDECORATED, stage.getStyle());
    assertTrue(splashScreen.layout.getScene().getStylesheets()
        .contains(getClass().getResource("/splashscreen.css").toExternalForm()));
    assertEquals(SPLASH_SCREEN, splashScreen.layout.getId());
    assertEquals(Cursor.WAIT, splashScreen.layout.getCursor());
    assertTrue(splashScreen.layout.getChildren().contains(splashScreen.message));
  }

  @Test
  public void labels(FxRobot robot) {
    assertEquals(messageSource.getMessage(MESSAGE_PREFIX + MESSAGE, null, Locale.getDefault()),
        splashScreen.message.getText());
  }

  @Test
  public void handleApplicationNotification(FxRobot robot) {
    robot.interact(() -> splashScreen.handleApplicationNotification(new ApplicationStarted()));
    assertFalse(stage.isShowing());
  }

  @Test
  public void handleApplicationNotification_OtherEvent(FxRobot robot) {
    robot.interact(
        () -> splashScreen.handleApplicationNotification(new Preloader.PreloaderNotification() {}));
    assertTrue(stage.isShowing());
  }

  @Test
  public void handleErrorNotification(FxRobot robot) {
    Preloader.ErrorNotification error = new Preloader.ErrorNotification(null, "Test details",
        new IllegalStateException("Test exception"));
    Platform.runLater(() -> splashScreen.handleErrorNotification(error));
    robot.interact(() -> {
    });
    DialogPane alertPane = robot.lookup(".dialog-pane").query();
    assertEquals(messageSource.getMessage(MESSAGE_PREFIX + ERROR_CONTENT,
        new Object[] { error.getDetails() }, Locale.getDefault()), alertPane.getContentText());
    robot.clickOn(".button");
  }
}

package ca.qc.ircm.maxquant2saint.gui;

import static ca.qc.ircm.maxquant2saint.gui.ProgressDialog.PROGRESS_DIALOG;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import ca.qc.ircm.maxquant2saint.test.config.TestFxTestAnnotations;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.DialogEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@TestFxTestAnnotations
@ExtendWith(ApplicationExtension.class)
public class ProgressDialogTest {
  private ProgressDialog dialog;
  private Scene scene;
  @Mock
  private ChangeListener<Number> progressListener;
  @Mock
  private EventHandler<DialogEvent> closeHandler;
  @Mock
  private EventHandler<DialogEvent> hidingHandler;
  @Mock
  private EventHandler<DialogEvent> hiddenHandler;

  @Start
  private void start(Stage stage) {
    scene = new Scene(new BorderPane());
    stage.setScene(scene);
    dialog = new ProgressDialog();
    dialog.progressProperty().addListener(progressListener);
    dialog.setOnCloseRequest(closeHandler);
    dialog.show();
  }

  @Test
  public void styles(FxRobot robot) {
    assertTrue(dialog.getDialogPane().getStyleClass().contains(PROGRESS_DIALOG));
    assertTrue(dialog.getDialogPane().getStylesheets()
        .contains(getClass().getResource("/application.css").toExternalForm()));
  }

  @Test
  public void title(FxRobot robot) {
    String title = "test title";
    robot.interact(() -> dialog.setTitle(title));
    assertEquals(title, dialog.getTitle());
  }

  @Test
  public void message(FxRobot robot) {
    String content = "test content";
    robot.interact(() -> dialog.setContentText(content));
    assertEquals(content, dialog.getContentText());
  }

  @Test
  public void progress(FxRobot robot) {
    assertNotNull(dialog.progressIndicator.getParent());
    assertTrue(dialog.progressIndicator.getParent().getStyleClass().contains("graphic-container"));
    robot.interact(() -> dialog.setProgress(0.6));
    assertEquals(0.6, dialog.progressIndicator.getProgress());
  }

  @Test
  public void cancel(FxRobot robot) {
    robot.clickOn(".button");
    verify(closeHandler).handle(any());
  }
}

package ca.qc.ircm.maxquant2saint.gui;

import static ca.qc.ircm.maxquant2saint.Constants.messagePrefix;
import static ca.qc.ircm.maxquant2saint.gui.AboutDialog.ABOUT_DIALOG;
import static ca.qc.ircm.maxquant2saint.gui.AboutDialog.COMMIT;
import static ca.qc.ircm.maxquant2saint.gui.AboutDialog.EXPANDABLE;
import static ca.qc.ircm.maxquant2saint.gui.AboutDialog.HEADER;
import static ca.qc.ircm.maxquant2saint.gui.AboutDialog.TITLE;
import static ca.qc.ircm.maxquant2saint.gui.AboutDialog.VERSION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import ca.qc.ircm.maxquant2saint.test.config.TestFxTestAnnotations;
import java.util.Locale;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.DialogEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@TestFxTestAnnotations
@ExtendWith(ApplicationExtension.class)
public class AboutDialogTest {
  private static final Logger logger = LoggerFactory.getLogger(AboutDialogTest.class);
  private static final String MESSAGE_PREFIX = messagePrefix(AboutDialog.class);
  private AboutDialog dialog;
  private Scene scene;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private ObjectFactory<AboutDialog> dialogFactory;
  @Mock
  private EventHandler<DialogEvent> closeHandler;

  @Start
  private void start(Stage stage) {
    scene = new Scene(new BorderPane());
    stage.setScene(scene);
    dialog = dialogFactory.getObject();
    dialog.setOnCloseRequest(closeHandler);
    dialog.show();
  }

  @Test
  public void styles(FxRobot robot) {
    assertEquals(ABOUT_DIALOG, dialog.getDialogPane().getId());
    assertTrue(dialog.getDialogPane().getStylesheets()
        .contains(getClass().getResource("/application.css").toExternalForm()));
    assertEquals(dialog.expandableContent, dialog.getDialogPane().getExpandableContent());
    assertTrue(dialog.expandableContent.getStyleClass().contains(EXPANDABLE));
    assertTrue(dialog.expandableContent.getChildren().contains(dialog.commit));
  }

  @Test
  public void labels(FxRobot robot) {
    assertEquals(messageSource.getMessage(MESSAGE_PREFIX + TITLE, null, Locale.getDefault()),
        dialog.getTitle());
    assertEquals(messageSource.getMessage(MESSAGE_PREFIX + HEADER, null, Locale.getDefault()),
        dialog.getHeaderText());
    assertEquals(messageSource.getMessage(MESSAGE_PREFIX + VERSION, null, Locale.getDefault()),
        dialog.getContentText());
    assertEquals(messageSource.getMessage(MESSAGE_PREFIX + COMMIT, null, Locale.getDefault()),
        dialog.commit.getText());
  }

  @Test
  public void ok(FxRobot robot) {
    robot.clickOn(".button");
    verify(closeHandler).handle(any());
  }
}

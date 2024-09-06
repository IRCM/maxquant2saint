package ca.qc.ircm.maxquant2saint;

import static ca.qc.ircm.maxquant2saint.Constants.messagePrefix;

import java.util.Locale;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * About dialog.
 */
@FxComponent
public class AboutDialog extends Dialog {
  /**
   * ID of about dialog.
   */
  public static final String ABOUT_DIALOG = "about-dialog";
  /**
   * Key to message for dialog's title.
   */
  public static final String TITLE = "title";
  /**
   * Key to message for dialog header text.
   */
  public static final String HEADER = "header";
  /**
   * Key to message for dialog content text. This will contain the application's version.
   */
  public static final String VERSION = "version";
  /**
   * Style class of expendable content.
   */
  public static final String EXPANDABLE = "expandable";
  /**
   * Key to message for commit label text.
   */
  public static final String COMMIT = "commit";
  private static final String MESSAGE_PREFIX = messagePrefix(AboutDialog.class);
  /**
   * Expendable content. Contains commit number.
   */
  protected VBox expandableContent = new VBox();
  /**
   * Commit number.
   */
  protected Label commit = new Label();

  @Autowired
  AboutDialog(MessageSource messageSource) {
    getDialogPane().setId(ABOUT_DIALOG);
    getDialogPane().getStylesheets()
        .add(getClass().getResource("/application.css").toExternalForm());
    setTitle(messageSource.getMessage(MESSAGE_PREFIX + TITLE, null, Locale.getDefault()));
    setHeaderText(messageSource.getMessage(MESSAGE_PREFIX + HEADER, null, Locale.getDefault()));
    setContentText(messageSource.getMessage(MESSAGE_PREFIX + VERSION, null, Locale.getDefault()));
    getDialogPane().setExpandableContent(expandableContent);
    expandableContent.getChildren().add(commit);
    expandableContent.getStyleClass().add(EXPANDABLE);
    commit.setText(messageSource.getMessage(MESSAGE_PREFIX + COMMIT, null, Locale.getDefault()));
    getDialogPane().getButtonTypes().add(ButtonType.OK);
  }
}

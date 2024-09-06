package ca.qc.ircm.maxquant2saint.gui;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;

/**
 * Progres dialog.
 */
public class ProgressDialog extends Dialog {
  /**
   * Style class of progress dialog.
   */
  public static final String PROGRESS_DIALOG = "progress-dialog";
  /**
   * Show progression.
   */
  protected ProgressIndicator progressIndicator = new ProgressIndicator();

  /**
   * Creates {@link ProgressDialog}.
   */
  public ProgressDialog() {
    getDialogPane().getStyleClass().add(PROGRESS_DIALOG);
    getDialogPane().getStylesheets()
        .add(getClass().getResource("/application.css").toExternalForm());
    setGraphic(progressIndicator);
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
  }

  /**
   * Returns progression as a {@link DoubleProperty}.
   * 
   * @return progression property
   */
  public DoubleProperty progressProperty() {
    return progressIndicator.progressProperty();
  }

  /**
   * Returns progression.
   *
   * @return progression
   */
  public double getProgress() {
    return progressIndicator.getProgress();
  }

  /**
   * Sets progression.
   *
   * @param value
   *          progression
   */
  public void setProgress(double value) {
    progressIndicator.setProgress(value);
  }
}

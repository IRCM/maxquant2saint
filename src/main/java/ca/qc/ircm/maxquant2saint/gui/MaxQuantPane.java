package ca.qc.ircm.maxquant2saint.gui;

import static ca.qc.ircm.maxquant2saint.Constants.messagePrefix;

import java.io.File;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * MaxQuant output file selection.
 */
@FxComponent
public class MaxQuantPane extends VBox {
  /**
   * ID of MaxQuant files pane.
   */
  public static final String MAXQUANT = "maxquant";
  /**
   * Key to message for header label text.
   */
  public static final String HEADER = "header";
  /**
   * Key to message for protein groups file text.
   */
  public static final String PROTEIN_GROUPS = "proteinGroups";
  public static final String PROTEIN_GROUPS_FILE_CHOOSER_EXTENSION = "proteinGroups.txt";
  /**
   * Key to message for FASTA file text.
   */
  public static final String FASTA = "fasta";
  public static final String FASTA_FILE_CHOOSER_EXTENSION = "*.txt,*.fasta";
  /**
   * Key to message for score text.
   */
  public static final String SCORE = "score";
  /**
   * LFQ score.
   */
  public static final String SCORE_LFQ = "LFQ";
  private static final String MESSAGE_PREFIX = messagePrefix(MaxQuantPane.class);
  /**
   * Header.
   */
  protected Label header = new Label();
  /**
   * Protein groups file label.
   */
  protected Label proteinGroupsLabel = new Label();
  /**
   * Show file chooser to select protein groups file.
   */
  protected Button proteinGroupsButton = new Button();
  /**
   * Shows selected protein groups file.
   */
  protected Label proteinGroups = new Label();
  /**
   * File chooser for protein groups file.
   */
  protected FileChooser proteinGroupsFileChooser = new FileChooser();
  /**
   * Fasta file label.
   */
  protected Label fastaLabel = new Label();
  /**
   * Show file chooser to select FASTA file.
   */
  protected Button fastaButton = new Button();
  /**
   * Shows selected FASTA file.
   */
  protected Label fasta = new Label();
  /**
   * File chooser for FASTA file.
   */
  protected FileChooser fastaFileChooser = new FileChooser();
  /**
   * Score type label.
   */
  protected Label scoreLabel = new Label();
  /**
   * Score type.
   */
  protected ChoiceBox<String> score = new ChoiceBox<>();

  @Autowired
  MaxQuantPane(MessageSource messageSource) {
    setId(MAXQUANT);
    HBox proteinGroupsLayout = new HBox();
    HBox fastaLayout = new HBox();
    HBox scoreLayout = new HBox();
    getChildren().addAll(header, proteinGroupsLayout, fastaLayout, scoreLayout);
    header.getStyleClass().add("h2");
    header.setText(messageSource.getMessage(MESSAGE_PREFIX + HEADER, null, Locale.getDefault()));
    proteinGroupsLayout.getChildren().addAll(proteinGroupsLabel, proteinGroupsButton,
        proteinGroups);
    proteinGroupsLayout.getStyleClass().add("hbox");
    proteinGroupsLabel.setText(
        messageSource.getMessage(MESSAGE_PREFIX + PROTEIN_GROUPS, null, Locale.getDefault()));
    proteinGroupsButton.setText(messageSource
        .getMessage(MESSAGE_PREFIX + PROTEIN_GROUPS + ".button", null, Locale.getDefault()));
    proteinGroupsButton.setOnAction(e -> {
      File selection = proteinGroupsFileChooser.showOpenDialog(getScene().getWindow());
      if (selection != null) {
        proteinGroups.setText(selection.getName());
        proteinGroups.getTooltip().setText(selection.getPath());
      }
    });
    proteinGroupsFileChooser.setTitle(messageSource.getMessage(
        MESSAGE_PREFIX + PROTEIN_GROUPS + ".fileChooser.title", null, Locale.getDefault()));
    proteinGroupsFileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter(
            messageSource.getMessage(MESSAGE_PREFIX + PROTEIN_GROUPS + ".fileChooser.description",
                null, Locale.getDefault()),
            PROTEIN_GROUPS_FILE_CHOOSER_EXTENSION));
    proteinGroups.setTooltip(new Tooltip());
    fastaLayout.getChildren().addAll(fastaLabel, fastaButton, fasta);
    fastaLayout.getStyleClass().add("hbox");
    fastaLabel.setText(messageSource.getMessage(MESSAGE_PREFIX + FASTA, null, Locale.getDefault()));
    fastaLabel.setTooltip(new Tooltip());
    fastaButton.setText(
        messageSource.getMessage(MESSAGE_PREFIX + FASTA + ".button", null, Locale.getDefault()));
    fastaButton.setOnAction(e -> {
      File selection = fastaFileChooser.showOpenDialog(getScene().getWindow());
      if (selection != null) {
        fasta.setText(selection.getName());
        fasta.getTooltip().setText(selection.getPath());
      }
    });
    fastaFileChooser.setTitle(messageSource
        .getMessage(MESSAGE_PREFIX + FASTA + ".fileChooser.title", null, Locale.getDefault()));
    fastaFileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter(
            messageSource.getMessage(MESSAGE_PREFIX + FASTA + ".fileChooser.description", null,
                Locale.getDefault()),
            FASTA_FILE_CHOOSER_EXTENSION));
    fasta.setTooltip(new Tooltip());
    scoreLayout.getChildren().addAll(scoreLabel, score);
    scoreLayout.getStyleClass().add("hbox");
    scoreLabel.setText(messageSource.getMessage(MESSAGE_PREFIX + SCORE, null, Locale.getDefault()));
    score.setItems(FXCollections.observableArrayList(SCORE_LFQ));
    score.setValue(SCORE_LFQ);
  }
}

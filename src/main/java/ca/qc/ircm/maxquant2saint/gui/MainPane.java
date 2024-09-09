package ca.qc.ircm.maxquant2saint.gui;

import static ca.qc.ircm.maxquant2saint.Constants.messagePrefix;

import java.util.Locale;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * Main component of the application.
 */
@FxComponent
public class MainPane extends BorderPane {
  /**
   * ID of main component.
   */
  public static final String MAIN = "main";
  /**
   * Key to message for this window's title.
   */
  public static final String TITLE = "title";
  /**
   * Key to message for help menu text.
   */
  public static final String HELP = "help";
  /**
   * Key to message for about menu text.
   */
  public static final String ABOUT = "about";
  /**
   * Style class for buttons parent component.
   */
  public static final String BUTTONS = "buttons";
  /**
   * Key to message for convert button text.
   */
  public static final String CONVERT = "convert";
  /**
   * Base key for messages related to file chooser.
   */
  public static final String FILE_CHOOSER = "fileChooser";
  /**
   * Key to message for file chooser's title.
   */
  public static final String FILE_CHOOSER_TITLE = FILE_CHOOSER + ".title";
  /**
   * Key to message for file chooser extension filter description.
   */
  public static final String FILE_CHOOSER_DESCRIPTION = FILE_CHOOSER + ".description";
  /**
   * Allowed extensions in output file.
   */
  public static final String FILE_CHOOSER_EXTENSION = "*.txt";
  private static final String MESSAGE_PREFIX = messagePrefix(MainPane.class);
  /**
   * Menu bar at top.
   */
  protected MenuBar menu = new MenuBar();
  /**
   * Help menu.
   */
  protected Menu help = new Menu();
  /**
   * About sub-menu.
   */
  protected MenuItem about = new MenuItem();
  protected MaxQuantPane maxQuantPane;
  /**
   * Buttons container at the bottom.
   */
  protected HBox buttons = new HBox();
  /**
   * Convert button.
   */
  protected Button convert = new Button();
  /**
   * Selects output file.
   */
  protected FileChooser fileChooser = new FileChooser();

  @Autowired
  MainPane(ObjectFactory<AboutDialog> aboutDialogFactory, MaxQuantPane maxQuantPane,
      MessageSource messageSource) {
    this.maxQuantPane = maxQuantPane;
    setId(MAIN);
    setTop(menu);
    menu.getMenus().add(help);
    help.getItems().add(about);
    help.setText(messageSource.getMessage(MESSAGE_PREFIX + HELP, null, Locale.getDefault()));
    about.setText(messageSource.getMessage(MESSAGE_PREFIX + ABOUT, null, Locale.getDefault()));
    about.setOnAction(e -> aboutDialogFactory.getObject().showAndWait());
    setCenter(maxQuantPane);
    setBottom(buttons);
    buttons.getChildren().add(convert);
    buttons.getStyleClass().add(BUTTONS);
    convert.setText(messageSource.getMessage(MESSAGE_PREFIX + CONVERT, null, Locale.getDefault()));
    fileChooser.setTitle(
        messageSource.getMessage(MESSAGE_PREFIX + FILE_CHOOSER_TITLE, null, Locale.getDefault()));
    fileChooser.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter(messageSource
            .getMessage(MESSAGE_PREFIX + FILE_CHOOSER_DESCRIPTION, null, Locale.getDefault()),
            FILE_CHOOSER_EXTENSION));
  }
}

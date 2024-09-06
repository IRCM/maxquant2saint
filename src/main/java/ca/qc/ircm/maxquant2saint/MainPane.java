package ca.qc.ircm.maxquant2saint;

import static ca.qc.ircm.maxquant2saint.Constants.messagePrefix;

import java.util.Locale;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
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

  @Autowired
  MainPane(ObjectFactory<AboutDialog> aboutDialogFactory, MessageSource messageSource) {
    setId(MAIN);
    setTop(menu);
    menu.getMenus().add(help);
    help.getItems().add(about);
    help.setText(messageSource.getMessage(MESSAGE_PREFIX + HELP, null, Locale.getDefault()));
    about.setText(messageSource.getMessage(MESSAGE_PREFIX + ABOUT, null, Locale.getDefault()));
    about.setOnAction(e -> aboutDialogFactory.getObject().showAndWait());
  }
}

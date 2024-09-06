package ca.qc.ircm.maxquant2saint;

import java.util.regex.Pattern;
import org.springframework.context.MessageSource;

/**
 * Constants.
 */
public class Constants {
  /**
   * Strip this key from class name, if it matches.
   */
  private static final String STRIP_KEY =
      Pattern.quote(Constants.class.getPackage().getName() + ".");

  /**
   * Key prefix to use to get messages from {@link MessageSource}.
   * <p>
   * Here is an example on how to use prefix.
   *
   * <pre>
   * private static final String MESSAGE_PREFIX = messagePrefix(Constants.class);
   * messageSource.getMessage(MESSAGE_PREFIX + "title", new Object[] {}, locale);
   * </pre>
   * </p>
   *
   * @param baseClass
   *          class to use to obtain prefix
   * @return key prefix to use to get messages from {@link MessageSource}
   */
  public static String messagePrefix(Class<?> baseClass) {
    return baseClass.getName().replaceFirst(STRIP_KEY, "") + ".";
  }
}

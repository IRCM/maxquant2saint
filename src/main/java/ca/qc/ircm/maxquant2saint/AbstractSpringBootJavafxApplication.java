package ca.qc.ircm.maxquant2saint;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Creates Spring's context in {@link Application#init()}.
 */
public abstract class AbstractSpringBootJavafxApplication extends Application {
  /**
   * Spring's context.
   */
  protected ConfigurableApplicationContext applicationContext;

  /**
   * Creates Spring's context.
   */
  @Override
  public void init() {
    applicationContext =
        SpringApplication.run(getClass(), getParameters().getRaw().toArray(new String[0]));
    applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
  }

  /**
   * Closes Spring's context.
   * 
   * @throws Exception
   *           any exception thrown by super or while closing Spring's context.
   */
  @Override
  public void stop() throws Exception {
    super.stop();
    applicationContext.close();
  }
}

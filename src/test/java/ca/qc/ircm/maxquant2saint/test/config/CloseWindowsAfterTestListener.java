package ca.qc.ircm.maxquant2saint.test.config;

import java.util.ArrayList;
import java.util.concurrent.FutureTask;
import javafx.application.Platform;
import javafx.stage.Window;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

/**
 * Hides all JavaFX windows after each test.
 */
public class CloseWindowsAfterTestListener implements TestExecutionListener {
  @Override
  public void afterTestMethod(TestContext testContext) throws Exception {
    FutureTask<Void> closeWindows =
        new FutureTask<>(() -> new ArrayList<>(Window.getWindows()).forEach(Window::hide), null);
    Platform.runLater(closeWindows);
    closeWindows.get();
  }
}

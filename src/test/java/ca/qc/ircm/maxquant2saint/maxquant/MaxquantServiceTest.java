package ca.qc.ircm.maxquant2saint.maxquant;

import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.LFQ;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

@TestAnnotations
public class MaxquantServiceTest {
  private MaxquantService maxquantService;
  @Mock
  private MaxquantParser maxquantParser;
  @Mock
  private MaxquantProteinGroup group1;
  @Mock
  private MaxquantProteinGroup group2;
  @Mock
  private MaxquantProteinGroup group3;

  /**
   * Before test.
   */
  @BeforeEach
  public void beforeTest() {
    maxquantService = new MaxquantService(maxquantParser);
  }

  @Test
  public void proteinGroups() throws Throwable {
    Path file = Paths.get(getClass().getResource("/proteinGroups_.txt").toURI());
    doAnswer(i -> {
      @SuppressWarnings("unchecked")
      Consumer<MaxquantProteinGroup> handler = (Consumer<MaxquantProteinGroup>) i.getArgument(2);
      handler.accept(group1);
      handler.accept(group2);
      handler.accept(group3);
      return null;
    }).when(maxquantParser).parse(any(), any(), any());
    List<MaxquantProteinGroup> groups = maxquantService.proteinGroups(file, LFQ);
    verify(maxquantParser).parse(eq(file), eq(LFQ), any());
    assertEquals(3, groups.size());
    assertEquals(group1, groups.get(0));
    assertEquals(group2, groups.get(1));
    assertEquals(group3, groups.get(2));
  }

  @Test
  public void proteinGroups_IoException() throws Throwable {
    Path file = Paths.get(getClass().getResource("/proteinGroups_.txt").toURI());
    doThrow(new IOException("test")).when(maxquantParser).parse(any(), any(), any());
    assertThrows(IllegalStateException.class, () -> {
      maxquantService.proteinGroups(file, LFQ);
    });
  }
}

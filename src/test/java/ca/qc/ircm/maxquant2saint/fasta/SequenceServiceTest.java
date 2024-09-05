package ca.qc.ircm.maxquant2saint.fasta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

@TestAnnotations
public class SequenceServiceTest {
  private SequenceService sequenceService;
  @Mock
  private FastaConfiguration configuration;

  @BeforeEach
  public void beforeTest() {
    sequenceService = new SequenceService(configuration);
    when(configuration.getProteinId()).thenReturn(">.*\\|(.*)\\|");
  }

  @Test
  public void sequencesLength() throws Throwable {
    Path file = Paths.get(getClass().getResource("/human-trimmed.fasta").toURI());
    Map<String, Integer> lengths = sequenceService.sequencesLength(file);
    assertEquals(5, lengths.size());
    assertEquals((Integer) 128, lengths.get("Q9H9K5"));
    assertEquals((Integer) 68, lengths.get("P31689"));
    assertEquals((Integer) 41, lengths.get("P08246"));
    assertEquals((Integer) 72, lengths.get("P63244"));
    assertEquals((Integer) 69, lengths.get("P10144"));
  }
}

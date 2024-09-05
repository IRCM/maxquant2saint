package ca.qc.ircm.maxquant2saint.fasta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@TestAnnotations
public class FastaConfigurationTest {
  @Autowired
  private FastaConfiguration configuration;

  @Test
  public void defaultValues() throws Throwable {
    assertEquals(">.*\\|(.*)\\|", configuration.getProteinId());
  }
}

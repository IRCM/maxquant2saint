package ca.qc.ircm.maxquant2saint;

import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.LFQ;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@TestAnnotations
public class ConversionConfigurationTest {
  @Autowired
  private ConversionConfiguration configuration;

  @Test
  public void defaultValues() throws Throwable {
    assertEquals(new File("proteinGroups.txt"), configuration.getFile());
    assertEquals(new File("human.fasta"), configuration.getFasta());
    assertEquals(LFQ, configuration.getIntensity());
    assertEquals("(.*)_\\d+", configuration.getBait());
    assertEquals("FLAG.*", configuration.getControl());
  }
}

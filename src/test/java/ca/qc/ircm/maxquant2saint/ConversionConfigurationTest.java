package ca.qc.ircm.maxquant2saint;

import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.LFQ;
import static org.junit.Assert.assertEquals;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.io.File;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class ConversionConfigurationTest {
  @Inject
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

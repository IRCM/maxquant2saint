package ca.qc.ircm.maxquant2saint.maxquant;

import static org.junit.Assert.assertEquals;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class MaxquantConfigurationTest {
  @Inject
  private MaxquantConfiguration configuration;

  @Test
  public void defaultValues() throws Throwable {
    assertEquals("Protein IDs", configuration.getHeaders().getProteinIds());
    assertEquals("Gene names", configuration.getHeaders().getGeneNames());
    assertEquals("LFQ intensity (.*)", configuration.getHeaders().getLfq());
    assertEquals("MS/MS count (.*)", configuration.getHeaders().getMsmsCount());
    assertEquals("Intensity (.*)", configuration.getHeaders().getIntensity());
    assertEquals("Peptides (.*)", configuration.getHeaders().getPeptides());
    assertEquals("Razor + unique peptides (.*)", configuration.getHeaders().getRazorPeptides());
    assertEquals("Unique peptides (.*)", configuration.getHeaders().getUniquePeptides());
    assertEquals("Sequence coverage (.*) [%]", configuration.getHeaders().getCoverage());
  }
}

package ca.qc.ircm.maxquant2saint.maxquant;

import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.COVERAGE;
import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.INTENSITY;
import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.LFQ;
import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.MSMS_COUNT;
import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.PEPTIDES;
import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.RAZOR_PEPTIDES;
import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.UNIQUE_PEPTIDES;
import static org.junit.Assert.assertEquals;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class MaxquantConfigurationTest {
  @Autowired
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

  @Test
  public void getHeader() throws Throwable {
    assertEquals("LFQ intensity (.*)", configuration.getHeader(LFQ));
    assertEquals("MS/MS count (.*)", configuration.getHeader(MSMS_COUNT));
    assertEquals("Intensity (.*)", configuration.getHeader(INTENSITY));
    assertEquals("Peptides (.*)", configuration.getHeader(PEPTIDES));
    assertEquals("Razor + unique peptides (.*)", configuration.getHeader(RAZOR_PEPTIDES));
    assertEquals("Unique peptides (.*)", configuration.getHeader(UNIQUE_PEPTIDES));
    assertEquals("Sequence coverage (.*) [%]", configuration.getHeader(COVERAGE));
  }
}

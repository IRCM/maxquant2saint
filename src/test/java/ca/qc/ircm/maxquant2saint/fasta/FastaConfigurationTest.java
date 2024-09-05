package ca.qc.ircm.maxquant2saint.fasta;

import static org.junit.Assert.assertEquals;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class FastaConfigurationTest {
  @Autowired
  private FastaConfiguration configuration;

  @Test
  public void defaultValues() throws Throwable {
    assertEquals(">.*\\|(.*)\\|", configuration.getProteinId());
  }
}

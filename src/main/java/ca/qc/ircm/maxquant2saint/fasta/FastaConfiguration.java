package ca.qc.ircm.maxquant2saint.fasta;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = FastaConfiguration.PREFIX)
public class FastaConfiguration {
  public static final String PREFIX = "fasta";
  private String proteinId;

  public String getProteinId() {
    return proteinId;
  }

  public void setProteinId(String proteinId) {
    this.proteinId = proteinId;
  }
}

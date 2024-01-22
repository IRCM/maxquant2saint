package ca.qc.ircm.maxquant2saint;

import ca.qc.ircm.maxquant2saint.maxquant.Intensity;
import java.io.File;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = ConversionConfiguration.PREFIX)
public class ConversionConfiguration {
  public static final String PREFIX = "conversion";
  private File file;
  private File fasta;
  private Intensity intensity;
  private String bait;
  private String control;

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public File getFasta() {
    return fasta;
  }

  public void setFasta(File fasta) {
    this.fasta = fasta;
  }

  public Intensity getIntensity() {
    return intensity;
  }

  public void setIntensity(Intensity intensity) {
    this.intensity = intensity;
  }

  public String getBait() {
    return bait;
  }

  public void setBait(String bait) {
    this.bait = bait;
  }

  public String getControl() {
    return control;
  }

  public void setControl(String control) {
    this.control = control;
  }
}

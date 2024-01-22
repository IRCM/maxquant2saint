package ca.qc.ircm.maxquant2saint.maxquant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = MaxquantConfiguration.PREFIX)
public class MaxquantConfiguration {
  public static final String PREFIX = "maxquant";
  private Headers headers;

  /**
   * Returns intensity header pattern.
   * 
   * @param intensity
   *          intensity
   * @return intensity header pattern
   */
  public String getHeader(Intensity intensity) {
    switch (intensity) {
      case LFQ:
        return headers.lfq;
      case MSMS_COUNT:
        return headers.msmsCount;
      case INTENSITY:
        return headers.intensity;
      case PEPTIDES:
        return headers.peptides;
      case RAZOR_PEPTIDES:
        return headers.razorPeptides;
      case UNIQUE_PEPTIDES:
        return headers.uniquePeptides;
      case COVERAGE:
        return headers.coverage;
      default:
        throw new AssertionError(
            Intensity.class.getSimpleName() + " " + intensity + " not covered in switch");
    }
  }

  public Headers getHeaders() {
    return headers;
  }

  public void setHeaders(Headers headers) {
    this.headers = headers;
  }

  public static class Headers {
    private String proteinIds;
    private String geneNames;
    private String lfq;
    private String msmsCount;
    private String intensity;
    private String peptides;
    private String razorPeptides;
    private String uniquePeptides;
    private String coverage;

    public String getProteinIds() {
      return proteinIds;
    }

    public void setProteinIds(String proteinIds) {
      this.proteinIds = proteinIds;
    }

    public String getGeneNames() {
      return geneNames;
    }

    public void setGeneNames(String geneNames) {
      this.geneNames = geneNames;
    }

    public String getLfq() {
      return lfq;
    }

    public void setLfq(String lfq) {
      this.lfq = lfq;
    }

    public String getMsmsCount() {
      return msmsCount;
    }

    public void setMsmsCount(String msmsCount) {
      this.msmsCount = msmsCount;
    }

    public String getIntensity() {
      return intensity;
    }

    public void setIntensity(String intensity) {
      this.intensity = intensity;
    }

    public String getPeptides() {
      return peptides;
    }

    public void setPeptides(String peptides) {
      this.peptides = peptides;
    }

    public String getRazorPeptides() {
      return razorPeptides;
    }

    public void setRazorPeptides(String razorPeptides) {
      this.razorPeptides = razorPeptides;
    }

    public String getUniquePeptides() {
      return uniquePeptides;
    }

    public void setUniquePeptides(String uniquePeptides) {
      this.uniquePeptides = uniquePeptides;
    }

    public String getCoverage() {
      return coverage;
    }

    public void setCoverage(String coverage) {
      this.coverage = coverage;
    }
  }
}

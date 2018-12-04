package ca.qc.ircm.maxquant2saint.saint;

/**
 * A sample.
 */
public class Sample {
  public String name;
  public String bait;
  public boolean control;

  public Sample() {
  }

  /**
   * Creates a new sample.
   * 
   * @param name
   *          sample's name
   * @param bait
   *          bait's name
   * @param control
   *          true if sample is a control
   */
  public Sample(String name, String bait, boolean control) {
    this.name = name;
    this.bait = bait;
    this.control = control;
  }
}

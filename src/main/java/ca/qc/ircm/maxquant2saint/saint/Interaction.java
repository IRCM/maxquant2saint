package ca.qc.ircm.maxquant2saint.saint;

/**
 * An interaction between a bait and a prey.
 */
public class Interaction {
  public Sample sample;
  public Prey prey;
  public double intensity;

  public Interaction() {
  }

  /**
   * Creates a new interaction.
   * 
   * @param sample
   *          sample
   * @param prey
   *          prey
   * @param intensity
   *          intensity
   */
  public Interaction(Sample sample, Prey prey, double intensity) {
    this.sample = sample;
    this.prey = prey;
    this.intensity = intensity;
  }
}

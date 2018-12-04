package ca.qc.ircm.maxquant2saint.saint;

/**
 * A prey.
 */
public class Prey {
  public String id;
  public int length;
  public String gene;

  public Prey() {
  }

  public Prey(String id) {
    this.id = id;
  }

  /**
   * Creates a prey.
   *
   * @param id
   *          protein id
   * @param length
   *          length of protein in amino acids
   * @param gene
   *          gene's name
   */
  public Prey(String id, int length, String gene) {
    super();
    this.id = id;
    this.length = length;
    this.gene = gene;
  }
}

package ca.qc.ircm.maxquant2saint.fasta;

/**
 * A sequence in a FASTA file.
 */
public class Sequence {
  public String header;
  public String sequence;

  public Sequence() {
  }

  public Sequence(String header, String sequence) {
    this.header = header;
    this.sequence = sequence;
  }
}

package ca.qc.ircm.maxquant2saint.fasta;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;

/**
 * Parses Fasta files.
 */
public class FastaReader implements Closeable {
  private static final String ENTRY_STARTSWITH = ">";
  private BufferedReader reader;
  private String line;

  public FastaReader(BufferedReader reader) throws IOException {
    this.reader = reader;
    line = reader.readLine();
  }

  /**
   * Returns next sequence found in reader.
   *
   * @return next sequence found in reader
   * @throws IOException
   *           could not read from reader
   */
  public Sequence readSequence() throws IOException {
    while (line != null && !line.startsWith(ENTRY_STARTSWITH)) {
      line = reader.readLine();
    }
    if (line == null) {
      return null;
    }
    Sequence sequence = new Sequence();
    sequence.header = line;
    StringBuilder sequenceBuilder = new StringBuilder();
    line = reader.readLine();
    while (line != null && !line.startsWith(ENTRY_STARTSWITH)) {
      sequenceBuilder.append(line);
      line = reader.readLine();
    }
    sequence.sequence = sequenceBuilder.toString();
    return sequence;
  }

  @Override
  public void close() throws IOException {
    reader.close();
  }
}

package ca.qc.ircm.maxquant2saint.saint;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

/**
 * Writes SAINT prey files.
 */
public class SaintPreyWriter implements Closeable {
  private static final String SEPARATOR = "\t";
  private static final String NEW_LINE = System.getProperty("line.separator");
  private final Writer writer;

  public SaintPreyWriter(Writer writer) {
    this.writer = writer;
  }

  public void writePrey(String protein, int proteinLength, String gene) throws IOException {
    writer.write(protein);
    writer.write(SEPARATOR);
    writer.write(String.valueOf(proteinLength));
    writer.write(SEPARATOR);
    writer.write(gene);
    writer.write(NEW_LINE);
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
}

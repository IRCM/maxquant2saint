package ca.qc.ircm.maxquant2saint.saint;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

/**
 * Writes SAINT bait files.
 */
public class SaintBaitWriter implements Closeable {
  private static final String SEPARATOR = "\t";
  private static final String NEW_LINE = System.getProperty("line.separator");
  private final Writer writer;

  public SaintBaitWriter(Writer writer) {
    this.writer = writer;
  }

  public void writeBait(String sample, String bait, boolean control) throws IOException {
    writer.write(sample);
    writer.write(SEPARATOR);
    writer.write(bait);
    writer.write(SEPARATOR);
    writer.write(control ? "C" : "T");
    writer.write(NEW_LINE);
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
}

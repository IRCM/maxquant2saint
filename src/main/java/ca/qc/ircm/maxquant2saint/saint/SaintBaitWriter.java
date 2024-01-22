package ca.qc.ircm.maxquant2saint.saint;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

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

  /**
   * Writes sample in SAINT bait format.
   *
   * @param sample
   *          sample sample
   * @throws IOException
   *           could not write to writer
   */
  public void writeSample(Sample sample) throws IOException {
    writer.write(sample.name);
    writer.write(SEPARATOR);
    writer.write(Objects.toString(sample.bait, ""));
    writer.write(SEPARATOR);
    writer.write(sample.control ? "C" : "T");
    writer.write(NEW_LINE);
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
}

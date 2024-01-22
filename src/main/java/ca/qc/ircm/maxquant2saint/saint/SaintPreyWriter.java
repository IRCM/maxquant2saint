package ca.qc.ircm.maxquant2saint.saint;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

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

  /**
   * Writes prey in SAINT format.
   *
   * @param prey
   *          prey
   * @throws IOException
   *           could not write to writer
   */
  public void writePrey(Prey prey) throws IOException {
    writer.write(prey.id);
    writer.write(SEPARATOR);
    writer.write(String.valueOf(prey.length));
    writer.write(SEPARATOR);
    writer.write(Objects.toString(prey.gene, ""));
    writer.write(NEW_LINE);
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
}

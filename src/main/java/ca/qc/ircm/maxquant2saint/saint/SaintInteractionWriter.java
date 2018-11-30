package ca.qc.ircm.maxquant2saint.saint;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Writes SAINT interactions files.
 */
public class SaintInteractionWriter implements Closeable {
  private static final String SEPARATOR = "\t";
  private static final String NEW_LINE = System.getProperty("line.separator");
  private final Writer writer;
  private NumberFormat intensityFormatter = new DecimalFormat("0.######");

  public SaintInteractionWriter(Writer writer) {
    this.writer = writer;
  }

  public void writeInteraction(String sample, String bait, String prey, double intensity)
      throws IOException {
    writer.write(sample);
    writer.write(SEPARATOR);
    writer.write(bait);
    writer.write(SEPARATOR);
    writer.write(prey);
    writer.write(SEPARATOR);
    writer.write(intensityFormatter.format(intensity));
    writer.write(NEW_LINE);
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
}

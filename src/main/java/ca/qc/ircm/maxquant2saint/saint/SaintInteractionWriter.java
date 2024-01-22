package ca.qc.ircm.maxquant2saint.saint;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

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

  /**
   * Writes interaction in SAINT format.
   *
   * @param interaction
   *          interaction
   * @throws IOException
   *           could not write to writer
   */
  public void writeInteraction(Interaction interaction) throws IOException {
    writer.write(interaction.sample.name);
    writer.write(SEPARATOR);
    writer.write(Objects.toString(interaction.sample.bait, ""));
    writer.write(SEPARATOR);
    writer.write(interaction.prey.id);
    writer.write(SEPARATOR);
    writer.write(intensityFormatter.format(interaction.intensity));
    writer.write(NEW_LINE);
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }
}

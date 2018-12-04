package ca.qc.ircm.maxquant2saint.saint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Services for SAINT.
 */
public class SaintService {
  /**
   * Write samples as a SAINT bait file.
   *
   * @param samples
   *          samples
   * @param file
   *          file
   */
  public void writeBaits(List<Sample> samples, Path file) {
    try (SaintBaitWriter writer = new SaintBaitWriter(Files.newBufferedWriter(file))) {
      for (Sample sample : samples) {
        writer.writeSample(sample);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Could not write bait file " + file, e);
    }
  }

  /**
   * Write preys as a SAINT prey file.
   *
   * @param preys
   *          preys
   * @param file
   *          file
   */
  public void writePreys(List<Prey> preys, Path file) {
    try (SaintPreyWriter writer = new SaintPreyWriter(Files.newBufferedWriter(file))) {
      for (Prey prey : preys) {
        writer.writePrey(prey);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Could not write prey file " + file, e);
    }
  }

  /**
   * Write interactions as a SAINT interaction file.
   *
   * @param interactions
   *          interactions
   * @param file
   *          file
   */
  public void writeInteractions(List<Interaction> interactions, Path file) {
    try (
        SaintInteractionWriter writer = new SaintInteractionWriter(Files.newBufferedWriter(file))) {
      for (Interaction interaction : interactions) {
        writer.writeInteraction(interaction);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Could not write interaction file " + file, e);
    }
  }
}

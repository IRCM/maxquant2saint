package ca.qc.ircm.maxquant2saint.fasta;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;

/**
 * Parses Fasta files.
 */
public class FastaParser {
  private static final String ENTRY_STARTSWITH = ">";

  /**
   * Parses Fasta file.
   *
   * @param file
   *          file
   * @param handler
   *          handles Fasta entries
   * @throws IOException
   *           could not parse file
   */
  public void parse(Path file, BiConsumer<String, String> handler) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(file)) {
      BiConsumer<String, StringBuilder> handleEntry = (name, sequence) -> {
        if (name != null) {
          handler.accept(name, sequence.toString());
        }
        sequence.setLength(0);
      };
      String name = null;
      StringBuilder sequence = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith(ENTRY_STARTSWITH)) {
          handleEntry.accept(name, sequence);
          name = line.substring(ENTRY_STARTSWITH.length());
        } else {
          sequence.append(line);
        }
      }
      handleEntry.accept(name, sequence);
    }
  }
}

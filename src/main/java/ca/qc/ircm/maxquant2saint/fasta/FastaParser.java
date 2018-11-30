/*
 * Copyright (c) 2018 Institut de recherches cliniques de Montreal (IRCM)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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

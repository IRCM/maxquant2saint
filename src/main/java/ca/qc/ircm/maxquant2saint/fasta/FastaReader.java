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

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

  /**
   * Writes interaction in SAINT format.
   * 
   * @param sample
   *          sample name
   * @param bait
   *          bait name
   * @param prey
   *          prey name
   * @param intensity
   *          intensity
   * @throws IOException
   *           could not write to writer
   */
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

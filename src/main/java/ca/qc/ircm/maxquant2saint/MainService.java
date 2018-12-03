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

package ca.qc.ircm.maxquant2saint;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Main service.
 */
@Service
public class MainService {
  @Inject
  private MaxquantToSaintConverter converter;
  @Inject
  private ConversionConfiguration configuration;

  protected MainService() {
  }

  protected MainService(MaxquantToSaintConverter converter, ConversionConfiguration configuration) {
    this.converter = converter;
    this.configuration = configuration;
  }

  /**
   * Convert MaxQuant protein groups file to SAINT input files.
   *
   * @param args
   *          command line arguments
   */
  public void main(String[] args) {
    List<String> fileArgs = Arrays.asList(args).stream().filter(arg -> !arg.startsWith("--"))
        .collect(Collectors.toList());
    Path file = configuration.getFile().toPath();
    if (fileArgs.size() >= 1) {
      file = Paths.get(fileArgs.get(0));
    }
    Path fasta = configuration.getFasta().toPath();
    if (fileArgs.size() >= 2) {
      fasta = Paths.get(fileArgs.get(1));
    }
    converter.convert(file, fasta);
  }
}

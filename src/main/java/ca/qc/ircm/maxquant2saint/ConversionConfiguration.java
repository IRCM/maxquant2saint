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

import ca.qc.ircm.maxquant2saint.maxquant.Intensity;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = ConversionConfiguration.PREFIX)
public class ConversionConfiguration {
  public static final String PREFIX = "conversion";
  private File file;
  private File fasta;
  private Intensity intensity;
  private Map<String, List<String>> baits;

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public File getFasta() {
    return fasta;
  }

  public void setFasta(File fasta) {
    this.fasta = fasta;
  }

  public Intensity getIntensity() {
    return intensity;
  }

  public void setIntensity(Intensity intensity) {
    this.intensity = intensity;
  }

  public Map<String, List<String>> getBaits() {
    return baits;
  }

  public void setBaits(Map<String, List<String>> baits) {
    this.baits = baits;
  }
}

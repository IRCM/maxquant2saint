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
  private String bait;
  private String control;

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

  public String getBait() {
    return bait;
  }

  public void setBait(String bait) {
    this.bait = bait;
  }

  public String getControl() {
    return control;
  }

  public void setControl(String control) {
    this.control = control;
  }
}

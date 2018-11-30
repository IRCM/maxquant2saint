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

import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.LFQ;
import static org.junit.Assert.assertEquals;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.io.File;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class ConversionConfigurationTest {
  @Inject
  private ConversionConfiguration configuration;

  @Test
  public void defaultValues() throws Throwable {
    assertEquals(new File("proteinGroups.txt"), configuration.getFile());
    assertEquals(new File("human.fasta"), configuration.getFasta());
    assertEquals(LFQ, configuration.getIntensity());
    assertEquals(2, configuration.getBaits().size());
    assertEquals(3, configuration.getBaits().get("flag").size());
    assertEquals("flag_01", configuration.getBaits().get("flag").get(0));
    assertEquals("flag_02", configuration.getBaits().get("flag").get(1));
    assertEquals("flag_03", configuration.getBaits().get("flag").get(2));
    assertEquals(2, configuration.getBaits().get("polr2a").size());
    assertEquals("polr2a_01", configuration.getBaits().get("polr2a").get(0));
    assertEquals("polr2a_02", configuration.getBaits().get("polr2a").get(1));
  }
}

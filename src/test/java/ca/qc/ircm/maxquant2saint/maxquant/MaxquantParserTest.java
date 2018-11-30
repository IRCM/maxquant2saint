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

package ca.qc.ircm.maxquant2saint.maxquant;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class MaxquantParserTest {
  private static final double DELTA = 0.01;
  private MaxquantParser maxquantParser;
  @Mock
  private MaxquantConfiguration configuration;
  @Mock
  private MaxquantConfiguration.Headers configurationHeaders;
  @Mock
  private Consumer<MaxquantProteinGroup> handler;
  @Captor
  private ArgumentCaptor<MaxquantProteinGroup> groupCaptor;

  /**
   * Before test.
   */
  @Before
  public void beforeTest() {
    maxquantParser = new MaxquantParser(configuration);
    when(configuration.getHeaders()).thenReturn(configurationHeaders);
    when(configurationHeaders.getGeneNames()).thenReturn("Gene names");
    when(configurationHeaders.getProteinIds()).thenReturn("Protein IDs");
  }

  @Test
  public void parse_Lfq() throws Throwable {
    Path file = Paths.get(getClass().getResource("/proteinGroups.txt").toURI());
    maxquantParser.parse(file, "LFQ intensity (.*)", handler);
    verify(handler, times(27)).accept(groupCaptor.capture());
    MaxquantProteinGroup group = groupCaptor.getAllValues().get(1);
    assertEquals(5, group.proteinIds.size());
    assertEquals("Q9UHW5", group.proteinIds.get(0));
    assertEquals("H0YHZ5", group.proteinIds.get(4));
    assertEquals(1, group.geneNames.size());
    assertEquals("GPN3", group.geneNames.get(0));
    assertEquals(19, group.intensities.size());
    assertEquals(0.0, group.intensities.get("FLAG_01"), DELTA);
    assertEquals(15722000.0, group.intensities.get("POLR1C_N32I_01"), DELTA);
    assertEquals(1864800.0, group.intensities.get("POLR1C_N74S_02"), DELTA);
    assertEquals(10101000.0, group.intensities.get("POLR1C_WT_02"), DELTA);
    assertEquals(5490800.0, group.intensities.get("POLR1C_R279Q_03"), DELTA);
    group = groupCaptor.getAllValues().get(18);
    assertEquals(6, group.proteinIds.size());
    assertEquals("Q9NW08", group.proteinIds.get(0));
    assertEquals("F8VRU2", group.proteinIds.get(5));
    assertEquals(2, group.geneNames.size());
    assertEquals("POLR3B", group.geneNames.get(0));
    assertEquals("DKFZp686D10173", group.geneNames.get(1));
    assertEquals(19, group.intensities.size());
    assertEquals(1003200.0, group.intensities.get("FLAG_01"), DELTA);
    assertEquals(193340000.0, group.intensities.get("POLR1C_N32I_01"), DELTA);
    assertEquals(83871000.0, group.intensities.get("POLR1C_N74S_02"), DELTA);
    assertEquals(625780000.0, group.intensities.get("POLR1C_WT_02"), DELTA);
    assertEquals(297260000.0, group.intensities.get("POLR1C_R279Q_03"), DELTA);
  }

  @Test
  public void parse_Peptides() throws Throwable {
    Path file = Paths.get(getClass().getResource("/proteinGroups.txt").toURI());
    maxquantParser.parse(file, "Peptides (.*)", handler);
    verify(handler, times(27)).accept(groupCaptor.capture());
    MaxquantProteinGroup group = groupCaptor.getAllValues().get(1);
    assertEquals(5, group.proteinIds.size());
    assertEquals("Q9UHW5", group.proteinIds.get(0));
    assertEquals("H0YHZ5", group.proteinIds.get(4));
    assertEquals(1, group.geneNames.size());
    assertEquals("GPN3", group.geneNames.get(0));
    assertEquals(19, group.intensities.size());
    assertEquals(0.0, group.intensities.get("FLAG_01"), DELTA);
    assertEquals(7.0, group.intensities.get("POLR1C_N32I_01"), DELTA);
    assertEquals(4.0, group.intensities.get("POLR1C_N74S_02"), DELTA);
    assertEquals(7.0, group.intensities.get("POLR1C_WT_02"), DELTA);
    assertEquals(5.0, group.intensities.get("POLR1C_R279Q_03"), DELTA);
    group = groupCaptor.getAllValues().get(18);
    assertEquals(6, group.proteinIds.size());
    assertEquals("Q9NW08", group.proteinIds.get(0));
    assertEquals("F8VRU2", group.proteinIds.get(5));
    assertEquals(2, group.geneNames.size());
    assertEquals("POLR3B", group.geneNames.get(0));
    assertEquals("DKFZp686D10173", group.geneNames.get(1));
    assertEquals(19, group.intensities.size());
    assertEquals(6.0, group.intensities.get("FLAG_01"), DELTA);
    assertEquals(52.0, group.intensities.get("POLR1C_N32I_01"), DELTA);
    assertEquals(51.0, group.intensities.get("POLR1C_N74S_02"), DELTA);
    assertEquals(58.0, group.intensities.get("POLR1C_WT_02"), DELTA);
    assertEquals(67.0, group.intensities.get("POLR1C_R279Q_03"), DELTA);
  }
}

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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class SequenceServiceTest {
  private SequenceService sequenceService;
  @Mock
  private FastaConfiguration configuration;

  @Before
  public void beforeTest() {
    sequenceService = new SequenceService(configuration);
    when(configuration.getProteinId()).thenReturn(">.*\\|(.*)\\|");
  }

  @Test
  public void sequencesLength() throws Throwable {
    Path file = Paths.get(getClass().getResource("/human-trimmed.fasta").toURI());
    Map<String, Integer> lengths = sequenceService.sequencesLength(file);
    assertEquals(5, lengths.size());
    assertEquals((Integer) 128, lengths.get("Q9H9K5"));
    assertEquals((Integer) 68, lengths.get("P31689"));
    assertEquals((Integer) 41, lengths.get("P08246"));
    assertEquals((Integer) 72, lengths.get("P63244"));
    assertEquals((Integer) 69, lengths.get("P10144"));
  }
}

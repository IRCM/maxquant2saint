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

import static ca.qc.ircm.maxquant2saint.maxquant.Intensity.LFQ;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class MaxquantServiceTest {
  private MaxquantService maxquantService;
  @Mock
  private MaxquantParser maxquantParser;
  @Mock
  private MaxquantProteinGroup group1;
  @Mock
  private MaxquantProteinGroup group2;
  @Mock
  private MaxquantProteinGroup group3;

  /**
   * Before test.
   */
  @Before
  public void beforeTest() {
    maxquantService = new MaxquantService(maxquantParser);
  }

  @Test
  public void proteinGroups() throws Throwable {
    Path file = Paths.get(getClass().getResource("/proteinGroups.txt").toURI());
    doAnswer(i -> {
      @SuppressWarnings("unchecked")
      Consumer<MaxquantProteinGroup> handler = (Consumer<MaxquantProteinGroup>) i.getArgument(2);
      handler.accept(group1);
      handler.accept(group2);
      handler.accept(group3);
      return null;
    }).when(maxquantParser).parse(any(), any(), any());
    List<MaxquantProteinGroup> groups = maxquantService.proteinGroups(file, LFQ);
    verify(maxquantParser).parse(eq(file), eq(LFQ), any());
    assertEquals(3, groups.size());
    assertEquals(group1, groups.get(0));
    assertEquals(group2, groups.get(1));
    assertEquals(group3, groups.get(2));
  }

  @Test(expected = IllegalStateException.class)
  public void proteinGroups_IoException() throws Throwable {
    Path file = Paths.get(getClass().getResource("/proteinGroups.txt").toURI());
    doThrow(new IOException("test")).when(maxquantParser).parse(any(), any(), any());
    maxquantService.proteinGroups(file, LFQ);
  }
}

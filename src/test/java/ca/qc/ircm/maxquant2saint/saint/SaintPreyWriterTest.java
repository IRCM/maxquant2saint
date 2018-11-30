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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class SaintPreyWriterTest {
  private SaintPreyWriter writer;
  private StringWriter delegate = new StringWriter();

  /**
   * Before test.
   */
  @Before
  public void beforeTest() {
    writer = new SaintPreyWriter(delegate);
  }

  @Test
  public void writePrey() throws Throwable {
    writer.writePrey("protein1", 200, "gene1");
    writer.writePrey("protein2", 210, "gene2");
    writer.writePrey("protein3", 300, "gene3");
    writer.writePrey("protein4", 190, "gene4");
    List<String> lines = Arrays.asList(delegate.toString().split("\r?\n"));
    assertEquals("protein1\t200\tgene1", lines.get(0));
    assertEquals("protein2\t210\tgene2", lines.get(1));
    assertEquals("protein3\t300\tgene3", lines.get(2));
    assertEquals("protein4\t190\tgene4", lines.get(3));
  }

  @Test
  public void close() throws Throwable {
    Writer mock = Mockito.mock(Writer.class);
    writer = new SaintPreyWriter(mock);
    writer.close();
    verify(mock).close();
  }
}

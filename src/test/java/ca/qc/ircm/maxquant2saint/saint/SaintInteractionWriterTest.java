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
public class SaintInteractionWriterTest {
  private SaintInteractionWriter writer;
  private StringWriter delegate = new StringWriter();

  /**
   * Before test.
   */
  @Before
  public void beforeTest() {
    writer = new SaintInteractionWriter(delegate);
  }

  @Test
  public void writeInteraction() throws Throwable {
    writer.writeInteraction("my_sample1", "my_control1", "gene1", 10.0);
    writer.writeInteraction("my_sample1", "my_control1", "gene2", 12.2);
    writer.writeInteraction("my_sample2", "my_bait1", "gene1", 20.47);
    writer.writeInteraction("my_sample2", "my_bait1", "gene2", 25.0);
    writer.writeInteraction("my_sample3", "my_bait2", "gene2", 32.0);
    writer.writeInteraction("my_sample4", "my_bait2", "gene2", 27.1);
    writer.writeInteraction("my_sample5", "my_control2", "gene1", 13.0);
    List<String> lines = Arrays.asList(delegate.toString().split("\r?\n"));
    assertEquals("my_sample1\tmy_control1\tgene1\t10", lines.get(0));
    assertEquals("my_sample1\tmy_control1\tgene2\t12.2", lines.get(1));
    assertEquals("my_sample2\tmy_bait1\tgene1\t20.47", lines.get(2));
    assertEquals("my_sample2\tmy_bait1\tgene2\t25", lines.get(3));
    assertEquals("my_sample3\tmy_bait2\tgene2\t32", lines.get(4));
    assertEquals("my_sample4\tmy_bait2\tgene2\t27.1", lines.get(5));
    assertEquals("my_sample5\tmy_control2\tgene1\t13", lines.get(6));
  }

  @Test
  public void close() throws Throwable {
    Writer mock = Mockito.mock(Writer.class);
    writer = new SaintInteractionWriter(mock);
    writer.close();
    verify(mock).close();
  }
}

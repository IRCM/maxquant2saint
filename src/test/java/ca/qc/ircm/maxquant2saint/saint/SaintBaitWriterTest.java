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
public class SaintBaitWriterTest {
  private SaintBaitWriter writer;
  private StringWriter delegate = new StringWriter();

  /**
   * Before test.
   */
  @Before
  public void beforeTest() {
    writer = new SaintBaitWriter(delegate);
  }

  @Test
  public void writeBait() throws Throwable {
    writer.writeSample(new Sample("my_sample0", "my_control0", true));
    writer.writeSample(new Sample("my_sample1", "my_bait1", false));
    writer.writeSample(new Sample("my_sample2", "my_bait1", false));
    writer.writeSample(new Sample("my_sample3", "my_bait2", false));
    writer.writeSample(new Sample("my_sample4", "my_control1", true));
    writer.writeSample(new Sample("my_sample5", "my_control2", true));
    writer.writeSample(new Sample("my_sample6", null, true));
    List<String> lines = Arrays.asList(delegate.toString().split("\r?\n"));
    assertEquals("my_sample0\tmy_control0\tC", lines.get(0));
    assertEquals("my_sample1\tmy_bait1\tT", lines.get(1));
    assertEquals("my_sample2\tmy_bait1\tT", lines.get(2));
    assertEquals("my_sample3\tmy_bait2\tT", lines.get(3));
    assertEquals("my_sample4\tmy_control1\tC", lines.get(4));
    assertEquals("my_sample5\tmy_control2\tC", lines.get(5));
    assertEquals("my_sample6\t\tC", lines.get(6));
  }

  @Test
  public void close() throws Throwable {
    Writer mock = Mockito.mock(Writer.class);
    writer = new SaintBaitWriter(mock);
    writer.close();
    verify(mock).close();
  }
}

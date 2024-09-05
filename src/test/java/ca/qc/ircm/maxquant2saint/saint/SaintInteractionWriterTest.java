package ca.qc.ircm.maxquant2saint.saint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@TestAnnotations
public class SaintInteractionWriterTest {
  private SaintInteractionWriter writer;
  private StringWriter delegate = new StringWriter();

  /**
   * Before test.
   */
  @BeforeEach
  public void beforeTest() {
    writer = new SaintInteractionWriter(delegate);
  }

  @Test
  public void writeInteraction() throws Throwable {
    writer.writeInteraction(
        new Interaction(new Sample("my_sample1", "my_control1", true), new Prey("gene1"), 10.0));
    writer.writeInteraction(
        new Interaction(new Sample("my_sample1", "my_control1", true), new Prey("gene2"), 12.2));
    writer.writeInteraction(
        new Interaction(new Sample("my_sample2", "my_bait1", false), new Prey("gene1"), 20.47));
    writer.writeInteraction(
        new Interaction(new Sample("my_sample2", "my_bait1", false), new Prey("gene2"), 25.0));
    writer.writeInteraction(
        new Interaction(new Sample("my_sample3", "my_bait2", false), new Prey("gene2"), 32.0));
    writer.writeInteraction(
        new Interaction(new Sample("my_sample4", "my_bait2", false), new Prey("gene2"), 27.1));
    writer.writeInteraction(
        new Interaction(new Sample("my_sample5", null, true), new Prey("gene1"), 13.0));
    List<String> lines = Arrays.asList(delegate.toString().split("\r?\n"));
    assertEquals("my_sample1\tmy_control1\tgene1\t10", lines.get(0));
    assertEquals("my_sample1\tmy_control1\tgene2\t12.2", lines.get(1));
    assertEquals("my_sample2\tmy_bait1\tgene1\t20.47", lines.get(2));
    assertEquals("my_sample2\tmy_bait1\tgene2\t25", lines.get(3));
    assertEquals("my_sample3\tmy_bait2\tgene2\t32", lines.get(4));
    assertEquals("my_sample4\tmy_bait2\tgene2\t27.1", lines.get(5));
    assertEquals("my_sample5\t\tgene1\t13", lines.get(6));
  }

  @Test
  public void close() throws Throwable {
    Writer mock = Mockito.mock(Writer.class);
    writer = new SaintInteractionWriter(mock);
    writer.close();
    verify(mock).close();
  }
}

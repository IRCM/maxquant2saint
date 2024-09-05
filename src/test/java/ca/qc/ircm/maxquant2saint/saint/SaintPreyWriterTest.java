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
public class SaintPreyWriterTest {
  private SaintPreyWriter writer;
  private StringWriter delegate = new StringWriter();

  /**
   * Before test.
   */
  @BeforeEach
  public void beforeTest() {
    writer = new SaintPreyWriter(delegate);
  }

  @Test
  public void writePrey() throws Throwable {
    writer.writePrey(new Prey("protein1", 200, "gene1"));
    writer.writePrey(new Prey("protein2", 210, "gene2"));
    writer.writePrey(new Prey("protein3", 300, "gene3"));
    writer.writePrey(new Prey("protein4", 190, null));
    List<String> lines = Arrays.asList(delegate.toString().split("\r?\n"));
    assertEquals("protein1\t200\tgene1", lines.get(0));
    assertEquals("protein2\t210\tgene2", lines.get(1));
    assertEquals("protein3\t300\tgene3", lines.get(2));
    assertEquals("protein4\t190\t", lines.get(3));
  }

  @Test
  public void close() throws Throwable {
    Writer mock = Mockito.mock(Writer.class);
    writer = new SaintPreyWriter(mock);
    writer.close();
    verify(mock).close();
  }
}

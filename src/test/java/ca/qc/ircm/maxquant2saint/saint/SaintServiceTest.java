package ca.qc.ircm.maxquant2saint.saint;

import static org.junit.Assert.assertEquals;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class SaintServiceTest {
  private SaintService service;
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  /**
   * Before test.
   */
  @Before
  public void beforeTest() {
    service = new SaintService();
  }

  @Test
  public void writeBaits() throws Throwable {
    List<Sample> samples = new ArrayList<>();
    samples.add(new Sample("my_sample0", "my_control0", true));
    samples.add(new Sample("my_sample1", "my_bait1", false));
    samples.add(new Sample("my_sample2", "my_bait1", false));
    samples.add(new Sample("my_sample3", "my_bait2", false));
    samples.add(new Sample("my_sample4", "my_control1", true));
    samples.add(new Sample("my_sample5", "my_control2", true));
    samples.add(new Sample("my_sample6", null, true));
    Path file = temporaryFolder.newFile("bait.txt").toPath();
    service.writeBaits(samples, file);
    List<String> lines = Files.readAllLines(file);
    assertEquals("my_sample0\tmy_control0\tC", lines.get(0));
    assertEquals("my_sample1\tmy_bait1\tT", lines.get(1));
    assertEquals("my_sample2\tmy_bait1\tT", lines.get(2));
    assertEquals("my_sample3\tmy_bait2\tT", lines.get(3));
    assertEquals("my_sample4\tmy_control1\tC", lines.get(4));
    assertEquals("my_sample5\tmy_control2\tC", lines.get(5));
    assertEquals("my_sample6\t\tC", lines.get(6));
  }

  @Test
  public void writePreys() throws Throwable {
    List<Prey> preys = new ArrayList<>();
    preys.add(new Prey("protein1", 200, "gene1"));
    preys.add(new Prey("protein2", 210, "gene2"));
    preys.add(new Prey("protein3", 300, "gene3"));
    preys.add(new Prey("protein4", 190, null));
    Path file = temporaryFolder.newFile("prey.txt").toPath();
    service.writePreys(preys, file);
    List<String> lines = Files.readAllLines(file);
    assertEquals("protein1\t200\tgene1", lines.get(0));
    assertEquals("protein2\t210\tgene2", lines.get(1));
    assertEquals("protein3\t300\tgene3", lines.get(2));
    assertEquals("protein4\t190\t", lines.get(3));
  }

  @Test
  public void writeInteractions() throws Throwable {
    List<Interaction> interactions = new ArrayList<>();
    interactions.add(
        new Interaction(new Sample("my_sample1", "my_control1", true), new Prey("gene1"), 10.0));
    interactions.add(
        new Interaction(new Sample("my_sample1", "my_control1", true), new Prey("gene2"), 12.2));
    interactions.add(
        new Interaction(new Sample("my_sample2", "my_bait1", false), new Prey("gene1"), 20.47));
    interactions
        .add(new Interaction(new Sample("my_sample2", "my_bait1", false), new Prey("gene2"), 25.0));
    interactions
        .add(new Interaction(new Sample("my_sample3", "my_bait2", false), new Prey("gene2"), 32.0));
    interactions
        .add(new Interaction(new Sample("my_sample4", "my_bait2", false), new Prey("gene2"), 27.1));
    interactions
        .add(new Interaction(new Sample("my_sample5", null, true), new Prey("gene1"), 13.0));
    Path file = temporaryFolder.newFile("interactions.txt").toPath();
    service.writeInteractions(interactions, file);
    List<String> lines = Files.readAllLines(file);
    assertEquals("my_sample1\tmy_control1\tgene1\t10", lines.get(0));
    assertEquals("my_sample1\tmy_control1\tgene2\t12.2", lines.get(1));
    assertEquals("my_sample2\tmy_bait1\tgene1\t20.47", lines.get(2));
    assertEquals("my_sample2\tmy_bait1\tgene2\t25", lines.get(3));
    assertEquals("my_sample3\tmy_bait2\tgene2\t32", lines.get(4));
    assertEquals("my_sample4\tmy_bait2\tgene2\t27.1", lines.get(5));
    assertEquals("my_sample5\t\tgene1\t13", lines.get(6));
  }
}

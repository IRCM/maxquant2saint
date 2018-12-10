package ca.qc.ircm.maxquant2saint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.qc.ircm.maxquant2saint.fasta.SequenceService;
import ca.qc.ircm.maxquant2saint.maxquant.Intensity;
import ca.qc.ircm.maxquant2saint.maxquant.MaxquantProteinGroup;
import ca.qc.ircm.maxquant2saint.maxquant.MaxquantService;
import ca.qc.ircm.maxquant2saint.saint.Interaction;
import ca.qc.ircm.maxquant2saint.saint.Prey;
import ca.qc.ircm.maxquant2saint.saint.SaintService;
import ca.qc.ircm.maxquant2saint.saint.Sample;
import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class MaxquantToSaintConverterTest {
  private static final int MAX_GROUPS = 200;
  private static final double DELTA = 0.000001;
  private MaxquantToSaintConverter converter;
  @Mock
  private ConversionConfiguration configuration;
  @Mock
  private MaxquantService maxquantService;
  @Mock
  private SequenceService sequenceService;
  @Mock
  private SaintService saintService;
  @Captor
  private ArgumentCaptor<Path> fileCaptor;
  @Captor
  private ArgumentCaptor<List<Sample>> samplesCaptor;
  @Captor
  private ArgumentCaptor<List<Prey>> preysCaptor;
  @Captor
  private ArgumentCaptor<List<Interaction>> interactionsCaptor;
  private Intensity intensity = Intensity.LFQ;
  private Path file = Paths.get("proteinGroup.txt");
  private Path fasta = Paths.get("human.fasta");
  private List<Sample> samples;
  private List<MaxquantProteinGroup> groups;
  private Map<String, Integer> lengths;
  private Random random = new Random();

  /**
   * Before test.
   */
  @Before
  public void beforeTest() {
    converter =
        new MaxquantToSaintConverter(configuration, maxquantService, sequenceService, saintService);
    when(configuration.getIntensity()).thenReturn(intensity);
    when(configuration.getBait()).thenReturn("(.*)_\\d+");
    when(configuration.getControl()).thenReturn("FLAG.*");
    samples = generateSamples();
    groups = generateGroups(samples);
    lengths = generateLengths(groups);
    when(maxquantService.proteinGroups(any(), any())).thenReturn(groups);
    when(sequenceService.sequencesLength(any())).thenReturn(lengths);
  }

  private List<Sample> generateSamples() {
    List<Sample> samples = new ArrayList<>();
    samples.add(new Sample("FLAG_1", "FLAG_1", true));
    samples.add(new Sample("FLAG_2", "FLAG_2", true));
    samples.add(new Sample("FLAG_3", "FLAG_3", true));
    samples.add(new Sample("FLAG_4", "FLAG_4", true));
    samples.add(new Sample("POLR2A_1", "POLR2A", false));
    samples.add(new Sample("POLR2A_2", "POLR2A", false));
    samples.add(new Sample("POLR2B_1", "POLR2B", false));
    samples.add(new Sample("POLR2B_2", "POLR2B", false));
    samples.add(new Sample("OTHERNAME", "OTHERNAME", false));
    return samples;
  }

  private List<MaxquantProteinGroup> generateGroups(List<Sample> samples) {
    return IntStream.range(0, MAX_GROUPS).mapToObj(i -> generateGroup(samples))
        .collect(Collectors.toList());
  }

  private MaxquantProteinGroup generateGroup(List<Sample> samples) {
    MaxquantProteinGroup group = new MaxquantProteinGroup();
    group.proteinIds = IntStream.range(0, random.nextInt(4) + 1)
        .mapToObj(i -> RandomStringUtils.randomAlphanumeric(10)).collect(Collectors.toList());
    group.geneNames = IntStream.range(0, random.nextInt(2) + 1)
        .mapToObj(i -> RandomStringUtils.randomAlphanumeric(8)).collect(Collectors.toList());
    group.intensities = samples.stream()
        .collect(Collectors.toMap(sample -> sample.name, sample -> random.nextDouble()));
    return group;
  }

  private Map<String, Integer> generateLengths(List<MaxquantProteinGroup> groups) {
    return generateProteinLengths(
        groups.stream().flatMap(group -> group.proteinIds.stream()).collect(Collectors.toList()));
  }

  private Map<String, Integer> generateProteinLengths(List<String> proteins) {
    return proteins.stream()
        .collect(Collectors.toMap(protein -> protein, protein -> random.nextInt(50) + 5));
  }

  private int max(List<String> proteins) {
    return proteins.stream().map(protein -> lengths.get(protein)).mapToInt(val -> val).max()
        .orElse(-1);
  }

  private MaxquantProteinGroup group(Interaction interaction) {
    return groups.stream()
        .filter(group -> interaction.prey.id
            .equals(group.proteinIds.stream().collect(Collectors.joining(";"))))
        .findFirst().orElse(null);
  }

  @Test
  public void convert() {
    converter.convert(file, fasta);

    verify(maxquantService).proteinGroups(file, intensity);
    verify(sequenceService).sequencesLength(fasta);
    verify(saintService).writeBaits(samplesCaptor.capture(), fileCaptor.capture());
    List<Sample> samples = samplesCaptor.getValue();
    assertEquals(this.samples.size(), samples.size());
    for (int i = 0; i < samples.size(); i++) {
      Sample sample = samples.get(i);
      Sample expected = this.samples.stream().filter(sam -> sam.name.equals(sample.name))
          .findFirst().orElse(null);
      assertNotNull(expected);
      assertEquals(expected.bait, sample.bait);
      assertEquals(expected.control, sample.control);
    }
    assertEquals(file.resolveSibling("bait.txt"), fileCaptor.getValue());
    verify(saintService).writePreys(preysCaptor.capture(), fileCaptor.capture());
    List<Prey> preys = preysCaptor.getValue();
    assertEquals(groups.size(), preys.size());
    for (int i = 0; i < preys.size(); i++) {
      Prey prey = preys.get(i);
      MaxquantProteinGroup group = groups.get(i);
      assertEquals(group.proteinIds.stream().collect(Collectors.joining(";")), prey.id);
      assertEquals(group.geneNames.stream().collect(Collectors.joining(";")), prey.gene);
      assertEquals(max(group.proteinIds), prey.length);
    }
    assertEquals(file.resolveSibling("prey.txt"), fileCaptor.getValue());
    verify(saintService).writeInteractions(interactionsCaptor.capture(), fileCaptor.capture());
    List<Interaction> interactions = interactionsCaptor.getValue();
    for (Interaction interaction : interactions) {
      MaxquantProteinGroup group = group(interaction);
      assertEquals(group.proteinIds.stream().collect(Collectors.joining(";")), interaction.prey.id);
      assertEquals(group.geneNames.stream().collect(Collectors.joining(";")),
          interaction.prey.gene);
      assertEquals(max(group.proteinIds), interaction.prey.length);
      String sampleName = interaction.sample.name;
      Sample sample =
          samples.stream().filter(sam -> sam.name.equals(sampleName)).findFirst().orElse(null);
      assertEquals(sample.name, interaction.sample.name);
      assertEquals(sample.bait, interaction.sample.bait);
      assertEquals(sample.control, interaction.sample.control);
      assertEquals(group.intensities.get(sample.name), interaction.intensity, DELTA);
    }
    assertEquals(file.resolveSibling("interactions.txt"), fileCaptor.getValue());
  }
}

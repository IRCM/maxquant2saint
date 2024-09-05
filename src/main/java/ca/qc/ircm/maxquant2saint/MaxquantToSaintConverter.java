package ca.qc.ircm.maxquant2saint;

import ca.qc.ircm.maxquant2saint.fasta.SequenceService;
import ca.qc.ircm.maxquant2saint.maxquant.Intensity;
import ca.qc.ircm.maxquant2saint.maxquant.MaxquantProteinGroup;
import ca.qc.ircm.maxquant2saint.maxquant.MaxquantService;
import ca.qc.ircm.maxquant2saint.saint.Interaction;
import ca.qc.ircm.maxquant2saint.saint.Prey;
import ca.qc.ircm.maxquant2saint.saint.SaintService;
import ca.qc.ircm.maxquant2saint.saint.Sample;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converts MaxQuant protein groups file to SAINT input files.
 */
@Component
public class MaxquantToSaintConverter {
  // SAINT has a maximum characters for protein length of around 950 characters, so limit protein ids to 40.
  private static final int MAX_PROTEIN_IDS = 40;
  private static final int MAX_GENE_IDS = 4;
  private static final double DELTA = 0.0000000001;
  private static final String ELEMENT_SEPARATOR = ";";
  private static final Logger logger = LoggerFactory.getLogger(MaxquantToSaintConverter.class);
  @Autowired
  private ConversionConfiguration configuration;
  @Autowired
  private MaxquantService maxquantService;
  @Autowired
  private SequenceService sequenceService;
  @Autowired
  private SaintService saintService;

  protected MaxquantToSaintConverter() {
  }

  protected MaxquantToSaintConverter(ConversionConfiguration configuration,
      MaxquantService maxquantService, SequenceService sequenceService, SaintService saintService) {
    this.configuration = configuration;
    this.maxquantService = maxquantService;
    this.sequenceService = sequenceService;
    this.saintService = saintService;
  }

  /**
   * Convert MaxQuant protein groups file to SAINT input files.
   *
   * @param file
   *          MaxQuant protein groups file
   * @param fasta
   *          FASTA file used in MaxQuant search
   */
  public void convert(Path file, Path fasta) {
    Intensity intensity = configuration.getIntensity();
    Pattern baitPattern = Pattern.compile(configuration.getBait());
    Pattern controlPattern = Pattern.compile(configuration.getControl());
    Function<String, Boolean> isControl = sampleName -> {
      return controlPattern.matcher(sampleName).matches();
    };
    Function<String, String> baitName = sampleName -> {
      boolean control = isControl.apply(sampleName);
      Matcher baitMatcher = baitPattern.matcher(sampleName);
      return !control && baitMatcher.matches() ? baitMatcher.group(1) : sampleName;
    };
    List<MaxquantProteinGroup> groups = maxquantService.proteinGroups(file, intensity);
    if (groups.isEmpty()) {
      logger.warn("No protein groups in file {}", file);
    }
    Function<String, Sample> createSample = sampleName -> {
      Sample sample = new Sample();
      sample.name = sampleName;
      sample.bait = baitName.apply(sampleName);
      sample.control = isControl.apply(sampleName);
      return sample;
    };
    final List<Sample> samples = groups.get(0).intensities.keySet().stream()
        .map(sampleName -> createSample.apply(sampleName)).collect(Collectors.toList());
    final Map<String, Integer> lengths;
    final int averageLength;
    if (fasta != null) {
      lengths = sequenceService.sequencesLength(fasta);
      int tempAverageLength = (int) Math.round(groups.stream()
          .flatMap(group -> group.proteinIds.stream()).map(protein -> lengths.get(protein))
          .filter(value -> value != null).mapToInt(value -> value).average().orElse(0));
      if (tempAverageLength == 0) {
        tempAverageLength = (int) Math
            .round(lengths.values().stream().mapToInt(value -> value).average().orElse(0));
      }
      if (tempAverageLength == 0) {
        logger.warn("Cannot find length of any proteins");
      }
      averageLength = tempAverageLength;
    } else {
      logger.warn("Cannot find length of any proteins");
      lengths = new HashMap<>();
      averageLength = 0;
    }
    Function<MaxquantProteinGroup, Prey> createPrey = group -> {
      int length = group.proteinIds.stream().map(protein -> lengths.get(protein))
          .filter(value -> value != null).mapToInt(value -> value).max().orElseGet(() -> {
            logger.info("using average length {} for protein group {}", averageLength,
                group.proteinIds);
            return averageLength;
          });
      return new Prey(
          group.proteinIds.stream().limit(MAX_PROTEIN_IDS)
              .collect(Collectors.joining(ELEMENT_SEPARATOR)),
          length, group.geneNames.stream().limit(MAX_GENE_IDS)
              .collect(Collectors.joining(ELEMENT_SEPARATOR)));
    };
    List<Prey> preys =
        groups.stream().map(group -> createPrey.apply(group)).collect(Collectors.toList());
    List<Interaction> interactions = new ArrayList<>();
    for (MaxquantProteinGroup group : groups) {
      for (Map.Entry<String, Double> intensities : group.intensities.entrySet()) {
        if (Math.abs(intensities.getValue()) > DELTA) {
          Sample sample = createSample.apply(intensities.getKey());
          Prey prey = createPrey.apply(group);
          interactions.add(new Interaction(sample, prey, intensities.getValue()));
        }
      }
    }
    //    double intensityMin =
    //        interactions.stream().mapToDouble(inter -> inter.intensity).min().orElse(1.0);
    //    interactions.stream().forEach(inter -> inter.intensity = inter.intensity / intensityMin);
    final Path baitFile = file.resolveSibling("bait.txt");
    final Path preyFile = file.resolveSibling("prey.txt");
    final Path interactionsFile = file.resolveSibling("interactions.txt");
    saintService.writeBaits(samples, baitFile);
    saintService.writePreys(preys, preyFile);
    saintService.writeInteractions(interactions, interactionsFile);
  }

  private double log2(double value) {
    return Math.log(value) / Math.log(2);
  }
}

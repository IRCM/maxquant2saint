package ca.qc.ircm.maxquant2saint.maxquant;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Inject;
import org.springframework.stereotype.Component;

/**
 * Parses MaxQuant files.
 */
@Component
public class MaxquantParser {
  private static final String SEPARATOR = "\t";
  private static final String GENE_NAME_SEPARATOR = ";";
  @Inject
  private MaxquantConfiguration configuration;

  protected MaxquantParser() {
  }

  protected MaxquantParser(MaxquantConfiguration configuration) {
    this.configuration = configuration;
  }

  /**
   * Parses MaxQuant files.
   *
   * @param file
   *          file
   * @param intensityHeader
   *          pattern of the header of intensities column to parse
   * @param handler
   *          handles protein groups
   * @throws IOException
   *           could not parse file
   */
  public void parse(Path file, String intensityHeader, Consumer<MaxquantProteinGroup> handler)
      throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(file)) {
      String headerLine = reader.readLine();
      if (headerLine == null) {
        return;
      }
      String[] headers = headerLine.split(SEPARATOR, -1);
      Pattern intensityHeaderPattern = Pattern.compile(intensityHeader);
      List<SampleHeader> sampleHeaders = IntStream.range(0, headers.length)
          .filter(column -> intensityHeaderPattern.matcher(headers[column]).matches()).boxed()
          .map(column -> {
            Matcher matcher = intensityHeaderPattern.matcher(headers[column]);
            matcher.matches();
            return new SampleHeader(matcher.group(1), column);
          }).collect(Collectors.toList());
      Pattern geneNamesHeaderPattern = Pattern.compile(configuration.getHeaders().getGeneNames());
      int geneNamesColumn = IntStream.range(0, headers.length)
          .filter(column -> geneNamesHeaderPattern.matcher(headers[column]).matches()).findFirst()
          .orElseThrow(() -> new IllegalStateException(
              "Column " + configuration.getHeaders().getGeneNames() + " is missing"));
      String line;
      while ((line = reader.readLine()) != null) {
        String[] columns = line.split(SEPARATOR, -1);
        MaxquantProteinGroup group = new MaxquantProteinGroup();
        group.geneNames = Arrays.asList(columns[geneNamesColumn].split(GENE_NAME_SEPARATOR));
        group.intensities = sampleHeaders.stream().collect(Collectors.toMap(header -> header.name,
            header -> Double.parseDouble(columns[header.index])));
        handler.accept(group);
      }
    }
  }

  private static class SampleHeader {
    String name;
    int index;

    SampleHeader(String name, int index) {
      this.name = name;
      this.index = index;
    }
  }
}

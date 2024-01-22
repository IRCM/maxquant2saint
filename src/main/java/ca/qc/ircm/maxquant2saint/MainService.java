package ca.qc.ircm.maxquant2saint;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 * Main service.
 */
@Service
public class MainService {
  @Inject
  private MaxquantToSaintConverter converter;
  @Inject
  private ConversionConfiguration configuration;

  protected MainService() {
  }

  protected MainService(MaxquantToSaintConverter converter, ConversionConfiguration configuration) {
    this.converter = converter;
    this.configuration = configuration;
  }

  /**
   * Convert MaxQuant protein groups file to SAINT input files.
   *
   * @param args
   *          command line arguments
   */
  public void main(String[] args) {
    List<String> fileArgs = Arrays.asList(args).stream().filter(arg -> !arg.startsWith("--"))
        .collect(Collectors.toList());
    Path file = configuration.getFile().toPath();
    if (fileArgs.size() >= 1) {
      file = Paths.get(fileArgs.get(0));
    }
    Path fasta = configuration.getFasta().toPath();
    if (fileArgs.size() >= 2) {
      fasta = Paths.get(fileArgs.get(1));
    }
    converter.convert(file, fasta);
  }
}

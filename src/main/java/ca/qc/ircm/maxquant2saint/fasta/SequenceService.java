package ca.qc.ircm.maxquant2saint.fasta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Sequence service.
 */
@Service
public class SequenceService {
  private static final Logger logger = LoggerFactory.getLogger(SequenceService.class);
  @Autowired
  private FastaConfiguration configuration;

  protected SequenceService() {
  }

  protected SequenceService(FastaConfiguration configuration) {
    this.configuration = configuration;
  }

  /**
   * Finds sequence's length in FASTA file.
   *
   * @param file
   *          FASTA file
   * @return sequence's length in FASTA file
   */
  public Map<String, Integer> sequencesLength(Path file) {
    try (FastaReader reader = new FastaReader(Files.newBufferedReader(file))) {
      Map<String, Integer> sequences = new HashMap<>();
      Pattern proteinIdPattern = Pattern.compile(configuration.getProteinId());
      Sequence sequence;
      while ((sequence = reader.readSequence()) != null) {
        Matcher matcher = proteinIdPattern.matcher(sequence.header);
        if (matcher.find()) {
          sequences.put(matcher.group(1), sequence.sequence.length());
        }
      }
      if (sequences.isEmpty()) {
        logger.warn("Could not find any sequence in file {} with protein id pattern {}", file,
            proteinIdPattern.pattern());
      }
      return sequences;
    } catch (IOException e) {
      throw new IllegalStateException("Could not find sequences length in file" + file, e);
    }
  }
}

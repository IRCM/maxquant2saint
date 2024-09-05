package ca.qc.ircm.maxquant2saint.maxquant;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for MaxQuant.
 */
@Service
public class MaxquantService {
  @Autowired
  private MaxquantParser maxquantParser;

  protected MaxquantService() {
  }

  protected MaxquantService(MaxquantParser maxquantParser) {
    this.maxquantParser = maxquantParser;
  }

  /**
   * Returns all protein groups from MaxQuant's proteinGroups.txt file.
   *
   * @param file
   *          file
   * @param intensity
   *          intensity
   * @return all protein groups from MaxQuant's proteinGroups.txt file
   */
  public List<MaxquantProteinGroup> proteinGroups(Path file, Intensity intensity) {
    List<MaxquantProteinGroup> groups = new ArrayList<>();
    try {
      maxquantParser.parse(file, intensity, group -> {
        groups.add(group);
      });
      return groups;
    } catch (IOException e) {
      throw new IllegalStateException("Could not parse file " + file, e);
    }
  }
}

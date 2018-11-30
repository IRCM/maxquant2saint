package ca.qc.ircm.maxquant2saint.maxquant;

import java.util.List;
import java.util.Map;

/**
 * One protein group data in a MaxQuant file.
 */
public class MaxquantProteinGroup {
  public List<String> proteinIds;
  public List<String> geneNames;
  public Map<String, Double> intensities;
}

package ca.qc.ircm.maxquant2saint;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class MainServiceTest {
  private MainService mainService;
  @Mock
  private MaxquantToSaintConverter converter;
  @Mock
  private ConversionConfiguration configuration;
  private Path file = Paths.get("proteinGroup.txt");
  private Path fasta = Paths.get("human.fasta");

  /**
   * Before test.
   */
  @Before
  public void beforeTest() {
    mainService = new MainService(converter, configuration);
    when(configuration.getFile()).thenReturn(file.toFile());
    when(configuration.getFasta()).thenReturn(fasta.toFile());
  }

  @Test
  public void main_NoParameters() {
    mainService.main(new String[] {});

    verify(converter).convert(file, fasta);
  }

  @Test
  public void main_OnlyConfigurationParameters() {
    mainService.main(new String[] { "--spring.application.name=my_app", "--log.file=test.log" });

    verify(converter).convert(file, fasta);
  }

  @Test
  public void main_ReplaceFile() {
    mainService.main(new String[] { "my_proteinGroup.txt" });

    verify(converter).convert(Paths.get("my_proteinGroup.txt"), fasta);
  }

  @Test
  public void main_ReplaceFileAndFasta() {
    mainService.main(new String[] { "my_proteinGroup.txt", "my.fasta" });

    verify(converter).convert(Paths.get("my_proteinGroup.txt"), Paths.get("my.fasta"));
  }

  @Test
  public void main_ReplaceFileWithConfigurationParameters() {
    mainService.main(new String[] { "--spring.application.name=my_app", "--log.file=test.log",
        "my_proteinGroup.txt" });

    verify(converter).convert(Paths.get("my_proteinGroup.txt"), fasta);
  }

  @Test
  public void main_ReplaceFileAndFastaWithConfigurationParameters() {
    mainService.main(new String[] { "--spring.application.name=my_app", "--log.file=test.log",
        "my_proteinGroup.txt", "my.fasta" });

    verify(converter).convert(Paths.get("my_proteinGroup.txt"), Paths.get("my.fasta"));
  }

  @Test
  public void main_AbsolutePaths() {
    mainService.main(new String[] { "/user/me/my_proteinGroup.txt", "/user/me/my.fasta" });

    verify(converter).convert(Paths.get("/user/me/my_proteinGroup.txt"),
        Paths.get("/user/me/my.fasta"));
  }
}

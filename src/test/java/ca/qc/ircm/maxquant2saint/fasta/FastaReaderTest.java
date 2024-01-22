package ca.qc.ircm.maxquant2saint.fasta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TestAnnotations
public class FastaReaderTest {
  private FastaReader fastaParser;
  @Mock
  private BiConsumer<String, String> handler;
  @Captor
  private ArgumentCaptor<String> nameCaptor;
  @Captor
  private ArgumentCaptor<String> sequenceCaptor;

  @Test
  public void readSequence() throws Throwable {
    Path file = Paths.get(getClass().getResource("/human-trimmed.fasta").toURI());
    fastaParser = new FastaReader(Files.newBufferedReader(file));
    Sequence sequence = fastaParser.readSequence();
    assertEquals(">sp|Q9H9K5|MER34_HUMAN Endogenous retroviral envelope protein", sequence.header);
    assertEquals(
        "MGSLSNYALLQLTLTAFLTILVQPQHLLAPVFRTLSILTNQSNCWLCEHLDNAEQPELVF"
            + "VPASASTWWTYSGQWMYERVWYPQAEVQNHSTSSYRKVTWHWEASMEAQGLSFAQVRLLE" + "GNFSLCVE",
        sequence.sequence);
    sequence = fastaParser.readSequence();
    assertEquals(">sp|P31689|DNJA1_HUMAN DnaJ homolog subfamily A member", sequence.header);
    assertEquals("MVKETTYYDVLGVKPNATQEELKKAYRKLALKYHPDKNPNEGEKFKQISQAYEVLSDAKK" + "RELYDKGG",
        sequence.sequence);
    sequence = fastaParser.readSequence();
    assertEquals(">sp|P08246|ELNE_HUMAN Neutrophil elastase", sequence.header);
    assertEquals("MTLGRRLACLFLACVLPALLLGGTALASEIVGGRRARPHAW", sequence.sequence);
    sequence = fastaParser.readSequence();
    assertEquals(">sp|P63244|RACK1_HUMAN Receptor of activated protein C kinase", sequence.header);
    assertEquals("MTEQMTLRGTLKGHNGWVTQIATTPQFPDMILSASRDKTIIMWKLTRDETNYGIPQRALR" + "GHSHFVSDVVIS",
        sequence.sequence);
    sequence = fastaParser.readSequence();
    assertEquals(">sp|P10144|GRAB_HUMAN Granzyme B", sequence.header);
    assertEquals("MQPILLLLAFLLLPRADAGEIIGGHEAKPHSRPYMAYLMIWDQKSLKRCGGFLIRDDFVL" + "TAAHCWGSS",
        sequence.sequence);
    sequence = fastaParser.readSequence();
    assertNull(sequence);
  }

  @Test
  public void readSequence_Comment() throws Throwable {
    Path file = Paths.get(getClass().getResource("/human-trimmed-headcomment.fasta").toURI());
    fastaParser = new FastaReader(Files.newBufferedReader(file));
    Sequence sequence = fastaParser.readSequence();
    assertEquals(">sp|P31689|DNJA1_HUMAN DnaJ homolog subfamily A member", sequence.header);
    assertEquals("MVKETTYYDVLGVKPNATQEELKKAYRKLALKYHPDKNPNEGEKFKQISQAYEVLSDAKK" + "RELYDKGG",
        sequence.sequence);
    sequence = fastaParser.readSequence();
    assertEquals(">sp|P08246|ELNE_HUMAN Neutrophil elastase", sequence.header);
    assertEquals("MTLGRRLACLFLACVLPALLLGGTALASEIVGGRRARPHAW", sequence.sequence);
    sequence = fastaParser.readSequence();
    assertEquals(">sp|P63244|RACK1_HUMAN Receptor of activated protein C kinase", sequence.header);
    assertEquals("MTEQMTLRGTLKGHNGWVTQIATTPQFPDMILSASRDKTIIMWKLTRDETNYGIPQRALR" + "GHSHFVSDVVIS",
        sequence.sequence);
    sequence = fastaParser.readSequence();
    assertEquals(">sp|P10144|GRAB_HUMAN Granzyme B", sequence.header);
    assertEquals("MQPILLLLAFLLLPRADAGEIIGGHEAKPHSRPYMAYLMIWDQKSLKRCGGFLIRDDFVL" + "TAAHCWGSS",
        sequence.sequence);
    sequence = fastaParser.readSequence();
    assertNull(sequence);
  }
}

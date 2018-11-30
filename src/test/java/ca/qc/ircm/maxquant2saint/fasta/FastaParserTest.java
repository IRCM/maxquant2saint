/*
 * Copyright (c) 2018 Institut de recherches cliniques de Montreal (IRCM)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package ca.qc.ircm.maxquant2saint.fasta;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ca.qc.ircm.maxquant2saint.test.config.TestAnnotations;
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
public class FastaParserTest {
  private FastaParser fastaParser = new FastaParser();
  @Mock
  private BiConsumer<String, String> handler;
  @Captor
  private ArgumentCaptor<String> nameCaptor;
  @Captor
  private ArgumentCaptor<String> sequenceCaptor;

  @Test
  public void parse() throws Throwable {
    Path file = Paths.get(getClass().getResource("/human-trimmed.fasta").toURI());
    fastaParser.parse(file, handler);
    verify(handler, times(5)).accept(nameCaptor.capture(), sequenceCaptor.capture());
    assertEquals("sp|Q9H9K5|MER34_HUMAN Endogenous retroviral envelope protein",
        nameCaptor.getAllValues().get(0));
    assertEquals(
        "MGSLSNYALLQLTLTAFLTILVQPQHLLAPVFRTLSILTNQSNCWLCEHLDNAEQPELVF"
            + "VPASASTWWTYSGQWMYERVWYPQAEVQNHSTSSYRKVTWHWEASMEAQGLSFAQVRLLE" + "GNFSLCVE",
        sequenceCaptor.getAllValues().get(0));
    assertEquals("sp|P31689|DNJA1_HUMAN DnaJ homolog subfamily A member",
        nameCaptor.getAllValues().get(1));
    assertEquals("MVKETTYYDVLGVKPNATQEELKKAYRKLALKYHPDKNPNEGEKFKQISQAYEVLSDAKK" + "RELYDKGG",
        sequenceCaptor.getAllValues().get(1));
    assertEquals("sp|P08246|ELNE_HUMAN Neutrophil elastase", nameCaptor.getAllValues().get(2));
    assertEquals("MTLGRRLACLFLACVLPALLLGGTALASEIVGGRRARPHAW", sequenceCaptor.getAllValues().get(2));
    assertEquals("sp|P63244|RACK1_HUMAN Receptor of activated protein C kinase",
        nameCaptor.getAllValues().get(3));
    assertEquals("MTEQMTLRGTLKGHNGWVTQIATTPQFPDMILSASRDKTIIMWKLTRDETNYGIPQRALR" + "GHSHFVSDVVIS",
        sequenceCaptor.getAllValues().get(3));
    assertEquals("sp|P10144|GRAB_HUMAN Granzyme B", nameCaptor.getAllValues().get(4));
    assertEquals("MQPILLLLAFLLLPRADAGEIIGGHEAKPHSRPYMAYLMIWDQKSLKRCGGFLIRDDFVL" + "TAAHCWGSS",
        sequenceCaptor.getAllValues().get(4));
  }

  @Test
  public void parse_Comment() throws Throwable {
    Path file = Paths.get(getClass().getResource("/human-trimmed-headcomment.fasta").toURI());
    fastaParser.parse(file, handler);
    verify(handler, times(4)).accept(nameCaptor.capture(), sequenceCaptor.capture());
    assertEquals("sp|P31689|DNJA1_HUMAN DnaJ homolog subfamily A member",
        nameCaptor.getAllValues().get(0));
    assertEquals("MVKETTYYDVLGVKPNATQEELKKAYRKLALKYHPDKNPNEGEKFKQISQAYEVLSDAKK" + "RELYDKGG",
        sequenceCaptor.getAllValues().get(0));
    assertEquals("sp|P08246|ELNE_HUMAN Neutrophil elastase", nameCaptor.getAllValues().get(1));
    assertEquals("MTLGRRLACLFLACVLPALLLGGTALASEIVGGRRARPHAW", sequenceCaptor.getAllValues().get(1));
    assertEquals("sp|P63244|RACK1_HUMAN Receptor of activated protein C kinase",
        nameCaptor.getAllValues().get(2));
    assertEquals("MTEQMTLRGTLKGHNGWVTQIATTPQFPDMILSASRDKTIIMWKLTRDETNYGIPQRALR" + "GHSHFVSDVVIS",
        sequenceCaptor.getAllValues().get(2));
    assertEquals("sp|P10144|GRAB_HUMAN Granzyme B", nameCaptor.getAllValues().get(3));
    assertEquals("MQPILLLLAFLLLPRADAGEIIGGHEAKPHSRPYMAYLMIWDQKSLKRCGGFLIRDDFVL" + "TAAHCWGSS",
        sequenceCaptor.getAllValues().get(3));
  }
}

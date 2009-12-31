// $Id$

/*
 * Gudoku (http://sourceforge.net/projects/gudoku)
 * Sudoku-Implementierung auf Basis des Google Webtoolkit 
 * (http://code.google.com/webtoolkit/). Die L�sungsalgorithmen in Java laufen 
 * parallel. Die Sudoku-R�tsel werden mittels JDBC in einer Datenbank
 * gespeichert.
 * 
 * Copyright (C) 2008 J�rgen Dufner
 *
 * Dieses Programm ist freie Software. Sie k�nnen es unter den Bedingungen der 
 * GNU General Public License, wie von der Free Software Foundation 
 * ver�ffentlicht, weitergeben und/oder modifizieren, entweder gem�� Version 3 
 * der Lizenz oder (nach Ihrer Option) jeder sp�teren Version.
 *
 * Die Ver�ffentlichung dieses Programms erfolgt in der Hoffnung, da� es Ihnen 
 * von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die 
 * implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT F�R EINEN 
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem 
 * Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
 *
 */
package de.jdufner.sudoku.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class LiteralEleminationBuilder extends EleminationBuilder {

  private static final Logger LOG = Logger.getLogger(LiteralEleminationBuilder.class);

  public LiteralEleminationBuilder() {
    super();
  }

  @Override
  protected void executeBuilder() {
    for (Literal literal : generateRandomListOfCandidates()) {
      Collection<Cell> cells = sudoku.getCellsByValue(literal);
      for (Cell cell : randomizeCells(cells)) {
        eleminateIfPossible(cell);
      }
    }
  }

  private List<Cell> randomizeCells(Collection<Cell> cells) {
    LOG.debug(cells);
    RandomData randomData = new RandomDataImpl();
    Object[] objs = randomData.nextSample(cells, cells.size());
    List<Cell> arbitraryPermutation = new ArrayList<Cell>();
    for (int i = 0; i < objs.length; i++) {
      arbitraryPermutation.add((Cell) objs[i]);
    }
    LOG.debug(arbitraryPermutation);
    return arbitraryPermutation;
  }

  private List<Literal> generateRandomListOfCandidates() {
    List<Literal> list = new ArrayList<Literal>();
    list.addAll(sudoku.getSize().initializeCandidates());

    RandomData randomData = new RandomDataImpl();
    Object[] objs = randomData.nextSample(list, list.size());
    List<Literal> arbitraryPermutation = new ArrayList<Literal>();
    for (int i = 0; i < objs.length; i++) {
      arbitraryPermutation.add((Literal) objs[i]);
    }
    LOG.debug(arbitraryPermutation);
    return arbitraryPermutation;
  }

}

// $Id$

/*
 * Gudoku (http://sourceforge.net/projects/gudoku)
 * Sudoku-Implementierung auf Basis des Google Webtoolkit 
 * (http://code.google.com/webtoolkit/). Die Lösungsalgorithmen in Java laufen 
 * parallel. Die Sudoku-Rätsel werden mittels JDBC in einer Datenbank
 * gespeichert.
 * 
 * Copyright (C) 2008 Jürgen Dufner
 *
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der 
 * GNU General Public License, wie von der Free Software Foundation 
 * veröffentlicht, weitergeben und/oder modifizieren, entweder gemäß Version 3 
 * der Lizenz oder (nach Ihrer Option) jeder späteren Version.
 *
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen 
 * von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die 
 * implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN 
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem 
 * Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
 *
 */
package de.jdufner.sudoku.solver.strategy;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-03-08
 * @version $Revision$
 */
public abstract class AbstractStrategyTestCase extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(AbstractStrategyTestCase.class);

  protected transient Sudoku sudoku;
  protected transient Strategy strategy;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    sudoku = SudokuFactory.buildSudoku(getSudokuAsString());
    strategy = getStrategy();
  }

  protected abstract String getSudokuAsString();

  protected abstract Strategy getStrategy();

  protected abstract int getNumberCommands();

  public void testAllgemein() {
    final StrategyResult strategyResult = strategy.execute();
    assertNotNull("Eine Strategie muss ein Ergebnis ungleich null liefern.", strategyResult);
    assertNotNull("Eine Strategie muss eine (leere) Liste von Befehlen liefern.", strategyResult.getCommands());
    assertTrue("Eine Strategie muss mindestens ein Befehl liefern.", strategyResult.getCommands().size() > 0);
    LOG.debug(strategyResult.getCommands());
    assertEquals("Ein Strategie muss diese Anzahl von Befehlen liefern.", getNumberCommands(), strategyResult
        .getCommands().size());
  }
}

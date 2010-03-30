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
package de.jdufner.sudoku.solver.service;

import java.util.Collection;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandUtils;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.strategy.StrategyResult;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-14
 * @version $Revision$
 */
public final class ExtendedSolverTest extends AbstractSolverTestCase {

  private final static Logger LOG = Logger.getLogger(ExtendedSolverTest.class);

  private Sudoku quest = SudokuFactory
  //.buildSudoku(".1...4..3.3..5..7.74....1.......25....1...6....35.......2....47.9..7..2.4..3...1.");
      .buildSudoku("6..9..8..2.857..1..3.8..5....34..7.29...2...68.7..54....1..4.7..9..871.5..6..3..9");
  //      .buildSudoku(Examples.SEHR_SCHWER);
  private ExtendedSolver extendedSolver;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    extendedSolver = getStrategySolverWithBacktracking();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testSolve() {
    final Solution solution = extendedSolver.getSolution(quest);
    for (StrategyResult strategyResult : solution.getResults()) {
      LOG.debug("(" + strategyResult.getNumberFixedBefore() + " Zellen /" + strategyResult.getNumberCandidatesBefore()
          + " Kandidaten)" + strategyResult.getSudokuBefore());
      final Collection<? extends Command> cmds = CommandUtils.aggregateCommandsIfPossible(strategyResult.getCommands());
      final StringBuilder sb = new StringBuilder().append(strategyResult.getStrategyName()).append('(').append(
          cmds.size()).append(')').append(':');
      boolean first = true;
      for (Command cmd : cmds) {
        if (first) {
          first = false;
        } else {
          sb.append(',');
        }
        sb.append(cmd.toJavascriptString());
      }
      LOG.debug(sb.toString());
    }
    LOG.debug(solution);
  }
}

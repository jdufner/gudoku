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
import de.jdufner.sudoku.solver.strategy.configuration.StrategyConfiguration;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyThreadingEnum;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class StrategyExecutorTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(StrategyExecutorTest.class);

  /**
   * Dieses Sudoku ist nur mit Hilfe von Raten lösbar
   */
  private static final String SUDOKU_AS_STRING =
  //".5..9...2.6..2...8..7...9...81.............5...69.3......7.2..5...6..4..8.9.5...3";
  //".1...4..3.3..5..7.74....1.......25....1...6....35.......2....47.9..7..2.4..3...1.";
  ".....2..9..2...7.8..5..6...6.......342...3..7..7.1...4.71..........8......69.5.2.";

  private transient Sudoku sudoku;

  public StrategyExecutorTest(final String name) {
    super(name);
  }

  @Override
  public void setUp() throws Exception {
    sudoku = SudokuFactory.buildSudoku(SUDOKU_AS_STRING);
  }

  /*
   * public void testExecuteParallel() { final StrategyConfiguration configuration = new
   * StrategyConfiguration(StrategyThreadingEnum.PARALLEL) .add(StrategyNameEnum.values()); final StrategyExecutor
   * strategyExecutor = new StrategyExecutor(sudoku, configuration); LOG.debug(strategyExecutor);
   * strategyExecutor.execute(); // SolutionImpl solution = strategyExecutor.buildStrategySolution(); //
   * LOG.debug(solution); }
   */

  public void testExecuteSerial() {
    final StrategyConfiguration configuration = new StrategyConfiguration(StrategyThreadingEnum.SERIAL).add(
        StrategyNameEnum.values()).remove(StrategyNameEnum.BACKTRACKING);
    final StrategyExecutor strategyExecutor = new StrategyExecutor(sudoku, configuration);
    LOG.debug(strategyExecutor);
    strategyExecutor.execute();
    //    SolutionImpl solution = strategyExecutor.buildStrategySolution();
    //    LOG.debug(solution);
  }
}

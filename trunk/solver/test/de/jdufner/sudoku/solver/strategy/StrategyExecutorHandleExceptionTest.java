// $Id: StrategyExecutorHandleExceptionTest.java,v 1.1 2009/12/13 20:31:37 jdufner Exp $

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
package de.jdufner.sudoku.solver.strategy;

import java.util.concurrent.ExecutionException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyConfiguration;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyThreadingEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.1 $
 */
public class StrategyExecutorHandleExceptionTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(StrategyExecutorHandleExceptionTest.class);

  /**
   * Dieses Sudoku ist nur mit Hilfe von Raten l�sbar
   */
  private static final String SUDOKU_AS_STRING = ".5..9...2.6..2...8..7...9...81.............5...69.3......7.2..5...6..4..8.9.5...3";

  private Sudoku sudoku;

  public StrategyExecutorHandleExceptionTest(String name) {
    super(name);
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    sudoku = SudokuFactory.buildSudoku(SUDOKU_AS_STRING);
    System.setProperty("test.solver.exceptionInParallelTask", "true");
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
    System.clearProperty("test.solver.exceptionInParallelTask");
  }

  public void testExecute() {
    try {
      StrategyConfiguration configuration = new StrategyConfiguration(StrategyThreadingEnum.PARALLEL)
          .add(StrategyNameEnum.values());
      StrategyExecutor strategyExecutor = new StrategyExecutor(sudoku, configuration);
      LOG.debug(strategyExecutor);
      strategyExecutor.execute();
      fail("SudokuRuntimeException erwartet.");
    } catch (Exception e) {
      assertTrue(contains(e, ExecutionException.class));
    }
  }

  private boolean contains(final Exception container, final Class<? extends Exception> subject) {
    Throwable exception = container;
    while (exception != null && !(exception.getClass().equals(subject))) {
      exception = exception.getCause();
    }
    if (exception != null && exception.getClass().equals(subject)) {
      return true;
    }
    return false;
  }
}

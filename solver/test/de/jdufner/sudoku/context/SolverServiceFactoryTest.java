// $Id: SolverServiceFactoryTest.java,v 1.9 2009/12/26 23:08:00 jdufner Exp $

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
package de.jdufner.sudoku.context;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.exceptions.SudokuRuntimeException;
import de.jdufner.sudoku.common.factory.SudokuPool;
import de.jdufner.sudoku.common.misc.Log;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.9 $
 */
public final class SolverServiceFactoryTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(SolverServiceFactoryTest.class);

  //  private static final Logger APPROACH = Logger.getLogger("approach");
  //  private static final Logger SUDOKU = Logger.getLogger("sudoku");

  public void testGetStrategySolver() {
    SolverServiceFactory.getInstance().getStrategySolver();
  }

  public void testGetStrategySolverWithBacktracking() {
    SolverServiceFactory.getInstance().getStrategySolverWithBacktracking();
  }

  public void testInstantiateBacktracktingSolver() {
    SolverServiceFactory.getInstance().getBacktrackingSolver();
  }

  public void testInstantiateObjectPool1() {
    SolverServiceFactory.getInstance().getSudokuPool();
  }

  public void testInstantiateObjectPool2() {
    SudokuPool pool = SolverServiceFactory.getInstance().getSudokuPool();
    Sudoku s = pool.borrowSudoku();
    pool.returnSudoku(s);
  }

  public void testInstantiateObjectPool3() {
    SudokuPool pool = SolverServiceFactory.getInstance().getSudokuPool();
    List<Sudoku> sudokus = new ArrayList<Sudoku>();
    try {
      for (int i = 0; i <= 100; i++) {
        sudokus.add(pool.borrowSudoku());
      }
      fail("'NoSuchElementException: Pool exhausted' expected");
    } catch (SudokuRuntimeException sre) {
      if (sre.getCause() != null && sre.getCause() instanceof NoSuchElementException) {
        NoSuchElementException nse = (NoSuchElementException) sre.getCause();
        LOG.debug(nse.getMessage(), nse);
      } else {
        throw sre;
      }
    } catch (NoSuchElementException nse) {
      LOG.debug(nse.getMessage(), nse);
    } finally {
      for (Sudoku s : sudokus) {
        pool.returnSudoku(s);
      }
    }
  }

  public void testLogger() {
    LOG.debug("LLOOGGG__DDEEBBUUGG");
    LOG.info("LLOOGGG__IINNFFOO");
    LOG.warn("LLOOGGG__WWAARRNN");
    LOG.error("LLOOGGG__EERROORRRR");
    LOG.fatal("LLOOGGG__FFAATTAALL");

    Log.APPROACH.debug("AAPPPPRROOAACCHH__DDEEBBUUGG");
    Log.APPROACH.info("AAPPPPRROOAACCHH__IINNFFOO");
    Log.APPROACH.warn("AAPPPPRROOAACCHH__WWAARRNN");
    Log.APPROACH.error("AAPPPPRROOAACCHH__EERROORRRR");
    Log.APPROACH.fatal("AAPPPPRROOAACCHH__FFAATTAALL");

    Log.SUDOKU.debug("SSUUDDOOKKUU__DDEEBBUUGG");
    Log.SUDOKU.info("SSUUDDOOKKUU__IINNFFOO");
    Log.SUDOKU.warn("SSUUDDOOKKUU__WWAARRNN");
    Log.SUDOKU.error("SSUUDDOOKKUU__EERROORRRR");
    Log.SUDOKU.fatal("SSUUDDOOKKUU__FFAATTAALL");
  }

  public void testRandomData() {
    int i = SolverServiceFactory.getInstance().getRandomData().nextInt(1, 100);
    LOG.debug(i);
    assertTrue(i >= 1);
    assertTrue(i <= 100);
  }

}

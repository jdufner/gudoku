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
package de.jdufner.sudoku.context;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

import org.apache.commons.math.random.RandomData;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.exceptions.SudokuRuntimeException;
import de.jdufner.sudoku.common.factory.SudokuPool;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SolverServiceFactoryTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(SolverServiceFactoryTest.class);

  public void testInstantiateObjectPool1() {
    SolverServiceFactory.INSTANCE.getBean(SudokuPool.class);
  }

  public void testInstantiateObjectPool2() {
    SudokuPool pool = (SudokuPool) SolverServiceFactory.INSTANCE.getBean(SudokuPool.class);
    Sudoku s = pool.borrowSudoku();
    pool.returnSudoku(s);
  }

  public void testInstantiateObjectPool3() {
    SudokuPool pool = (SudokuPool) SolverServiceFactory.INSTANCE.getBean(SudokuPool.class);
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
  }

  public void testRandomData() {
    RandomData randomData = (RandomData) SolverServiceFactory.INSTANCE.getBean(RandomData.class);
    int i = randomData.nextInt(1, 100);
    LOG.debug(i);
    assertTrue(i >= 1);
    assertTrue(i <= 100);
  }

}

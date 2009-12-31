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
package de.jdufner.sudoku.common.factory;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.factory.SudokuPool;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SudokuPoolTest extends TestCase {
  private static final Logger LOG = Logger.getLogger(SudokuPoolTest.class);
  private static final int POOL_SIZE = 100;

  private SudokuPool pool = null;
  private Sudoku s = null;

  @Override
  public void setUp() throws Exception {
    pool = SolverServiceFactory.getInstance().getSudokuPool();
  }

  public void testBorrowSudoku1() {
    s = pool.borrowSudoku();
    assertTrue(s.isValid());
    assertEquals(1, pool.getNumActive());
    assertTrue(pool.getNumIdle() >= 0);
    pool.returnSudoku(s);
    assertEquals(0, pool.getNumActive());
    assertTrue(pool.getNumIdle() >= 0);
  }

  public void testBorrowSudoku2() {
    Sudoku[] sudokus = new Sudoku[POOL_SIZE];
    for (int i = 0; i < POOL_SIZE; i++) {
      sudokus[i] = pool.borrowSudoku();
      assertTrue(sudokus[i].isValid());
      assertEquals(i + 1, pool.getNumActive());
      assertTrue(pool.getNumIdle() >= 0);
    }
    for (int i = 0; i < POOL_SIZE; i++) {
      pool.returnSudoku(sudokus[i]);
      assertEquals(POOL_SIZE - i - 1, pool.getNumActive());
      assertTrue(pool.getNumIdle() >= 0);
    }
  }

  public void testBorrowSudoku3() {
    Sudoku original = SudokuFactory.buildEmpty(SudokuSize.DEFAULT);
    s = pool.borrowSudoku(original);
    assertEquals(1, pool.getNumActive());
    assertEquals(original, s);
    assertNotSame(original, s);
    assertEquals(original.getCell(0), s.getCell(0));
    assertNotSame(original.getCell(0), s.getCell(0));
    assertEquals(original.getCell(0).getCandidates(), s.getCell(0).getCandidates());
    assertNotSame(original.getCell(0).getCandidates(), s.getCell(0).getCandidates());
    pool.returnSudoku(s);
    assertEquals(0, pool.getNumActive());
  }

  public void testBorrowSudoku4() {
    Sudoku original = SudokuFactory.buildFilled(SudokuSize.DEFAULT);
    s = pool.borrowSudoku(original);
    assertEquals(1, pool.getNumActive());
    assertEquals(original, s);
    assertNotSame(original, s);
    assertEquals(original.getCell(0), s.getCell(0));
    assertNotSame(original.getCell(0), s.getCell(0));
    assertEquals(original.getCell(0).getCandidates(), s.getCell(0).getCandidates());
    assertNotSame(original.getCell(0).getCandidates(), s.getCell(0).getCandidates());
    assertEquals(0, original.getCell(0).getCandidates().size());
    assertEquals(0, s.getCell(0).getCandidates().size());
    pool.returnSudoku(s);
    assertEquals(0, pool.getNumActive());
  }

  public void testBorrowSudoku5() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.SCHWER);
    original.resetAndClearCandidatesOfNonFixed();
    LOG.debug(original.toShortString());
    s = pool.borrowSudoku(original);
    assertEquals(1, pool.getNumActive());
    assertEquals(original, s);
    assertNotSame(original, s);
    assertEquals(original.getCell(0), s.getCell(0));
    assertNotSame(original.getCell(0), s.getCell(0));
    assertEquals(original.getCell(0).getCandidates(), s.getCell(0).getCandidates());
    assertNotSame(original.getCell(0).getCandidates(), s.getCell(0).getCandidates());
    // assertEquals(0, original.getCell(0).getCandidates().size());
    // assertEquals(0, s.getCell(0).getCandidates().size());
    assertEquals(original.getCell(4), s.getCell(4));
    assertNotSame(original.getCell(4), s.getCell(4));
    assertEquals(original.getCell(4).getCandidates(), s.getCell(4).getCandidates());
    assertNotSame(original.getCell(4).getCandidates(), s.getCell(4).getCandidates());
    // assertEquals(0, original.getCell(4).getCandidates().size());
    // assertEquals(0, s.getCell(4).getCandidates().size());
    pool.returnSudoku(s);
    assertEquals(0, pool.getNumActive());
  }

}

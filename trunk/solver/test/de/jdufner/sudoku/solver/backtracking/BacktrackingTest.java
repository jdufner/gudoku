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
package de.jdufner.sudoku.solver.backtracking;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.Solver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class BacktrackingTest extends TestCase {
  private static final Logger LOG = Logger.getLogger(BacktrackingTest.class);
  private static final boolean SHORT_TEST = true;

  private Solver solver = null;

  public BacktrackingTest(String name) {
    super(name);
  }

  @Override
  public void setUp() throws Exception {
    solver = SolverServiceFactory.getInstance().getBacktrackingSolver();
  }

  public void testProblem() {
    Sudoku sudoku1 = SudokuFactory
        .buildSudoku("9:0,0,0,0,4,0,0,3,0,9,8,0,6,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1,0,0,4,0,5,0,7,0,0,6,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,9,0,8,0,7,6,0,7,0,0,3,0,0,0,0");
    Backtracking backtracking = new Backtracking(sudoku1, 1);
    Sudoku result1 = backtracking.firstSolution();
    assertNotNull(result1);
    assertTrue(result1.isSolved());
    assertTrue(result1.isSolvedByCheckSum());
    assertTrue(result1.isValid());

    Solver s = SolverServiceFactory.getInstance().getBacktrackingSolver();
    Sudoku sudoku2 = SudokuFactory
        .buildSudoku("9:0,0,0,0,4,0,0,3,0,9,8,0,6,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1,0,0,4,0,5,0,7,0,0,6,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,9,0,8,0,7,6,0,7,0,0,3,0,0,0,0");
    Sudoku result2 = s.solve(sudoku2);
    assertTrue(result2.isSolved());
    assertTrue(result2.isSolvedByCheckSum());
    assertTrue(result2.isValid());
  }

  public void testFirstSolution1() {
    Sudoku sudoku = SudokuFactory.buildSudoku(Examples.SCHWER);
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
    assertTrue(result.isSolvedByCheckSum());
    assertTrue(result.isValid());
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  /**
   * Dauer: ca. 5 Sekunden
   * 
   * @throws Exception
   */
  public void testFirstSolution2() {
    Sudoku sudoku = SudokuFactory
        .buildSudoku("9:7,0,0,1,0,8,0,0,0,0,9,0,0,0,0,0,3,2,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,1,0,0,9,6,0,0,2,0,0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,1,0,0,0,3,2,0,0,0,0,0,0,6");
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
    assertTrue(result.isSolvedByCheckSum());
    assertTrue(result.isValid());
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  public void testFirstSolution3() {
    Sudoku sudoku = SudokuFactory
        .buildSudoku("9:0,8,2,0,1,0,0,0,0,7,0,0,0,0,0,0,3,0,0,0,0,0,0,6,0,0,5,0,0,0,0,0,0,0,8,0,3,0,0,7,0,0,0,0,0,0,0,0,0,0,0,1,0,4,4,0,1,0,0,0,0,0,6,0,0,0,0,5,0,0,0,0,0,0,0,8,0,0,0,0,0");
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
    assertTrue(result.isSolvedByCheckSum());
    assertTrue(result.isValid());
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  /**
   * Dauer: ca. 780 Sekunden / 13 Minuten
   * 
   * @throws Exception
   */
  public void testFirstSolution4() {
    if (!SHORT_TEST) {
      Sudoku sudoku = SudokuFactory
          .buildSudoku("9:9,0,0,1,0,0,3,0,0,4,0,0,0,0,0,0,5,0,0,0,0,8,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0,4,0,0,6,8,0,0,0,0,0,0,0,0,0,0,0,0,6,7,8,3,2,0,0,7,0,0,0,0,0,0,0,0,0,0,1,0,0");
      Sudoku result = solver.solve(sudoku);
      LOG.debug(result);
      assertTrue(result.isSolved());
      assertTrue(result.isSolvedByCheckSum());
      assertTrue(result.isValid());
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  public void testFirstSolution5() {
    Sudoku sudoku = SudokuFactory
        .buildSudoku("9:9,2,1,6,8,3,0,5,0,5,8,6,9,7,4,1,3,2,3,4,7,5,0,0,9,6,8,7,1,8,4,5,6,2,9,3,4,6,0,0,0,0,8,0,5,2,0,5,8,0,0,0,0,0,8,0,4,0,6,0,5,2,1,6,5,2,1,0,8,0,4,0,1,7,0,2,4,5,0,8,0");
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
    assertTrue(result.isSolvedByCheckSum());
    assertTrue(result.isValid());
    if (!SHORT_TEST) {
      assertTrue(solver.isSolvable(sudoku));
      assertTrue(solver.isUnique(sudoku));
    }
  }

  public void testCountSolutions() {
    if (!SHORT_TEST) {
      Sudoku sudoku = SudokuFactory.buildSudoku(Examples.SCHWER);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("SCHWER numberSolutions=" + numberSolutions);
    }
  }

  public void testCountSolutions1() {
    if (!SHORT_TEST) {
      Sudoku sudoku = SudokuFactory.buildSudoku(Examples.KLEIN_EINDEUTIG);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("KLEIN_EINDEUTIG numberSolutions=" + numberSolutions);
      assertEquals(1, numberSolutions);
    }
  }

  public void testCountSolutions2() {
    if (!SHORT_TEST) {
      Sudoku sudoku = SudokuFactory.buildSudoku(Examples.KLEIN_MEHRDEUTIG_1);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("KLEIN_MEHRDEUTIG_1 numberSolutions=" + numberSolutions);
      assertEquals(2, numberSolutions);
    }
  }

  public void testCountSolutions3() {
    if (!SHORT_TEST) {
      Sudoku sudoku = SudokuFactory.buildSudoku(Examples.KLEIN_MEHRDEUTIG_2);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("KLEIN_MEHRDEUTIG_2 numberSolutions=" + numberSolutions);
      assertEquals(4, numberSolutions);
    }
  }

  public void testCountSolutions4() {
    if (!SHORT_TEST) {
      Sudoku sudoku = SudokuFactory.buildEmpty(SudokuSize.VIER);
      Backtracking backtracking = new Backtracking(sudoku, 1);
      int numberSolutions = backtracking.countSolutions();
      LOG.debug("EMPTY_VIER numberSolutions=" + numberSolutions);
      assertEquals(288, numberSolutions);
    }
  }

  public void testFirstSolutionOfShuffled() {
    Sudoku underDeterminedSudoku = SudokuFactory.buildShuffled(SudokuSize.DEFAULT);
    LOG.debug(underDeterminedSudoku);
    Backtracking backtracking = new Backtracking(underDeterminedSudoku, 1);
    Sudoku firstSolution = backtracking.firstSolution();
    LOG.debug(firstSolution);
  }
}
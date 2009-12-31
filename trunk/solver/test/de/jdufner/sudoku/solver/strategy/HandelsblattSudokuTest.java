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

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.solver.service.StrategySolver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class HandelsblattSudokuTest extends TestCase {
  private static final Logger LOG = Logger.getLogger(HandelsblattSudokuTest.class);

  public HandelsblattSudokuTest(String name) {
    super(name);
  }

  public void testMittel20071109() {
    final String mySudoku = "9:" + //
        "0,0,0,4,0,5,7,6,2," + //
        "0,7,0,3,6,1,0,0,4," + //
        "0,0,8,0,0,9,0,0,5," + //
        "0,0,0,0,4,0,0,5,0," + //
        "1,0,0,0,7,6,9,0,0," + //
        "0,0,0,2,0,8,0,0,0," + //
        "3,0,0,1,9,0,0,0,0," + //
        "0,4,1,0,0,0,6,0,0," + //
        "0,9,2,0,0,0,5,0,0"; //
    Sudoku sudoku = SudokuFactory.buildSudoku(mySudoku);
    Solver solver = new StrategySolver();
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result.toString());
    assertTrue(result.isSolved());
  }

  public void testSchwer20071109() {
    final String mySudoku = "9:" + //
        "2,0,5,0,0,1,0,0,0," + //
        "0,0,0,0,0,9,0,0,0," + //
        "9,0,8,0,2,5,0,3,0," + //
        "0,0,0,8,3,0,9,4,0," + //
        "1,0,6,7,0,0,0,0,0," + //
        "0,3,0,0,1,2,0,0,0," + //
        "0,0,0,0,0,0,0,1,0," + //
        "7,0,0,0,6,3,8,9,2," + //
        "0,2,0,0,0,0,4,0,0"; //
    Sudoku sudoku = SudokuFactory.buildSudoku(mySudoku);
    Solver solver = new StrategySolver();
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result.toString());
    assertTrue(result.isSolved());
  }

  public void testSolverSchwer20071130() {
    final String mySudoku = "9:" + //
        "0,0,0,1,3,0,0,2,0," + //
        "0,7,0,9,0,0,0,8,4," + //
        "0,2,0,4,0,8,9,5,1," + //
        "7,0,0,0,0,6,0,3,5," + //
        "0,0,0,0,0,0,0,4,9," + //
        "2,0,0,0,9,0,1,6,8," + //
        "8,0,0,0,0,7,0,0,0," + //
        "0,1,0,0,4,0,0,0,6," + //
        "0,0,0,3,0,0,5,0,0"; //
    Sudoku sudoku = SudokuFactory.buildSudoku(mySudoku);
    Solver solver = null;

    // ohne Backtracking
    solver = SolverServiceFactory.getInstance().getStrategySolver();
    Sudoku result1 = solver.solve(sudoku);
    LOG.debug(result1.toString());
    assertTrue(result1.isSolved());

    // mit Backtracking
    solver = SolverServiceFactory.getInstance().getStrategySolver();
    Sudoku result2 = solver.solve(sudoku);
    LOG.debug(result2.toString());
    assertTrue(result2.isSolved());
  }

  public void testExtendedSolverSchwer20071130() {
    final String mySudoku = "9:" + //
        "0,0,0,1,3,0,0,2,0," + //
        "0,7,0,9,0,0,0,8,4," + //
        "0,2,0,4,0,8,9,5,1," + //
        "7,0,0,0,0,6,0,3,5," + //
        "0,0,0,0,0,0,0,4,9," + //
        "2,0,0,0,9,0,1,6,8," + //
        "8,0,0,0,0,7,0,0,0," + //
        "0,1,0,0,4,0,0,0,6," + //
        "0,0,0,3,0,0,5,0,0"; //
    Sudoku sudoku = SudokuFactory.buildSudoku(mySudoku);
    ExtendedSolver extendedSolver = SolverServiceFactory.getInstance().getStrategySolver();
    Solution solution = extendedSolver.getSolution(sudoku);
    LOG.debug(solution.toString());
    assertTrue(solution.getResult().isSolved());
  }

}


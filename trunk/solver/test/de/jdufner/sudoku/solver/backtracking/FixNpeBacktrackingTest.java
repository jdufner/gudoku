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
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.Solver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class FixNpeBacktrackingTest extends TestCase {
  private static final Logger LOG = Logger.getLogger(Backtracking.class);
  private static SolverServiceFactory solverServiceFactory = SolverServiceFactory.getInstance();

  public FixNpeBacktrackingTest(String name) {
    super(name);
  }

  public void testFirstSolution1() {
    Sudoku sudoku = SudokuFactory
        .buildSudoku("9:9,0,0,0,8,3,0,0,0,5,0,0,0,7,0,0,0,0,0,4,0,0,0,0,0,6,0,0,1,0,4,0,6,0,0,0,0,0,0,0,0,0,8,0,5,0,0,0,0,0,0,0,0,0,8,0,0,0,0,0,5,0,0,0,0,0,1,0,0,0,4,0,0,0,0,2,0,0,0,0,0");
    Solver solver = solverServiceFactory.getBacktrackingSolver();
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
  }

  public void testFirstSolution2() {
    Sudoku sudoku = SudokuFactory
        .buildSudoku("9:9,2,1,6,8,3,0,5,0,5,8,6,9,7,4,1,3,2,3,4,7,5,0,0,9,6,8,7,1,8,4,5,6,2,9,3,4,6,0,0,0,0,8,0,5,2,0,5,8,0,0,0,0,0,8,0,4,0,6,0,5,2,1,6,5,2,1,0,8,0,4,0,1,7,0,2,4,5,0,8,0");
    Solver solver = solverServiceFactory.getBacktrackingSolver();
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
  }

  public void testFirstSolution3() {
    Sudoku sudoku = SudokuFactory
        .buildSudoku("9:9,2,1,6,8,3,7,5,4,5,8,6,9,7,4,1,3,2,3,4,7,5,2,1,9,6,8,7,1,8,4,5,6,2,9,3,4,6,9,7,3,2,8,1,5,2,3,5,8,1,9,4,7,6,8,9,4,3,6,7,5,2,1,6,5,2,1,9,8,3,4,7,1,7,3,2,4,5,6,8,9");
    Solver solver = solverServiceFactory.getBacktrackingSolver();
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result);
    assertTrue(result.isSolved());
  }

}


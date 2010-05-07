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
package de.jdufner.sudoku.solver.ddt.free100;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.ddt.AbstractSolverExcelTestCase;
import de.jdufner.sudoku.solver.service.Solver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Free100Test extends AbstractSolverExcelTestCase {
  private static final Logger LOG = Logger.getLogger(Free100Test.class);

  private String sudokuAsString;
  private boolean solveable;

  public void testStrategySolver() {
    Sudoku sudoku = SudokuFactory.buildSudoku(sudokuAsString);
    Solver solver = getStrategySolver();
    Sudoku result = solver.solve(sudoku);
    if (LOG.isDebugEnabled()) {
      LOG.debug(result.toString());
    }
    assertEquals(solveable, result.isSolved());
  }

  // public void testBacktrackingSolver() {
  // Sudoku sudoku = SudokuFactory.buildSudoku(sudokuAsString);
  // Solver solver = getBacktrackingSolver();
  // Sudoku result = solver.getSolution(sudoku);
  // if (LOG.isDebugEnabled()) {
  // LOG.debug(result.toString());
  // }
  // assertTrue(result.isSolved());
  // }

  public String getSudokuAsString() {
    return sudokuAsString;
  }

  public void setSudokuAsString(String sudokuAsString) {
    this.sudokuAsString = sudokuAsString;
  }

  public boolean isSolveable() {
    return solveable;
  }

  public void setSolveable(boolean solveable) {
    this.solveable = solveable;
  }

}


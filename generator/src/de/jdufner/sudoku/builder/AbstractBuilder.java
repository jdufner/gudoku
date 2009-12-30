// $Id: AbstractBuilder.java,v 1.6 2009/12/20 19:34:49 jdufner Exp $

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
package de.jdufner.sudoku.builder;

import org.apache.commons.math.random.RandomData;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.ExtendedSolver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.6 $
 */
public abstract class AbstractBuilder implements Builder {

  protected Sudoku sudoku;
  protected ExtendedSolver strategySolverWithBacktracking;
  protected RandomData randomData;

  public AbstractBuilder() {
  }

  public void setSize(SudokuSize sudokuSize) {
    //    sudoku = SudokuFactory.buildEmpty(sudokusize);
    Sudoku underDeterminedSudoku = SudokuFactory.buildShuffled(sudokuSize, getRandomData());
    sudoku = getStrategySolverWithBacktracking().solve(underDeterminedSudoku);
  }

  //
  // Spring Wiring
  //

  public ExtendedSolver getStrategySolverWithBacktracking() {
    return strategySolverWithBacktracking;
  }

  public void setStrategySolverWithBacktracking(ExtendedSolver strategySolverWithBacktracking) {
    this.strategySolverWithBacktracking = strategySolverWithBacktracking;
  }

  public RandomData getRandomData() {
    return randomData;
  }

  public void setRandomData(RandomData randomData) {
    this.randomData = randomData;
  }

}

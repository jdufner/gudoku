// $Id: BacktrackingPerformanceTest.java,v 1.5 2009/11/17 20:34:33 jdufner Exp $

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

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.5 $
 */
public final class BacktrackingPerformanceTest extends TestCase {
  private static final Logger LOG = Logger.getLogger(BacktrackingPerformanceTest.class);

  private Sudoku sudoku = null;
  private long startTime = 0;
  private long endTime = 0;

  @Override
  public void setUp() {
    // sudoku = SudokuFactory.buildSudoku(Examples.SCHWER);
    sudoku = SudokuFactory
        .buildSudoku("9:0,0,0,0,4,0,0,3,0,9,8,0,6,0,1,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,1,0,0,4,0,5,0,7,0,0,6,0,0,0,0,0,0,0,0,0,0,5,0,0,0,0,0,0,0,0,0,9,0,8,0,7,6,0,7,0,0,3,0,0,0,0");
  }

  public void testFirstSolution() {
    startTime = System.currentTimeMillis();
    Backtracking backtracking = new Backtracking(sudoku, 1);
    Sudoku result1 = backtracking.firstSolution();
    assertNotNull(result1);
    assertTrue(result1.isSolved());
    assertTrue(result1.isSolvedByCheckSum());
    assertTrue(result1.isValid());
    endTime = System.currentTimeMillis();
    LOG.info("Dauer parallel: " + (endTime - startTime) + " ms");
  }

}


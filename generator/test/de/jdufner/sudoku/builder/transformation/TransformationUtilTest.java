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
package de.jdufner.sudoku.builder.transformation;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class TransformationUtilTest extends TestCase {
  private static final Logger log = Logger.getLogger(TransformationUtilTest.class);

  public void testSwapColumns() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    Sudoku swapped = TransformationUtil.swapColumns(original, 0, 1);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(0, 1).getValue());
    assertEquals(original.getCell(1, 0).getValue(), swapped.getCell(1, 1).getValue());
    assertEquals(original.getCell(2, 0).getValue(), swapped.getCell(2, 1).getValue());
    assertEquals(original.getCell(3, 0).getValue(), swapped.getCell(3, 1).getValue());
    assertEquals(original.getCell(4, 0).getValue(), swapped.getCell(4, 1).getValue());
    assertEquals(original.getCell(5, 0).getValue(), swapped.getCell(5, 1).getValue());
    assertEquals(original.getCell(6, 0).getValue(), swapped.getCell(6, 1).getValue());
    assertEquals(original.getCell(7, 0).getValue(), swapped.getCell(7, 1).getValue());
    assertEquals(original.getCell(8, 0).getValue(), swapped.getCell(8, 1).getValue());
  }

  public void testSwapRows() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    Sudoku swapped = TransformationUtil.swapRows(original, 1, 2);
    assertEquals(original.getCell(2, 0).getValue(), swapped.getCell(1, 0).getValue());
    assertEquals(original.getCell(2, 1).getValue(), swapped.getCell(1, 1).getValue());
    assertEquals(original.getCell(2, 2).getValue(), swapped.getCell(1, 2).getValue());
    assertEquals(original.getCell(2, 3).getValue(), swapped.getCell(1, 3).getValue());
    assertEquals(original.getCell(2, 4).getValue(), swapped.getCell(1, 4).getValue());
    assertEquals(original.getCell(2, 5).getValue(), swapped.getCell(1, 5).getValue());
    assertEquals(original.getCell(2, 6).getValue(), swapped.getCell(1, 6).getValue());
    assertEquals(original.getCell(2, 7).getValue(), swapped.getCell(1, 7).getValue());
    assertEquals(original.getCell(2, 8).getValue(), swapped.getCell(1, 8).getValue());
  }

  public void testSwapColumBlocks() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    log.debug("original");
    log.debug(original);
    Sudoku swapped = TransformationUtil.swapColumnBlock(original, 1, 2);
    log.debug("swapped");
    log.debug(swapped);
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(0, 6).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(0, 7).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(0, 8).getValue());
    assertEquals(original.getCell(1, 3).getValue(), swapped.getCell(1, 6).getValue());
    assertEquals(original.getCell(1, 4).getValue(), swapped.getCell(1, 7).getValue());
    assertEquals(original.getCell(1, 5).getValue(), swapped.getCell(1, 8).getValue());
    assertEquals(original.getCell(2, 3).getValue(), swapped.getCell(2, 6).getValue());
    assertEquals(original.getCell(2, 4).getValue(), swapped.getCell(2, 7).getValue());
    assertEquals(original.getCell(2, 5).getValue(), swapped.getCell(2, 8).getValue());
    assertEquals(original.getCell(3, 3).getValue(), swapped.getCell(3, 6).getValue());
    assertEquals(original.getCell(3, 4).getValue(), swapped.getCell(3, 7).getValue());
    assertEquals(original.getCell(3, 5).getValue(), swapped.getCell(3, 8).getValue());
    assertEquals(original.getCell(4, 3).getValue(), swapped.getCell(4, 6).getValue());
    assertEquals(original.getCell(4, 4).getValue(), swapped.getCell(4, 7).getValue());
    assertEquals(original.getCell(4, 5).getValue(), swapped.getCell(4, 8).getValue());
    assertEquals(original.getCell(5, 3).getValue(), swapped.getCell(5, 6).getValue());
    assertEquals(original.getCell(5, 4).getValue(), swapped.getCell(5, 7).getValue());
    assertEquals(original.getCell(5, 5).getValue(), swapped.getCell(5, 8).getValue());
    assertEquals(original.getCell(6, 3).getValue(), swapped.getCell(6, 6).getValue());
    assertEquals(original.getCell(6, 4).getValue(), swapped.getCell(6, 7).getValue());
    assertEquals(original.getCell(6, 5).getValue(), swapped.getCell(6, 8).getValue());
    assertEquals(original.getCell(7, 3).getValue(), swapped.getCell(7, 6).getValue());
    assertEquals(original.getCell(7, 4).getValue(), swapped.getCell(7, 7).getValue());
    assertEquals(original.getCell(7, 5).getValue(), swapped.getCell(7, 8).getValue());
    assertEquals(original.getCell(8, 3).getValue(), swapped.getCell(8, 6).getValue());
    assertEquals(original.getCell(8, 4).getValue(), swapped.getCell(8, 7).getValue());
    assertEquals(original.getCell(8, 5).getValue(), swapped.getCell(8, 8).getValue());
  }

  public void testSwapRowBlocks() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    // log.debug("original");
    // log.debug(original);
    Sudoku swapped = TransformationUtil.swapRowBlock(original, 1, 2);
    // log.debug("swapped");
    // log.debug(swapped);
    assertEquals(original.getCell(3, 0).getValue(), swapped.getCell(6, 0).getValue());
    assertEquals(original.getCell(4, 0).getValue(), swapped.getCell(7, 0).getValue());
    assertEquals(original.getCell(5, 0).getValue(), swapped.getCell(8, 0).getValue());
    assertEquals(original.getCell(3, 1).getValue(), swapped.getCell(6, 1).getValue());
    assertEquals(original.getCell(4, 1).getValue(), swapped.getCell(7, 1).getValue());
    assertEquals(original.getCell(5, 1).getValue(), swapped.getCell(8, 1).getValue());
    assertEquals(original.getCell(3, 2).getValue(), swapped.getCell(6, 2).getValue());
    assertEquals(original.getCell(4, 2).getValue(), swapped.getCell(7, 2).getValue());
    assertEquals(original.getCell(5, 2).getValue(), swapped.getCell(8, 2).getValue());
    assertEquals(original.getCell(3, 3).getValue(), swapped.getCell(6, 3).getValue());
    assertEquals(original.getCell(4, 3).getValue(), swapped.getCell(7, 3).getValue());
    assertEquals(original.getCell(5, 3).getValue(), swapped.getCell(8, 3).getValue());
    assertEquals(original.getCell(3, 4).getValue(), swapped.getCell(6, 4).getValue());
    assertEquals(original.getCell(4, 4).getValue(), swapped.getCell(7, 4).getValue());
    assertEquals(original.getCell(5, 4).getValue(), swapped.getCell(8, 4).getValue());
    assertEquals(original.getCell(3, 5).getValue(), swapped.getCell(6, 5).getValue());
    assertEquals(original.getCell(4, 5).getValue(), swapped.getCell(7, 5).getValue());
    assertEquals(original.getCell(5, 5).getValue(), swapped.getCell(8, 5).getValue());
    assertEquals(original.getCell(3, 6).getValue(), swapped.getCell(6, 6).getValue());
    assertEquals(original.getCell(4, 6).getValue(), swapped.getCell(7, 6).getValue());
    assertEquals(original.getCell(5, 6).getValue(), swapped.getCell(8, 6).getValue());
    assertEquals(original.getCell(3, 7).getValue(), swapped.getCell(6, 7).getValue());
    assertEquals(original.getCell(4, 7).getValue(), swapped.getCell(7, 7).getValue());
    assertEquals(original.getCell(5, 7).getValue(), swapped.getCell(8, 7).getValue());
    assertEquals(original.getCell(3, 8).getValue(), swapped.getCell(6, 8).getValue());
    assertEquals(original.getCell(4, 8).getValue(), swapped.getCell(7, 8).getValue());
    assertEquals(original.getCell(5, 8).getValue(), swapped.getCell(8, 8).getValue());
  }

  public void testRotateQuarterClockwise() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    // log.debug("original");
    // log.debug(original);
    Sudoku swapped = TransformationUtil.rotateBlockClockwise(original);
    // log.debug("swapped");
    // log.debug(swapped);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(0, 8).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(1, 8).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(2, 8).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(3, 8).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(4, 8).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(5, 8).getValue());
  }

  public void testRotateQuarterCounterClockwise() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    // log.debug("original");
    // log.debug(original);
    Sudoku swapped = TransformationUtil.rotateBlockCounterClockwise(original);
    // log.debug("swapped");
    // log.debug(swapped);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(8, 0).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(7, 0).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(6, 0).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(5, 0).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(4, 0).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(3, 0).getValue());
  }

  public void testRotateHalfClockwise() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    // log.debug("original");
    // log.debug(original);
    Sudoku swapped = TransformationUtil.rotateHalfClockwise(original);
    // log.debug("swapped");
    // log.debug(swapped);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(8, 8).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(8, 7).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(8, 6).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(8, 5).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(8, 4).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(8, 3).getValue());
  }

  public void testMirrorVertically() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    // log.debug("original");
    // log.debug(original);
    Sudoku swapped = TransformationUtil.mirrorVertically(original);
    // log.debug("swapped");
    // log.debug(swapped);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(0, 8).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(0, 7).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(0, 6).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(0, 5).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(0, 4).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(0, 3).getValue());
  }

  public void testMirrorHorizontally() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    // log.debug("original");
    // log.debug(original);
    Sudoku swapped = TransformationUtil.mirrorHorizontally(original);
    // log.debug("swapped");
    // log.debug(swapped);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(8, 0).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(8, 1).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(8, 2).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(8, 3).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(8, 4).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(8, 5).getValue());
  }

  public void testMirrorDiagonally() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    // log.debug("original");
    // log.debug(original);
    Sudoku swapped = TransformationUtil.mirrorDiagonally(original);
    // log.debug("swapped");
    // log.debug(swapped);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(8, 8).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(7, 8).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(6, 8).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(5, 8).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(4, 8).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(3, 8).getValue());
  }

  public void testMirrorCounterDiagonally() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    // log.debug("original");
    // log.debug(original);
    Sudoku swapped = TransformationUtil.mirrorCounterDiagonally(original);
    // log.debug("swapped");
    // log.debug(swapped);
    assertEquals(original.getCell(0, 0).getValue(), swapped.getCell(0, 0).getValue());
    assertEquals(original.getCell(0, 1).getValue(), swapped.getCell(1, 0).getValue());
    assertEquals(original.getCell(0, 2).getValue(), swapped.getCell(2, 0).getValue());
    assertEquals(original.getCell(0, 3).getValue(), swapped.getCell(3, 0).getValue());
    assertEquals(original.getCell(0, 4).getValue(), swapped.getCell(4, 0).getValue());
    assertEquals(original.getCell(0, 5).getValue(), swapped.getCell(5, 0).getValue());
  }

  public void testSwapArbitraryColumns() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    for (int i = 0; i < 10; i++) {
      TransformationUtil.swapArbitraryColumns(original);
    }
  }

  public void testArbitraryTransformation1() {
    Sudoku original = SudokuFactory.buildSudoku(Examples.LEICHT);
    Sudoku swapped = original;
    log.debug(original);
    for (int i = 0; i < 100; i++) {
      swapped = TransformationUtil.arbitraryTransformation(swapped);
    }
    log.debug(swapped);
  }

  public void testArbitraryTransformation2() {
    Sudoku original = SudokuFactory.buildFilled(SudokuSize.DEFAULT);
    Sudoku swapped = original;
    log.debug(original);
    for (int i = 0; i < 100; i++) {
      swapped = TransformationUtil.arbitraryTransformation(swapped);
      assertTrue(swapped.isValid());
    }
    log.debug(swapped);
  }

}
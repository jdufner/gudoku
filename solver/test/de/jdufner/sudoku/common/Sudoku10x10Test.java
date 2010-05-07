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
package de.jdufner.sudoku.common;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Block;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Column;
import de.jdufner.sudoku.common.board.Row;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Sudoku10x10Test extends AbstractSolverTestCase {
  private static final Logger LOG = Logger.getLogger(Sudoku10x10Test.class);

  public static final String EMPTY = "10:" + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0," + //
      "0,0,0,0,0,0,0,0,0,0";

  public static final String FILLED = "10:" + //
      "1,2,3,4,5,6,7,8,9,10," + //
      "3,4,5,6,7,8,9,10,1,2," + //
      "5,6,7,8,9,10,1,2,3,4," + //
      "7,8,9,10,1,2,3,4,5,6," + //
      "9,10,1,2,3,4,5,6,7,8," + //
      "10,1,2,3,4,5,6,7,8,9," + //
      "2,3,4,5,6,7,8,9,10,1," + //
      "4,5,6,7,8,9,10,1,2,3," + //
      "6,7,8,9,10,1,2,3,4,5," + //
      "8,9,10,1,2,3,4,5,6,7";

  public static final String COMPLETE = "10:" + //
      "1,2,3,4,5,6,7,8,9,10," + //
      "3,4,5,6,7,8,9,10,1,2," + //
      "5,6,7,8,9,10,1,2,3,4," + //
      "7,8,9,10,1,2,3,4,5,6," + //
      "9,10,1,2,3,4,5,6,7,8," + //
      "2,3,4,5,6,7,8,9,10,1," + //
      "4,5,6,7,8,9,10,1,2,3," + //
      "6,7,8,9,10,1,2,3,4,5," + //
      "8,9,10,1,2,3,4,5,6,7," + //
      "10,1,2,3,4,5,6,7,8,9";

  public Sudoku10x10Test(String name) {
    super(name);
  }

  public void testBuildFromString() {
    Sudoku sudoku = SudokuFactory.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    assertTrue(COMPLETE.equals(sudoku.getSize().getUnitSize() + ":" + sudoku.toShortString()));
    assertEquals(sudoku.getSize().getUnitSize(), sudoku.getBlock(0).getCells().size());
    assertEquals(sudoku.getSize().getUnitSize(), sudoku.getColumn(0).getCells().size());
    assertEquals(sudoku.getSize().getUnitSize(), sudoku.getRow(0).getCells().size());
  }

  public void testBuildEmpty() {
    Sudoku sudoku = SudokuFactory.buildEmpty(SudokuSize.ZEHN);
    LOG.debug(sudoku.toLongString());
    LOG.debug(sudoku.toShortString());
    assertTrue(EMPTY.equals(sudoku.getSize().getUnitSize() + ":" + sudoku.toShortString()));
  }

  public void testBuildFilled() {
    Sudoku sudoku = SudokuFactory.buildFilled(SudokuSize.ZEHN);
    assertTrue(sudoku.isValid());
    assertTrue(sudoku.isSolved());
    assertTrue(sudoku.isSolvedByCheckSum());
    LOG.debug(sudoku.toLongString());
    LOG.debug(sudoku.toShortString());
    assertTrue(FILLED.equals(sudoku.getSize().getUnitSize() + ":" + sudoku.toShortString()));
  }

  public void testCellMetadata() {
    Sudoku sudoku = SudokuFactory.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    int k = 0;
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        Cell cell = sudoku.getCell(i, j);
        assertEquals(i, cell.getRowIndex());
        assertEquals(j, cell.getColumnIndex());
        assertEquals(k, cell.getNumber());
        if (i >= 0 && i <= 4 && j >= 0 && j <= 1) {
          assertEquals(0, cell.getBlockIndex());
        }
        k += 1;
      }
    }
    assertTrue(COMPLETE.equals(sudoku.getSize().getUnitSize() + ":" + sudoku.toShortString()));
  }

  public void testBlockMetadata() {
    Sudoku sudoku = SudokuFactory.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      Block block = sudoku.getBlock(i);
      for (Cell cell : block.getCells()) {
        assertEquals(i, cell.getBlockIndex());
        if (i == 0) {
          assertTrue(containsValue(cell.getBlockIndex(), new int[] { 0, 1, 10, 11, 20, 21, 30, 31, 40, 41 }));
        }
      }
    }
  }

  private boolean containsValue(int value, int[] values) {
    for (int i = 0; i < values.length; i++) {
      if (values[i] == value) {
        return true;
      }
    }
    return false;
  }

  public void testColumnMetadata() {
    Sudoku sudoku = SudokuFactory.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      Column column = sudoku.getColumn(i);
      for (Cell cell : column.getCells()) {
        assertEquals(i, cell.getColumnIndex());
      }
      if (column.getIndex() >= 0 && column.getIndex() <= 1) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBlockWidth());
        assertEquals(0, column.getBlockIndexes()[0]);
        assertEquals(1, column.getBlockIndexes()[1]);
      }
      if (column.getIndex() >= 2 && column.getIndex() <= 3) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBlockWidth());
        assertEquals(2, column.getBlockIndexes()[0]);
        assertEquals(3, column.getBlockIndexes()[1]);
      }
      if (column.getIndex() >= 4 && column.getIndex() <= 5) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBlockWidth());
        assertEquals(4, column.getBlockIndexes()[0]);
        assertEquals(5, column.getBlockIndexes()[1]);
      }
      if (column.getIndex() >= 6 && column.getIndex() <= 7) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBlockWidth());
        assertEquals(6, column.getBlockIndexes()[0]);
        assertEquals(7, column.getBlockIndexes()[1]);
      }
      if (column.getIndex() >= 8 && column.getIndex() <= 9) {
        assertTrue(column.getBlockIndexes().length == sudoku.getSize().getBlockWidth());
        assertEquals(8, column.getBlockIndexes()[0]);
        assertEquals(9, column.getBlockIndexes()[1]);
      }
    }
  }

  public void testRowMetadata() {
    Sudoku sudoku = SudokuFactory.buildSudoku(COMPLETE);
    LOG.debug(sudoku.toShortString());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      Row row = sudoku.getRow(i);
      for (Cell cell : row.getCells()) {
        assertEquals(i, cell.getRowIndex());
      }
      if (row.getIndex() >= 0 && row.getIndex() <= 4) {
        assertTrue(row.getBlockIndexes().length == sudoku.getSize().getBlockHeight());
        assertEquals(0, row.getBlockIndexes()[0]);
        assertEquals(1, row.getBlockIndexes()[1]);
        assertEquals(2, row.getBlockIndexes()[2]);
        assertEquals(3, row.getBlockIndexes()[3]);
        assertEquals(4, row.getBlockIndexes()[4]);
      }
      if (row.getIndex() >= 5 && row.getIndex() <= 9) {
        assertTrue(row.getBlockIndexes().length == sudoku.getSize().getBlockHeight());
        assertEquals(5, row.getBlockIndexes()[0]);
        assertEquals(6, row.getBlockIndexes()[1]);
        assertEquals(7, row.getBlockIndexes()[2]);
        assertEquals(8, row.getBlockIndexes()[3]);
        assertEquals(9, row.getBlockIndexes()[4]);
      }
    }
  }

}

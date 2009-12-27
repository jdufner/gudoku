// $Id: BlockUtilsTest.java,v 1.1 2009/12/21 22:20:19 jdufner Exp $

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
package de.jdufner.sudoku.common.board;

import junit.framework.TestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 21.12.2009
 * @version $Revision: 1.1 $
 */
public final class BlockUtilsTest extends TestCase {

  public void testIsFirstColumnInBlock() {
    assertTrue(BlockUtils.isFirstColumnInBlock(0, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstColumnInBlock(1, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstColumnInBlock(2, SudokuSize.NEUN));
    assertTrue(BlockUtils.isFirstColumnInBlock(3, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstColumnInBlock(4, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstColumnInBlock(5, SudokuSize.NEUN));
    assertTrue(BlockUtils.isFirstColumnInBlock(6, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstColumnInBlock(7, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstColumnInBlock(8, SudokuSize.NEUN));
  }

  public void testIsFirstRowInBlock() {
    assertTrue(BlockUtils.isFirstRowInBlock(0, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstRowInBlock(1, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstRowInBlock(2, SudokuSize.NEUN));
    assertTrue(BlockUtils.isFirstRowInBlock(3, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstRowInBlock(4, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstRowInBlock(5, SudokuSize.NEUN));
    assertTrue(BlockUtils.isFirstRowInBlock(6, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstRowInBlock(7, SudokuSize.NEUN));
    assertFalse(BlockUtils.isFirstRowInBlock(8, SudokuSize.NEUN));
  }

  public void testIsLastColumnInRow() {
    assertFalse(BlockUtils.isLastColumnInRow(0, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastColumnInRow(1, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastColumnInRow(2, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastColumnInRow(3, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastColumnInRow(4, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastColumnInRow(5, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastColumnInRow(6, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastColumnInRow(7, SudokuSize.NEUN));
    assertTrue(BlockUtils.isLastColumnInRow(8, SudokuSize.NEUN));
  }

  public void testIsLastRowInColumn() {
    assertFalse(BlockUtils.isLastRowInColumn(0, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastRowInColumn(1, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastRowInColumn(2, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastRowInColumn(3, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastRowInColumn(4, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastRowInColumn(5, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastRowInColumn(6, SudokuSize.NEUN));
    assertFalse(BlockUtils.isLastRowInColumn(7, SudokuSize.NEUN));
    assertTrue(BlockUtils.isLastRowInColumn(8, SudokuSize.NEUN));
  }

}

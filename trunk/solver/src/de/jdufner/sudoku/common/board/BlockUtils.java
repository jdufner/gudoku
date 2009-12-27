// $Id: BlockUtils.java,v 1.2 2009/12/21 22:20:19 jdufner Exp $

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

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.2 $
 */
public final class BlockUtils {

  private BlockUtils() {
  }

  public static int[] getColumnsByBlock(final int block, final SudokuSize sudokuSize) {
    int[] columns = new int[sudokuSize.getBlockWidth()];
    for (int i = 0; i < sudokuSize.getBlockWidth(); i++) {
      columns[i] = block * sudokuSize.getBlockWidth() + i;
    }
    return columns;
  }

  /**
   * Liefert den Blockindex zu einer Zelle (genauer Zeilen- und Spaltenindex) in Abhängigkeit der Größe zurück.
   * 
   * @param rowIndex
   * @param columnIndex
   * @param sudokuSize
   * @return
   */
  public static int getBlockIndexByRowIndexAndColumnIndex(final int rowIndex, final int columnIndex,
      final SudokuSize sudokuSize) {
    return (rowIndex / sudokuSize.getBlockHeight()) * sudokuSize.getBlockHeight()
        + (columnIndex / sudokuSize.getBlockWidth());
  }

  public static boolean isFirstColumnInBlock(final int columnIndex, final SudokuSize sudokuSize) {
    if (columnIndex % sudokuSize.getBlockWidth() == 0) {
      return true;
    }
    return false;
  }

  public static boolean isFirstRowInBlock(final int rowIndex, final SudokuSize sudokuSize) {
    if (rowIndex % sudokuSize.getBlockHeight() == 0) {
      return true;
    }
    return false;
  }

  public static boolean isLastColumnInRow(final int columnIndex, final SudokuSize sudokuSize) {
    if (columnIndex == sudokuSize.getUnitSize() - 1) {
      return true;
    }
    return false;
  }

  public static boolean isLastRowInColumn(final int rowIndex, final SudokuSize sudokuSize) {
    if (rowIndex == sudokuSize.getUnitSize() - 1) {
      return true;
    }
    return false;
  }

}

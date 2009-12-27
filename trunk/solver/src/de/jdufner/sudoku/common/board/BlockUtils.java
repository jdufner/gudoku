// $Id: BlockUtils.java,v 1.2 2009/12/21 22:20:19 jdufner Exp $

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
   * Liefert den Blockindex zu einer Zelle (genauer Zeilen- und Spaltenindex) in Abh�ngigkeit der Gr��e zur�ck.
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

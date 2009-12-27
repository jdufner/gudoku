// $Id: Block.java,v 1.1 2009/11/17 20:34:32 jdufner Exp $

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

import java.util.List;


/**
 * A block represents a unit of m x n cells. It spans about several columns and rown and contains each literal one time.
 * 
 * TODO Umbenennen in Box http://www.sudopedia.org/wiki/Box
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.1 $
 */
public final class Block extends Unit {

  /**
   * @param sudoku
   *          Die Gr��e des Sudokus.
   * @param index
   *          Der Index des Blocks.
   * @param cells
   *          Eine Liste aller Zellen, die in diesem Block enthalten sind.
   */
  public Block(final SudokuSize sudokuSize, final int index, final List<Cell> cells) {
    super(sudokuSize, index, cells);
  }

  /**
   * @return
   */
  public int[] getColumnIndexes() {
    final int startIndex = (index % sudokuSize.getBlockHeight()) + sudokuSize.getBlockWidth();
    int[] columnIndexes = new int[sudokuSize.getBlockWidth()];
    for (int i = 0; i < sudokuSize.getBlockWidth(); i++) {
      columnIndexes[i] = startIndex + i;
    }
    return columnIndexes;
  }

  /**
   * @return
   */
  public int[] getRowIndexes() {
    final int startIndex = (index / sudokuSize.getBlockHeight()) * sudokuSize.getBlockHeight();
    int[] rowIndexes = new int[sudokuSize.getBlockHeight()];
    for (int i = 0; i < sudokuSize.getBlockHeight(); i++) {
      rowIndexes[i] = startIndex + i;
    }
    return rowIndexes;
  }

  @Override
  public boolean equals(final Object other) { // NOPMD by J�rgen on 16.11.09 00:23
    if (other instanceof Block) {
      return super.equals(other);
    }
    return false;
  }

}

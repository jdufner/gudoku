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
package de.jdufner.sudoku.common.board;

import java.util.List;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Row extends Unit {

  public Row(final SudokuSize sudokuSize, final int index, final List<Cell> cells) {
    super(sudokuSize, index, cells);
  }

  /**
   * @return
   */
  public int[] getBlockIndexes() {
    final int startIndex = (index / sudokuSize.getBlockHeight()) * sudokuSize.getBlockHeight();
    int[] blockIndexes = new int[sudokuSize.getBlockHeight()];
    for (int i = 0; i < blockIndexes.length; i++) {
      blockIndexes[i] = startIndex + i;
    }
    return blockIndexes;
  }

  @Override
  public boolean equals(final Object other) { // NOPMD by J�rgen on 16.11.09 00:23
    if (other instanceof Row) {
      return super.equals(other);
    }
    return false;
  }

  @Override
  public String toString() {
    return "Row " + super.toString();
  }

}

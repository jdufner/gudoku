// $Id: ShortString.java,v 1.8 2009/11/22 23:54:36 jdufner Exp $

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
package de.jdufner.sudoku.common.cellhandlerimpls;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.8 $
 */
public final class ShortString implements CellHandler {

  private final transient SudokuSize groesse;
  private transient StringBuilder stringBuilder = null;

  public ShortString(final Sudoku sudoku) {
    groesse = sudoku.getSize();
  }

  @Override
  public void initialize() {
    stringBuilder = new StringBuilder();
  }

  @Override
  public void handleCell(final Cell cell) {
    if (groesse.equals(SudokuSize.DEFAULT)) {
      if (cell.getValue().getValue() == 0) {
        stringBuilder.append(".");
      } else {
        stringBuilder.append(cell.getValue().getValue());
      }
    } else {
      stringBuilder.append(cell.getValue().getValue());
      if (!(cell.getRowIndex() >= groesse.getUnitSize() - 1 && cell.getColumnIndex() >= groesse.getUnitSize() - 1)) {
        stringBuilder.append(',');
      }
    }
  }

  @Override
  public String toString() {
    return stringBuilder.toString();
  }

}

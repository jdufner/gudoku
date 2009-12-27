// $Id: LongString.java,v 1.7 2009/11/17 20:34:33 jdufner Exp $

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

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.7 $
 */
public final class LongString implements CellHandler {

  private static String lineSeparator = System.getProperty("line.separator");

  private final transient Sudoku sudoku;
  private final transient StringBuilder stringBuilder = new StringBuilder();
  private transient int countValues = 0;
  private transient int possibleValues = 0;

  public LongString(final Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  @Override
  public void initialize() {
    // Nothing to do
  }

  @Override
  public void handleCell(final Cell cell) {
    if (cell.isFixed()) {
      countValues++;
    } else {
      possibleValues += cell.getCandidates().size();
    }
    stringBuilder.append(cell.toString());
    stringBuilder.append(lineSeparator);
  }

  @Override
  public String toString() {
    stringBuilder.append(countValues + " values found.").append(lineSeparator);
    stringBuilder.append(possibleValues + " values open.").append(lineSeparator);
    stringBuilder.append("Level: ").append(sudoku.getLevel()).append(lineSeparator);
    stringBuilder.append("Size: ").append(sudoku.getSize()).append(lineSeparator);
    if (sudoku.isSolved()) {
      stringBuilder.append("Sudoku solved!");
    }
    return stringBuilder.toString();
  }

}

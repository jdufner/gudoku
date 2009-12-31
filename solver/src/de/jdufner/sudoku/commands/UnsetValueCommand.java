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
package de.jdufner.sudoku.commands;

import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class UnsetValueCommand extends AbstractSingleValueCommand {

  protected UnsetValueCommand(final String creator, final int row, final int column, final Literal value) {
    super(creator);
    this.row = row;
    this.column = column;
    this.value = value;
  }

  @Override
  public void executeCommand(final Sudoku sudoku) {
    if (sudoku.getCell(row, column).isFixed()) {
      sudoku.getCell(row, column).setValue(Literal.EMPTY);
      successfully = true;
    } else {
      successfully = false;
    }
  }

  @Override
  public void unexecuteCommand(final Sudoku sudoku) {
    sudoku.getCell(row, column).setValue(value);
  }

  @Override
  public boolean reversible() {
    return true;
  }

  @Override
  public String toString() {
    return getCreator() + ": Entferne Wert " + value + " aus Zelle (" + row + ", " + column + ")";
  }

  @Override
  protected String toString(Sudoku sudoku) {
    return getCreator() + ": Entferne Wert " + value + " aus Zelle " + getCell(sudoku);
  }

}

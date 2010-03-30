// $Id: NonFixed.java 7 2009-12-31 20:46:32Z jdufner $

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
import de.jdufner.sudoku.common.board.SudokuSize;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 7 $
 */
public final class NotStarted implements CellHandler {

  // private static final Logger LOG = Logger.getLogger(NotStarted.class);

  private transient final SudokuSize sudokuSize;
  private transient boolean booleanValue = true;

  public NotStarted(final SudokuSize sudokuSize) {
    this.sudokuSize = sudokuSize;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void handleCell(final Cell cell) {
    if (booleanValue && !cell.isFixed() && cell.getCandidates().size() != sudokuSize.getUnitSize()) {
      booleanValue = false;
    }
  }

  public boolean isBooleanValue() {
    return booleanValue;
  }

}

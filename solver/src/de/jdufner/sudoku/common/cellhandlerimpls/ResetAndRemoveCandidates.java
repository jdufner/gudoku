// $Id: ResetAndRemoveCandidates.java,v 1.7 2009/12/17 22:17:40 jdufner Exp $

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
package de.jdufner.sudoku.common.cellhandlerimpls;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.Sudoku;

/**
 * Dieser {@link CellHandler} setzt alle ungesetzten Felder eines Sudokus zurück und entfernt die Kandidaten, die
 * bereits in gleichem Quadrant, Spalte oder Zeile gesetzt sind. Der {@link CellHandler} setzt nicht das Cell, auch wenn
 * nur ein Kandidat übrig bleibt.
 * 
 * @see Cell#resetCandidates()
 * @see Cell#removeCandidatesAndSetIfOnlyOneRemains(java.util.Collection)
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.7 $
 */
public final class ResetAndRemoveCandidates implements CellHandler {
  // TODO Die besetzten Zellen der Blöcke, Spalten und Zeilen zwischenspeichern.

  private Sudoku sudoku;

  public ResetAndRemoveCandidates(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  @Override
  public void initialize() {
    // Nothing to do
  }

  // TODO Performance Tuning: Ist es besser 3x removeAll aufzurufen oder nur 1x
  // removeAll mit allen Literals auf einmal
  @Override
  public void handleCell(final Cell cell) {
    if (!cell.isFixed()) {
      cell.resetCandidates();
      cell.getCandidates().removeAll(sudoku.getBlock(cell.getBlockIndex()).getFixedAsLiteral());
      cell.getCandidates().removeAll(sudoku.getColumn(cell.getColumnIndex()).getFixedAsLiteral());
      cell.getCandidates().removeAll(sudoku.getRow(cell.getRowIndex()).getFixedAsLiteral());
    }
  }

}

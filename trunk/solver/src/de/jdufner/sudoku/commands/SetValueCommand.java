// $Id$

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
package de.jdufner.sudoku.commands;

import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Dieser Befehl setzt mittels {@link Cell#setValue(Literal)} einen festen Wert {@link Literal} in eine {@link Cell}.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SetValueCommand extends AbstractSingleValueCommand {

  /**
   * Das sind die {@link Candidates} die beim setzen der Zelle gelöscht wurden.
   */
  private transient Candidates<Literal> candidates;

  protected SetValueCommand(final StrategyNameEnum strategyNameEnum, final int row, final int column,
      final Literal value) {
    super(strategyNameEnum);
    this.rowIndex = row;
    this.columnIndex = column;
    this.value = value;
  }

  protected SetValueCommand(final StrategyNameEnum strategyNameEnum, final Cell cell, final Literal value) {
    super(strategyNameEnum);
    this.rowIndex = cell.getRowIndex();
    this.columnIndex = cell.getColumnIndex();
    this.value = value;
  }

  @Override
  public void executeCommand(final Sudoku sudoku) {
    if (sudoku.getCell(rowIndex, columnIndex).isFixed()) {
      successfully = false;
    } else {
      candidates = (Candidates<Literal>) getCell(sudoku).getCandidates().clone();
      sudoku.getCell(rowIndex, columnIndex).setValue(value);
      successfully = true;
    }
  }

  @Override
  public void unexecuteCommand(final Sudoku sudoku) {
    sudoku.getCell(rowIndex, columnIndex).setValue(Literal.EMPTY);
    sudoku.getCell(rowIndex, columnIndex).setCandidates(candidates);
  }

  @Override
  public boolean reversible() {
    return true;
  }

  @Override
  public String toString() {
    return getStrategyName() + ": Setze Wert " + value + " in Zelle (" + rowIndex + ", " + columnIndex + ")";
  }

  @Override
  protected String toString(Sudoku sudoku) {
    return getStrategyName() + ": Setze Wert " + value + " in Zelle " + getCell(sudoku);
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof SetValueCommand) {
      final SetValueCommand that = (SetValueCommand) other;
      if (this.value.equals(that.value) && this.rowIndex == that.rowIndex && this.columnIndex == that.columnIndex) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return rowIndex;
  }

}

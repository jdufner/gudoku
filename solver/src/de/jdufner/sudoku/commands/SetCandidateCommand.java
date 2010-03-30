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
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SetCandidateCommand extends AbstractSingleValueCommand {

  private final static String JAVASCRIPT_COMMAND_NAME = "SC";

  private SetCandidateCommand(final StrategyNameEnum strategyNameEnum, final int row, final int column,
      final Literal value) {
    super(strategyNameEnum);
    this.rowIndex = row;
    this.columnIndex = column;
    this.value = value;
  }

  @Override
  public void executeCommand(final Sudoku sudoku) {
    successfully = sudoku.getCell(rowIndex, columnIndex).getCandidates().add(value);
  }

  @Override
  public void unexecuteCommand(final Sudoku sudoku) {
    sudoku.getCell(rowIndex, columnIndex).getCandidates().remove(value);
  }

  @Override
  public boolean reversible() {
    return true;
  }

  @Override
  public String toString() {
    return getStrategyName() + ": Setze Kandidat " + value + " in Zelle (" + rowIndex + ", " + columnIndex + ")";
  }

  @Override
  protected String toString(final Sudoku sudoku) {
    return getStrategyName() + ": Setze Kandidat " + value + " in Zelle " + getCell(sudoku);
  }

  @Override
  public boolean equals(final Object other) { // NOPMD J�rgen Dufner 14.03.2010
    if (other instanceof SetCandidateCommand) {
      return super.equals(other);
    }
    return false;
  }

  @Override
  public String toJavascriptString() {
    return getJavascriptCellNumber() + JAVASCRIPT_COMMAND_NAME + value.getValue();
  }

  public static class SetCandidateCommandBuilder {

    private final transient StrategyNameEnum strategyNameEnum;
    private final transient int rowIndex;
    private final transient int columnIndex;
    private final transient Literal value;

    public SetCandidateCommandBuilder(final StrategyNameEnum strategyNameEnum, final int rowIndex,
        final int columnIndex, final Literal value) {
      this.strategyNameEnum = strategyNameEnum;
      this.rowIndex = rowIndex;
      this.columnIndex = columnIndex;
      this.value = value;
    }

    public SetCandidateCommand build() {
      return new SetCandidateCommand(strategyNameEnum, rowIndex, columnIndex, value);
    }

  }

}

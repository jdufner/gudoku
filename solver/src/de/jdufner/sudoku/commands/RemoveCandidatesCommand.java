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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Entfernt einen oder mehrere {@link Literal} aus der Liste der {@link Candidates}.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class RemoveCandidatesCommand extends AbstractCommand {

  private final static String JAVASCRIPT_COMMAND_NAME = "RM";

  /**
   * Die Kandidaten, die entfernt werden sollen.
   */
  private transient Collection<Literal> candidates;

  /**
   * Konstruktor eines Befehls.
   * 
   * @param strategyNameEnum
   *          Der Erzeuger des Befehls.
   * @param cell
   *          Die Zelle auf welcher der Befehl ausgeführt werden soll.
   * @param candidatesToRemove
   *          Die Kandidaten, welche entfernt werden sollen.
   */
  private RemoveCandidatesCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Collection<Literal> candidatesToRemove) {
    super(strategyNameEnum);
    init(cell, candidatesToRemove);
  }

  private void init(final Cell cell, final Collection<Literal> candidatesToRemove) {
    rowIndex = cell.getRowIndex();
    columnIndex = cell.getColumnIndex();
    candidates = candidatesToRemove;
  }

  public Collection<Literal> getCandidates() {
    return candidates;
  }

  public void setCandidates(Collection<Literal> candidates) {
    this.candidates = candidates;
  }

  @Override
  public void executeCommand(final Grid sudoku) {
    successfully = sudoku.getCell(getCell(sudoku).getNumber()).removeCandidatesAndSetIfOnlyOneRemains(candidates);
    assert getCell(sudoku).isValid() : "Zelle ist in keinem gültigen Zustand.";
  }

  @Override
  public void unexecuteCommand(final Grid sudoku) {
    if (getCell(sudoku).isFixed()) {
      getCell(sudoku).setValue(Literal.EMPTY);
      getCell(sudoku).setCandidates(new Candidates<Literal>(this.candidates));
    } else {
      getCell(sudoku).getCandidates().addAll(candidates);
    }
  }

  @Override
  public boolean reversible() {
    return true;
  }

  @Override
  public String toString() {
    return getStrategyName() + ": Entferne Kandidaten " + candidates + " in Zelle (" + rowIndex + ", " + columnIndex
        + ")";
  }

  @Override
  protected String toString(final Grid sudoku) {
    return getStrategyName() + ": Entferne Kandidaten " + candidates + " in Zelle " + getCell(sudoku);
  }

  @Override
  public boolean equals(final Object other) { // NOPMD Jürgen Dufner 15.03.2010
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof RemoveCandidatesCommand) {
      final RemoveCandidatesCommand that = (RemoveCandidatesCommand) other;
      return (super.equals(other) && isEqual(this.candidates, that.candidates));
    }
    return false;
  }

  @Override
  public String toJavascriptString() {
    return getJavascriptCellNumber() + JAVASCRIPT_COMMAND_NAME + getJavascriptCandidates();
  }

  private String getJavascriptCandidates() {
    final StringBuilder sb = new StringBuilder();
    for (Literal l : candidates) {
      sb.append(l.getValue());
    }
    return sb.toString();
  }

  public static class RemoveCandidatesCommandBuilder {
    private final transient StrategyNameEnum strategyNameEnum;
    private transient Cell cell = null;
    private final transient int rowIndex;
    private final transient int columnIndex;
    private final transient Set<Literal> candidates = new HashSet<Literal>();

    public RemoveCandidatesCommandBuilder(final StrategyNameEnum strategyNameEnum, final int rowIndex,
        final int columnIndex) {
      this.strategyNameEnum = strategyNameEnum;
      this.rowIndex = rowIndex;
      this.columnIndex = columnIndex;
    }

    public RemoveCandidatesCommandBuilder(final StrategyNameEnum strategyNameEnum, final Cell cell) {
      this.strategyNameEnum = strategyNameEnum;
      this.cell = cell;
      this.rowIndex = -1;
      this.columnIndex = -1;
    }

    public RemoveCandidatesCommandBuilder addCandidate(final Literal candiate) {
      candidates.add(candiate);
      return this;
    }

    public RemoveCandidatesCommandBuilder addCandidate(final Collection<Literal> candiates) {
      candidates.addAll(candiates);
      return this;
    }

    public RemoveCandidatesCommandBuilder addCandidate(final int... values) {
      for (int value : values) {
        candidates.add(Literal.getInstance(value));
      }
      return this;
    }

    public Command build() {
      if (candidates.isEmpty()) {
        throw new IllegalStateException("Es wurden keine Kandidaten hinzugefügt, Command kann nicht erzeugt werden.");
      }
      if (cell == null && rowIndex < 0 && columnIndex < 0) {
        throw new IllegalStateException(
            "Es wurde keine Zelle hinzugefügt oder Zellkoordinaten angegeben, Command kann nicht erzeugt werden.");
      }
      if (cell == null) {
        cell = new Cell(rowIndex, columnIndex, Literal.EMPTY, SudokuSize.DEFAULT);
      }
      return new RemoveCandidatesCommand(strategyNameEnum, cell, candidates);
    }
  }

}

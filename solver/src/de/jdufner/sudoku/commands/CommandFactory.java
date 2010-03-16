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
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since
 * @version $Revision$
 */
public final class CommandFactory {

  //  private final static Logger LOG = Logger.getLogger(CommandFactory.class);

  private CommandFactory() {
  }

  public static Command buildRemoveCandidatesCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Literal candidateToRemove) {
    return new RemoveCandidatesCommand(strategyNameEnum, cell, candidateToRemove);
  }

  public static Command buildRemoveCandidatesCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Candidates<Literal> candidatesToRemove) {
    return new RemoveCandidatesCommand(strategyNameEnum, cell, candidatesToRemove);
  }

  public static Command buildRetainCandidatesCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Collection<Literal> candidates) {
    return new RetainCandidatesCommand(strategyNameEnum, cell, candidates);
  }

  public static Command buildSetCandidateCommand(final StrategyNameEnum strategyNameEnum, final int row,
      final int column, final Literal value) {
    return new SetCandidateCommand(strategyNameEnum, row, column, value);
  }

  public static Command buildSetValueCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Literal value) {
    return new SetValueCommand(strategyNameEnum, cell, value);
  }

  public static Command buildSetValueCommand(final StrategyNameEnum strategyNameEnum, final int row, final int column,
      final Literal value) {
    return new SetValueCommand(strategyNameEnum, row, column, value);
  }

  public static Command buildUnsetCandidateCommand(final StrategyNameEnum strategyNameEnum, final int row,
      final int column, final Literal value) {
    return new UnsetCandidateCommand(strategyNameEnum, row, column, value);
  }

  public static Command buildUnsetValueCommand(final StrategyNameEnum strategyNameEnum, final int row,
      final int column, final Literal value) {
    return new UnsetValueCommand(strategyNameEnum, row, column, value);
  }

  public static class RetainCommandBuilder {
    private final StrategyNameEnum strategyNameEnum;
    private final int rowIndex;
    private final int columnIndex;
    private final Set<Literal> candidates = new HashSet<Literal>();

    public RetainCommandBuilder(final StrategyNameEnum strategyNameEnum, final int rowIndex, final int columnIndex) {
      this.strategyNameEnum = strategyNameEnum;
      this.rowIndex = rowIndex;
      this.columnIndex = columnIndex;
    }

    public RetainCommandBuilder addCandidate(final Literal candiate) {
      candidates.add(candiate);
      return this;
    }

    public RetainCommandBuilder addCandidate(final int... values) {
      for (int value : values) {
        candidates.add(Literal.getInstance(value));
      }
      return this;
    }

    public Command build() {
      if (candidates.isEmpty()) {
        throw new IllegalStateException("Es wurden keine Kandidaten hinzugefügt, Command kann nicht erzeugt werden.");
      }
      return buildRetainCandidatesCommand(strategyNameEnum, new Cell(rowIndex, columnIndex, Literal.EMPTY,
          SudokuSize.DEFAULT), candidates);
    }
  }

}

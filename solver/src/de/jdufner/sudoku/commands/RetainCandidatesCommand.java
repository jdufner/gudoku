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

import java.util.Collection;

import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Entfernt alle Kandidaten {@link Candidates} von einer Zelle, au�er die �bergebenen Kandidaten auf der {@link Cell}.
 * Die �bergebenen Kandidaten verbleiben in der Zelle.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class RetainCandidatesCommand extends AbstractCommand {

  /**
   * Die Kandidaten, die verbleiben sollen.
   */
  private final transient Collection<Literal> candidates;
  /**
   * Die Kandidaten, die entfernt werden sollen.
   */
  private final transient Collection<Literal> candidatesToRemove = new Candidates<Literal>();

  /**
   * Konstruktor eines Befehls, welcher alle anderen Kandiaten entfernt, au�er die �bergebenen.
   * 
   * @param strategyNameEnum
   *          Der Erzeuger des Befehls.
   * @param cell
   *          Die Zelle auf welcher der Befehl ausgef�hrt werden soll.
   * @param candidates
   *          Die Kandidaten, welche beibehalten werden sollen.
   */
  protected RetainCandidatesCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Collection<Literal> candidates) {
    super(strategyNameEnum);
    this.rowIndex = cell.getRowIndex();
    this.columnIndex = cell.getColumnIndex();
    this.candidates = candidates;
    for (Literal candidate : candidates) {
      if (!cell.getCandidates().contains(candidate)) {
        candidatesToRemove.add(candidate);
      }
    }
  }

  @Override
  public void executeCommand(final Sudoku sudoku) {
    final int numberOfCandidatesBefore = sudoku.getCell(getCell(sudoku).getNumber()).getCandidates().size();
    sudoku.getCell(getCell(sudoku).getNumber()).getCandidates().retainAll(candidates);
    final int numberOfCandatesAfter = sudoku.getCell(getCell(sudoku).getNumber()).getCandidates().size();
    if (numberOfCandatesAfter < numberOfCandidatesBefore) {
      successfully = true;
    }
  }

  @Override
  public void unexecuteCommand(final Sudoku sudoku) {
    getCell(sudoku).getCandidates().addAll(candidatesToRemove);
  }

  @Override
  public boolean reversible() {
    return true;
  }

  @Override
  public String toString() {
    return getStrategyName() + ": Behalte Kandidaten " + candidates + " in Zelle (" + rowIndex + ", " + columnIndex
        + ")";
  }

  @Override
  protected String toString(final Sudoku sudoku) {
    return getStrategyName() + ": Behalte Kandidaten " + candidates + " in Zelle " + getCell(sudoku);
  }

  @Override
  public boolean equals(final Object other) { // NOPMD J�rgen Dufner 15.03.2010
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof RetainCandidatesCommand) {
      final RetainCandidatesCommand that = (RetainCandidatesCommand) other;
      return (super.equals(other) && isEqual(this.candidates, that.candidates));
    }
    return false;
  }

}

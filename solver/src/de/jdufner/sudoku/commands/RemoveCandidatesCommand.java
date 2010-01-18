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

/**
 * Entfernt einen oder mehrere {@link Literal} aus der Liste der {@link Candidates}.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class RemoveCandidatesCommand extends AbstractCommand {

  /**
   * Die Kandidaten, die entfernt werden sollen.
   */
  private transient Candidates<Literal> candidates;

  /**
   * Konstruktor eines Befehls. Ist ein vereinfachter Konstruktor für
   * {@link #RemoveCandidatesCommand(String, Cell, Candidates)}
   * 
   * @param creator
   *          Der Erzeuger des Befehls.
   * @param cell
   *          Die Zelle auf welcher der Befehl ausgeführt werden soll.
   * @param candidateToRemove
   *          Der Kandidat, welcher entfernt werden soll.
   */
  protected RemoveCandidatesCommand(final String creator, final Cell cell, final Literal candidateToRemove) {
    super(creator);
    final Candidates<Literal> candidatesToRemove = new Candidates<Literal>();
    candidatesToRemove.add(candidateToRemove);
    init(cell, candidatesToRemove);
  }

  /**
   * Konstruktor eines Befehls.
   * 
   * @param creator
   *          Der Erzeuger des Befehls.
   * @param cell
   *          Die Zelle auf welcher der Befehl ausgeführt werden soll.
   * @param candidatesToRemove
   *          Die Kandidaten, welche entfernt werden sollen.
   */
  public RemoveCandidatesCommand(final String creator, final Cell cell, final Candidates<Literal> candidatesToRemove) {
    super(creator);
    init(cell, candidatesToRemove);
  }

  private void init(final Cell cell, final Candidates<Literal> candidatesToRemove) {
    rowIndex = cell.getRowIndex();
    columnIndex = cell.getColumnIndex();
    candidates = candidatesToRemove;
  }

  @Override
  public void executeCommand(final Sudoku sudoku) {
    successfully = sudoku.getCell(getCell(sudoku).getNumber()).removeCandidatesAndSetIfOnlyOneRemains(candidates);
    assert getCell(sudoku).isValid() : "Zelle ist in keinem gültigen Zustand.";
  }

  @Override
  public void unexecuteCommand(final Sudoku sudoku) {
    if (getCell(sudoku).isFixed()) {
      getCell(sudoku).setValue(Literal.EMPTY);
      getCell(sudoku).setCandidates(candidates);
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
    return getCreator() + ": Entferne Kandidaten " + candidates + " in Zelle (" + rowIndex + ", " + columnIndex + ")";
  }

  @Override
  protected String toString(Sudoku sudoku) {
    return getCreator() + ": Entferne Kandidaten " + candidates + " in Zelle " + getCell(sudoku);
  }

  @Override
  public boolean equals(final Object other) {
    if (other == null) {
      return false;
    }
    if (this == other) {
      return true;
    }
    if (other instanceof RemoveCandidatesCommand) {
      final RemoveCandidatesCommand that = (RemoveCandidatesCommand) other;
      if (this.rowIndex == that.rowIndex && this.columnIndex == that.columnIndex && this.candidates.isEquals(that.candidates)) {
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

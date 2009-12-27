// $Id: RetainCandidatesCommand.java,v 1.10 2009/12/14 20:46:15 jdufner Exp $

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

import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;

/**
 * Entfernt alle Kandidaten {@link Candidates} von einer Zelle, außer die übergebenen Kandidaten auf der {@link Cell}.
 * Die übergebenen Kandidaten verbleiben in der Zelle.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.10 $
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
   * Konstruktor eines Befehls, welcher alle anderen Kandiaten entfernt, außer die übergebenen.
   * 
   * @param creator
   *          Der Erzeuger des Befehls.
   * @param cell
   *          Die Zelle auf welcher der Befehl ausgeführt werden soll.
   * @param candidates
   *          Die Kandidaten, welche beibehalten werden sollen.
   */
  protected RetainCandidatesCommand(final String creator, final Cell cell, final Collection<Literal> candidates) {
    super(creator);
    this.row = cell.getRowIndex();
    this.column = cell.getColumnIndex();
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
    return getCreator() + ": Behalte Kandidaten " + candidates + " in Zelle (" + row + ", " + column + ")";
  }

  @Override
  protected String toString(Sudoku sudoku) {
    return getCreator() + ": Behalte Kandidaten " + candidates + " in Zelle " + getCell(sudoku);
  }

  @Override
  public boolean equals(final Object other) {
    if (other == null) {
      return false;
    }
    if (this == other) {
      return true;
    }
    if (other instanceof RetainCandidatesCommand) {
      final RetainCandidatesCommand that = (RetainCandidatesCommand) other;
      if (this.row == that.row && this.column == that.column && this.candidates.containsAll(that.candidates)
          && that.candidates.containsAll(this.candidates)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return row;
  }

}

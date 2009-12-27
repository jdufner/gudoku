// $Id: StrategyResult.java,v 1.4 2009/12/05 23:27:47 jdufner Exp $

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
package de.jdufner.sudoku.solver.strategy;

import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since
 * @version $Revision: 1.4 $
 */
public final class StrategyResult {

  private transient long startTime = 0;
  private transient long endTime = 0;

  private transient int numberCandidatesBefore = 0;
  private transient int numberCandidatesAfter = 0;

  private transient int numberFixedBefore = 0;
  private transient int numberFixedAfter = 0;

  private transient String strategyName = null;
  private transient Level level = Level.UNBEKANNT;
  private transient Collection<Command> commands;

  private transient boolean sudokuUnique = true;

  public StrategyResult() {
  }

  public StrategyResult(final String strategyName, final Level level) {
    this.strategyName = strategyName;
    this.level = level;
  }

  /**
   * Startet die Zeitmessung f�r die Ausf�hrung einer Strategie.
   */
  protected void start() {
    startTime = System.currentTimeMillis();
  }

  /**
   * Stoppt die Zeitmessung f�r die Ausf�hrung einer Strategie.
   */
  protected void stop() {
    endTime = System.currentTimeMillis();
  }

  /**
   * Liefert die Dauer f�r die Ausf�hrung einer Strategie in Millisekunden.
   * 
   * @return Ausf�hrungsdauer einer Strategie in Millisekunden.
   */
  public long getDurationInMillis() {
    if (startTime == 0) {
      throw new IllegalStateException("Startzeitpunkt wurde nicht gesetzt.");
    }
    if (endTime == 0) {
      throw new IllegalStateException("Endzeitpunkt wurde nicht gesetzt.");
    }
    if (endTime < startTime) {
      throw new IllegalStateException("Endzeitpunkt liegt vor Startzeitpunkt.");
    }
    return endTime - startTime;
  }

  protected void storeStateBefore(final Sudoku sudoku) {
    numberCandidatesBefore = sudoku.getNumberOfCandidates();
    numberFixedBefore = sudoku.getNumberOfFixed();
  }

  protected void storeStateAfter(final Sudoku sudoku) {
    numberCandidatesAfter = sudoku.getNumberOfCandidates();
    numberFixedAfter = sudoku.getNumberOfFixed();
  }

  public int getNumberEleminatedCandidates() {
    if (numberCandidatesAfter > numberCandidatesBefore) {
      throw new IllegalStateException("Die Anzahl der Kandidaten ist nach Ausf�hrung einer Strategie gr��er als zuvor.");
    }
    return numberCandidatesBefore - numberCandidatesAfter;
  }

  public int getNumberNewlyFixedCells() {
    if (numberFixedAfter < numberFixedBefore) {
      throw new IllegalStateException(
          "Die Anzahl der gesetzten Zellen ist nach der Ausf�hrung einer Strategie kleiner als zuvor.");
    }
    return numberFixedAfter - numberFixedBefore;
  }

  public String getStrategyName() {
    return strategyName;
  }

  public void setStrategyName(final String strategyName) {
    this.strategyName = strategyName;
  }

  public Level getLevel() {
    return level;
  }

  public void setLevel(final Level level) {
    this.level = level;
  }

  public Collection<Command> getCommands() {
    return commands;
  }

  public void setCommands(final Collection<Command> commands) {
    this.commands = commands;
  }

  public boolean isSudokuUnique() {
    return sudokuUnique;
  }

  public void setSudokuUnique(final boolean sudokuUnique) {
    this.sudokuUnique = sudokuUnique;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Strategie ").append(getStrategyName());
    sb.append(", Schwierigkeitsgrad ").append(getLevel());
    sb.append(", Dauer ").append(getDurationInMillis()).append(" ms");
    sb.append(", Entfernte Kandidaten ").append(getNumberEleminatedCandidates());
    sb.append(", Gesetzte Zelle ").append(getNumberNewlyFixedCells());
    return sb.toString();
  }

}

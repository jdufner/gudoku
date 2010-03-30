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
package de.jdufner.sudoku.solver.strategy;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.service.StrategySolver;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Diese Klasse stellt die allgemeine Funktion f�r eine L�sungsstrategie eines Sudokus zur Verf�gung.
 * 
 * Diese Klasse geh�rt nicht zur Schnittstelle des Pakets. Der Zugriff erfolgt �ber den {@link StrategySolver}.
 * 
 * TODO Trotzdem muss ein Interface Strategy eingef�hrt werden, damit klar wird welche Methoden von au�en aufgerufen
 * werden und welche f�r interne Zwecke da sind.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractStrategy implements Strategy {

  private static final Logger LOG = Logger.getLogger(AbstractStrategy.class);

  private final transient Sudoku sudoku;
  private transient Collection<Command> commands = new HashSet<Command>();

  /**
   * Erzeugt eine konkrete Strategie.
   * 
   * @param sudoku
   */
  protected AbstractStrategy(final Sudoku sudoku) {
    this.sudoku = sudoku;
    assert sudoku.isValid() : "Das �bergebene Sudoku ist in keinem g�ltigen Zustand!";
  }

  protected Sudoku getSudoku() {
    return sudoku;
  }

  protected Collection<Command> getCommands() {
    return commands;
  }

  protected void setCommands(final Collection<Command> commands) {
    this.commands = commands;
  }

  /*
   * @see de.jdufner.sudoku.solver.strategy.Strategy#getLevel()
   */
  public abstract Level getLevel();

  /**
   * Diese Methode wird von die Subklasse f�r die tats�chliche Strategie implemeniert.
   * 
   * @return Den Namen der Strategie.
   */
  public abstract StrategyNameEnum getStrategyName();

  /**
   * Diese Methode wird von die Subklasse f�r die tats�chliche Strategie implemeniert.
   * 
   * @return
   */
  protected abstract Collection<Command> executeStrategy();

  /**
   * F�hrt diese Strategie aus und f�hrt einige zus�tzliche Dinge durch.
   * 
   * Diese Methode darf nicht �berschrieben werden. Hier werden einige Dinge f�r alle Strategien implementiert, wie
   * bspw. Zeitmessung.
   * 
   * @return Das Ausf�hrungsergebnis.
   */
  public final StrategyResult execute() {
    final StrategyResult strategyResult = new StrategyResult(getStrategyName(), getLevel());
    if (LOG.isInfoEnabled()) {
      LOG.info("F�hre " + getStrategyName() + " (" + getClass().getSimpleName() + ") aus.");
    }
    //strategyResult.storeStateBefore(getSudoku());
    strategyResult.start();
    setCommands(executeStrategy());
    strategyResult.stop();
    //strategyResult.storeStateAfter(getSudoku());
    strategyResult.setCommands(getCommands());
    strategyResult.setSudokuUnique(isSudokuUnique());
    if (LOG.isInfoEnabled()) {
      LOG.info(getClass().getSimpleName() + " hat " + getCommands().size() + " Commands erzeugt.");
    }
    if (LOG.isDebugEnabled()) {
      LOG.info("Ausf�hrungsdauer von " + getClass().getSimpleName() + " war " + strategyResult.getDurationInMillis()
          + " ms.");
    }
    return strategyResult;
  }

  /**
   * Liefert den Standardwert <code>true</code> zur�ck f�r alle Strategien. Nur in der Backtracking-Strategie existiert
   * eine echte Implementierung.
   * 
   * @return <code>true</code> als Standardwert.
   */
  @Override
  public boolean isSudokuUnique() {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Verwende Standardwert.");
    }
    return true;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

}

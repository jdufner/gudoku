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
package de.jdufner.sudoku.solver.service;

import java.util.Collection;
import java.util.List;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandUtils;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.StrategyResult;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Diese Klasse beschreibt eine L�sung eines Sudokus. Eine L�sung setzt sich aus mehreren Einzelschritten zusammen. Eine
 * vorhandene L�sung ist eine nur eine L�sung, m�glicherweise existieren weitere L�sungen, sowohl in dem Sinne, dass
 * dieselbe L�sung anders erzeugt werden kann durch andere L�sungsschritte als auch, dass m�glicherweise das Sudoku
 * unterbestimmt ist und weitere L�sungen existieren.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class Solution {

  private final transient Sudoku quest;
  private transient Sudoku result;
  private transient boolean unique;
  private transient Level level;
  private transient List<StrategyResult> results;

  /**
   * @param quest
   *          Das Start-Sudoku, f�r das die L�sung berechnet werden. Hier muss schon eine Kopie / Klon �bergeben werden.
   */
  public Solution(final Sudoku quest) {
    this.quest = quest;
  }

  public int getNumberSuccessfulCommand(final StrategyNameEnum strategyNameEnum) {
    int number = 0;
    for (StrategyResult strategyResult : results) {
      for (Command command : strategyResult.getCommands()) {
        if (command.isSuccessfully() && command.getStrategyName().equals(strategyNameEnum.name())) {
          number++;
        }
      }
    }
    return number;
  }

  public Sudoku getQuest() {
    return quest;
  }

  public Sudoku getResult() {
    return result;
  }

  public void setResult(final Sudoku result) {
    this.result = result;
  }

  public boolean isUnique() {
    return unique;
  }

  public void setUnique(final boolean unique) {
    this.unique = unique;
  }

  public Level getLevel() {
    return level;
  }

  public void setLevel(final Level level) {
    this.level = level;
  }

  public List<StrategyResult> getResults() {
    return results;
  }

  public void setResults(final List<StrategyResult> results) {
    this.results = results;
  }

  @Override
  public String toString() {
    final String lineSeparator = System.getProperty("line.separator");
    final StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append(lineSeparator);
    sb.append("R�tsel: ").append(getQuest()).append(lineSeparator);
    sb.append("L�sung: ").append(getResult()).append(lineSeparator);
    sb.append("Eindeutigkeit: ").append(isUnique()).append(lineSeparator);
    sb.append("Schwierigkeitsgrad: ").append(getLevel()).append(lineSeparator);

    int step = 0;
    if (getResult() == null) {
      sb.append("Kein Ergebnis gefunden.");
    } else {
      for (StrategyResult strategyResult : getResults()) {
        step += 1;
        sb.append(step).append(". Zwischenschritt(").append(strategyResult.getNumberFixedBefore()).append(" Zellen /")
            .append(strategyResult.getNumberCandidatesBefore()).append(" Kandidaten): ").append(
                strategyResult.getSudokuBefore()).append(lineSeparator);
        final Collection<? extends Command> cmds = CommandUtils.aggregateCommandsIfPossible(strategyResult
            .getCommands());
        sb.append(strategyResult.getStrategyName()).append('(').append(cmds.size()).append(')').append(": ");
        boolean first = true;
        for (Command cmd : cmds) {
          if (first) {
            first = false;
          } else {
            sb.append(',');
          }
          sb.append(cmd.toJavascriptString());
        }
        sb.append(lineSeparator);
      }
    }
    return sb.toString();
  }

}

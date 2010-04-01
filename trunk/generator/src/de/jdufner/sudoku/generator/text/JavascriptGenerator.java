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
package de.jdufner.sudoku.generator.text;

import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandUtils;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.solver.strategy.StrategyResult;

/**
 * Erzeugt eine Javascript-Repr�sentation der Solution. Das Javascript-Objektmodell ist im HTML-Code vorgegeben.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-04-01
 * @version $Revision$
 */
public final class JavascriptGenerator {

  private JavascriptGenerator() {
  }

  public static String toJavascript(int id, Solution solution) {
    final String lineSeparator = System.getProperty("line.separator");
    StringBuilder sb = new StringBuilder();
    sb.append("new solution(");
    sb.append(id).append(", \"").append(solution.getQuest()).append("\", \"").append(solution.getResult()).append(
        "\", \"").append(solution.getLevel().getName()).append("\", ").append(solution.isUnique()).append(", [")
        .append(lineSeparator);
    int i = 0;
    for (StrategyResult strategyResult : solution.getResults()) {
      if (i != 0) {
        sb.append(",");
      }
      sb.append("new step(");
      sb.append("\"").append(strategyResult.getStrategyName().name()).append("\", \"").append(
          strategyResult.getSudokuBefore()).append("\", [");
      final Collection<? extends Command> cmds = CommandUtils.aggregateCommandsIfPossible(strategyResult.getCommands());
      int j = 0;
      for (Command cmd : cmds) {
        if (j != 0) {
          sb.append(",");
        }
        sb.append("\"").append(cmd.toJavascriptString()).append("\"");
        j++;
      }
      sb.append("])").append(lineSeparator);
      i++;
    }
    sb.append("])").append(lineSeparator);
    return sb.toString();
  }

}

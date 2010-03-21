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
package de.jdufner.sudoku.solver.strategy.intersection.removal;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class BoxLineReductionRowStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 0).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 1).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 6, 2).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 0).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 1).addCandidate(8)
        .build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.INTERSECTION_REMOVAL, 7, 2).addCandidate(8)
        .build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new BoxLineReductionRowStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "3-6-8,7,3-5-8,4,9,3-6,2,1-8,1-5,2-4-6-9,2-5-6-9,2-4-5-9,1,8,6-7,3,7-9,4-5-7-9,1,8-9,3-4-8-9,2,3-7,5,4-7-8-9,6,4-7-9,5,4,1-2-9,8,1-3-6-7,1-3-6-7-9,1-7-9,1-2-7-9,3-6,6-9,3,1-9,6-7-9,2,1-4-6-7-9,1-4-7-9,5,8,7,1-2-6-8-9,1-2-8-9,5,1-3-4-6,1-3-4-6-9,1-4-9,1-2-9,3-6,2-3-4-8-9,1-2-8-9,1-2-3-4-7-8-9,3-6-7-9,5,1-3-4-6-7-8-9,1-7-8-9,1-2-7-8-9,1-2-7-9,2-3-8-9,1-2-5-8-9,1-2-3-5-7-8-9,3-7-9,1-3-7,1-3-7-8-9,6,4,1-2-7-9,4-8-9,1-8-9,6,7-9,1-4-7,2,5,3,1-7-9";
  }

}

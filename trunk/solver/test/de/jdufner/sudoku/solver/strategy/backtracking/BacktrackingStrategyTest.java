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
package de.jdufner.sudoku.solver.strategy.backtracking;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.SetValueCommand.SetValueCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class BacktrackingStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 0, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 5, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 5, 8, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 6, 1, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 7, 4, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 8, 7, 2).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 0, 6, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 1, 4, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 8, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 7, 7, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 8, 0, 4).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 0, 0, 5).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 1, 8, 5).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 2, 5, 5).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 2, 5).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 0, 4, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 2, 7, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 8, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 1, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 6, 6, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 7, 0, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 8, 5, 6).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 1, 7).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 5, 6, 7).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 6, 8, 7).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 1, 6, 8).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 7, 8).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 5, 1, 8).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 8, 8, 8).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 0, 8, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 1, 5, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 2, 2, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 3, 7, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 4, 4, 9).build());
    commands.add(new SetValueCommandBuilder(StrategyNameEnum.BACKTRACKING, 5, 0, 9).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new BacktrackingStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    //return "2-4-6-9,2-4-6,3,2-6-9,8,7,2-5-6-9,5-6-9,1,2-6-8-9,5,6-9,1,4,2-3-6-9,2-3-6-8-9,7,3-6-9,7,1-2-6-8,1-6-9,5,2-6-9,2-3-6-9,4,3-6-8-9,3-6-9,3-4-5-6-8,9,4-5-6,7,1-6,1-5-6-8,3-5-6,1-3-4-5-6,2,1,4-6-7-8,2,4-6-8-9,3,5-6-8-9,5-6-7-9,4-5-6-9,4-5-6-7-9,3-4-5-6,4-6-7,4-5-6-7,2-4-6-9,1-2-6-9,1-2-5-6-9,3-5-6-7-9,1-3-4-5-6-9,8,2-5-6-9,3,1-5-6-7-9,2-6-9,1-2-6-7-9,4,5-6-7-8-9,5-6-8-9,5-6-7-9,2-4-5-6-9,1-2-4-6-7,8,2-3-6-9,1-2-6-7-9,1-2-6-9,3-5-6-7-9,3-4-5-6-9,3-4-5-6-7-9,4-6-9,4-6-7,4-6-7-9,3-6-8-9,5,6-8-9,1,2,3-4-6-7-9";
    return "5-9,1,2,8,4-6,3,4-6,7,5-9,7,3,6,2,4-9,5-9,4-8,1,5-8,8,4,5-9,1,7,5-6,2,6-9,3,2-5-6,2-6-7,5-9,4,3,8,1,2-6-9,2-6-7,1,6-8,3,7,2-9,2-9,5,4-6-8,4-6-8,2-9,2-7-8,4,6,5,1,7-8,3,2-7-8-9,3,2-6,1,9,8,4,6-7,5,2-6-7,2-4-6,9,8,5,2-6,7,3,2-4-6,1,2-4-6,5,7,3,1,2-6,9,2-4-6-8,2-4-6-8";
  }

}

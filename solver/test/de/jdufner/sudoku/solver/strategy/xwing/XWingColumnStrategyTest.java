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
package de.jdufner.sudoku.solver.strategy.xwing;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class XWingColumnStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 1).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 4).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 6).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 2, 8).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 8, 4).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 8, 6).addCandidate(6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.XWING, 8, 8).addCandidate(6).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new XWingColumnStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "2,8,3,4,6-9,1,7,5,6-9,1,6-9,5,2-3-9,8,7,2-6,4,3-6-9,7,6-9,4,2-3-5-9,3-5-6-9,3-5-6-9,1-2-6-8,6-8-9,1-3-6-8-9,4,1,7-9,5-8-9,2,5-9,5-6-8,3,5-6-7-8,6,2,7-9,3-5-8-9,1,3-5-9,4,7-8,5-7-8,3,5,8,6,7,4,9,1,2,8,3,1,5-9,4-5-6-9,2,5-6,7-9,4-7-9,5,7,6,1,4-9,8,3,2,4-9,9,4,2,7,3-5-6,3-5-6,1-5-6-8,6-8,1-5-6-8";
  }

}

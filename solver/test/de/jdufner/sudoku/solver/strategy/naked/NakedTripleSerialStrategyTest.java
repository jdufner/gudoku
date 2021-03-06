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
package de.jdufner.sudoku.solver.strategy.naked;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

public final class NakedTripleSerialStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 2, 0).addCandidate(1, 3, 4).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 2, 1).addCandidate(1, 3, 4).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 2, 3).addCandidate(1, 3, 4).build());

    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 0, 3).addCandidate(2, 3, 6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 1, 3).addCandidate(2, 3, 6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 2, 3).addCandidate(2, 3, 6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 3, 3).addCandidate(2, 3, 6).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 4, 3).addCandidate(2, 3, 6).build());

    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 0, 3).addCandidate(3, 4, 5).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 1, 3).addCandidate(3, 4, 5).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 2, 3).addCandidate(3, 4, 5).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 3, 4).addCandidate(3, 4, 5).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 4, 4).addCandidate(3, 4, 5).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 6, 4).addCandidate(3, 4, 5).build());

    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 6, 0).addCandidate(3, 4, 8).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 7, 0).addCandidate(3, 4, 8).build());
    commands.add(new RemoveCandidatesCommandBuilder(StrategyNameEnum.NAKED_TRIPLE, 7, 1).addCandidate(3, 4, 8).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new NakedTripleSerialStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "(1378),(3468),(34),(134578),(345),2,(13456),(13456),9,(13),(346),2,(1345),(345),9,7,(13456),8,(13789),(3489),5,(13478),(34),6,(134),(134),2,6,1,(89),(245),(2459),7,(2589),(589),3,4,2,(89),(56),(569),3,(15689),(15689),7,(35),(35),7,(26),1,8,(269),(69),4,(23589),7,1,(236),(236),4,(389),(389),(56),(2359),(3459),(34),(236),8,1,(349),7,(56),(38),(348),6,9,7,5,(348),2,1";
  }

}

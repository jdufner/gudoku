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
package de.jdufner.sudoku.solver.strategy.hidden;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RetainCandidatesCommand.RetainCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-03-15
 * @version $Revision$
 */
public final class HiddenTripleSerialStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands
        .add(new RetainCandidatesCommandBuilder(StrategyNameEnum.HIDDEN_TRIPLE, 0, 6).addCandidate(2, 5, 7).build());
    commands
        .add(new RetainCandidatesCommandBuilder(StrategyNameEnum.HIDDEN_TRIPLE, 0, 8).addCandidate(2, 5, 7).build());
    commands
        .add(new RetainCandidatesCommandBuilder(StrategyNameEnum.HIDDEN_TRIPLE, 1, 8).addCandidate(2, 5, 7).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    return new HiddenTripleSerialStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "3-7-8,1-2-3-6-8,1-2-7-8,4,3-5,9,1-2-5-6-7-8,1-6-8,2-5-6-7-8,3-7-9,2-3-6-9,2-7-9,1,3-5,8,4,6-9,2-5-6-7-9,4-8-9,1-4-8-9,5,2,6,7,1-8,3,8-9,4-5-8-9,2-4-8-9,6,3,4-9,2-5,2-8,7,1,3-4-5-8-9,1-2-3-4-8-9,1-2-4-8-9,6,7,1-2-5,2-3-8,4-8-9,2-3-4-8-9,3-4-9,7,1-2-4-9,8,4-9,1-2,2-3-6,5,2-3-4-6-9,6,4-8-9,4-7-8-9,5-9,1-8,3,1-5-7-8,2,4-5-7-8,2,5,3,7,1-8,6,9,1-4-8,4-8,1,8-9,7-8-9,5-9,2,4,3-5-6-7-8,6-8,3-5-6-7-8";
  }

}

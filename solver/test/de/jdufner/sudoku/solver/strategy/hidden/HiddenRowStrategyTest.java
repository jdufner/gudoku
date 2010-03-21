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
package de.jdufner.sudoku.solver.strategy.hidden;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RetainCandidatesCommand.RetainCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;

public final class HiddenRowStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    commands.add(new RetainCandidatesCommandBuilder(null, 6, 0).addCandidate(6, 8).build());
    commands.add(new RetainCandidatesCommandBuilder(null, 6, 1).addCandidate(6, 8).build());
    return commands;
  }

  @Override
  protected Strategy getStrategy() {
    final HiddenRowStrategy hiddenRowStrategy = new HiddenRowStrategy(sudoku);
    hiddenRowStrategy.setSize(2);
    return hiddenRowStrategy;
  }

  @Override
  protected String getSudokuAsString() {
    return "5-7,5-7,8,1-3-4-9,1-3-4-9,6,3-4,2,1-3-4-9,2,3,6,1-4-8-9,1-4-7-9,4-7-8-9,4-7,5,1-4-7-9,1,9,4,5,2-3-7,2-7,8,6,3-7,5-6-7-8,5-6-7-8,5-7,2-4-8-9,2-4-5-6-7-9,3,1,4-7-9,4-7-8-9,6-7-8,1,3,4-8-9,4-6-7-9,4-7-8-9,5,4-7-9,2,9,4,2,1-8,1-5-7,5-7-8,6,3-7,3-7-8,4-5-6-7-8,2-5-6-7-8,5-7,2-3-4,2-3-4-5,2-4-5,9,1,3-4-7,3,2-7,1,6,2-4-9,2-4-9,2-4-7,8,5,4-5,2-5,9,7,8,1,2-3-4,3-4,6";
  }

}

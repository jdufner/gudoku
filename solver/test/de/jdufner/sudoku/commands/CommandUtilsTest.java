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
package de.jdufner.sudoku.commands;

import java.util.ArrayList;
import java.util.List;

import de.jdufner.sudoku.commands.RemoveCandidatesCommand.RemoveCandidatesCommandBuilder;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

public final class CommandUtilsTest extends AbstractSolverTestCase {

  public void testIsEqual1() {
    final Command rcc1 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final List<Command> commands1 = new ArrayList<Command>();
    commands1.add(rcc1);
    final Command rcc2 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final List<Command> commands2 = new ArrayList<Command>();
    commands2.add(rcc2);
    assertTrue(CommandUtils.isEqual(commands1, commands2));
  }

  public void testIsEqual2() {
    final Command rcc1 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 3).build();
    final List<Command> commands1 = new ArrayList<Command>();
    commands1.add(rcc1);
    final Command rcc2 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final List<Command> commands2 = new ArrayList<Command>();
    commands2.add(rcc2);
    assertFalse(CommandUtils.isEqual(commands1, commands2));
  }

  public void testIsEqual3() {
    final Command rcc1 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final Command rcc3 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 8).addCandidate(1, 2).build();
    final List<Command> commands1 = new ArrayList<Command>();
    commands1.add(rcc1);
    commands1.add(rcc3);
    final Command rcc2 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 6).addCandidate(1, 2).build();
    final Command rcc4 = new RemoveCandidatesCommandBuilder(StrategyNameEnum.SIMPLE, 7, 8).addCandidate(1, 2).build();
    final List<Command> commands2 = new ArrayList<Command>();
    commands2.add(rcc2);
    commands2.add(rcc4);
    assertTrue(CommandUtils.isEqual(commands1, commands2));
  }
}

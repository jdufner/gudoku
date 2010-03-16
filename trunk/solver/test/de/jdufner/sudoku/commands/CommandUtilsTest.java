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
package de.jdufner.sudoku.commands;

import java.util.ArrayList;
import java.util.List;

import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

public final class CommandUtilsTest extends AbstractSolverTestCase {

  public void testIsEqual1() {
    final Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(Literal.getInstance(1));
    candidates1.add(Literal.getInstance(2));
    final Command rcc1 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 6,
        Literal.EMPTY, SudokuSize.DEFAULT), candidates1);
    final List<Command> commands1 = new ArrayList<Command>();
    commands1.add(rcc1);
    final Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(Literal.getInstance(2));
    candidates2.add(Literal.getInstance(1));
    final Command rcc2 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 6,
        Literal.EMPTY, SudokuSize.DEFAULT), candidates2);
    final List<Command> commands2 = new ArrayList<Command>();
    commands2.add(rcc2);
    assertTrue(CommandUtils.areEquals(commands1, commands2));
  }

  public void testIsEqual2() {
    final Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(Literal.getInstance(1));
    candidates1.add(Literal.getInstance(3));
    final Command rcc1 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 6,
        Literal.EMPTY, SudokuSize.DEFAULT), candidates1);
    final List<Command> commands1 = new ArrayList<Command>();
    commands1.add(rcc1);
    final Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(Literal.getInstance(2));
    candidates2.add(Literal.getInstance(1));
    final Command rcc2 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 6,
        Literal.EMPTY, SudokuSize.DEFAULT), candidates2);
    final List<Command> commands2 = new ArrayList<Command>();
    commands2.add(rcc2);
    assertFalse(CommandUtils.areEquals(commands1, commands2));
  }

  public void testIsEqual3() {
    final Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(Literal.getInstance(1));
    candidates1.add(Literal.getInstance(2));
    final Command rcc1 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 6,
        Literal.EMPTY, SudokuSize.DEFAULT), candidates1);
    final Command rcc3 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 8,
        Literal.EMPTY, SudokuSize.DEFAULT), candidates1);
    final List<Command> commands1 = new ArrayList<Command>();
    commands1.add(rcc1);
    commands1.add(rcc3);
    final Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(Literal.getInstance(2));
    candidates2.add(Literal.getInstance(1));
    final Command rcc2 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 6,
        Literal.EMPTY, SudokuSize.DEFAULT), candidates2);
    final Command rcc4 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 8,
        Literal.EMPTY, SudokuSize.DEFAULT), candidates2);
    final List<Command> commands2 = new ArrayList<Command>();
    commands2.add(rcc2);
    commands2.add(rcc4);
    assertTrue(CommandUtils.areEquals(commands1, commands2));
  }

}

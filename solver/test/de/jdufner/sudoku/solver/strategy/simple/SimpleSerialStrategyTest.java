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
package de.jdufner.sudoku.solver.strategy.simple;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandFactory;
import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.solver.strategy.Strategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-03-08
 * @version $Revision$
 */
public final class SimpleSerialStrategyTest extends AbstractSimpleStrategyTestCase {

  @Override
  protected Strategy getStrategy() {
    return new SimpleSerialStrategy(sudoku);
  }

  @Override
  protected Collection<Command> getCommands() {
    final Collection<Command> commands = new ArrayList<Command>();
    final Candidates<Literal> candidates = new Candidates<Literal>();
    candidates.add(Literal.getInstance(1));
    candidates.add(Literal.getInstance(2));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 6, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 7, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 8, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(8, 6, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates));
    final Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(Literal.getInstance(2));
    candidates2.add(Literal.getInstance(3));
    candidates2.add(Literal.getInstance(4));
    candidates2.add(Literal.getInstance(5));
    candidates2.add(Literal.getInstance(6));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(1, 5, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates2));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(3, 5, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates2));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 5, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates2));
    final Candidates<Literal> candidates3 = new Candidates<Literal>();
    candidates3.add(Literal.getInstance(4));
    candidates3.add(Literal.getInstance(5));
    candidates3.add(Literal.getInstance(8));
    candidates3.add(Literal.getInstance(9));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(6, 3, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates3));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(6, 4, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates3));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 3, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates3));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 5, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates3));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(8, 4, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates3));
    final Candidates<Literal> candidates4 = new Candidates<Literal>();
    candidates4.add(Literal.getInstance(1));
    candidates4.add(Literal.getInstance(4));
    candidates4.add(Literal.getInstance(7));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(6, 3, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates4));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(6, 4, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates4));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(6, 6, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates4));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(6, 7, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates4));
    final Candidates<Literal> candidates5 = new Candidates<Literal>();
    candidates5.add(Literal.getInstance(1));
    candidates5.add(Literal.getInstance(3));
    candidates5.add(Literal.getInstance(4));
    candidates5.add(Literal.getInstance(7));
    candidates5.add(Literal.getInstance(8));
    candidates5.add(Literal.getInstance(9));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(2, 8, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates5));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 8, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates5));
    final Candidates<Literal> candidates6 = new Candidates<Literal>();
    candidates6.add(Literal.getInstance(1));
    candidates6.add(Literal.getInstance(2));
    candidates6.add(Literal.getInstance(5));
    candidates6.add(Literal.getInstance(6));
    candidates6.add(Literal.getInstance(9));
    commands.add(CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(8, 6, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates6));
    return commands;
  }

}

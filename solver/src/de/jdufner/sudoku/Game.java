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
package de.jdufner.sudoku;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandManager;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.file.PropertiesLoader;
import de.jdufner.sudoku.solver.service.ExtendedSolver;

/**
 * Das Spiel ({@link Game}) ist die zentrale Schnittstelle zur Steuerung des Sudokus.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Game {

  final private int id;
  final private Sudoku quest;
  final private Sudoku solution;
  final private CommandManager commandManager;

  public Game(final int id) {
    this.id = id;

    // quest = SudokuFactory.buildSudoku(Examples.ING_DIBA);

    final PropertiesLoader propertiesLoader = new PropertiesLoader();
    final String sudokuAsString = propertiesLoader.getSudokuAsString(id);
    quest = SudokuFactory.buildSudoku(sudokuAsString);
    ExtendedSolver extendedSolver = (ExtendedSolver) SolverServiceFactory.getInstance().getBean(
        SolverServiceFactory.STRATEGY_SOLVER_WITH_BACKTRACKING);
    solution = extendedSolver.solve(quest);

    commandManager = new CommandManager();
  }

  public int getId() {
    return id;
  }

  public Sudoku getQuest() {
    return quest;
  }

  public Sudoku getSolution() {
    return solution;
  }

  public Cell doCommand(final Command command) {
    commandManager.doCommand(quest, command);
    return quest.getCell(command.getRowIndex(), command.getColumnIndex());
  }

  public Cell undo() {
    final Command command = commandManager.undoCommand(quest);
    return quest.getCell(command.getRowIndex(), command.getColumnIndex());
  }

  public Cell redo() {
    final Command command = commandManager.redoCommand(quest);
    return quest.getCell(command.getRowIndex(), command.getColumnIndex());
  }

  public boolean isUndoPossible() {
    return commandManager.isUndoPossible();
  }

  public boolean isRedoPossible() {
    return commandManager.isRedoPossible();
  }

  public boolean isCorrect(final int rowIndex, final int columnIndex) {
    final Cell questCell = quest.getCell(rowIndex, columnIndex);
    final Cell solutionCell = solution.getCell(rowIndex, columnIndex);
    return questCell.equals(solutionCell);
  }

}

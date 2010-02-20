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
package de.jdufner.sudoku.solver.strategy.backtracking;

import java.util.Collection;
import java.util.List;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandFactory;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.backtracking.Backtracking;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class BacktrackingStrategy extends AbstractStrategy implements CellHandler {

  private transient Sudoku result = null;
  private transient boolean sudokuUnique = true;

  public BacktrackingStrategy(final Sudoku sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.SEHR_SCHWER;
  }

  @Override
  public boolean isSudokuUnique() {
    return sudokuUnique;
  }

  @Override
  public Collection<Command> executeStrategy() {
    final Backtracking backtracking = new Backtracking(getSudoku(), 1);
    final List<Sudoku> results = backtracking.firstSolutions(2);
    result = results.get(0);
    if (results.size() > 1) {
      sudokuUnique = false;
    }
    HandlerUtil.forEachCell(getSudoku(), this);
    return getCommands();
  }

  @Override
  public void initialize() {
    // nothing to do
  }

  @Override
  public void handleCell(final Cell cell) {
    if (!cell.isFixed()) {
      getCommands().add(
          CommandFactory.buildSetValueCommand(StrategyNameEnum.BACKTRACKING, cell, result.getCell(cell.getNumber())
              .getValue()));
    }
  }

}

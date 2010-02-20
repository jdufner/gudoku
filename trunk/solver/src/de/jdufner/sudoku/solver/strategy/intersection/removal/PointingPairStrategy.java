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
package de.jdufner.sudoku.solver.strategy.intersection.removal;

import java.util.Collection;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandFactory;
import de.jdufner.sudoku.common.board.Block;
import de.jdufner.sudoku.common.board.BlockHandler;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Column;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Row;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class PointingPairStrategy extends AbstractStrategy implements BlockHandler, Callable<Collection<Command>> {

  private static final Logger LOG = Logger.getLogger(PointingPairStrategy.class);

  public PointingPairStrategy(final Sudoku sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.MITTEL;
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachBlock(getSudoku(), this);
    return getCommands();
  }

  public void handleBlock(final Block block) {
    final Literal2CellMap literal2CellMapBlock = new Literal2CellMap(block.getCells());
    for (Literal testCandidate : block.getCandidates()) {
      checkColumn(literal2CellMapBlock, testCandidate);
      checkRow(literal2CellMapBlock, testCandidate);
    }
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

  private void checkColumn(final Literal2CellMap literal2CellMapBlock, final Literal testCandidate) {
    if (literal2CellMapBlock.getCellsContainingLiteral(testCandidate).size() > 1
        && literal2CellMapBlock.getCellsContainingLiteral(testCandidate).size() < getSudoku().getSize()
            .getBlockHeight()) {
      if (areCellsInSameColumn(literal2CellMapBlock.getCellsContainingLiteral(testCandidate))) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Found candidate "
              + testCandidate
              + " in column "
              + getSudoku().getColumn(
                  literal2CellMapBlock.getCellsContainingLiteral(testCandidate).first().getColumnIndex())
              + " in block "
              + getSudoku().getBlock(
                  literal2CellMapBlock.getCellsContainingLiteral(testCandidate).first().getBlockIndex()) + " only");
        }
        removeCandidateInColumnExceptInBlock(testCandidate, getSudoku().getColumn(
            literal2CellMapBlock.getCellsContainingLiteral(testCandidate).first().getColumnIndex()), getSudoku()
            .getBlock(literal2CellMapBlock.getCellsContainingLiteral(testCandidate).first().getBlockIndex()));
      }
    }
  }

  private boolean areCellsInSameColumn(final Collection<Cell> cells) {
    Column column = null;
    for (Cell cell : cells) {
      if (column == null) {
        column = getSudoku().getColumn(cell.getColumnIndex());
      }
      if (!column.equals(getSudoku().getColumn(cell.getColumnIndex()))) {
        return false;
      }
    }
    return true;
  }

  private void removeCandidateInColumnExceptInBlock(final Literal testCandidate, final Column column, final Block block) {
    for (Cell cell : column.getNonFixed()) {
      if (!getSudoku().getBlock(cell.getBlockIndex()).equals(block)) {
        getCommands().add(
            CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.INTERSECTION_REMOVAL, cell, testCandidate));
      }
    }
  }

  private void checkRow(final Literal2CellMap literal2CellMapBlock, final Literal testCandidate) {
    if (literal2CellMapBlock.getCellsContainingLiteral(testCandidate).size() > 1
        && literal2CellMapBlock.getCellsContainingLiteral(testCandidate).size() < getSudoku().getSize().getBlockWidth()) {
      if (areCellsInSameRow(literal2CellMapBlock.getCellsContainingLiteral(testCandidate))) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Found candidate "
              + testCandidate
              + " in row "
              + getSudoku().getRow(literal2CellMapBlock.getCellsContainingLiteral(testCandidate).first().getRowIndex())
              + " in block "
              + getSudoku().getBlock(
                  literal2CellMapBlock.getCellsContainingLiteral(testCandidate).first().getBlockIndex()) + " only");
        }
        removeCandidateInRowExceptInBlock(testCandidate, getSudoku().getRow(
            literal2CellMapBlock.getCellsContainingLiteral(testCandidate).first().getRowIndex()), getSudoku().getBlock(
            literal2CellMapBlock.getCellsContainingLiteral(testCandidate).first().getBlockIndex()));
      }
    }
  }

  private boolean areCellsInSameRow(final Collection<Cell> cells) {
    Row row = null;
    for (Cell cell : cells) {
      if (row == null) {
        row = getSudoku().getRow(cell.getRowIndex());
      }
      if (!row.equals(getSudoku().getRow(cell.getRowIndex()))) {
        return false;
      }
    }
    return true;
  }

  private void removeCandidateInRowExceptInBlock(final Literal testCandidate, final Row row, final Block block) {
    for (Cell cell : row.getNonFixed()) {
      if (!getSudoku().getBlock(cell.getBlockIndex()).equals(block)) {
        getCommands().add(
            CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.INTERSECTION_REMOVAL, cell, testCandidate));
      }
    }
  }

}

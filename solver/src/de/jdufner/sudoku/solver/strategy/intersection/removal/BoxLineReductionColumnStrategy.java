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
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Column;
import de.jdufner.sudoku.common.board.ColumnHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class BoxLineReductionColumnStrategy extends AbstractBoxLineReductionStrategy implements ColumnHandler,
    Callable<Collection<Command>> {
  private static final Logger LOG = Logger.getLogger(BoxLineReductionColumnStrategy.class);

  public BoxLineReductionColumnStrategy(final Sudoku sudoku) {
    super(sudoku);
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachColumn(getSudoku(), this);
    return getCommands();
  }

  public void handleColumn(final Column column) {
    final Literal2CellMap literal2CellMapColumn = new Literal2CellMap(column.getCells());
    for (Literal testCandidate : column.getCandidates()) {
      if (literal2CellMapColumn.getCellsContainingLiteral(testCandidate).size() > 1
          && literal2CellMapColumn.getCellsContainingLiteral(testCandidate).size() <= getSudoku().getSize()
              .getBlockHeight()) {
        if (areCellsInSameBlock(literal2CellMapColumn.getCellsContainingLiteral(testCandidate))) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Found candidate "
                + testCandidate
                + " in column "
                + getSudoku().getColumn(
                    literal2CellMapColumn.getCellsContainingLiteral(testCandidate).first().getColumnIndex())
                + " in block "
                + getSudoku().getBlock(
                    literal2CellMapColumn.getCellsContainingLiteral(testCandidate).first().getBlockIndex()) + " only");
          }
          removeCandidateInBlockExceptInColumn(testCandidate, getSudoku().getBlock(
              literal2CellMapColumn.getCellsContainingLiteral(testCandidate).first().getBlockIndex()), column);
        }
      }
    }
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

  // TODO Funktioniert diese Methode richtig?
  //  private boolean areCellsInSameBlock(final Collection<Cell> cells) {
  //    Block block = null;
  //    for (Cell cell : cells) {
  //      if (block == null) {
  //        block = getSudoku().getBlock(cell.getBlockIndex());
  //      }
  //      if (!block.equals(getSudoku().getBlock(cell.getBlockIndex()))) {
  //        return false;
  //      }
  //    }
  //    return true;
  //  }

  private void removeCandidateInBlockExceptInColumn(final Literal testCandidate, final Block block, final Column column) {
    for (Cell cell : block.getNonFixed()) {
      if (!getSudoku().getColumn(cell.getColumnIndex()).equals(column)) {
        getCommands().add(
            CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.INTERSECTION_REMOVAL, cell, testCandidate));
      }
    }
  }

}

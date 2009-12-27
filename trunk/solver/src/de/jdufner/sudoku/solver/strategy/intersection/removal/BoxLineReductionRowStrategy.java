// $Id: BoxLineReductionRowStrategy.java,v 1.16 2009/12/17 22:17:37 jdufner Exp $

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
package de.jdufner.sudoku.solver.strategy.intersection.removal;

import java.util.Collection;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandFactory;
import de.jdufner.sudoku.common.board.Block;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Row;
import de.jdufner.sudoku.common.board.RowHandler;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.16 $
 */
public final class BoxLineReductionRowStrategy extends AbstractStrategy implements RowHandler,
    Callable<Collection<Command>> {
  private static final Logger LOG = Logger.getLogger(BoxLineReductionRowStrategy.class);

  public BoxLineReductionRowStrategy(final Sudoku sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.SCHWER;
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachRow(getSudoku(), this);
    return getCommands();
  }

  public void handleRow(final Row row) {
    final Literal2CellMap literal2CellMapRow = new Literal2CellMap(row.getCells());
    for (Literal testCandidate : row.getCandidates()) {
      if (literal2CellMapRow.getCellsContainingLiteral(testCandidate).size() > 1
          && literal2CellMapRow.getCellsContainingLiteral(testCandidate).size() <= getSudoku().getSize()
              .getBlockWidth()) {
        if (areCellsInSameBlock(literal2CellMapRow.getCellsContainingLiteral(testCandidate))) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Found candidate "
                + testCandidate
                + " in row "
                + getSudoku().getColumn(
                    literal2CellMapRow.getCellsContainingLiteral(testCandidate).first().getColumnIndex())
                + " in block "
                + getSudoku().getBlock(
                    literal2CellMapRow.getCellsContainingLiteral(testCandidate).first().getBlockIndex()) + " only");
          }
          removeCandidateInBlockExceptInRow(testCandidate, getSudoku().getBlock(
              literal2CellMapRow.getCellsContainingLiteral(testCandidate).first().getBlockIndex()), row);
        }
      }
    }
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

  // TODO Funktioniert diese Methode richtig?
  private boolean areCellsInSameBlock(final Collection<Cell> cells) {
    Block block = null;
    for (Cell cell : cells) {
      if (block == null) {
        block = getSudoku().getBlock(cell.getBlockIndex());
      }
      if (!block.equals(getSudoku().getBlock(cell.getBlockIndex()))) {
        return false;
      }
    }
    return true;
  }

  private void removeCandidateInBlockExceptInRow(final Literal testCandidate, final Block block, final Row row) {
    for (Cell cell : block.getNonFixed()) {
      if (!getSudoku().getRow(cell.getRowIndex()).equals(row)) {
        getCommands().add(
            CommandFactory.buildRemoveCandidatesCommand(this.getClass().getSimpleName(), cell, testCandidate));
      }
    }
  }

}

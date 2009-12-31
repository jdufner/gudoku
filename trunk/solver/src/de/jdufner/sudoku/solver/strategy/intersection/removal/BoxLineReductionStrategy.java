// $Id$

/*
 *import java.util.Collection;

 import org.apache.log4j.Logger;

 import de.jdufner.sudoku.common.Block;
 import de.jdufner.sudoku.common.Cell;
 import de.jdufner.sudoku.common.Column;
 import de.jdufner.sudoku.common.ColumnHandler;
 import de.jdufner.sudoku.common.Command;
 import de.jdufner.sudoku.common.HandlerUtil;
 import de.jdufner.sudoku.common.Level;
 import de.jdufner.sudoku.common.Literal;
 import de.jdufner.sudoku.common.Row;
 import de.jdufner.sudoku.common.RowHandler;
 import de.jdufner.sudoku.common.Sudoku;
 import de.jdufner.sudoku.common.commands.RemoveCandidatesCommand;
 import de.jdufner.sudoku.solver.strategy.Literal2CellMap;
 import de.jdufner.sudoku.solver.strategy.Strategy;
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
import de.jdufner.sudoku.common.board.Row;
import de.jdufner.sudoku.common.board.RowHandler;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class BoxLineReductionStrategy extends AbstractStrategy implements ColumnHandler, RowHandler {

  private static final Logger LOG = Logger.getLogger(BoxLineReductionStrategy.class);

  public BoxLineReductionStrategy(final Sudoku sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.SCHWER;
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachColumn(getSudoku(), this);
    HandlerUtil.forEachRow(getSudoku(), this);
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

  private void removeCandidateInBlockExceptInColumn(final Literal testCandidate, final Block block, final Column column) {
    for (Cell cell : block.getNonFixed()) {
      if (!getSudoku().getColumn(cell.getColumnIndex()).equals(column)) {
        getCommands().add(
            CommandFactory.buildRemoveCandidatesCommand(this.getClass().getSimpleName(), cell, testCandidate));
      }
    }
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

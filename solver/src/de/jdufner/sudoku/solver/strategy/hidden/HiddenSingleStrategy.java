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
import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandFactory;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.Unit;
import de.jdufner.sudoku.common.board.UnitHandler;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Wird diese Klasse �berhaupt ben�tigt? Dieser Fall tritt ein, wenn nur ein Kandidat, der in keiner anderen Zelle in
 * derselben Unit vorkommt, existiert.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class HiddenSingleStrategy extends AbstractStrategy implements UnitHandler {
  private static final Logger LOG = Logger.getLogger(HiddenSingleStrategy.class);

  public HiddenSingleStrategy(final Sudoku sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.LEICHT;
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachUnit(getSudoku(), this);
    return getCommands();
  }

  public void handleUnit(final Unit unit) {
    final Literal2CellMap literal2CellMap = new Literal2CellMap(unit.getCells());
    for (Literal single : literal2CellMap.getCandidatesByNumber(1)) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Found unique candidate " + single + " in Unit " + unit);
      }
      for (Cell cell : literal2CellMap.getCellsContainingLiteral(single)) {
        // TODO evtl andere Implementierungsvariante ausprobieren
        final List<Literal> fixed = new ArrayList<Literal>();
        fixed.addAll(getSudoku().getBlock(cell.getBlockIndex()).getFixedAsLiteral());
        fixed.addAll(getSudoku().getColumn(cell.getColumnIndex()).getFixedAsLiteral());
        fixed.addAll(getSudoku().getRow(cell.getRowIndex()).getFixedAsLiteral());
        if (!fixed.contains(single)) {
          getCommands().add(CommandFactory.buildSetValueCommand(StrategyNameEnum.HIDDEN_SINGLE, cell, single));
        }
      }
    }
  }

}

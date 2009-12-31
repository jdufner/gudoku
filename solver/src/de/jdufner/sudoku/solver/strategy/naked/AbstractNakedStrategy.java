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
package de.jdufner.sudoku.solver.strategy.naked;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.RemoveCandidatesCommand;
import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.Unit;
import de.jdufner.sudoku.common.board.UnitHandler;
import de.jdufner.sudoku.common.collections.SortedCandidates;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since
 * @version $Revision$
 */
public abstract class AbstractNakedStrategy extends AbstractStrategy implements NakedUnit, UnitHandler {

  private static final Logger LOG = Logger.getLogger(AbstractNakedStrategy.class);

  private int size;

  /**
   * In dieser Zuordnung werden pro Einheit die Kandidaten gespeichert, die bereits in der Strategie geprüft wurden.
   */
  private final transient Map<Unit, List<SortedCandidates<Literal>>> allreadyHandledCandidatesPerUnit = new HashMap<Unit, List<SortedCandidates<Literal>>>();

  protected AbstractNakedStrategy(final Sudoku sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.LEICHT;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public void setSize(final int size) {
    this.size = size;
  }

  @Override
  public void handleUnit(final Unit unit) {
    final SortedSet<Cell> nonFixed = unit.getNonFixed();
    if (nonFixed.size() == getSize()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug(getClass().getSimpleName() + " skipped for unit " + unit);
      }
      return;
    }
    for (Cell cell : nonFixed) {
      if (areCandidatesAllreadyHandled(unit, cell.getCandidates())) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Candidates " + cell.getCandidates() + " are allready handled in Unit " + unit);
        }
        continue;
      }
      if (cell.getCandidates().size() == getSize()) {
        putCandidatesIntoMap(unit, cell.getCandidates().getSorted());
        final Candidates<Cell> cellsWithSameCandidates = findCellsWithSameCandidatesInUnit(cell, unit);
        if (cellsWithSameCandidates.size() == getSize()) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Found " + getSize() + " cells " + cellsWithSameCandidates + " in " + unit);
          }
          removeCandidatesInOtherCells(cell.getCandidates(), cellsWithSameCandidates, unit);
        }
      }
    }
  }

  /**
   * Legt die Kandidaten pro Unit in der Zuordnung ab.
   * 
   * @param unit
   * @param candidates
   */
  protected void putCandidatesIntoMap(final Unit unit, final SortedCandidates<Literal> candidates) {
    if (allreadyHandledCandidatesPerUnit.get(unit) == null) {
      final List<SortedCandidates<Literal>> setOfCandidates = new ArrayList<SortedCandidates<Literal>>();
      allreadyHandledCandidatesPerUnit.put(unit, setOfCandidates);
    }
    if (!allreadyHandledCandidatesPerUnit.get(unit).contains(candidates)) {
      allreadyHandledCandidatesPerUnit.get(unit).add(candidates);
    }
  }

  /**
   * @param unit
   * @param candidates
   * @return <code>true</code>, wenn für die angegebene Einheit bereits die Kandidaten vorhanden sind, sonst
   *         <code>false</code>
   */
  protected boolean areCandidatesAllreadyHandled(final Unit unit, final Collection<Literal> candidates) {
    if (allreadyHandledCandidatesPerUnit.get(unit) == null) {
      return false;
    }
    if (!allreadyHandledCandidatesPerUnit.get(unit).contains(candidates)) {
      return false;
    }
    return true;
  }

  /**
   * The result list return the given cell too.
   * 
   * @param cell
   * @param unit
   * @return
   */
  protected final Candidates<Cell> findCellsWithSameCandidatesInUnit(final Cell cell, final Unit unit) {
    final Candidates<Cell> cells = new Candidates<Cell>();
    for (Cell cell2 : unit.getNonFixed()) {
      if (cell2.getCandidates().containsAtMost(cell.getCandidates())) {
        cells.add(cell2);
      }
    }
    return cells;
  }

  /**
   * @param candidates
   * @param excludedCells
   * @param unit
   */
  protected final void removeCandidatesInOtherCells(final Candidates<Literal> candidates,
      final List<Cell> excludedCells, final Unit unit) {
    for (Cell cell : unit.getNonFixed()) {
      if (!excludedCells.contains(cell)) {
        // cell.removeCandidatesAndSetIfOnlyOneRemains(candidates);
        // sudoku.addCommand(new RemoveCandidatesCommand(cell, candidates));
        getCommands().add(new RemoveCandidatesCommand(this.getClass().getSimpleName(), cell, candidates));
      }
    }
  }

}

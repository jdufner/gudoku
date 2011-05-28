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
package de.jdufner.sudoku.solver.strategy.hidden;

import java.util.Collection;
import java.util.SortedSet;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.RetainCandidatesCommand.RetainCandidatesCommandBuilder;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Grid;
import de.jdufner.sudoku.common.board.House;
import de.jdufner.sudoku.common.board.UnitHandler;
import de.jdufner.sudoku.common.collections.Kombination;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Abstrakte Superklasse der <a href="http://www.sudopedia.org/wiki/Hidden_Subset">Hidden Subsets</a>. Hier ist die
 * Logik der Hidden Subsets implementiert.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractHiddenStrategy extends AbstractStrategy implements HiddenUnit, UnitHandler,
    Callable<Collection<Command>> {

  private static final Logger LOG = Logger.getLogger(AbstractHiddenStrategy.class);

  private transient int size;
  private transient StrategyNameEnum strategyNameEnum;

  protected AbstractHiddenStrategy(final Grid sudoku) {
    super(sudoku);
  }

  @Override
  public Level getLevel() {
    return Level.MITTEL;
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
  public StrategyNameEnum getStrategyName() {
    return strategyNameEnum;
  }

  @Override
  public void setStrategyName(final StrategyNameEnum strategyNameEnum) {
    this.strategyNameEnum = strategyNameEnum;
  }

  /**
   * @see Callable#call()
   */
  public Collection<Command> call() {
    return executeStrategy();
  }

  /**
   * Ermittle alle Kandidaten, die genau so häufig, wie die angegebene Anzahl {@link #getSize()} vorkommen. Diese
   * Kandidaten müssen in gleicher Anzahl Zellen auftreten. In Zellen, die mindestens alle Kandidaten gleichzeitig
   * enthalten, können alle weiteren Kandidaten gelöscht werden. Mit anderen Worten, nur die Kandidaten müssen in den
   * Zellen verbleiben.
   */
  public void handleUnit(final House unit) {
    assert getSize() >= 2 && getSize() <= 4 : "Größe des Subsets muss zwischen 2 und 4 liegen.";
    if (LOG.isDebugEnabled()) {
      LOG.debug("Handle Unit " + unit);
    }
    final Literal2CellMap literal2CellMap = new Literal2CellMap(unit.getCells());
    final SortedSet<Literal> literals = literal2CellMap.getCandidatesByNumber(getSize());
    if (literals.size() < getSize()) {
      return;
    }
    final Kombination<Literal> kombinationBuilder = new Kombination<Literal>(literals, getSize());
    Collection<Literal> kombination;
    while (kombinationBuilder.hasNextKombination()) {
      kombinationBuilder.buildNextKombination();
      kombination = kombinationBuilder.getKombination();
      if (LOG.isDebugEnabled()) {
        LOG.debug(kombination);
      }
      final SortedSet<Cell> cells = unit.getCellsThooseCandidatesContains(kombination);
      buildRetainCandidatesCommand(cells, kombination);
    }
  }

  private void buildRetainCandidatesCommand(final SortedSet<Cell> cells, final Collection<Literal> kombination) {
    if (cells.size() == getSize()) {
      for (Cell cell : cells) {
        if (cell.getCandidates().size() > getSize()) {
          if (LOG.isDebugEnabled()) { // NOPMD by Jürgen on 08.11.09 00:23
            LOG.debug("Remove other candidates than " + kombination + " in cell " + cell);
          }
          final Command cmd = new RetainCandidatesCommandBuilder(strategyNameEnum, cell).addCandidate(kombination)
              .build();
          getCommands().add(cmd);
        }
      }
    }
  }

}

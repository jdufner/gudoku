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

import java.util.Collection;
import java.util.SortedSet;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.commands.CommandFactory;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Literal2CellMap;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.Unit;
import de.jdufner.sudoku.common.board.UnitHandler;
import de.jdufner.sudoku.common.collections.Kombination;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Abstrakte Superklasse der <a href="http://www.sudopedia.org/wiki/Hidden_Subset">Hidden Subsets</a>. Hier ist die
 * Logik der Hidden Subsets implementiert.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractHiddenStrategy extends AbstractStrategy implements HiddenUnit, UnitHandler,
    Callable<Collection<Command>> {

  private static final Logger LOG = Logger.getLogger(AbstractHiddenStrategy.class);

  private int size;

  protected AbstractHiddenStrategy(final Sudoku sudoku) {
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

  /**
   * @see Callable#call()
   */
  public Collection<Command> call() {
    return executeStrategy();
  }

  /**
   * Ermittle alle Kandidaten, die genau so h�ufig, wie die angegebene Anzahl {@link #getSize()} vorkommen. Diese
   * Kandidaten m�ssen in gleicher Anzahl Zellen auftreten. In Zellen, die mindestens alle Kandidaten gleichzeitig
   * enthalten, k�nnen alle weiteren Kandidaten gel�scht werden. Mit anderen Worten, nur die Kandidaten m�ssen in den
   * Zellen verbleiben.
   */
  public void handleUnit(final Unit unit) {
    assert getSize() >= 2 && getSize() <= 4 : "Gr��e des Subsets muss zwischen 2 und 4 liegen.";
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
          if (LOG.isDebugEnabled()) { // NOPMD by J�rgen on 08.11.09 00:23
            LOG.debug("Remove other candidates than " + kombination + " in cell " + cell);
          }
          final Command cmd = CommandFactory.buildRetainCandidatesCommand(StrategyNameEnum.HIDDEN_PAIR, cell,
              kombination); // NOPMD by J�rgen on 08.11.09 22:39
          getCommands().add(cmd);
        }
      }
    }
  }

}

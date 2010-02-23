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
package de.jdufner.sudoku.solver.strategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.exceptions.SudokuRuntimeException;
import de.jdufner.sudoku.common.misc.Log;
import de.jdufner.sudoku.solver.strategy.backtracking.BacktrackingStrategy;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyConfiguration;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyThreadingEnum;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenPairParallelStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenPairSerialStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenQuadParallelStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenQuadSerialStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenSingleStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenTripleParallelStrategy;
import de.jdufner.sudoku.solver.strategy.hidden.HiddenTripleSerialStrategy;
import de.jdufner.sudoku.solver.strategy.intersection.removal.IntersectionRemovalParallelStrategy;
import de.jdufner.sudoku.solver.strategy.intersection.removal.IntersectionRemovalSerialStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedPairParallelStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedPairSerialStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedQuadParallelStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedQuadSerialStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedTripleParallelStrategy;
import de.jdufner.sudoku.solver.strategy.naked.NakedTripleSerialStrategy;
import de.jdufner.sudoku.solver.strategy.simple.SimpleParallelStrategy;
import de.jdufner.sudoku.solver.strategy.swordfish.SwordFishParallelStrategy;
import de.jdufner.sudoku.solver.strategy.xwing.XWingParallelStrategy;
import de.jdufner.sudoku.solver.strategy.xwing.XWingSerialStrategy;
import de.jdufner.sudoku.solver.strategy.ywing.YWingSerialStrategy;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class StrategyExecutor {

  private static final Logger LOG = Logger.getLogger(StrategyExecutor.class);

  final private transient Sudoku sudoku;
  final private transient StrategyConfiguration configuration;

  final private transient List<StrategyResult> result = new ArrayList<StrategyResult>();

  public StrategyExecutor(final Sudoku sudoku, final StrategyConfiguration configuration) {
    this.sudoku = sudoku;
    this.configuration = configuration;
    // Die folgende Pr�fung ist so wichtig, dass ein assert nicht ausreicht.
    if (!sudoku.isValid()) {
      throw new IllegalStateException("Das �bergebene Sudoku ist in keinem g�ltigen Zustand!");
    }
  }

  public List<StrategyResult> execute() {
    int zaehler = 0;
    boolean doExecute = true;
    while (doExecute) {
      zaehler += 1;
      Log.log("Starte Iteration " + zaehler);
      LOG.info("Starte Iteration " + zaehler);
      Log.log("Sudoku " + getSudoku() //
          + " (" + getSudoku().getNumberOfFixed() + " Zellen gesetzt, " //
          + getSudoku().getNumberOfCandidates() + " Kandidaten )");
      LOG.info("Sudoku " + getSudoku() //
          + " (" + getSudoku().getNumberOfFixed() + " Zellen gesetzt, " //
          + getSudoku().getNumberOfCandidates() + " Kandidaten )");
      //LOG.info("Sudoku vor Iteration " + zaehler + ": " + getSudoku());
      final StrategyResult strategyResult = executeStrategy();
      if (strategyResult == null) {
        Log.log("Keinen weiteren L�sungschrift gefunden. Sudoku nicht gel�st!");
        LOG.info("Keinen weiteren L�sungschrift gefunden. Sudoku nicht gel�st!");
        doExecute = false;
      } else {
        result.add(strategyResult);
      }
      if (getSudoku().isSolved()) {
        if (getSudoku().isValid() && getSudoku().isSolvedByCheckSum()) {
          Log.log("Sudoku gel�st! " + getSudoku());
          LOG.debug("Sudoku gel�st! " + getSudoku());
          doExecute = false;
        } else {
          Log.log("Sudoku scheint gel�st zu sein, aber irgendwas ist schief gelaufen.");
          LOG.warn("Sudoku scheint gel�st zu sein, aber irgendwas ist schief gelaufen.");
        }
      } else {
        Log.log("Sudoku noch nicht gel�st, weitermachen.");
        LOG.debug("Sudoku noch nicht gel�st, weitermachen.");
      }
    }
    return result;
  }

  private StrategyResult executeStrategy() {
    for (Class<? extends AbstractStrategy> strategyClass : getStrategies()) {
      final Strategy strategy = instantiateStrategy(strategyClass, getSudoku());
      final StrategyResult strategyResult = strategy.execute();
      if (LOG.isDebugEnabled()) {
        LOG.debug("Sudoku " + getSudoku());
        if (getSudoku().isValid()) {
          LOG.debug("Sudoku ist g�ltig.");
        } else {
          LOG.debug("Sudoku ist nicht g�ltig!");
        }
        if (getSudoku().isSolvedByCheckSum()) {
          LOG.debug("Sudoku ist gel�st und durch Pr�fsummen best�tigt.");
        } else {
          LOG.debug("Sudoku ist nicht gel�st.");
        }
      }
      if (strategyResult.getCommands().size() > 0) {
        strategyResult.storeStateBefore(getSudoku());
        executeCommands(strategyResult.getCommands());
        strategyResult.storeStateAfter(getSudoku());
        if (strategyResult.getNumberEleminatedCandidates() > 0) {
          Log.log(strategy + " entfernte " + strategyResult.getNumberEleminatedCandidates() + " Kandidaten und setzte "
              + strategyResult.getNumberNewlyFixedCells() + " Zellen.");
          if (LOG.isInfoEnabled()) {
            LOG.info(strategy + " entfernte " + strategyResult.getNumberEleminatedCandidates()
                + " Kandidaten und setzte " + strategyResult.getNumberNewlyFixedCells() + " Zellen.");
          }
          return strategyResult;
        } else {
          Log.log(strategy + " hat keinen Kandidat entfernt und keine Zelle gesetzt");
          if (LOG.isInfoEnabled()) {
            LOG.info(strategy + " hat keinen Kandidat entfernt und keine Zelle gesetzt");
          }
        }
      }
    }
    LOG.debug("Kein Kandidat entfernt!");
    return null;
  }

  /**
   * TODO Performancetest: Ist es sinnvoll den alten Befehl oder den umfassenderen Befehl zu suchen oder ist es billiger
   * das Kommando auszuf�hren, auch wenn es nutzlos ist.
   * 
   * @param commands
   */
  private void executeCommands(final Collection<Command> commands) {
    for (Command command : commands) {
      command.execute(getSudoku());
    }
  }

  /**
   * Erzeugt eine Instanz einer Strategie ohne eine deklarierte Exception zu werfen, sondern nur eine nicht-deklarierte
   * Exception.
   * 
   * @param clazz
   * @param sudoku
   * @return
   */
  private Strategy instantiateStrategy(final Class<? extends AbstractStrategy> clazz, final Sudoku sudoku) {
    try {
      return instantiateStrategyThrowingExceptions(clazz, sudoku);
    } catch (Exception e) {
      throw new SudokuRuntimeException(e);
    }
  }

  /**
   * Erzeugt eine Instanz einer Strategie.
   * 
   * @param strategyClass
   * @param sudoku
   * @return
   * @throws SecurityException
   * @throws NoSuchMethodException
   * @throws IllegalArgumentException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  private AbstractStrategy instantiateStrategyThrowingExceptions(final Class<? extends AbstractStrategy> strategyClass,
      final Sudoku sudoku) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
      InstantiationException, IllegalAccessException, InvocationTargetException {
    final Constructor<? extends AbstractStrategy> strategyConstructor = strategyClass
        .getConstructor(new Class[] { Sudoku.class });
    final AbstractStrategy strategy = strategyConstructor.newInstance(new Object[] { sudoku });
    if (LOG.isDebugEnabled()) {
      LOG.debug("Strategie " + strategy + " erfolgreicht instanziiert.");
    }
    return strategy;
  }

  public StrategyConfiguration getConfiguration() {
    return configuration;
  }

  public Sudoku getSudoku() {
    return sudoku;
  }

  private List<Class<? extends AbstractStrategy>> getStrategies() { // NOPMD by J�rgen on 24.11.09 22:10
    final List<Class<? extends AbstractStrategy>> strategies = new ArrayList<Class<? extends AbstractStrategy>>();
    if (getConfiguration().getThreading().equals(StrategyThreadingEnum.SERIAL)) {
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.SIMPLE)) {
        strategies.add(SimpleParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_SINGLE)) {
        strategies.add(HiddenSingleStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_PAIR)) {
        strategies.add(NakedPairSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_TRIPLE)) {
        strategies.add(NakedTripleSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_QUAD)) {
        strategies.add(NakedQuadSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_PAIR)) {
        strategies.add(HiddenPairSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_TRIPLE)) {
        strategies.add(HiddenTripleSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_QUAD)) {
        strategies.add(HiddenQuadSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.INTERSECTION_REMOVAL)) {
        strategies.add(IntersectionRemovalSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.YWING)) {
        strategies.add(YWingSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.XWING)) {
        strategies.add(XWingSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.SWORDFISH)) {
        strategies.add(SwordFishParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.BACKTRACKING)) {
        strategies.add(BacktrackingStrategy.class);
      }
    } else {
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.SIMPLE)) {
        strategies.add(SimpleParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_SINGLE)) {
        strategies.add(HiddenSingleStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_PAIR)) {
        strategies.add(NakedPairParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_TRIPLE)) {
        strategies.add(NakedTripleParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.NAKED_QUAD)) {
        strategies.add(NakedQuadParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_PAIR)) {
        strategies.add(HiddenPairParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_TRIPLE)) {
        strategies.add(HiddenTripleParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.HIDDEN_QUAD)) {
        strategies.add(HiddenQuadParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.INTERSECTION_REMOVAL)) {
        strategies.add(IntersectionRemovalParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.YWING)) {
        strategies.add(YWingSerialStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.XWING)) {
        strategies.add(XWingParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.SWORDFISH)) {
        strategies.add(SwordFishParallelStrategy.class);
      }
      if (getConfiguration().getStrategies().contains(StrategyNameEnum.BACKTRACKING)) {
        strategies.add(BacktrackingStrategy.class);
      }
    }
    return strategies;
  }

}

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
package de.jdufner.sudoku.commands;

import java.util.Collection;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since
 * @version $Revision$
 */
public final class CommandFactory {

  private final static Logger LOG = Logger.getLogger(CommandFactory.class);

  private CommandFactory() {
  }

  public static Command buildRemoveCandidatesCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Literal candidateToRemove) {
    final RemoveCandidatesCommand rcc = new RemoveCandidatesCommand(strategyNameEnum, cell, candidateToRemove);
    return rcc;
  }

  public static Command buildRemoveCandidatesCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Candidates<Literal> candidatesToRemove) {
    final RemoveCandidatesCommand rcc = new RemoveCandidatesCommand(strategyNameEnum, cell, candidatesToRemove);
    return rcc;
  }

  public static Command buildRetainCandidatesCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Collection<Literal> candidates) {
    final RetainCandidatesCommand rcc = new RetainCandidatesCommand(strategyNameEnum, cell, candidates);
    return rcc;
  }

  public static Command buildSetCandidateCommand(final StrategyNameEnum strategyNameEnum, final int row,
      final int column, final Literal value) {
    final SetCandidateCommand scc = new SetCandidateCommand(strategyNameEnum, row, column, value);
    return scc;
  }

  public static Command buildSetValueCommand(final StrategyNameEnum strategyNameEnum, final Cell cell,
      final Literal value) {
    final SetValueCommand svc = new SetValueCommand(strategyNameEnum, cell, value);
    return svc;
  }

  public static Command buildSetValueCommand(final StrategyNameEnum strategyNameEnum, final int row, final int column,
      final Literal value) {
    final SetValueCommand svc = new SetValueCommand(strategyNameEnum, row, column, value);
    return svc;
  }

  public static Command buildUnsetCandidateCommand(final StrategyNameEnum strategyNameEnum, final int row,
      final int column, final Literal value) {
    final UnsetCandidateCommand ucc = new UnsetCandidateCommand(strategyNameEnum, row, column, value);
    return ucc;
  }

  public static Command buildUnsetValueCommand(final StrategyNameEnum strategyNameEnum, final int row,
      final int column, final Literal value) {
    final UnsetValueCommand uvc = new UnsetValueCommand(strategyNameEnum, row, column, value);
    return uvc;
  }

}

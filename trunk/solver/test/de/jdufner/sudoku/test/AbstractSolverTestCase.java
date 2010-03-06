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
package de.jdufner.sudoku.test;

import junit.framework.TestCase;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solver;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-02-26
 * @version $Revision$
 */
public abstract class AbstractSolverTestCase extends TestCase {

  protected transient Solver backtrackingSolver;
  protected transient ExtendedSolver strategySolver;
  protected transient ExtendedSolver strategySolverWithBacktracking;

  public AbstractSolverTestCase() {
    super();
  }

  public AbstractSolverTestCase(final String name) {
    super(name);
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    backtrackingSolver = (Solver) SolverServiceFactory.INSTANCE.getBean(SolverServiceFactory.BACKTRACKING_SOLVER);
    strategySolver = (ExtendedSolver) SolverServiceFactory.INSTANCE.getBean(SolverServiceFactory.STRATEGY_SOLVER);
    strategySolverWithBacktracking = (ExtendedSolver) SolverServiceFactory.INSTANCE
        .getBean(SolverServiceFactory.STRATEGY_SOLVER_WITH_BACKTRACKING);
  }

  protected Solver getBacktrackingSolver() {
    return backtrackingSolver;
  }

  protected ExtendedSolver getStrategySolver() {
    return strategySolver;
  }

  protected ExtendedSolver getStrategySolverWithBacktracking() {
    return strategySolverWithBacktracking;
  }

}

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
package de.jdufner.sudoku.solver.ddt;

import org.ddsteps.data.DataLoader;
import org.ddsteps.data.support.DataLoaderFactory;
import org.ddsteps.testcase.support.DDStepsExcelTestCase;

import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractSolverExcelTestCase extends DDStepsExcelTestCase {

  protected Solver getBacktrackingSolver() {
    return (Solver) SolverServiceFactory.INSTANCE.getBean(SolverServiceFactory.BACKTRACKING_SOLVER);
  }

  protected Solver getStrategySolver() {
    return (ExtendedSolver) SolverServiceFactory.INSTANCE.getBean(SolverServiceFactory.STRATEGY_SOLVER);
  }

  protected Solver getStrategySolverWithBacktracking() {
    return (ExtendedSolver) SolverServiceFactory.INSTANCE
        .getBean(SolverServiceFactory.STRATEGY_SOLVER_WITH_BACKTRACKING);
  }

  @Override
  public DataLoader createDataLoader() {
    return DataLoaderFactory.getCachingExcelDataLoader();
  }

}

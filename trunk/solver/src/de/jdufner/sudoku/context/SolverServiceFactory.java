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
package de.jdufner.sudoku.context;

import org.apache.commons.math.random.RandomData;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import de.jdufner.sudoku.common.factory.SudokuPool;
import de.jdufner.sudoku.common.validator.SudokuValidator;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SolverServiceFactory {
  private static final Logger LOG = Logger.getLogger(SolverServiceFactory.class);

  private static final String STRATEGY_SOLVER = "strategySolver";
  private static final String STRATEGY_SOLVER_WITH_BACKTRACKING = "strategySolverWithBacktracking";
  private static final String BACKTRACKING_SOLVER = "backtrackingSolver";

  private final transient ApplicationContext applicationContext;

  private static class SingletonHolder {
    private static SolverServiceFactory instance = new SolverServiceFactory(); // NOPMD by Jürgen on 14.11.09 23:14
  }

  private SolverServiceFactory() {
    applicationContext = new ClassPathXmlApplicationContext("solver-context.xml");
    try {
      Log4jConfigurer.initLogging(Log4jConfigurer.CLASSPATH_URL_PREFIX + "log4j.properties", 10000);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    }
  }

  public static SolverServiceFactory getInstance() {
    return SingletonHolder.instance;
  }

  public ExtendedSolver getStrategySolver() {
    return (ExtendedSolver) applicationContext.getBean(STRATEGY_SOLVER);
  }

  public ExtendedSolver getStrategySolverWithBacktracking() {
    return (ExtendedSolver) applicationContext.getBean(STRATEGY_SOLVER_WITH_BACKTRACKING);
  }

  public Solver getBacktrackingSolver() {
    return (Solver) applicationContext.getBean(BACKTRACKING_SOLVER);
  }

  public SudokuPool getSudokuPool() {
    return (SudokuPool) applicationContext.getBean("sudokuPool");
  }

  public SudokuValidator getSudokuValidator() {
    return (SudokuValidator) applicationContext.getBean("sudokuValidator");
  }

  public RandomData getRandomData() {
    return (RandomData) applicationContext.getBean("randomData");
  }

  public Object getBean(Class clazz) {
    String[] beanNames = applicationContext.getBeanNamesForType(clazz);
    if (beanNames.length == 0) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " nicht.");
    }
    if (beanNames.length > 1) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " mehrfach und ist nicht eindeutig.");
    }
    return applicationContext.getBean(beanNames[0]);
  }

}

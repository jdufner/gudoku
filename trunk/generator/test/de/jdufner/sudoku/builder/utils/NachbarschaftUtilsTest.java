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
package de.jdufner.sudoku.builder.utils;

import junit.framework.TestCase;

import org.apache.commons.math.random.RandomData;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.Solver;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-28
 * @version $Revision$
 */
public class NachbarschaftUtilsTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(NachbarschaftUtilsTest.class);

  private RandomData randomData = null;
  private Solver solver = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    solver = SolverServiceFactory.getInstance().getStrategySolverWithBacktracking();
    randomData = (RandomData) GeneratorServiceFactory.getInstance().getBean(RandomData.class);
  }

  public void testCheckNachbarschaft1() {
    Sudoku sudoku = SudokuFactory.buildFilled(SudokuSize.DEFAULT);
    LOG.debug(sudoku);
    assertFalse(NachbarschaftUtils.checkNachbarschaft(sudoku));
  }

  public void testCheckNachbachschaft2() {
    Sudoku underDeterminedSudoku = SudokuFactory.buildShuffled(SudokuSize.DEFAULT, randomData);
    Sudoku sudoku = solver.solve(underDeterminedSudoku);
    LOG.debug(sudoku);
    assertTrue(NachbarschaftUtils.checkNachbarschaft(sudoku));
  }

}

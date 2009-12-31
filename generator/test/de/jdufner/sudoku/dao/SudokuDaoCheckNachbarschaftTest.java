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
package de.jdufner.sudoku.dao;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.builder.utils.NachbarschaftUtils;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.Solver;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 31.12.2009
 * @version $Revision$
 */
public final class SudokuDaoCheckNachbarschaftTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(SudokuDaoCheckNachbarschaftTest.class);

  private SudokuDao sudokuDao = null;
  private Solver solver = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    sudokuDao = GeneratorServiceFactory.getInstance().getSudokuDao();
    solver = SolverServiceFactory.getInstance().getStrategySolverWithBacktracking();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

  public void testCheckNachbarschaftGespeicherterSudokus() {
    for (int i = 0; i < 27000; i++) {
      SudokuData sudokuData = sudokuDao.loadSudoku(i);
      if (sudokuData != null) {
        Sudoku sudoku = solver.solve(SudokuFactory.buildSudoku(sudokuData.getSudokuAsString()));
        if (NachbarschaftUtils.checkNachbarschaft(sudoku)) {
          LOG.info("Sudoku " + sudokuData.getId() + " ist OK.");
        } else {
          LOG.warn("Sudoku " + sudokuData.getId() + " ist NOK.");
          sudokuDao.deleteSudoku(sudokuData.getId());
        }
      }
    }
  }
}

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

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.board.XsudokuUtils;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.ExtendedSolver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SudokuDaoTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(SudokuDaoTest.class);

  private SudokuDao sudokuDao;
  private ExtendedSolver solver;

  @Override
  public void setUp() {
    sudokuDao = GeneratorServiceFactory.getInstance().getSudokuDao();
    solver = SolverServiceFactory.getInstance().getStrategySolverWithBacktracking();
  }

  public void testLoadSudoku() {
    SudokuData sudokuData = sudokuDao.loadSudoku(2505);
    LOG.debug(sudokuData);
  }

  public void testLoadSudokuOfDay() {
    Sudoku sudoku = sudokuDao.loadSudokuOfDay();
    LOG.debug(sudoku.toString());
  }

  public void testFindSudokus1() {
    List<SudokuData> sudokuDataList = sudokuDao.findSudokus(SudokuSize.DEFAULT, Level.LEICHT, 3, Boolean.FALSE);
    LOG.debug(sudokuDataList);
    assertEquals(3, sudokuDataList.size());
  }

  public void testFindSudokus2() {
    boolean weitereObjekteVorhanden = true;
    int anzahlObjekteGesamt = 0;
    int index = 0;
    int number = 1000;
    do {
      List<SudokuData> sudokuDataList = sudokuDao.findSudokus(index, number);
      anzahlObjekteGesamt += sudokuDataList.size();
      index += sudokuDataList.size();
      if (sudokuDataList.size() < number) {
        weitereObjekteVorhanden = false;
      }
    } while (weitereObjekteVorhanden);
    LOG.debug(anzahlObjekteGesamt);
    assertTrue(anzahlObjekteGesamt > 25000);
  }

  public void testXsudoku() {
    boolean weitereObjekteVorhanden = true;
    int index = 0;
    int number = 1000;
    do {
      List<SudokuData> sudokuDataList = sudokuDao.findSudokus(index, number);
      for (SudokuData sudokuData : sudokuDataList) {
        Sudoku sudoku = SudokuFactory.buildSudoku(sudokuData.getSudokuAsString());
        sudoku = solver.solve(sudoku);
        if (XsudokuUtils.isXsudoku(sudoku)) {
          LOG.info("X-Sudoku gefunden: " + sudoku);
          sudokuData.setxSudoku("J");
        }
      }
      index += sudokuDataList.size();
      LOG.debug("Index: " + index);
      if (sudokuDataList.size() < number) {
        weitereObjekteVorhanden = false;
      }
      sudokuDao.update(sudokuDataList);
    } while (weitereObjekteVorhanden);
  }

}

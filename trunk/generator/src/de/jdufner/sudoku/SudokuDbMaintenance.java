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
package de.jdufner.sudoku;

import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.dao.SudokuDao;
import de.jdufner.sudoku.dao.SudokuData;
import de.jdufner.sudoku.dao.SudokuMapper;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solution;

public final class SudokuDbMaintenance extends AbstractMainClass {

  private static final Logger LOG = Logger.getLogger(SudokuDbMaintenance.class);

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception {
    SudokuDbMaintenance sudokuDbMaintenance = new SudokuDbMaintenance();
    sudokuDbMaintenance.start();
  }

  protected void run() {
    LOG.debug("START");
    SudokuDao sudokuDao = (SudokuDao) GeneratorServiceFactory.getInstance().getBean(SudokuDao.class);
    ExtendedSolver solver = (ExtendedSolver) SolverServiceFactory.getInstance().getBean(
        SolverServiceFactory.STRATEGY_SOLVER_WITH_BACKTRACKING);
    boolean weitereObjekteVorhanden = true;
    int index = 0;
    int number = 10;
    //    do {
    List<SudokuData> sudokuDataList = sudokuDao.findSudokus(index, number);
    for (SudokuData sudokuData : sudokuDataList) {
      Sudoku sudoku = SudokuFactory.buildSudoku(sudokuData.getSudokuAsString());
      Solution solution = solver.getSolution(sudoku);
      SudokuMapper.map(sudokuData, solution);
    }
    index += sudokuDataList.size();
    LOG.debug("Index: " + index);
    if (sudokuDataList.size() < number) {
      weitereObjekteVorhanden = false;
    }
    sudokuDao.update(sudokuDataList);
    //    } while (weitereObjekteVorhanden);
    LOG.debug("ENDE");
  }

}

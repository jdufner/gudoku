// $Id: SudokuDaoTest.java,v 1.8 2009/12/26 23:06:47 jdufner Exp $

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
package de.jdufner.sudoku.dao;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.8 $
 */
public final class SudokuDaoTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(SudokuDaoTest.class);

  private SudokuDao sudokuDao;

  @Override
  public void setUp() {
    sudokuDao = GeneratorServiceFactory.getInstance().getSudokuDao();
  }

  public void testSaveSudoku() {
    Sudoku sudoku = SudokuFactory.buildSudoku(Examples.ING_DIBA);
    sudokuDao.saveSudoku(sudoku);
  }

  public void testLoadSudokuOfDay() {
    Sudoku sudoku = sudokuDao.loadSudokuOfDay();
    LOG.debug(sudoku.toString());
  }

  public void testLoadSudoku() {
    SudokuData sudokuData = sudokuDao.loadSudoku(2505);
    LOG.debug(sudokuData);
  }

  public void testFindSudokus() {
    List<SudokuData> sudokuDataList = sudokuDao.findSudokus(SudokuSize.DEFAULT, Level.LEICHT, 3, Boolean.FALSE);
    LOG.debug(sudokuDataList);
    assertEquals(3, sudokuDataList.size());
  }
}
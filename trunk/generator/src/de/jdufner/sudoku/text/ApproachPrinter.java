// $Id: ApproachPrinter.java,v 1.2 2009/12/26 23:20:45 jdufner Exp $

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
package de.jdufner.sudoku.text;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Log;
import de.jdufner.sudoku.dao.SudokuDao;
import de.jdufner.sudoku.dao.SudokuData;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 22.12.2009
 * @version $Revision: 1.2 $
 */
public final class ApproachPrinter {

  private static final Logger LOG = Logger.getLogger(ApproachPrinter.class);

  private SudokuDao sudokuDao;
  private ExtendedSolver extendedSolver;

  public void print(int id) {
    LOG.info("Start!");
    Log.APPROACH.info("");
    Log.APPROACH.info("START Sudoku " + id);
    SudokuData sudokuData = getSudokuDao().loadSudoku(id);
    Sudoku sudoku = SudokuFactory.buildSudoku(sudokuData.getSudokuAsString());
    Solution solution = getExtendedSolver().getSolution(sudoku);
    Log.APPROACH.info("Zusammenfassung");
    Log.APPROACH.info("            Rätsel: " + solution.getQuest());
    Log.APPROACH.info("            Lösung: " + solution.getResult());
    Log.APPROACH.info("     Eindeutigkeit: " + solution.getLevel());
    Log.APPROACH.info("Schwierigkeitsgrad: " + solution.isUnique());
    Log.APPROACH.info("ENDE Sudoku " + id);
    Log.APPROACH.info("");
    LOG.info("Ende!");
  }

  //
  // Spring Wiring
  //

  public SudokuDao getSudokuDao() {
    return sudokuDao;
  }

  public void setSudokuDao(SudokuDao sudokuDao) {
    this.sudokuDao = sudokuDao;
  }

  public ExtendedSolver getExtendedSolver() {
    return extendedSolver;
  }

  public void setExtendedSolver(ExtendedSolver extendedSolver) {
    this.extendedSolver = extendedSolver;
  }

}

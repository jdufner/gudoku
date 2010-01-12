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
package de.jdufner.sudoku.text;

import java.io.IOException;

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
 * @version $Revision$
 */
public final class ApproachPrinter {

  private static final Logger LOG = Logger.getLogger(ApproachPrinter.class);

  private ApproachFilePrinter approachFilePrinter;
  private SudokuDao sudokuDao;
  private ExtendedSolver extendedSolver;

  public void print(int sudokuId) throws IOException {
    LOG.info("Start!");
    approachFilePrinter.openFile(sudokuId);
    Log.startRecording();
    Log.log("");
    Log.log("START Sudoku " + sudokuId);
    SudokuData sudokuData = getSudokuDao().loadSudoku(sudokuId);
    Sudoku sudoku = SudokuFactory.buildSudoku(sudokuData.getSudokuAsString());
    Solution solution = getExtendedSolver().getSolution(sudoku);
    Log.log("Zusammenfassung");
    Log.log("            Rätsel: " + solution.getQuest());
    Log.log("            Lösung: " + solution.getResult());
    Log.log("     Eindeutigkeit: " + solution.getLevel());
    Log.log("Schwierigkeitsgrad: " + solution.isUnique());
    Log.log("ENDE Sudoku " + sudokuId);
    Log.log("");
    approachFilePrinter.print(Log.getMessagesAndStopRecording());
    approachFilePrinter.closeAndCompressFile();
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

  public ApproachFilePrinter getApproachFilePrinter() {
    return approachFilePrinter;
  }

  public void setApproachFilePrinter(ApproachFilePrinter solutionPrinter) {
    this.approachFilePrinter = solutionPrinter;
  }

}

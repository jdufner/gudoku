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
package de.jdufner.sudoku.pdf;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.dao.SudokuDao;
import de.jdufner.sudoku.dao.SudokuData;
import de.jdufner.sudoku.solver.service.ExtendedSolver;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-20
 * @version $Revision$
 */
public final class PdfGenerator {

  private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'kkmmss");

  private SudokuDao sudokuDao;
  private PdfPrinter pdfPrinter;
  private ExtendedSolver solver;

  public void generate() throws FileNotFoundException, DocumentException {
    final List<SudokuData> allSudokuQuests = new ArrayList<SudokuData>();
    for (Level level : Level.values()) {
      List<SudokuData> sudokus = getSudokuDao().findSudokus(SudokuSize.DEFAULT, level, 6, Boolean.FALSE);
      allSudokuQuests.addAll(sudokus);
    }
    final List<SudokuData> allSudokuResults = new ArrayList<SudokuData>(allSudokuQuests.size());
    for (SudokuData sudokuData : allSudokuQuests) {
      SudokuData sudokuData2 = new SudokuData();
      Sudoku result = solver.solve(SudokuFactory.buildSudoku(sudokuData.getSudokuAsString()));
      sudokuData2.setId(sudokuData.getId());
      sudokuData2.setLevel(sudokuData.getLevel());
      sudokuData2.setSize(sudokuData.getSize());
      sudokuData2.setSudokuAsString(result.toString());
      allSudokuResults.add(sudokuData2);
    }

    final List<SudokuData> allSudokus = new ArrayList<SudokuData>(allSudokuQuests.size() * 2);
    allSudokus.addAll(allSudokuQuests);
    allSudokus.addAll(allSudokuResults);

    getPdfPrinter().print(allSudokus, "C:\\tmp\\Sudoku_" + FORMATTER.format(new Date()) + ".pdf");

    Date now = new Date();
    for (SudokuData sudoku : allSudokuQuests) {
      getSudokuDao().updatePrintedAt(sudoku.getId(), now);
    }
  }

  //
  // Spring-Wiring
  //

  public SudokuDao getSudokuDao() {
    return sudokuDao;
  }

  public void setSudokuDao(SudokuDao sudokuDao) {
    this.sudokuDao = sudokuDao;
  }

  public PdfPrinter getPdfPrinter() {
    return pdfPrinter;
  }

  public void setPdfPrinter(PdfPrinter pdfPrinter) {
    this.pdfPrinter = pdfPrinter;
  }

  public ExtendedSolver getSolver() {
    return solver;
  }

  public void setSolver(ExtendedSolver solver) {
    this.solver = solver;
  }

}

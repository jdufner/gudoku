// $Id$

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
package de.jdufner.sudoku.generator.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.dao.SudokuDao;
import de.jdufner.sudoku.dao.SudokuData;
import de.jdufner.sudoku.generator.pdf.PdfPrinter;
import de.jdufner.sudoku.generator.service.PdfGeneratorConfiguration.Page;
import de.jdufner.sudoku.generator.text.ApproachPrinter;
import de.jdufner.sudoku.solver.service.ExtendedSolver;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-20
 * @version $Revision$
 */
public final class PdfGeneratorService {

  private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'kkmmss");

  private SudokuDao sudokuDao;
  private PdfPrinter pdfPrinter;
  private ApproachPrinter approachPrinter;
  private ExtendedSolver solver;

  public void generate(PdfGeneratorConfiguration config) throws DocumentException, IOException {
    final List<SudokuData> allSudokuQuests = new ArrayList<SudokuData>();
    for (Page page : config.getPages()) {
      List<SudokuData> sudokus = getSudokuDao().findSudokus(config.getSize(), page.getLevel(), page.getNumber(),
          Boolean.FALSE);
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
      approachPrinter.print(sudokuData.getId());
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

  public ApproachPrinter getApproachPrinter() {
    return approachPrinter;
  }

  public void setApproachPrinter(ApproachPrinter approachPrinter) {
    this.approachPrinter = approachPrinter;
  }

}

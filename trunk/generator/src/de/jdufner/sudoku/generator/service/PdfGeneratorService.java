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
package de.jdufner.sudoku.generator.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;

import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.dao.SudokuDao;
import de.jdufner.sudoku.dao.SudokuData;
import de.jdufner.sudoku.generator.pdf.PdfPrinter;
import de.jdufner.sudoku.generator.service.PdfGeneratorConfiguration.Page;
import de.jdufner.sudoku.generator.text.ApproachFilePrinter;
import de.jdufner.sudoku.generator.text.JavascriptGenerator;
import de.jdufner.sudoku.solver.service.ExtendedSolver;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-20
 * @version $Revision$
 */
public final class PdfGeneratorService {

  private SudokuDao sudokuDao;
  private PdfPrinter pdfPrinter;
  private ApproachFilePrinter approachFilePrinter;
  private ExtendedSolver solver;
  private String fileDirectory;
  private String filePattern;

  public void generate(PdfGeneratorConfiguration config) throws DocumentException, IOException {
    final String filename = getFileDirectory() + System.getProperty("file.separator")
        + new SimpleDateFormat(getFilePattern()).format(new Date());
    approachFilePrinter.openFile(filename + ".txt.gz");
    final List<SudokuData> allSudokuQuests = new ArrayList<SudokuData>();
    for (Page page : config.getPages()) {
      List<SudokuData> sudokus = getSudokuDao().findSudokus(config.getSize(), page.getLevel(), page.getNumber(),
          Boolean.FALSE);
      allSudokuQuests.addAll(sudokus);
    }
    final List<SudokuData> allSudokuResults = new ArrayList<SudokuData>(allSudokuQuests.size());
    int index = 0;
    for (SudokuData sudokuData : allSudokuQuests) {
      SudokuData sudokuData2 = new SudokuData();
      Solution solution = solver.getSolution(SudokuFactory.buildSudoku(sudokuData.getSudokuAsString()));
      sudokuData2.setId(sudokuData.getId());
      sudokuData2.setLevel(sudokuData.getLevel());
      sudokuData2.setSize(sudokuData.getSize());
      sudokuData2.setSudokuAsString(solution.getResult().toString());
      if (index != 0) {
        approachFilePrinter.print(",");
      }
      approachFilePrinter.print(JavascriptGenerator.toJavascript(sudokuData.getId(), solution));
      allSudokuResults.add(sudokuData2);
      index++;
    }

    final List<SudokuData> allSudokus = new ArrayList<SudokuData>(allSudokuQuests.size() * 2);
    allSudokus.addAll(allSudokuQuests);
    allSudokus.addAll(allSudokuResults);

    getPdfPrinter().print(allSudokus, filename + ".pdf");

    Date now = new Date();
    for (SudokuData sudoku : allSudokuQuests) {
      getSudokuDao().updatePrintedAt(sudoku.getId(), now);
    }
    approachFilePrinter.closeAndCompressFile();
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

  public ApproachFilePrinter getApproachFilePrinter() {
    return approachFilePrinter;
  }

  public void setApproachFilePrinter(ApproachFilePrinter approachFilePrinter) {
    this.approachFilePrinter = approachFilePrinter;
  }

  public String getFileDirectory() {
    return fileDirectory;
  }

  public void setFileDirectory(String fileDirectory) {
    this.fileDirectory = fileDirectory;
  }

  public String getFilePattern() {
    return filePattern;
  }

  public void setFilePattern(String filePattern) {
    this.filePattern = filePattern;
  }

}

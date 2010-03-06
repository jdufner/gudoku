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
package de.jdufner.sudoku.generator.pdf;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.lowagie.text.DocumentException;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.dao.SudokuData;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-08
 * @version $Revision$
 */
public final class PdfPrinterImplTest extends TestCase {

  private static final String EXAMPLE = ".5..9...2.6..2...8..7...9...81.............5...69.3......7.2..5...6..4..8.9.5...3";

  public void testPrint() throws FileNotFoundException, DocumentException {
    SudokuData sudokuData = new SudokuData();
    sudokuData.setSudokuAsString(EXAMPLE);
    Sudoku s = SudokuFactory.buildSudoku(sudokuData.getSudokuAsString());
    sudokuData.setFixed(s.getNumberOfFixed());
    sudokuData.setGeneratedAt(new Date());
    sudokuData.setLevel(5);
    sudokuData.setSize(s.getSize().getUnitSize());
    List<SudokuData> sudokus = new ArrayList<SudokuData>();
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    sudokus.add(sudokuData);
    String fileName = "C:\\tmp\\PdfPrinterImplTest.pdf";
    PdfPrinter pdfPrinter = (PdfPrinter) GeneratorServiceFactory.INSTANCE.getBean(PdfPrinter.class);
    pdfPrinter.print(sudokus, fileName);
  }
}

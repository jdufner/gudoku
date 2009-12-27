// $Id: PdfPrinterImpl.java,v 1.4 2009/12/21 22:21:27 jdufner Exp $

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
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.dao.SudokuData;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-08
 * @version $Revision: 1.4 $
 */
public final class PdfPrinterImpl implements PdfPrinter {

  private Properties pdfStyle;

  @Override
  public void print(List<SudokuData> sudokus, String fileName) throws DocumentException, FileNotFoundException {
    Document document = new Document(PageSize.A4, 10, 10, 10, 10);
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
    writeDocument(document, sudokus);
    writer.close();
  }

  private Document writeDocument(Document document, List<SudokuData> sudokus) throws DocumentException {
    document.open();
    // TODO Von außen konfigurieren
    document.addAuthor("Jürgen Dufner");
    document.addCreationDate();
    document.addCreator("de.jdufner.sudoku.Generator");
    document.addKeywords("Sudoku");
    document.addTitle("30 Sudokus in unterschiedlichen Schwierigkeitsgraden");
    document.add(writePdfMetaTable(sudokus));
    document.close();
    return document;
  }

  private PdfPTable writePdfMetaTable(List<SudokuData> sudokus) throws DocumentException {
    // Tabelle für Formatierung von 2 Sudokus nebeneinander
    PdfPTable metaTable = new PdfPTable(2);
    metaTable.getDefaultCell().setBorder(0);
    metaTable.getDefaultCell().setPadding(5);
    metaTable.setWidthPercentage(100);
    metaTable.setHorizontalAlignment(Element.ALIGN_CENTER);

    for (SudokuData sudoku : sudokus) {
      metaTable.addCell(writePdfTable(sudoku));
    }

    metaTable.setComplete(true);
    return metaTable;
  }

  private PdfPTable writePdfTable(SudokuData sudokuData) {
    PdfPTable einzelnesSudoku = new PdfPTable(1);
    PdfPTable ueberschrift = new PdfPTable(2);
    PdfPCell linkeZelle = new PdfPCell(new Phrase("ID: " + sudokuData.getId()));
    linkeZelle.getPhrase().getFont().setSize(9f);
    linkeZelle.setBorder(Integer.parseInt(getPdfStyle().getProperty("border.none")));
    linkeZelle.setHorizontalAlignment(Element.ALIGN_LEFT);
    ueberschrift.addCell(linkeZelle);
    PdfPCell rechteZelle = new PdfPCell(new Phrase(Level.valueOf(sudokuData.getLevel()).getName() + " ("
        + sudokuData.getFixed() + ")"));
    rechteZelle.getPhrase().getFont().setSize(9f);
    rechteZelle.setBorder(0);
    rechteZelle.setHorizontalAlignment(Element.ALIGN_RIGHT);
    ueberschrift.addCell(rechteZelle);
    PdfPCell obereZelle = new PdfPCell(ueberschrift);
    obereZelle.setBorder(0);
    einzelnesSudoku.addCell(obereZelle);
    PdfCellHandler pdfCellHandler = new PdfCellHandler(SudokuSize.getByUnitSize(sudokuData.getSize()), getPdfStyle());
    pdfCellHandler.initialize();
    HandlerUtil.forEachCell(SudokuFactory.buildSudoku(sudokuData.getSudokuAsString()), pdfCellHandler);
    PdfPCell untereZelle = new PdfPCell(pdfCellHandler.getTable());
    untereZelle.setBorder(0);
    einzelnesSudoku.addCell(untereZelle);
    return einzelnesSudoku;
  }

  //
  // Spring Wiring
  //

  public Properties getPdfStyle() {
    return pdfStyle;
  }

  public void setPdfStyle(Properties style) {
    this.pdfStyle = style;
  }

}

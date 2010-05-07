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
package de.jdufner.sudoku.generator.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
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
 * @version $Revision$
 */
public final class PdfPrinterImpl implements PdfPrinter {

  private Properties pdfStyle;

  @Override
  public void printFrontpage(String name, List<PdfSolution> solutions, String fileName) throws DocumentException,
      FileNotFoundException {
    Document document = new Document(PageSize.A4, 10, 10, 10, 10);
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
    writeFrontpage(name, document, solutions);
    writer.close();
  }

  @Override
  public void printQuests(List<SudokuData> sudokus, String fileName) throws DocumentException, FileNotFoundException {
    Document document = new Document(PageSize.A4, 10, 10, 10, 10);
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
    writeDocument(document, sudokus);
    writer.close();
  }

  @Override
  public void printResults(List<SudokuData> sudokus, String fileName) throws DocumentException, FileNotFoundException {
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

  private void writeFrontpage(String name, Document document, List<PdfSolution> solutions) throws DocumentException {
    document.open();
    Paragraph p = new Paragraph(name);
    p.setAlignment(Element.ALIGN_CENTER);
    p.setSpacingBefore(20f);
    p.setSpacingAfter(20f);
    document.add(p);
    PdfPTable table = new PdfPTable(17);
    table.setWidthPercentage(100);
    int[] width = { 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
    table.setWidths(width);
    PdfPCell cell;
    table.addCell(buildHeaderCell("Name", 90, true, false));
    table.addCell(buildHeaderCell("Schwierigkeitsgrad", 90, false, false));
    table.addCell(buildHeaderCell("Besetzte Zellen", 90, false, false));
    table.addCell(buildHeaderCell("Simple", 90, false, false));
    table.addCell(buildHeaderCell("Hidden Single", 90, false, false));
    table.addCell(buildHeaderCell("Naked Pair", 90, false, false));
    table.addCell(buildHeaderCell("Naked Triple", 90, false, false));
    table.addCell(buildHeaderCell("Naked Quad", 90, false, false));
    table.addCell(buildHeaderCell("Hidden Pair", 90, false, false));
    table.addCell(buildHeaderCell("Hidden Triple", 90, false, false));
    table.addCell(buildHeaderCell("Hidden Quad", 90, false, false));
    table.addCell(buildHeaderCell("Intersection Removal", 90, false, false));
    table.addCell(buildHeaderCell("Y-Wing", 90, false, false));
    table.addCell(buildHeaderCell("X-Wing", 90, false, false));
    table.addCell(buildHeaderCell("Jellyfish", 90, false, false));
    table.addCell(buildHeaderCell("Swordfish", 90, false, false));
    table.addCell(buildHeaderCell("Backtracking", 90, false, true));
    boolean even = false;
    for (PdfSolution solution : solutions) {
      table.addCell(buildBodyNumberCell(solution.getId(), even, true, false));
      table.addCell(buildBodyTextCell(solution.getLevel().toString(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getFixed(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategySimple(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyHiddenSingle(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyNakedPair(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyNakedTriple(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyNakedQuad(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyHiddenPair(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyHiddenTriple(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyHiddenQuad(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyIntersectionRemoval(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyYwing(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyXwing(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyJellyfish(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategySwordfish(), even, false, false));
      table.addCell(buildBodyNumberCell(solution.getStrategyBacktracking(), even, false, true));
      even = (even ? false : true);
    }
    document.add(table);
    document.close();
  }

  private PdfPCell buildHeaderCell(String text, int rotation, boolean first, boolean last) {
    PdfPCell cell = new PdfPCell(new Phrase(text));
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    setBorder(cell, first, last);
    cell.setRotation(rotation);
    return cell;
  }

  private PdfPCell buildBodyNumberCell(int value, boolean even, boolean first, boolean last) {
    PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(value)));
    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    if (even) {
      cell.setGrayFill(0.8f);
    }
    setBorder(cell, first, last);
    //cell.setRotation(rotation);
    return cell;
  }

  private PdfPCell buildBodyTextCell(String value, boolean even, boolean first, boolean last) {
    PdfPCell cell = new PdfPCell(new Phrase(value));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    if (even) {
      cell.setGrayFill(0.8f);
    }
    setBorder(cell, first, last);
    //cell.setRotation(rotation);
    return cell;
  }

  private void setBorder(PdfPCell cell, boolean first, boolean last) {
    if (cell == null) {
      return;
    }
    if (first) {
      cell.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM | PdfPCell.LEFT);
    } else if (last) {
      cell.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM | PdfPCell.RIGHT);
    } else {
      cell.setBorder(PdfPCell.TOP | PdfPCell.BOTTOM);
    }
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

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
package de.jdufner.sudoku.pdf;

import java.awt.Color;
import java.util.Properties;

import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import de.jdufner.sudoku.common.board.BlockUtils;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellHandler;
import de.jdufner.sudoku.common.board.SudokuSize;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-07
 * @version $Revision$
 */
public final class PdfCellHandler implements CellHandler {

  private SudokuSize sudokuSize;
  private Properties pdfStyle;

  private PdfPTable table = null;

  public PdfCellHandler(SudokuSize sudokuSize, Properties pdfStyle) {
    this.sudokuSize = sudokuSize;
    this.pdfStyle = pdfStyle;
  }

  @Override
  public void handleCell(Cell cell) {
    PdfPCell pdfCell = null;
    if (cell.isFixed()) {
      pdfCell = new PdfPCell(new Paragraph(cell.getValue().toString()));
    } else {
      if (Boolean.getBoolean(getPdfStyle().getProperty("sudoku.board.candidates.print"))) {
        pdfCell = new PdfPCell(buildCandidates());
      } else {
        pdfCell = new PdfPCell();
      }
    }
    formatZelle(cell.getRowIndex(), cell.getColumnIndex(), pdfCell);
    table.addCell(pdfCell);
  }

  @Override
  public void initialize() {
    table = new PdfPTable(getSudokuSize().getUnitSize());
  }

  public PdfPTable getTable() {
    return table;
  }

  private boolean isEvenBlockIndex(final int rowIndex, final int columnIndex) {
    if (BlockUtils.getBlockIndexByRowIndexAndColumnIndex(rowIndex, columnIndex, getSudokuSize()) % 2 == 0) {
      return true;
    }
    return false;
  }

  private void formatZelle(final int zeile, final int spalte, final PdfPCell cell) {
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell.setBorderColor(new Color(//
        Integer.parseInt(getPdfStyle().getProperty("sudoku.board.border.color.red")), //
        Integer.parseInt(getPdfStyle().getProperty("sudoku.board.border.color.green")), //
        Integer.parseInt(getPdfStyle().getProperty("sudoku.board.border.color.blue"))));

    if (isEvenBlockIndex(zeile, spalte)) {
      cell.setBackgroundColor(new Color(//
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.odd.color.red")), //
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.odd.color.green")), //
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.odd.color.blue"))));
    } else {
      cell.setBackgroundColor(new Color(//
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.even.color.red")), //
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.even.color.green")), //
          Integer.parseInt(getPdfStyle().getProperty("sudoku.board.background.even.color.blue"))));
    }
    cell.setBorderWidth(PdfConstants.RAHMEN_DUENN);
    if (BlockUtils.isFirstRowInBlock(zeile, getSudokuSize())) {
      cell.setBorderWidthTop(PdfConstants.RAHMEN_DICK);
    } else {
      cell.setBorderWidthTop(PdfConstants.RAHMEN_DUENN);
    }
    if (BlockUtils.isLastColumnInRow(spalte, getSudokuSize())) {
      cell.setBorderWidthRight(PdfConstants.RAHMEN_DICK);
    } else {
      cell.setBorderWidthRight(PdfConstants.RAHMEN_DUENN);
    }
    if (BlockUtils.isLastRowInColumn(zeile, getSudokuSize())) {
      cell.setBorderWidthBottom(PdfConstants.RAHMEN_DICK);
    } else {
      cell.setBorderWidthBottom(PdfConstants.RAHMEN_DUENN);
    }
    if (BlockUtils.isFirstColumnInBlock(spalte, getSudokuSize())) {
      cell.setBorderWidthLeft(PdfConstants.RAHMEN_DICK);
    } else {
      cell.setBorderWidthLeft(PdfConstants.RAHMEN_DUENN);
    }
    cell.setFixedHeight(27f);
  }

  /**
   * 
   * @return 3x3 Tabelle gef�llt mit Kandidaten 1-9
   */
  private PdfPTable buildCandidates() {
    final float CANDIDATE_FONT_SIZE = 6f;
    final float CANDIDATE_PADDING = 1f;
    PdfPTable candidates = new PdfPTable(3);
    PdfPCell[][] candidate = new PdfPCell[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        Paragraph p = new Paragraph(String.valueOf(i * 3 + j + 1));
        p.getFont().setSize(CANDIDATE_FONT_SIZE);
        candidate[i][j] = new PdfPCell(p);
        candidate[i][j].setPadding(CANDIDATE_PADDING);
        candidate[i][j].setHorizontalAlignment(Element.ALIGN_CENTER);
        candidate[i][j].setVerticalAlignment(Element.ALIGN_MIDDLE);
        candidate[i][j].setBorderColor(new Color(PdfConstants.RAHMEN_FARBE[0], PdfConstants.RAHMEN_FARBE[1],
            PdfConstants.RAHMEN_FARBE[2]));
        candidate[i][j].setBorderWidth(PdfConstants.RAHMEN_KEIN);
        if (i > 0) {
          candidate[i][j].setBorderWidthTop(PdfConstants.RAHMEN_DUENN);
        }
        if (j > 0) {
          candidate[i][j].setBorderWidthLeft(PdfConstants.RAHMEN_DUENN);
        }
        candidates.addCell(candidate[i][j]);
      }
    }
    return candidates;
  }

  public SudokuSize getSudokuSize() {
    return sudokuSize;
  }

  public void setSudokuSize(SudokuSize sudokuSize) {
    this.sudokuSize = sudokuSize;
  }

  public Properties getPdfStyle() {
    return pdfStyle;
  }

  public void setPdfStyle(Properties pdfStyle) {
    this.pdfStyle = pdfStyle;
  }

}
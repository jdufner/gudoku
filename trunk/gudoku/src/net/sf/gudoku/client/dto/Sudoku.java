// $Id: Sudoku.java,v 1.7 2009/11/11 23:11:22 jdufner Exp $

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
package net.sf.gudoku.client.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.7 $
 */
public class Sudoku extends Observable implements Serializable {

  private static final long serialVersionUID = 1L;

  private SudokuSize size;
  private List<Cell> cells;
  private int numberOfFixed;

  public SudokuSize getSize() {
    return size;
  }

  public void setSize(SudokuSize size) {
    this.size = size;
  }

  public List<Cell> getCells() {
    return cells;
  }

  public void setCells(List<Cell> cells) {
    this.cells = cells;
  }

  public void setCell(Cell cell) {
    // Füge Zelle in Modell ein
    int rowInt = Cell.mapClientRow2ServerRow(cell.getRow());
    int columnInt = Cell.mapClientColumn2ServerColumn(cell.getColumn());
    int index = rowInt * getSize().getUnitSize() + columnInt;
    cells.set(index, cell);
    // Führe Änderungen an View aus
    notifyObservers(cell);
  }

  public Cell getCell(int row, int column) {
    int cellNumber = row * size.getUnitSize() + column;
    return (Cell) cells.get(cellNumber);
  }

  public int getNumberOfFixed() {
    return numberOfFixed;
  }

  public void setNumberOfFixed(int numberOfFixed) {
    this.numberOfFixed = numberOfFixed;
  }

  public int getRowChecksum(int row) {
    int checksum = 0;
    for (int i = 0; i < size.getUnitSize(); i++) {
      if (getCell(row, i).isFixed()) {
        checksum += getCell(row, i).getFixed();
      }
    }
    return checksum;
  }

  public int getColumnChecksum(int column) {
    int checksum = 0;
    for (int i = 0; i < size.getUnitSize(); i++) {
      if (getCell(i, column).isFixed()) {
        checksum += getCell(i, column).getFixed();
      }
    }
    return checksum;
  }

  public String toString() {
    return size.toString() + ":" + cells.toString();
  }

}
/*
 * $Log: Sudoku.java,v $
 * Revision 1.7  2009/11/11 23:11:22  jdufner
 * Migration to GWT 1.7
 * Revision 1.6 2008/06/06 21:07:43 jdufner Default-SerialUID eingebaut
 * 
 * Revision 1.5 2008/05/16 13:55:55 jdufner kleines Namensrefactoring
 * 
 * Revision 1.4 2008/05/16 13:24:39 jdufner Zeige Spiel-ID und Anzahl der gesetzten Felder, kleine Namensrefactorings
 * 
 * Revision 1.3 2008/05/15 21:40:20 jdufner toString-Methode implementiert Revision 1.2 2008/05/09 22:48:46 jdufner
 * Javadoc Version-Tag auf CVS keyword Revision gesetzt Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

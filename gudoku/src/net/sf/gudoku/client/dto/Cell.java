// $Id: Cell.java,v 1.7 2009/11/11 23:11:22 jdufner Exp $

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
public class Cell implements Serializable {

  private static final long serialVersionUID = 1L;

  private static char[] clientRows = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
  private static char[] clientColumns = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I' };

  public static String mapServer2Client(int row, int column) {
    return String.valueOf(mapServerColumn2ClientColumn(column)) + String.valueOf(mapServerRow2ClientRow(row));
  }

  public static char mapServerRow2ClientRow(int serverRow) {
    return clientRows[serverRow];
  }

  public static char mapServerColumn2ClientColumn(int serverColumn) {
    return clientColumns[serverColumn];
  }

  public static int mapClientRow2ServerRow(char clientRow) {
    for (int i = 0; i < clientRows.length; i++) {
      if (clientRows[i] == clientRow) {
        return i;
      }
    }
    throw new IllegalArgumentException();
  }

  public static int mapClientColumn2ServerColumn(char clientColumn) {
    for (int i = 0; i < clientColumns.length; i++) {
      if (clientColumns[i] == clientColumn) {
        return i;
      }
    }
    throw new IllegalArgumentException();
  }

  private char row, column;
  private List<Integer> candidates;
  private int fixed;
  private boolean initialValue = false;
  private boolean undoPossible, redoPossible = false;
  private int numberFixed;
  private boolean correct;

  public Cell() {
  }

  public String getPosition() {
    return String.valueOf(getColumn()) + String.valueOf(getRow());
  }

  public char getRow() {
    return row;
  }

  public void setRow(char row) {
    this.row = row;
  }

  public char getColumn() {
    return column;
  }

  public void setColumn(char column) {
    this.column = column;
  }

  public List<Integer> getCandidates() {
    return candidates;
  }

  public void setCandidates(List<Integer> candidates) {
    this.candidates = candidates;
  }

  public int getFixed() {
    return fixed;
  }

  public void setFixed(int fixed) {
    this.fixed = fixed;
  }

  public boolean isFixed() {
    if (fixed > 0) {
      return true;
    }
    return false;
  }

  public boolean isInitialValue() {
    return initialValue;
  }

  public void setInitialValue(boolean initialValue) {
    this.initialValue = initialValue;
  }

  public boolean isUndoPossible() {
    return undoPossible;
  }

  public void setUndoPossible(boolean undoPossible) {
    this.undoPossible = undoPossible;
  }

  public boolean isRedoPossible() {
    return redoPossible;
  }

  public void setRedoPossible(boolean redoPossible) {
    this.redoPossible = redoPossible;
  }

  public int getNumberFixed() {
    return numberFixed;
  }

  public void setNumberFixed(int numberFixed) {
    this.numberFixed = numberFixed;
  }

  public boolean isCorrect() {
    return correct;
  }

  public void setCorrect(boolean correct) {
    this.correct = correct;
  }

  public String toString() {
    return String.valueOf(getFixed());
  }

}
/*
 * $Log: Cell.java,v $
 * Revision 1.7  2009/11/11 23:11:22  jdufner
 * Migration to GWT 1.7
 * Revision 1.6 2008/06/06 21:07:46 jdufner Default-SerialUID eingebaut
 * 
 * Revision 1.5 2008/06/02 17:46:54 jdufner CVS Kommentar korrigiert, Game erweitert für Gudoku
 * 
 * Revision 1.4 2008/05/16 13:24:39 jdufner Zeige Spiel-ID und Anzahl der gesetzten Felder, kleine Namensrefactorings
 * 
 * Revision 1.3 2008/05/15 21:40:21 jdufner toString-Methode implementiert Revision 1.2 2008/05/09 22:48:46 jdufner
 * Javadoc Version-Tag auf CVS keyword Revision gesetzt Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

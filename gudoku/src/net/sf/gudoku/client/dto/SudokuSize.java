// $Id: SudokuSize.java,v 1.5 2008/06/06 21:07:42 jdufner Exp $

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

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.5 $
 */
public class SudokuSize implements Serializable {

  private static final long serialVersionUID = 1L;

  private int blockWidth;
  private int blockHeight;
  private int unitSize;
  private int totalSize;
  private int unitChecksum;

  public SudokuSize() {
  }

  public int getBlockWidth() {
    return blockWidth;
  }

  public void setBlockWidth(int blockWidth) {
    this.blockWidth = blockWidth;
  }

  public int getBlockHeight() {
    return blockHeight;
  }

  public void setBlockHeight(int blockHeight) {
    this.blockHeight = blockHeight;
  }

  public int getUnitSize() {
    return unitSize;
  }

  public void setUnitSize(int unitSize) {
    this.unitSize = unitSize;
  }

  public int getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(int totalSize) {
    this.totalSize = totalSize;
  }

  public int getUnitChecksum() {
    return unitChecksum;
  }

  public void setChecksum(int unitChecksum) {
    this.unitChecksum = unitChecksum;
  }

  public String toString() {
    return String.valueOf(getUnitSize());
  }

}
/*
 * $Log: SudokuSize.java,v $
 * Revision 1.5  2008/06/06 21:07:42  jdufner
 * Default-SerialUID eingebaut
 *
 * Revision 1.4  2008/05/16 13:24:39  jdufner
 * Zeige Spiel-ID und Anzahl der gesetzten Felder,
 * kleine Namensrefactorings
 *
 * Revision 1.3  2008/05/15 21:40:19  jdufner
 * toString-Methode implementiert
 * Revision 1.2 2008/05/09 22:48:46 jdufner Javadoc
 * Version-Tag auf CVS keyword Revision gesetzt Revision 1.1.1.1 2008/05/09
 * 20:34:09 jdufner Initial Check-In
 */

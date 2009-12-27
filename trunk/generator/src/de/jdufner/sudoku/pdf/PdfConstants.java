// $Id: PdfConstants.java,v 1.2 2009/12/21 22:21:27 jdufner Exp $

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

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-07
 * @version $Revision: 1.2 $
 */
public final class PdfConstants {

  /**
   * Farbe Linien: Schwarz
   */
  public static final int[] RAHMEN_FARBE = new int[] { 0x00, 0x00, 0x00 };
  /**
   * Keine Linie
   */
  public static final float RAHMEN_KEIN = 0f;
  /**
   * Dicke Linie
   */
  public static final float RAHMEN_DICK = 1.0f;
  /**
   * D�nner Linie
   */
  public static final float RAHMEN_DUENN = 0.5f;
  /**
   * Farbe Hintergrund: Hellgrau
   */
  public static final int[] HINTERGRUND_FARBE = new int[] { 0xCC, 0xCC, 0xCC };

}

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
package de.jdufner.sudoku.common.misc;

import org.apache.log4j.Logger;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 26.12.2009
 * @version $Revision$
 */
public final class Log { // NOPMD by J�rgen on 27.12.09 22:16

  public static final Logger APPROACH = Logger.getLogger("approach");
  public static final Logger SUDOKU = Logger.getLogger("sudoku");

  private static StringBuilder sb = null;
  private static final String NEWLINE = System.getProperty("line.separator");

  public static void log(final String message) {
    APPROACH.info(message);
    if (sb != null) {
      sb.append(message).append(NEWLINE);
    }
  }

  public static void startRecording() {
    sb = new StringBuilder();
  }

  public static String getMessagesAndStopRecording() {
    if (sb != null) {
      final String msg = sb.toString();
      sb = null;
      return msg;
    }
    return "";
  }

}

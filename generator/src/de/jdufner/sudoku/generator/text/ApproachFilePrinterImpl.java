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
package de.jdufner.sudoku.generator.text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-01-11
 * @version $Revision$
 */
public final class ApproachFilePrinterImpl implements ApproachFilePrinter {

  private static final Logger LOG = Logger.getLogger(ApproachFilePrinterImpl.class);
  // TODO In Konfiguration auslagern
  private static final String DIR = "C:\\tmp";

  private PrintWriter pw;

  @Override
  public void closeAndCompressFile() {
    if (pw != null) {
      //    pw.flush();
      pw.close();
    }
  }

  @Override
  public void openFile(final int sudokuId) throws IOException {
    final File file = new File(DIR, sudokuId + ".txt.gz");
    final FileOutputStream fos = new FileOutputStream(file);
    final GZIPOutputStream gos = new GZIPOutputStream(fos);
    pw = new PrintWriter(gos);
  }

  @Override
  public void print(final String message) {
    if (pw != null) {
      pw.print(message);
    }
  }

  @Override
  public void println(final String message) {
    if (pw != null) {
      pw.println(message);
    }
  }

}

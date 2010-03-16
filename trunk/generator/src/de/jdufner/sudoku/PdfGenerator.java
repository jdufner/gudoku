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
package de.jdufner.sudoku;

import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.generator.service.PdfGeneratorConfiguration;
import de.jdufner.sudoku.generator.service.PdfGeneratorService;

/**
 * Applikation zum Starten des PdfGenerators. Die Klasse liest die Sudokus aus der Datenbank und erzeugt ein PDF mit den
 * R�tsel und den L�sungen. Nebenbei werden auch noch die L�sungswege in Textdateien gespeichert und komprimiert.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-02-26
 * @version $Revision$
 */
public final class PdfGenerator extends AbstractMainClass {

  /**
   * Instanziert und startet den PdfGenerator.
   * 
   * @param args
   *          Es werden keine Parameter ausgewertet.
   */
  public static void main(String[] args) throws Exception {
    PdfGenerator pdfGenerator = new PdfGenerator();
    pdfGenerator.start();
  }

  /**
   * Implementiert die Logik und ruft den Service und Laden und Erzeugen des PDFs auf.
   */
  protected void run() throws Exception {
    PdfGeneratorService pdfGeneratorService = (PdfGeneratorService) GeneratorServiceFactory.INSTANCE
        .getBean(PdfGeneratorService.class);
    pdfGeneratorService.generate(new PdfGeneratorConfiguration.Builder().numberPerLevel(Level.LEICHT, 12)
        .numberPerLevel(Level.MITTEL, 12).numberPerLevel(Level.SCHWER, 12).build());
  }
}

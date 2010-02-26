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
package de.jdufner.sudoku.generator.service;

import java.io.IOException;

import junit.framework.TestCase;

import com.lowagie.text.DocumentException;

import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.generator.service.PdfGeneratorService;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-20
 * @version $Revision$
 */
public final class PdfGeneratorTest extends TestCase {

  private PdfGeneratorService pdfGenerator;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    pdfGenerator = (PdfGeneratorService) GeneratorServiceFactory.getInstance().getBean(PdfGeneratorService.class);
  }

  // TODO Das ist kein Test
  public void testGenerate() throws DocumentException, IOException {
    //pdfGenerator.generate();
  }

}

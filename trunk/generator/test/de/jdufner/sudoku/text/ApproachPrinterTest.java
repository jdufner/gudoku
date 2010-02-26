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
package de.jdufner.sudoku.text;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.generator.text.ApproachPrinter;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 22.12.2009
 * @version $Revision$
 */
public final class ApproachPrinterTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(ApproachPrinterTest.class);

  private ApproachPrinter approachPrinter;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    approachPrinter = (ApproachPrinter) GeneratorServiceFactory.getInstance().getBean(ApproachPrinter.class);
  }

  public void testasommerf() throws IOException {
    int[] ids = { 48383 };
    for (int id : ids) {
      LOG.info("#");
      LOG.info("# " + id);
      LOG.info("#");
      approachPrinter.print(id);
    }
  }

}

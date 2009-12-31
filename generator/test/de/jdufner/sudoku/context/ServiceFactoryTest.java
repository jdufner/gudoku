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
package de.jdufner.sudoku.context;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.builder.AbstractBuilder;
import de.jdufner.sudoku.builder.Builder;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class ServiceFactoryTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(ServiceFactoryTest.class);

  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testGetStrategySolver() throws Exception {
    SolverServiceFactory.getInstance().getStrategySolver();
  }

  public void testGetStrategySolverWithBacktracking() throws Exception {
    SolverServiceFactory.getInstance().getStrategySolverWithBacktracking();
  }

  public void testInstantiateBacktracktingSolver() throws Exception {
    SolverServiceFactory.getInstance().getBacktrackingSolver();
  }

  public void testGetLiteralEleminationBuilder() throws Exception {
    Builder builder = GeneratorServiceFactory.getInstance().getLiteralEleminationBuilder();
    assertNotNull(builder);
    AbstractBuilder abstractBuilder = (AbstractBuilder) builder;
    assertNotNull(abstractBuilder.getRandomData());
    assertNotNull(abstractBuilder.getStrategySolverWithBacktracking());
  }

  public void testGetRandomEleminationBuilder() throws Exception {
    Builder builder = GeneratorServiceFactory.getInstance().getRandomEleminationBuilder();
    assertNotNull(builder);
    AbstractBuilder abstractBuilder = (AbstractBuilder) builder;
    assertNotNull(abstractBuilder.getRandomData());
    assertNotNull(abstractBuilder.getStrategySolverWithBacktracking());
  }

  public void testGetSymetricRandomEleminationBuilder() throws Exception {
    Builder builder = GeneratorServiceFactory.getInstance().getSymetricRandomEleminationBuilder();
    assertNotNull(builder);
    AbstractBuilder abstractBuilder = (AbstractBuilder) builder;
    assertNotNull(abstractBuilder.getRandomData());
    assertNotNull(abstractBuilder.getStrategySolverWithBacktracking());
  }

  public void testGetSudokuDao() throws Exception {
    GeneratorServiceFactory.getInstance().getSudokuDao();
  }

  public void testGetPdfStyle() {
    Object obj = GeneratorServiceFactory.getInstance().getPdfStyle();
    LOG.debug(obj);
  }
}

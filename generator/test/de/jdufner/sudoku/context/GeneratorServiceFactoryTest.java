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
import de.jdufner.sudoku.builder.LiteralEleminationBuilder;
import de.jdufner.sudoku.builder.RandomEleminationBuilder;
import de.jdufner.sudoku.builder.SymetricRandomEleminationBuilder;
import de.jdufner.sudoku.dao.SudokuDao;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class GeneratorServiceFactoryTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(GeneratorServiceFactoryTest.class);

  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  @Override
  public void tearDown() throws Exception {
    super.tearDown();
  }

  public void testGetLiteralEleminationBuilder() throws Exception {
    Builder builder = (LiteralEleminationBuilder) GeneratorServiceFactory.INSTANCE
        .getBean(LiteralEleminationBuilder.class);
    assertNotNull(builder);
    AbstractBuilder abstractBuilder = (AbstractBuilder) builder;
    assertNotNull(abstractBuilder.getRandomData());
    assertNotNull(abstractBuilder.getStrategySolverWithBacktracking());
  }

  public void testGetRandomEleminationBuilder() throws Exception {
    Builder builder = (RandomEleminationBuilder) GeneratorServiceFactory.INSTANCE
        .getBean(RandomEleminationBuilder.class);
    assertNotNull(builder);
    AbstractBuilder abstractBuilder = (AbstractBuilder) builder;
    assertNotNull(abstractBuilder.getRandomData());
    assertNotNull(abstractBuilder.getStrategySolverWithBacktracking());
  }

  public void testGetSymetricRandomEleminationBuilder() throws Exception {
    Builder builder = (SymetricRandomEleminationBuilder) GeneratorServiceFactory.INSTANCE
        .getBean(SymetricRandomEleminationBuilder.class);
    assertNotNull(builder);
    AbstractBuilder abstractBuilder = (AbstractBuilder) builder;
    assertNotNull(abstractBuilder.getRandomData());
    assertNotNull(abstractBuilder.getStrategySolverWithBacktracking());
  }

  public void testGetSudokuDao() throws Exception {
    SudokuDao sudokuDao = (SudokuDao) GeneratorServiceFactory.INSTANCE.getBean(SudokuDao.class);
    assertTrue(sudokuDao instanceof SudokuDao);
  }

  public void testGetPdfStyle() {
    Object obj = GeneratorServiceFactory.INSTANCE.getPdfStyle();
    LOG.debug(obj);
  }
}

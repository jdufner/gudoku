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
package de.jdufner.sudoku.builder;

import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class LiteralEleminationBuilderTest extends TestCase {
  private static final Logger LOG = Logger.getLogger(LiteralEleminationBuilderTest.class);

  private Builder builder;
  private Sudoku sudoku;

  @Override
  public void setUp() throws Exception {
    builder = (LiteralEleminationBuilder) GeneratorServiceFactory.getInstance()
        .getBean(LiteralEleminationBuilder.class);
    builder.setSize(SudokuSize.DEFAULT);
  }

  public void testBuild() throws Exception {
    sudoku = builder.build();
    LOG.debug("Pr�fe G�ltigkeit:");
    assertTrue(sudoku.isValid());
    LOG.debug("Pr�fe Eindeutigkeit:");
    assertTrue(SolverServiceFactory.getInstance().getStrategySolverWithBacktracking().isUnique(sudoku));
    LOG.debug(sudoku);
  }

  public void testBuildSudokus() {
    Map<Level, Solution> map = builder.buildSudokus();
  }

}

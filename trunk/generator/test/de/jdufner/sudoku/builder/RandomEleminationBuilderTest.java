// $Id: RandomEleminationBuilderTest.java,v 1.9 2009/12/11 20:49:41 jdufner Exp $

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
package de.jdufner.sudoku.builder;

import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.9 $
 */
public final class RandomEleminationBuilderTest extends TestCase {
  private static final Logger LOG = Logger.getLogger(RandomEleminationBuilderTest.class);

  private Builder builder;

  @Override
  public void setUp() {
    builder = GeneratorServiceFactory.getInstance().getRandomEleminationBuilder();
    builder.setSize(SudokuSize.DEFAULT);
  }

  public void testBuild() throws Exception {
    Sudoku sudoku = builder.build();
    assertTrue(sudoku.isValid());
    assertFalse(sudoku.isSolved());
    assertTrue(SolverServiceFactory.getInstance().getStrategySolverWithBacktracking().isUnique(sudoku));
    assertTrue(SolverServiceFactory.getInstance().getStrategySolverWithBacktracking().isSolvable(sudoku));
  }

  public void testBuildSudokus() throws Exception {
    Map<Level, Sudoku> map = builder.buildSudokus();
    assertTrue(map.size() >= 1);
    LOG.debug(map.size() + " Sudokus gefunden.");
    for (Level level : map.keySet()) {
      if (level.equals(Level.SEHR_SCHWER)) {
        Sudoku result = SolverServiceFactory.getInstance().getStrategySolverWithBacktracking().solve(
            map.get(level));
        assertTrue(result.isSolved());
        assertTrue(result.isSolvedByCheckSum());
        assertTrue(result.isValid());
        assertNotNull(map.get(level).getLevel());
        assertTrue(Level.UNBEKANNT.compareTo(map.get(level).getLevel()) < 0);
      } else {
        Sudoku result = SolverServiceFactory.getInstance().getStrategySolverWithBacktracking().solve(
            map.get(level));
        assertTrue(result.isSolved());
        assertTrue(result.isSolvedByCheckSum());
        assertTrue(result.isValid());
        assertNotNull(map.get(level).getLevel());
        assertTrue(Level.UNBEKANNT.compareTo(map.get(level).getLevel()) < 0);
      }
    }
  }

}

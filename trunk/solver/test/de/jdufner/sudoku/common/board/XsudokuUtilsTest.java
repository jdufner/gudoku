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
package de.jdufner.sudoku.common.board;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.factory.SudokuFactory;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-01-15
 * @version $Revision$
 */
public final class XsudokuUtilsTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(XsudokuUtilsTest.class);

  public void testBuildMainDiagonal() {
    Sudoku sudoku = SudokuFactory
        .buildSudoku("1.......2.3.....4...5...6.....7.8.......9.......1.2.....3...4...5.....6.7.......8");
    MainDiagonal mainDiagonal = XsudokuUtils.buildMainDiagonal(sudoku);
    LOG.debug(mainDiagonal);
    assertTrue(mainDiagonal.isValid());
  }

  public void testBuildSecondaryDiagonal() {
    Sudoku sudoku = SudokuFactory
        .buildSudoku("1.......2.3.....4...5...6.....7.8.......9.......1.2.....3...4...5.....6.7.......8");
    SecondaryDiagonal secondaryDiagonal = XsudokuUtils.buildSecondaryDiagonal(sudoku);
    LOG.debug(secondaryDiagonal);
    assertTrue(secondaryDiagonal.isValid());
  }

}

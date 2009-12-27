// $Id: Sudoku_fuer_Profis_Test.java,v 1.7 2009/11/26 21:54:33 jdufner Exp $

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
package de.jdufner.sudoku.solver.strategy.profis;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.Solver;
import de.jdufner.sudoku.solver.service.StrategySolver;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.7 $
 */
public final class Sudoku_fuer_Profis_Test extends TestCase {
  private static final Logger LOG = Logger.getLogger(Sudoku_fuer_Profis_Test.class);

  public Sudoku_fuer_Profis_Test(String name) {
    super(name);
  }

  public void testTeuflischSchwer51() {
    final String mySudoku = "79..523..34.....9....7....8.2.1957.....3....2..6..81....4..6..3.3........61..32.."; //
    Sudoku sudoku = SudokuFactory.buildSudoku(mySudoku);
    Solver solver = new StrategySolver();
    Sudoku result = solver.solve(sudoku);
    LOG.debug(result.toString());
    assertFalse(sudoku.isSolved());
  }

}

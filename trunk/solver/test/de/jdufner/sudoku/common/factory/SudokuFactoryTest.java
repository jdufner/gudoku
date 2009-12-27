// $Id: SudokuFactoryTest.java,v 1.1 2009/11/22 22:36:59 jdufner Exp $

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
package de.jdufner.sudoku.common.factory;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.1 $
 */
public final class SudokuFactoryTest extends TestCase {
  private static final Logger LOG = Logger.getLogger(SudokuFactoryTest.class);

  public void testBuildFilledNeun() {
    Sudoku sudoku = SudokuFactory.buildFilled(SudokuSize.NEUN);
    LOG.debug(sudoku.toLongString());
    LOG.debug(sudoku.toShortString());
    assertTrue(sudoku.isValid());
    assertTrue(sudoku.isSolvedByCheckSum());
    assertEquals(1, sudoku.getCell(0, 0).getValue().getValue());
    assertEquals(4, sudoku.getCell(0, 3).getValue().getValue());
    assertEquals(7, sudoku.getCell(0, 6).getValue().getValue());
    assertEquals(9, sudoku.getCell(3, 0).getValue().getValue());
    assertEquals(3, sudoku.getCell(3, 3).getValue().getValue());
    assertEquals(6, sudoku.getCell(3, 6).getValue().getValue());
    assertEquals(8, sudoku.getCell(6, 0).getValue().getValue());
    assertEquals(2, sudoku.getCell(6, 3).getValue().getValue());
    assertEquals(5, sudoku.getCell(6, 6).getValue().getValue());
  }

  public void testBuildFilledZehn() {
    Sudoku sudoku = SudokuFactory.buildFilled(SudokuSize.ZEHN);
    LOG.debug(sudoku.toLongString());
    LOG.debug(sudoku.toShortString());
    assertTrue(sudoku.isValid());
    assertTrue(sudoku.isSolvedByCheckSum());
    assertEquals(1, sudoku.getCell(0, 0).getValue().getValue());
    assertEquals(3, sudoku.getCell(0, 2).getValue().getValue());
    assertEquals(5, sudoku.getCell(0, 4).getValue().getValue());
    assertEquals(7, sudoku.getCell(0, 6).getValue().getValue());
    assertEquals(9, sudoku.getCell(0, 8).getValue().getValue());
    assertEquals(10, sudoku.getCell(5, 0).getValue().getValue());
    assertEquals(2, sudoku.getCell(5, 2).getValue().getValue());
    assertEquals(4, sudoku.getCell(5, 4).getValue().getValue());
    assertEquals(6, sudoku.getCell(5, 6).getValue().getValue());
    assertEquals(8, sudoku.getCell(5, 8).getValue().getValue());
  }

}


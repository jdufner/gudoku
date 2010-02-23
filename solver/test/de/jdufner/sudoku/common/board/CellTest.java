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
package de.jdufner.sudoku.common.board;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class CellTest extends TestCase {
  // private static final Logger log = Logger.getLogger(CellTest.class);

  private Sudoku sudoku9x9;
  private Sudoku sudoku10x10;
  private Cell cell;

  @Override
  public void setUp() {
    sudoku9x9 = SudokuFactory.buildFilled(SudokuSize.NEUN);
    sudoku10x10 = SudokuFactory.buildFilled(SudokuSize.ZEHN);
  }

  public void testCell0() {
    cell = sudoku9x9.getCell(0);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
    cell = sudoku10x10.getCell(0);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell1() {
    cell = sudoku9x9.getCell(1);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
    cell = sudoku10x10.getCell(1);
    assertTrue(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell2() {
    cell = sudoku9x9.getCell(2);
    assertTrue(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell3() {
    cell = sudoku9x9.getCell(3);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell4() {
    cell = sudoku9x9.getCell(4);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell5() {
    cell = sudoku9x9.getCell(5);
    assertTrue(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell6() {
    cell = sudoku9x9.getCell(6);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell7() {
    cell = sudoku9x9.getCell(7);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell8() {
    cell = sudoku9x9.getCell(8);
    assertTrue(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell9() {
    cell = sudoku9x9.getCell(9);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell10() {
    cell = sudoku9x9.getCell(10);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell11() {
    cell = sudoku9x9.getCell(11);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell12() {
    cell = sudoku9x9.getCell(12);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell13() {
    cell = sudoku9x9.getCell(13);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell14() {
    cell = sudoku9x9.getCell(14);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell15() {
    cell = sudoku9x9.getCell(15);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell16() {
    cell = sudoku9x9.getCell(16);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell17() {
    cell = sudoku9x9.getCell(17);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell18() {
    cell = sudoku9x9.getCell(18);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell19() {
    cell = sudoku9x9.getCell(19);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell20() {
    cell = sudoku9x9.getCell(20);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell21() {
    cell = sudoku9x9.getCell(21);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell22() {
    cell = sudoku9x9.getCell(22);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell23() {
    cell = sudoku9x9.getCell(23);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell24() {
    cell = sudoku9x9.getCell(24);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell25() {
    cell = sudoku9x9.getCell(25);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell26() {
    cell = sudoku9x9.getCell(26);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell27() {
    cell = sudoku9x9.getCell(27);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell28() {
    cell = sudoku9x9.getCell(28);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell29() {
    cell = sudoku9x9.getCell(29);
    assertTrue(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell30() {
    cell = sudoku9x9.getCell(30);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell31() {
    cell = sudoku9x9.getCell(31);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell32() {
    cell = sudoku9x9.getCell(32);
    assertTrue(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell33() {
    cell = sudoku9x9.getCell(33);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell34() {
    cell = sudoku9x9.getCell(34);
    assertTrue(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell35() {
    cell = sudoku9x9.getCell(35);
    assertTrue(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell36() {
    cell = sudoku9x9.getCell(36);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell37() {
    cell = sudoku9x9.getCell(37);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell38() {
    cell = sudoku9x9.getCell(38);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell39() {
    cell = sudoku9x9.getCell(39);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell40() {
    cell = sudoku9x9.getCell(40);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell41() {
    cell = sudoku9x9.getCell(41);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell42() {
    cell = sudoku9x9.getCell(42);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell43() {
    cell = sudoku9x9.getCell(43);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell44() {
    cell = sudoku9x9.getCell(44);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertFalse(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell45() {
    cell = sudoku9x9.getCell(45);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell46() {
    cell = sudoku9x9.getCell(46);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell47() {
    cell = sudoku9x9.getCell(47);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell48() {
    cell = sudoku9x9.getCell(48);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell49() {
    cell = sudoku9x9.getCell(49);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell50() {
    cell = sudoku9x9.getCell(50);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell51() {
    cell = sudoku9x9.getCell(51);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertTrue(cell.isLeft());
  }

  public void testCell52() {
    cell = sudoku9x9.getCell(52);
    assertFalse(cell.isTop());
    assertFalse(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

  public void testCell53() {
    cell = sudoku9x9.getCell(53);
    assertFalse(cell.isTop());
    assertTrue(cell.isRight());
    assertTrue(cell.isBottom());
    assertFalse(cell.isLeft());
  }

}

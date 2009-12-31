// $Id: SetValueCommandTest.java,v 1.7 2009/12/14 20:47:49 jdufner Exp $

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
package de.jdufner.sudoku.commands;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;

public class SetValueCommandTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(SetValueCommandTest.class);

  private Sudoku sudoku = SudokuFactory.buildSudoku(Examples.ING_DIBA);

  protected void setUp() throws Exception {
    LOG.debug(sudoku);
  }

  public void testSetValueCommand() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Command scc1 = CommandFactory.buildSetValueCommand(this.getClass().getSimpleName(), 0, 2, Literal.getInstance(2));
    LOG.debug(scc1.getFrozenString());
    assertNull(scc1.getFrozenString());
    scc1.execute(sudoku);
    assertTrue(scc1.isSuccessfully());
    LOG.debug(scc1.getFrozenString());
    LOG.debug(cell);
    assertEquals("SetValueCommandTest: Setze Wert 2 in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", scc1
        .getFrozenString());

    assertTrue(cell.isFixed());
    assertEquals(0, cell.getCandidates().size());

    Command scc2 = CommandFactory.buildSetValueCommand(this.getClass().getSimpleName(), cell, Literal.getInstance(6));
    LOG.debug(scc2.getFrozenString());
    assertNull(scc2.getFrozenString());
    scc2.execute(sudoku);
    assertFalse(scc2.isSuccessfully());
    LOG.debug(scc2.getFrozenString());
    LOG.debug(cell);
    assertEquals("SetValueCommandTest: Setze Wert 6 in Zelle 2 (0, 2, 0) []", scc2.getFrozenString());

    assertTrue(cell.isFixed());
    assertEquals(0, cell.getCandidates().size());
  }

}
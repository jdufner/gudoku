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
package de.jdufner.sudoku.commands;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;

public final class UnsetCandidateCommandTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(UnsetCandidateCommandTest.class);

  private Sudoku sudoku = SudokuFactory.buildSudoku(Examples.ING_DIBA);

  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testUnsetCandidateCommand() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Command ucc = CommandFactory.buildUnsetCandidateCommand(this.getClass().getSimpleName(), 0, 2, Literal
        .getInstance(1));
    LOG.debug(ucc.getFrozenString());
    assertEquals(null, ucc.getFrozenString());
    ucc.execute(sudoku);
    assertTrue(ucc.isSuccessfully());
    LOG.debug(ucc.getFrozenString());
    assertEquals("UnsetCandidateCommandTest: Entferne Kandidat 1 aus Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]",
        ucc.getFrozenString());

    assertFalse(cell.isFixed());
    assertFalse(cell.getCandidates().contains(Literal.getInstance(1)));
    assertEquals(8, cell.getCandidates().size());
  }

}
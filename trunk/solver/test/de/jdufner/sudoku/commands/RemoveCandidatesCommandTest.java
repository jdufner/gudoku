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
package de.jdufner.sudoku.commands;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.common.misc.Examples;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;
import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since
 * @version $Revision$
 */
public final class RemoveCandidatesCommandTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(RemoveCandidatesCommandTest.class);

  private Sudoku sudoku = SudokuFactory.buildSudoku(Examples.ING_DIBA);

  public void setUp() {
    LOG.debug(sudoku);
  }

  public void testRemoveCandidatesCommand() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Literal l = sudoku.getCell(0, 0).getValue(); // 9
    Command rcc1 = CommandFactory.buildRemoveCandidatesCommand(null, cell, l);
    assertNull(rcc1.getFrozenString());
    rcc1.execute(sudoku);
    assertTrue(rcc1.isSuccessfully());
    assertTrue(cell.isValid());
    assertTrue(rcc1.reversible());
    LOG.debug(rcc1.getFrozenString());
    assertEquals("null: Entferne Kandidaten [9] in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", rcc1
        .getFrozenString());

    assertEquals(8, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(1)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(3)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(4)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(5)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(7)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(8)));
    assertFalse(cell.isFixed());

    // Kandidaten aus der Zeile
    Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(sudoku.getCell(0, 1).getValue()); // 5
    candidates1.add(sudoku.getCell(0, 6).getValue()); // 1
    candidates1.add(sudoku.getCell(0, 7).getValue()); // 8
    Command rcc2 = CommandFactory.buildRemoveCandidatesCommand(null, cell, candidates1);
    assertNull(rcc2.getFrozenString());
    rcc2.execute(sudoku);
    assertTrue(rcc2.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals("null: Entferne Kandidaten [5, 1, 8] in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8]", rcc2
        .getFrozenString());

    assertEquals(5, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(3)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(4)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(7)));
    assertFalse(cell.isFixed());

    // Kandidaten aus der Spalte
    Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(sudoku.getCell(5, 2).getValue()); // 4 
    candidates2.add(sudoku.getCell(6, 2).getValue()); // 5
    candidates2.add(sudoku.getCell(8, 2).getValue()); // 3
    Command rcc3 = CommandFactory.buildRemoveCandidatesCommand(null, cell, candidates2);
    assertNull(rcc3.getFrozenString());
    rcc3.execute(sudoku);
    assertTrue(rcc3.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals("null: Entferne Kandidaten [4, 5, 3] in Zelle 0 (0, 2, 0) [2, 3, 4, 6, 7]", rcc3.getFrozenString());

    assertEquals(3, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(7)));
    assertFalse(cell.isFixed());

    // Kandidaten aus dem Block
    Candidates<Literal> candidates3 = new Candidates<Literal>();
    candidates3.add(sudoku.getCell(0, 0).getValue()); // 9
    candidates3.add(sudoku.getCell(0, 1).getValue()); // 5
    candidates3.add(sudoku.getCell(1, 0).getValue()); // 8
    candidates3.add(sudoku.getCell(2, 0).getValue()); // 4
    candidates3.add(sudoku.getCell(2, 1).getValue()); // 7

    Command rcc4 = CommandFactory.buildRemoveCandidatesCommand(null, cell, candidates3);
    assertNull(rcc4.getFrozenString());
    rcc4.execute(sudoku);
    assertTrue(rcc4.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals("null: Entferne Kandidaten [9, 5, 8, 4, 7] in Zelle 0 (0, 2, 0) [2, 6, 7]", rcc4.getFrozenString());

    assertEquals(2, cell.getCandidates().size());
    assertTrue(cell.getCandidates().contains(Literal.getInstance(2)));
    assertTrue(cell.getCandidates().contains(Literal.getInstance(6)));
    assertFalse(cell.isFixed());

    // Entferne einen bereits entfernen Kandidat
    Command rcc6 = CommandFactory.buildRemoveCandidatesCommand(null, cell, Literal.getInstance(1));
    assertNull(rcc6.getFrozenString());
    rcc6.execute(sudoku);
    assertFalse(rcc6.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals("null: Entferne Kandidaten [1] in Zelle 0 (0, 2, 0) [2, 6]", rcc6.getFrozenString());

    // Entferne mehrere bereits entfernen Kandidaten
    Candidates<Literal> candidates4 = new Candidates<Literal>();
    candidates4.add(Literal.getInstance(1));
    candidates4.add(Literal.getInstance(3));
    Command rcc7 = CommandFactory.buildRemoveCandidatesCommand(null, cell, candidates4);
    assertNull(rcc7.getFrozenString());
    rcc7.execute(sudoku);
    assertFalse(rcc7.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals("null: Entferne Kandidaten [1, 3] in Zelle 0 (0, 2, 0) [2, 6]", rcc7.getFrozenString());

    // Entferne einen weiteren beliebigen Kandidaten und erwarte automatisches Setzen des verbleibenden Kandidaten
    Command rcc5 = CommandFactory.buildRemoveCandidatesCommand(null, cell, Literal.getInstance(2));
    assertNull(rcc5.getFrozenString());
    rcc5.execute(sudoku);
    assertTrue(rcc5.isSuccessfully());
    assertTrue(cell.isValid());
    assertEquals("null: Entferne Kandidaten [2] in Zelle 0 (0, 2, 0) [2, 6]", rcc5.getFrozenString());
    assertEquals(0, cell.getCandidates().size());
    assertTrue(cell.isFixed());
  }

  public void testRemoveAll() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(Literal.getInstance(1));
    candidates1.add(Literal.getInstance(2));
    candidates1.add(Literal.getInstance(3));
    candidates1.add(Literal.getInstance(4));
    candidates1.add(Literal.getInstance(5));
    candidates1.add(Literal.getInstance(6));
    candidates1.add(Literal.getInstance(7));
    candidates1.add(Literal.getInstance(8));
    candidates1.add(Literal.getInstance(9));

    Command rcc1 = CommandFactory.buildRemoveCandidatesCommand(null, cell, candidates1);
    assertNull(rcc1.getFrozenString());
    rcc1.execute(sudoku);
    assertTrue(rcc1.isSuccessfully());
    assertFalse(cell.isValid());
    assertTrue(rcc1.reversible());
    LOG.debug(rcc1.getFrozenString());
    assertEquals(
        "null: Entferne Kandidaten [1, 2, 3, 4, 5, 6, 7, 8, 9] in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", rcc1
            .getFrozenString());
    assertEquals(0, cell.getCandidates().size());

    rcc1.unexecute(sudoku);
    assertTrue(cell.isValid());
    assertEquals(9, cell.getCandidates().size());
  }

  public void testRemoveNone() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Candidates<Literal> candidates1 = new Candidates<Literal>();

    Command rcc1 = CommandFactory.buildRemoveCandidatesCommand(null, cell, candidates1);
    assertNull(rcc1.getFrozenString());
    rcc1.execute(sudoku);
    assertFalse(rcc1.isSuccessfully());
    assertTrue(cell.isValid());
    assertTrue(rcc1.reversible());
    LOG.debug(rcc1.getFrozenString());
    assertEquals("null: Entferne Kandidaten [] in Zelle 0 (0, 2, 0) [1, 2, 3, 4, 5, 6, 7, 8, 9]", rcc1
        .getFrozenString());
    assertEquals(9, cell.getCandidates().size());

    rcc1.unexecute(sudoku);
    assertTrue(cell.isValid());
    assertEquals(9, cell.getCandidates().size());
  }

  public void testRemoveDuplicate() {
    Cell cell = sudoku.getCell(0, 2);

    assertEquals(9, cell.getCandidates().size());

    Command rcc1 = CommandFactory.buildRemoveCandidatesCommand(null, cell, Literal.getInstance(1));
    rcc1.execute(sudoku);
    assertTrue(rcc1.isSuccessfully());

    Command rcc2 = CommandFactory.buildRemoveCandidatesCommand(null, cell, Literal.getInstance(1));
    rcc2.execute(sudoku);
    assertFalse(rcc2.isSuccessfully());

    Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(Literal.getInstance(8));
    candidates1.add(Literal.getInstance(9));

    Command rcc3 = CommandFactory.buildRemoveCandidatesCommand(null, cell, candidates1);
    rcc3.execute(sudoku);
    assertTrue(rcc3.isSuccessfully());

    Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(Literal.getInstance(8));
    candidates2.add(Literal.getInstance(9));

    Command rcc4 = CommandFactory.buildRemoveCandidatesCommand(null, cell, candidates2);
    rcc4.execute(sudoku);
    assertFalse(rcc4.isSuccessfully());
  }

  public void testEquals() {
    final Candidates<Literal> candidates1 = new Candidates<Literal>();
    candidates1.add(Literal.getInstance(1));
    candidates1.add(Literal.getInstance(2));
    Command rcc1 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 6, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates1);
    final Candidates<Literal> candidates2 = new Candidates<Literal>();
    candidates2.add(Literal.getInstance(2));
    candidates2.add(Literal.getInstance(1));
    Command rcc2 = CommandFactory.buildRemoveCandidatesCommand(StrategyNameEnum.SIMPLE, new Cell(7, 6, Literal.EMPTY,
        SudokuSize.DEFAULT), candidates1);
    assertEquals("Sollen gleich sein.", rcc1, rcc2);
    assertNotSame("Sind nicht dasselbe.", rcc1, rcc2);
  }

}

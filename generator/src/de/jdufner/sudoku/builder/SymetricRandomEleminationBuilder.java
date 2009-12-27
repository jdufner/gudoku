// $Id: SymetricRandomEleminationBuilder.java,v 1.8 2009/12/20 19:34:49 jdufner Exp $

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

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.8 $
 */
public final class SymetricRandomEleminationBuilder extends EleminationBuilder {
  private static final Logger log = Logger.getLogger(SymetricRandomEleminationBuilder.class);

  public SymetricRandomEleminationBuilder() {
    super();
  }

  public SymetricRandomEleminationBuilder(Sudoku sudoku, int stackSize) {
    super(sudoku, stackSize);
  }

  public Cell symetricCell(Cell cell) {
    return sudoku.getCell(//
        sudoku.getSize().getUnitSize() - 1 - cell.getRowIndex(), //
        sudoku.getSize().getUnitSize() - 1 - cell.getColumnIndex());
  }

  @Override
  protected void executeBuilder() {
    int cellCounter = 0;
    int eleminatedCellCounter = 0;
    for (Cell cell : buildRandomPermutationOfFixedOfFirstHalf()) {
      Cell cellCandidate1 = (Cell) cell.clone();
      Cell cellCandidate2 = (Cell) symetricCell(cell).clone();
      if (log.isDebugEnabled()) {
        log.debug("Leere Zellen " + cell + " und " + symetricCell(cell));
      }
      sudoku.getCell(cellCandidate1.getRowIndex(), cellCandidate1.getColumnIndex()).reset();
      cellCounter++;
      if (!cellCandidate1.equals(cellCandidate2)) {
        sudoku.getCell(cellCandidate2.getRowIndex(), cellCandidate2.getColumnIndex()).reset();
        cellCounter++;
      }
      Solution solution = strategySolverWithBacktracking.getSolution(sudoku);
      if (solution.isUnique()) {
        solution.getQuest().setLevel(solution.getLevel());
        level2SudokuMap.put(solution.getLevel(), solution.getQuest());
        eleminatedCellCounter++;
        if (!cellCandidate1.equals(cellCandidate2)) {
          eleminatedCellCounter++;
        }
        if (log.isInfoEnabled()) {
          log.info("(" + eleminatedCellCounter + "/" + cellCounter + ") Zellen " + cellCandidate1 + " und "
              + cellCandidate2 + " erfolgreich geleert. Neues Sudoku: " + sudoku.toShortString());
        }
      } else {
        sudoku.getCell(cellCandidate1.getRowIndex(), cellCandidate1.getColumnIndex()).setValue(
            cellCandidate1.getValue());
        if (!cellCandidate1.equals(cellCandidate2)) {
          sudoku.getCell(cellCandidate2.getRowIndex(), cellCandidate2.getColumnIndex()).setValue(
              cellCandidate2.getValue());
        }
        if (log.isInfoEnabled()) {
          log.info("(" + eleminatedCellCounter + "/" + cellCounter + ") Zelle " + cellCandidate1 + " und "
              + cellCandidate2 + " konnten nicht geleert werden, setze deshalb zurück und mache weiter.");
        }
      }
    }
  }

}

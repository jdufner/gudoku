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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A Unit contains each {@link Literal} one time.
 * 
 * TODO Umbenennen in House http://www.sudopedia.org/wiki/House
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class Unit implements Comparable<Unit> {
  /**
   * Die Gr��e ({@link SudokuSize}) des Sudokus.
   */
  protected transient SudokuSize sudokuSize;
  /**
   * Die Zellen, die in dieser Einheit enthalten sind.
   */
  protected transient List<Cell> cells;
  /**
   * Der Index dieser Einheit, beginnend mit 0.
   */
  protected transient int index;

  /**
   * @param sudokuSize
   *          Das Gr��e des Sudokus.
   * @param index
   *          Der Index der Einheit.
   * @param cells
   *          Eine Liste aller Zellen, die in dieser Einheit enthalten sind.
   */
  public Unit(final SudokuSize sudokuSize, final int index, final List<Cell> cells) {
    this.sudokuSize = sudokuSize;
    this.index = index;
    this.cells = cells;
  }

  /**
   * @return Der Index der Einheit.
   */
  public int getIndex() {
    return index;
  }

  /**
   * @return Eine Liste aller Zellen, die in dieser Einheit enthalten sind.
   */
  public List<Cell> getCells() {
    return cells;
  }

  /**
   * @return The fixed cells in the unit.
   */
  public SortedSet<Cell> getFixed() {
    final SortedSet<Cell> fixedCells = new TreeSet<Cell>();
    for (Cell cell : cells) {
      if (cell.isFixed()) {
        fixedCells.add(cell);
      }
    }
    return fixedCells;
  }

  /**
   * @return The fixed cells in the unit.
   */
  public Collection<Literal> getFixedAsLiteral() {
    // TODO Kann hier die Objekterzeugung reduziert werden?
    final Collection<Literal> fixedCellLiterals = new ArrayList<Literal>();
    for (Cell cell : cells) {
      if (cell.isFixed()) {
        fixedCellLiterals.add(cell.getValue());
      }
    }
    return fixedCellLiterals;
  }

  /**
   * @return The open cells in the unit.
   */
  public SortedSet<Cell> getNonFixed() {
    final SortedSet<Cell> nonFixedCells = new TreeSet<Cell>();
    for (Cell cell : cells) {
      if (!cell.isFixed()) {
        nonFixedCells.add(cell);
      }
    }
    return nonFixedCells;
  }

  /**
   * @param numberCandidates
   *          Die Anzahl der Kandidaten in einer Zelle.
   * @return Die noch nicht gesetzen Zellen mit der �bergebenen Anzahl an Kandidaten.
   */
  public SortedSet<Cell> getNonFixed(final int numberCandidates) {
    final SortedSet<Cell> nonFixedCells = new TreeSet<Cell>();
    for (Cell cell : cells) {
      if (!cell.isFixed() && cell.getCandidates().size() == numberCandidates) {
        nonFixedCells.add(cell);
      }
    }
    return nonFixedCells;
  }

  /**
   * @return The candidates of the unit.
   */
  public SortedSet<Literal> getCandidates() {
    final SortedSet<Literal> candidates = new TreeSet<Literal>();
    for (Cell cell : cells) {
      if (!cell.isFixed()) {
        candidates.addAll(cell.getCandidates());
      }
    }
    return candidates;
  }

  /**
   * @return <code>true</code>, wenn alle Zellen ({@link Cell}) g�ltig sind und jede gesetzte h�chstens einmal gesetzt
   *         ist und jedes Literal mindestens einmal vorhanden ist, sonst <code>false</code>.
   * @see Cell#isValid()
   */
  public boolean isValid() {
    boolean candidates[] = new boolean[sudokuSize.getUnitSize()];
    final Map<Literal, Integer> fixedCounter = new HashMap<Literal, Integer>();
    for (Cell cell : cells) {
      if (!cell.isValid()) {
        return false;
      }
      if (cell.isFixed()) {
        if (fixedCounter.get(cell.getValue()) == null) {
          fixedCounter.put(cell.getValue(), 0);
        } else {
          // Einheit kann nicht g�ltig sein, wenn bereits eine besetzte Zelle
          // mit diesem Wert vorhanden ist.
          return false;
        }
        fixedCounter.put(cell.getValue(), fixedCounter.get(cell.getValue()).intValue() + 1);
        candidates[cell.getValue().getValue() - 1] = true;
      } else {
        for (Literal candidate : cell.getCandidates()) {
          candidates[candidate.getValue() - 1] = true;
        }
      }
    }
    boolean result = true;
    for (int k = 0; k < candidates.length; k++) {
      result = result && candidates[k];
    }
    return result;
  }

  /**
   * Sums the fixed cells and returns the comparison to the checksum.
   * 
   * @return <code>true</code> if the sum of the fixed cell is equals to the checksum, else <code>false</code>
   */
  public boolean isSolved() {
    int sum = 0;
    for (Cell cell : getFixed()) {
      sum += cell.getValue().getValue();
    }
    if (sum == sudokuSize.getCheckSum()) {
      return true;
    }
    return false;
  }

  /**
   * @param literal
   * @return A list of cells that part of the unit and that candidates contains the given literal.
   */
  public SortedSet<Cell> getCellsThooseCandidatesContains(final Literal literal) {
    final SortedSet<Cell> cells2 = new TreeSet<Cell>();
    for (Cell cell : getNonFixed()) {
      if (cell.getCandidates().contains(literal)) {
        cells2.add(cell);
      }
    }
    return cells2;
  }

  /**
   * @param literals
   * @return A list of cells that part of the unit and that candidates contains the given literals.
   */
  public SortedSet<Cell> getCellsThooseCandidatesContains(final Collection<Literal> literals) {
    final SortedSet<Cell> cells2 = new TreeSet<Cell>();
    for (Cell cell : getNonFixed()) {
      if (cell.getCandidates().containsAll(literals)) {
        cells2.add(cell);
      }
    }
    return cells2;
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof Unit) {
      final Unit that = (Unit) other;
      return this.index == that.index;
    }
    return false;
  }

  @Override
  public int hashCode() {
    return this.index;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Unit " + index);
    sb.append(" ").append(getCells());
    return sb.toString();
  }

  @Override
  public int compareTo(final Unit other) {
    return index - other.index;
  }

}

// $Id: Cell.java,v 1.7 2009/12/26 23:08:00 jdufner Exp $

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
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.misc.Log;

/**
 * A cell is a field at the sudoku board. It has either a fixed value or a list of at least two candidates. A cell has
 * two coordinates: row index, column index. Each cell has a unique pair of row and cell index. Out of the row and
 * column index it is possible to compute the block index.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.7 $
 */
public final class Cell implements Cloneable, Comparable<Cell> {

  private static final Logger LOG = Logger.getLogger(Cell.class);
  private static final Random RANDOM = new Random();

  private transient SudokuSize sudokuSize;
  private final transient int rowIndex;
  private final transient int columnIndex;
  private final transient int blockIndex;
  private transient Integer number;
  private Literal value;
  private Candidates<Literal> candidates = new Candidates<Literal>();

  public Cell(final int rowIndex, final int columnIndex, final Literal value, final SudokuSize sudokuSize) {
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.blockIndex = BlockUtils.getBlockIndexByRowIndexAndColumnIndex(rowIndex, columnIndex, sudokuSize);
    this.sudokuSize = sudokuSize;
    setValue(value, false);
  }

  public Cell(final Cell cell) {
    this.rowIndex = cell.rowIndex;
    this.columnIndex = cell.columnIndex;
    this.blockIndex = cell.blockIndex;
    this.sudokuSize = cell.sudokuSize;
    this.value = cell.value;
    this.candidates.addAll(cell.candidates);
  }

  public Literal getValue() {
    return value;
  }

  public Candidates<Literal> getCandidates() {
    return candidates;
  }

  public void setCandidates(final Candidates<Literal> candidates) {
    this.candidates = candidates;
  }

  public boolean removeCandidateAndSetIfOnlyOneRemains(final Literal value) {
    final Collection<Literal> values = new ArrayList<Literal>(1);
    values.add(value);
    return removeCandidatesAndSetIfOnlyOneRemains(values);
  }

  public boolean removeCandidatesAndSetIfOnlyOneRemains(final Collection<Literal> values) {
    if (values.size() <= 0) {
      return false;
    }
    final boolean candidatesRemoved = candidates.removeAll(values);
    if (candidatesRemoved) {
      Log.APPROACH.info("Entfernte " + values.size() + " Kandidaten " + values + " aus " + this);
      if (LOG.isInfoEnabled()) {
        LOG.info("Removed " + values.size() + " candidates " + values + " from " + this);
      }
    }
    if (candidates.size() == 1) {
      setValue(candidates.get(0));
      if (LOG.isInfoEnabled()) {
        LOG.info("Set " + this);
      }
      assert candidateAvailableOrFixed();
    }
    assert candidateAvailableOrFixed();
    return candidatesRemoved;
  }

  /**
   * @return <code>true</code> if either is fixed and no candidates available or is not fixed and at least on candidate
   *         available
   */
  private boolean candidateAvailableOrFixed() {
    return (candidates.size() > 0 && value.getValue() == 0) || (candidates.size() <= 0 && value.getValue() > 0);
  }

  /**
   * Setzt einen Wert in dieser Zelle und leert die Kandidaten.
   * 
   * @param value
   *          Der Wert der gesetzt wird.
   * @param isInitialized
   *          <code>true</code>, wenn die Zelle bereits initialisiert ist.
   */
  public void setValue(final Literal value, final boolean isInitialized) {
    assert value != null : "Value in Cell.setValue() is null.";
    assert value.getValue() >= 0 : value + " is lower or equals than 0.";
    assert value.getValue() <= sudokuSize.getUnitSize() : value + " is greater or equals than "
        + sudokuSize.getUnitSize();
    //assert isAllowed(value) : value + " is " + this + " not allowed!";
    if (value == null) {
      LOG.warn("Tried to set " + value + " (null)");
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("Set " + value + " to " + this);
    }
    this.value = value;
    //    sudoku.setNumberOfFixedDirty(true);
    if (value.equals(Literal.EMPTY)) {
      resetCandidates();
    } else {
      if (candidates == null) {
        candidates = new Candidates<Literal>(sudokuSize.getUnitSize());
      } else {
        candidates.clear();
      }
    }
    if (isInitialized) {
      Log.APPROACH.info("Setzte " + value + " in " + this);
    }
    assert isValid();
  }

  /**
   * Sets the value and clear candidates.
   * 
   * @param value
   */
  public void setValue(final Literal value) {
    setValue(value, true);
  }

  /**
   * @return <code>true</code> value is set, else <code>false</code>
   */
  public boolean isFixed() {
    return value.getValue() > 0;
  }

  /**
   * @return <code>true</code> if either is fixed and has no candidates or is not fixed and has candidates, else
   *         <code>false</code>
   */
  public boolean isValid() {
    return (isFixed() && getCandidates().size() <= 0) || (!isFixed() && getCandidates().size() > 0);
  }

  public int getBlockIndex() {
    return blockIndex;
  }

  public int getColumnIndex() {
    return columnIndex;
  }

  public int getRowIndex() {
    return rowIndex;
  }

  @Override
  public String toString() {
    return String.valueOf(value + " (" + rowIndex + ", " + columnIndex + ", " + blockIndex + ") " + candidates);
  }

  /**
   * Two cells are equal if the row and columns index are equal.
   */
  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof Cell) {
      final Cell that = (Cell) other;
      if (getNumber() == that.getNumber() && getValue().equals(that.getValue())) {
        return true;
      }
    }
    return false;
  }

  /**
   * The hash code is the number of the cell.
   */
  @Override
  public int hashCode() {
    return getNumber();
  }

  public int compareTo(final Cell other) {
    return getNumber() - other.getNumber();
  }

  @Override
  public Cell clone() { // NOPMD by J�rgen on 08.11.09 00:21
    return new Cell(this);
  }

  /**
   * Resets a cell to no fixed value and resets the candidates.
   */
  public void reset() {
    value = Literal.getInstance(0);
    resetCandidates();
  }

  /**
   * Resets the candidates by inserting all literals in the list of candidates.
   */
  public void resetCandidates() {
    if (value.getValue() <= 0 || value.getValue() > sudokuSize.getUnitSize()) {
      if (candidates == null) {
        candidates = new Candidates<Literal>(sudokuSize.getUnitSize());
      } else {
        candidates.clear();
      }
      for (int i = 0; i < sudokuSize.getUnitSize(); i++) {
        candidates.add(Literal.getInstance(i + 1));
      }
    }
  }

  /**
   * @return The number of a cell by rowwise counting, e.g.
   * 
   *         <pre>
   *  0 1  2  3  4  5  6  7  8
   *   9 10 11 12 13 14 15 16 17
   *   ...
   * </pre>
   */
  public int getNumber() {
    if (number == null) {
      number = Integer.valueOf(getRowIndex() * sudokuSize.getUnitSize() + getColumnIndex());
    }
    return number.intValue();
  }

  /**
   * @return <code>true</code>, genau dann wenn dieses Cell in der ersten H�lfte einschlie�lich des mittleren Felds ist,
   *         sonst <code>false</code>.
   */
  public boolean isInFirstHalf() {
    return getNumber() <= sudokuSize.getTotalSize() / 2;
  }

  /**
   * @return <code>true</code>, wenn die Zelle die letzte Zelle im Sudoku ist, sonst <code>false</code>.
   */
  public boolean isLastField() {
    if (getNumber() >= sudokuSize.getTotalSize() - 1) {
      return true;
    }
    return false;
  }

  /**
   * @return
   */
  public Literal getRandomCandidate() {
    assert candidates.size() > 0;
    final int index = RANDOM.nextInt(candidates.size());
    assert index >= 0 && index < candidates.size();
    final Literal literal = candidates.get(index);
    assert candidates.contains(literal);
    return literal;
  }

  public boolean isTop() {
    if (getRowIndex() % sudokuSize.getBlockHeight() == 0) {
      return true;
    }
    return false;
  }

  public boolean isRight() {
    if (getColumnIndex() % sudokuSize.getBlockWidth() == sudokuSize.getBlockWidth() - 1) {
      return true;
    }
    return false;
  }

  public boolean isBottom() {
    if (getRowIndex() % sudokuSize.getBlockHeight() == sudokuSize.getBlockHeight() - 1) {
      return true;
    }
    return false;
  }

  public boolean isLeft() {
    if (getColumnIndex() % sudokuSize.getBlockWidth() == 0) {
      return true;
    }
    return false;
  }

  @Deprecated
  public static List<Cell> filterByIsFixed(final List<Cell> felder, final boolean isFixed) {
    final List<Cell> filteredList = new ArrayList<Cell>();
    for (Cell cell : felder) {
      if ((cell.isFixed() && isFixed) || (!cell.isFixed() && !isFixed)) {
        filteredList.add(cell);
      }
    }
    return filteredList;
  }

}
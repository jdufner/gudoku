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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.cellhandlerimpls.CandidateCounter;
import de.jdufner.sudoku.common.cellhandlerimpls.CellByValue;
import de.jdufner.sudoku.common.cellhandlerimpls.FixedCounter;
import de.jdufner.sudoku.common.cellhandlerimpls.LongString;
import de.jdufner.sudoku.common.cellhandlerimpls.ResetAndRemoveCandidates;
import de.jdufner.sudoku.common.cellhandlerimpls.ResetCell;
import de.jdufner.sudoku.common.cellhandlerimpls.ShortString;
import de.jdufner.sudoku.common.cellhandlerimpls.ShortStringWithCandidates;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.common.validator.SudokuValidator;
import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * Diese Klasse kapselt das Spielfeld mit Zeilen, Spalten und Bl�cken.
 * 
 * TODO Methode clone() und Interface Cloneable entfernen
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Sudoku implements Cloneable {

  private static final Logger LOG = Logger.getLogger(Sudoku.class);
  /**
   * Anzahl der Zellen in dem Sudoku.
   */
  private final transient SudokuSize size;
  /**
   * Schwierigkeitsgrad des Sudokus.
   */
  private transient Level level;
  /**
   * Das Sudoku ist ein zweidimensionales Array mit Zeile und Spalte
   */
  private final transient Cell[][] board;
  /**
   * <code>true</code>, wenn der Konstruktor durchlaufen wurde, sonst <code>false</code>
   */
  private transient boolean initialized = false;
  /**
   * Speichert die Bl�cke f�r den schnelleren Zugriff.
   */
  private final transient Map<Integer, Block> blockMap = new HashMap<Integer, Block>();
  /**
   * Speichert die Spalten f�r den schnelleren Zugriff.
   */
  private final transient Map<Integer, Column> columnMap = new HashMap<Integer, Column>();
  /**
   * Speicher die Zeilen f�r den schnelleren Zugriff.
   */
  private final transient Map<Integer, Row> rowMap = new HashMap<Integer, Row>();

  // Die folgenden Objekte k�nnen wiederverwendet werden
  private transient WeakReference<SudokuValidator> weakReferenceValidator = null;
  private transient ResetCell resetCell = null;
  private transient WeakReference<ResetAndRemoveCandidates> weakReferenceResetAndRemoveCandidates = null;
  private transient FixedCounter fixedCounter = null;
  private transient CandidateCounter candidateCounter = null;

  /**
   * Copy-Constructor
   * 
   * @param original
   *          Das Original vom dem dieses Sudoku kopiert wird.
   */
  public Sudoku(final Sudoku original) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Erzeuge Kopie eines Sudokus");
    }
    size = original.size;
    board = new Cell[size.getUnitSize()][size.getUnitSize()];
    for (int i = 0; i < size.getUnitSize(); i++) {
      for (int j = 0; j < size.getUnitSize(); j++) {
        board[i][j] = new Cell(original.board[i][j]);
        addToUnits(board[i][j]);
      }
    }
    initialized = true;
  }

  /**
   * Erzeugt aus einem String ein Sudoku.
   * 
   * @param size
   * @param values
   */
  public Sudoku(final SudokuSize size, final Integer[] values) {
    assert size.getTotalSize() == values.length : "Es werden " + size.getTotalSize()
        + " Werte erwartet, aber es wurden " + values.length + " Werte �bergeben.";
    if (LOG.isDebugEnabled()) {
      LOG.debug("Erzeuge neues Sudoku");
    }
    this.size = size;
    board = new Cell[size.getUnitSize()][size.getUnitSize()];
    int index = 0;
    for (int i = 0; i < size.getUnitSize(); i++) {
      for (int j = 0; j < size.getUnitSize(); j++) {
        board[i][j] = new Cell(i, j, Literal.getInstance(values[index]), size);
        addToUnits(board[i][j]);
        index++;
      }
    }
    initialized = true;
  }

  public Sudoku(final SudokuSize size, final Cell[] cells) {
    assert size.getTotalSize() == cells.length : "Es werden " + size.getTotalSize()
        + " Zellen erwartet, aber es wurden " + cells.length + " Zellen �bergeben.";
    if (LOG.isDebugEnabled()) {
      LOG.debug("Erzeuge neues Sudoku");
    }
    this.size = size;
    board = new Cell[size.getUnitSize()][size.getUnitSize()];
    for (int i = 0; i < size.getUnitSize(); i++) {
      for (int j = 0; j < size.getUnitSize(); j++) {
        board[i][j] = cells[CellUtils.getNumber(i, j, size)];
        addToUnits(board[i][j]);
      }
    }
    initialized = true;
  }

  /**
   * @param rowIndex
   *          Der Zeilenindex mit 0 beginnend.
   * @param columnIndex
   *          Der Spaltenindex mit 0 beginnend.
   * @return Gibt eine Zelle zur�ck.
   */
  public Cell getCell(final int rowIndex, final int columnIndex) {
    assert rowIndex >= 0 : "Zeilenindex muss gr��er als oder gleich 0 sein, ist aber " + rowIndex;
    assert rowIndex < size.getUnitSize() : "Zeilenindex muss kleiner als " + size.getUnitSize() + " sein, ist aber "
        + rowIndex;
    assert columnIndex >= 0 : "Spaltenindex muss gr��er als oder gleich 0 sein, ist aber " + columnIndex;
    assert columnIndex < size.getUnitSize() : "Spaltenindex muss kleiner als " + size.getUnitSize()
        + " sein, ist aber " + columnIndex;
    return board[rowIndex][columnIndex];
  }

  public Collection<Cell> getCellByRowAndBlock(final int rowIndex, final int blockIndex) {
    assert rowIndex >= 0 : "Zeilenindex muss gr��er als oder gleich 0 sein, ist aber " + rowIndex;
    assert rowIndex < size.getUnitSize() : "Zeilenindex muss kleiner als " + size.getUnitSize() + " sein, ist aber "
        + rowIndex;
    assert blockIndex >= 0 : "Blockindex muss gr��er als oder gleich 0 sein, ist aber " + blockIndex;
    assert blockIndex < size.getUnitSize() : "Blockindex muss kleiner als " + size.getUnitSize() + " sein, ist aber "
        + blockIndex;
    final Collection<Cell> result = new ArrayList<Cell>();
    for (int i = 0; i < size.getUnitSize(); i++) {
      final Cell cell = board[rowIndex][i];
      if (cell.getBlockIndex() == blockIndex) {
        result.add(cell);
      }
    }
    return result;
  }

  public Collection<Cell> getCellByColumnAndBlock(final int columnIndex, final int blockIndex) {
    assert columnIndex >= 0 : "Spaltenindex muss gr��er als oder gleich 0 sein, ist aber " + columnIndex;
    assert columnIndex < size.getUnitSize() : "Spaltenindex muss kleiner als " + size.getUnitSize()
        + " sein, ist aber " + columnIndex;
    assert blockIndex >= 0 : "Blockindex muss gr��er als oder gleich 0 sein, ist aber " + blockIndex;
    assert blockIndex < size.getUnitSize() : "Blockindex muss kleiner als " + size.getUnitSize() + " sein, ist aber "
        + blockIndex;
    final Collection<Cell> result = new ArrayList<Cell>();
    for (int i = 0; i < size.getUnitSize(); i++) {
      final Cell cell = board[i][columnIndex];
      if (cell.getBlockIndex() == blockIndex) {
        result.add(cell);
      }
    }
    return result;
  }

  /**
   * @param number
   *          Die Nummer der Zelle. Rechnet die Nummer in Spalte und Zeile um und rufe {@link #getCell(int, int)} auf.
   * @return Gibt eine Zelle zur�ck.
   */
  public Cell getCell(final int number) {
    assert number >= 0 : number + " must be greater or equals than 0";
    assert number < size.getTotalSize() : number + " must be lower than " + size.getTotalSize();
    final int row = number / size.getUnitSize();
    final int column = number % size.getUnitSize();
    return getCell(row, column);
  }

  /**
   * @param blockIndex
   * @return the cells in the given block
   */
  public Block getBlock(final int blockIndex) {
    assert blockIndex >= 0;
    assert blockIndex < size.getUnitSize();
    return blockMap.get(blockIndex);
  }

  /**
   * @param columnIndex
   * @return the cells in the given column
   */
  public Column getColumn(final int columnIndex) {
    assert columnIndex >= 0;
    assert columnIndex < size.getUnitSize();
    return columnMap.get(columnIndex);
  }

  /**
   * @param rowIndex
   * @return the cells in the given row
   */
  public Row getRow(final int rowIndex) {
    assert rowIndex >= 0;
    assert rowIndex < size.getUnitSize();
    return rowMap.get(rowIndex);
  }

  /**
   * @return Gibt eine Liste aller Zellen zur�ck.
   */
  public Collection<Cell> getCells() {
    final List<Cell> cells = new ArrayList<Cell>(size.getTotalSize());
    for (int i = 0; i < size.getTotalSize(); i++) {
      cells.add(getCell(i));
    }
    return cells;
  }

  /**
   * @param literal
   *          Der Wert, den eine einzelne Zelle haben muss.
   * @return Gibt eine Liste aller Zellen mit einem bestimmten Wert zur�ck.
   */
  public Collection<Cell> getCellsByValue(final Literal literal) {
    final CellByValue cellByValue = new CellByValue(literal);
    HandlerUtil.forEachCell(this, cellByValue);
    return cellByValue.getCells();
  }

  /**
   * @return Gibt eine Liste aller Bl�cke zur�ck.
   */
  public Collection<Block> getBlocks() {
    final List<Block> blocks = new ArrayList<Block>(size.getUnitSize());
    for (int i = 0; i < size.getUnitSize(); i++) {
      blocks.add(getBlock(i));
    }
    return blocks;
  }

  /**
   * @return Gibt eine Liste aller Spalten zur�ck.
   */
  public Collection<Column> getColumns() {
    final List<Column> columns = new ArrayList<Column>(size.getUnitSize());
    for (int i = 0; i < size.getUnitSize(); i++) {
      columns.add(getColumn(i));
    }
    return columns;
  }

  /**
   * @return Gibt eine Liste aller Zeilen zur�ck.
   */
  public Collection<Row> getRows() {
    final List<Row> rows = new ArrayList<Row>(size.getUnitSize());
    for (int i = 0; i < size.getUnitSize(); i++) {
      rows.add(getRow(i));
    }
    return rows;
  }

  /**
   * Gibt ein Sudoku in mehreren Zeilen aus. Pro Zeile wird eine Zelle inkl. Kandidaten ausgegeben. Wird vermutlich
   * nicht mehr verwendet. Inzwischen kann das Sudoku besser visualisiert werden.
   * 
   * @return Gibt einen String des Sudokus zur�ck, f�r Debugging-Zwecke.
   * @see LongString
   */
  public String toLongString() {
    final LongString longString = new LongString(this);
    HandlerUtil.forEachCell(this, longString);
    return longString.toString();
  }

  /**
   * @return Gibt einen String des Sudokus in einer Zeile zur�ck.
   * @see ShortString
   */
  public String toShortString() {
    final ShortString shortString = new ShortString(this);
    HandlerUtil.forEachCell(this, shortString);
    return shortString.toString();
  }

  /**
   * @return Gibt einen String des Sudokus in einer Zeile zur�ck. Die Zellen sind mit Komma getrennt, die Kandidaten
   *         sind mit Bindestrich getrennt.
   * @see ShortStringWithCandidates
   */
  public String toShortStringWithCandidates() {
    final ShortStringWithCandidates shortStringWithCandidates = new ShortStringWithCandidates();
    HandlerUtil.forEachCell(this, shortStringWithCandidates);
    return shortStringWithCandidates.toString();
  }

  /**
   * @return Gibt den Schwierigkeitsgrad des Sudokus zur�ck.
   */
  public Level getLevel() {
    if (level == null) {
      return Level.UNBEKANNT;
    }
    return level;
  }

  /**
   * @param level
   *          Setzt den Schwierigkeitsgrad des Sudokus. Sollte nicht verwendet werden, wird nur vom Generator verwendet.
   */
  public void setLevel(final Level level) {
    this.level = level;
  }

  /**
   * @return Gibt die Gr��e des Sudokus zur�ck.
   */
  public SudokuSize getSize() {
    return size;
  }

  /**
   * @return <code>true</code>, wenn alle Einheiten ({@link Unit}), also Bl�cke ({@link Block}), Zeilen ({@link Row})
   *         und Spalten ({@link Column}) g�ltig sind. Darf nur aufgerufen werden, wenn das Sudoku initialisiert ist.
   * @see Unit#isValid()
   */
  public boolean isValid() {
    assert initialized : "Das Sudoku muss initialisiert sein.";
    SudokuValidator validator = null;
    if (weakReferenceValidator != null) {
      validator = weakReferenceValidator.get();
    }
    if (validator == null) {
      validator = (SudokuValidator) SolverServiceFactory.INSTANCE.getBean(SudokuValidator.class);
      weakReferenceValidator = new WeakReference<SudokuValidator>(validator);
    }
    return validator.isValid(this);
  }

  /**
   * @return <code>true</code>, wenn das Sudoku initializiert ist, sonst <code>false</code>.
   */
  public boolean isInitialized() {
    return initialized;
  }

  /**
   * @return <code>true</code>, wenn die Anzahl der besetzten Zellen ({@link Cell}) gr��er oder gleich der Anzahl der
   *         Zellen ist, sonst <code>false</code>.
   */
  public boolean isSolved() {
    return getNumberOfFixed() >= getSize().getTotalSize();
  }

  /**
   * @return <code>true</code>, wenn die Pr�fsummen der aller Bl�cke, Spalten und Zeilen korrekt sind, sonst
   *         <code>false</code>.
   */
  public boolean isSolvedByCheckSum() {
    // assert isSolved();
    return areAllBlocksSolved() && areAllColumnsSolved() && areAllRowsSolved();
  }

  /**
   * @return Gibt die Anzahl der besetzen Zellen zur�ck.
   */
  public int getNumberOfFixed() {
    if (fixedCounter == null) {
      fixedCounter = new FixedCounter();
    }
    HandlerUtil.forEachCell(this, fixedCounter);
    return fixedCounter.getNumber();
  }

  /**
   * @return Gibt die Anzahl der freien Zellen zur�ck.
   */
  public int getNumberOfCandidates() {
    if (candidateCounter == null) {
      candidateCounter = new CandidateCounter();
    }
    HandlerUtil.forEachCell(this, candidateCounter);
    return candidateCounter.getNumber();
  }

  /**
   * Setzt die Kandidaten der freien Zellen zur�ck und entfernt die Kandidaten, f�r die feste Werte in den gleichen
   * Einheiten (Zeile, Spalte, Block) vorliegen.
   * 
   * @see #clear()
   * @see ResetAndRemoveCandidates
   */
  public void resetAndClearCandidatesOfNonFixed() {
    ResetAndRemoveCandidates resetAndRemoveCandidates = null;
    if (weakReferenceResetAndRemoveCandidates != null) {
      resetAndRemoveCandidates = weakReferenceResetAndRemoveCandidates.get();
    }
    if (resetAndRemoveCandidates == null) {
      resetAndRemoveCandidates = new ResetAndRemoveCandidates(this);
      weakReferenceResetAndRemoveCandidates = new WeakReference<ResetAndRemoveCandidates>(resetAndRemoveCandidates);
    }
    HandlerUtil.forEachCell(this, resetAndRemoveCandidates);
  }

  /**
   * Setzt die Kandidaten der freien Zellen zur�ck.
   * 
   * @see #resetAndClearCandidatesOfNonFixed()
   * @see ResetCell
   */
  public void clear() {
    if (resetCell == null) {
      resetCell = new ResetCell();
    }
    HandlerUtil.forEachCell(this, resetCell);
  }

  public Block getBlock(final int rowIndex, final int columnIndex) {
    return getBlock(BlockUtils.getBlockIndexByRowIndexAndColumnIndex(rowIndex, columnIndex, size));
  }

  @Override
  public String toString() {
    if (isSolved()) {
      return toShortString();
    }
    return toShortStringWithCandidates();
  }

  @Override
  public Sudoku clone() { // NOPMD by J�rgen on 08.11.09 00:21
    return new Sudoku(this);
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof Sudoku) {
      final Sudoku that = (Sudoku) other;
      return toShortString().equals(that.toShortString());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return toShortString().hashCode();
  }

  private boolean areAllBlocksSolved() {
    boolean solved = true;
    for (int i = 0; i < size.getUnitSize(); i++) {
      solved = solved && getBlock(i).isSolved();
      if (!solved) {
        break;
      }
    }
    return solved;
  }

  private boolean areAllColumnsSolved() {
    boolean solved = true;
    for (int i = 0; i < size.getUnitSize(); i++) {
      solved = solved && getColumn(i).isSolved();
      if (!solved) {
        break;
      }
    }
    return solved;
  }

  private boolean areAllRowsSolved() {
    boolean solved = true;
    for (int i = 0; i < size.getUnitSize(); i++) {
      solved = solved && getRow(i).isSolved();
      if (!solved) {
        break;
      }
    }
    return solved;
  }

  private void addToUnits(final Cell cell) {
    addToBlock(cell);
    addToColumn(cell);
    addToRow(cell);
  }

  private void addToBlock(final Cell cell) {
    Block block = blockMap.get(cell.getBlockIndex());
    if (block == null) {
      block = new Block(size, cell.getBlockIndex(), new ArrayList<Cell>());
      blockMap.put(cell.getBlockIndex(), block);
    }
    block.getCells().add(cell);
  }

  private void addToColumn(final Cell cell) {
    Column column = columnMap.get(cell.getColumnIndex());
    if (column == null) {
      column = new Column(size, cell.getColumnIndex(), new ArrayList<Cell>());
      columnMap.put(cell.getColumnIndex(), column);
    }
    column.getCells().add(cell);
  }

  private void addToRow(final Cell cell) {
    Row row = rowMap.get(cell.getRowIndex());
    if (row == null) {
      row = new Row(size, cell.getRowIndex(), new ArrayList<Cell>());
      rowMap.put(cell.getRowIndex(), row);
    }
    row.getCells().add(cell);
  }

}

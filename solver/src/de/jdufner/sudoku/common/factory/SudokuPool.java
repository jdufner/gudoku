// $Id: SudokuPool.java,v 1.1 2009/11/17 20:34:33 jdufner Exp $

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
package de.jdufner.sudoku.common.factory;

import org.apache.commons.pool.ObjectPool;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.exceptions.SudokuRuntimeException;

/**
 * Implementiert einen Pool voller Sudokus. Der Pool ist endlich groß und wird im <code>solver-context.xml</code>
 * konfiguriert. Sind alle Elemente aus dem Pool verbraucht, tritt eine Exception auf, wenn ein weiteres Sudoku
 * angefordert wird.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.1 $
 * 
 */
public final class SudokuPool {

  /**
   * Eine Poolimplementierung von Apache-Commons.
   */
  private transient ObjectPool objectPool;
  private transient SudokuSize size;

  /**
   * @param objectPool
   *          Der Pool, der mittels Spring konfiguriert wurde.
   */
  public void setObjectPool(final ObjectPool objectPool) {
    this.objectPool = objectPool;
  }

  public void setSudokuSize(final SudokuSize size) {
    if (this.size == null || !this.size.equals(size)) {
      this.size = size;
      final SudokuFactory factory = new SudokuFactory();
      factory.setSize(size);
      objectPool.setFactory(factory);
    }
  }

  /**
   * Holt ein Sudoku aus dem Pool.
   * 
   * @return
   * @throws Exception
   */
  public Sudoku borrowSudoku() {
    try {
      return (Sudoku) objectPool.borrowObject();
    } catch (Exception e) {
      throw new SudokuRuntimeException(e);
    }
  }

  /**
   * Holt ein Sudoku aus dem Pool und initialisiert des Sudoku mit den Werten des übergebenen Sudokus.
   * 
   * @param sudoku
   * @return
   * @throws Exception
   */
  public Sudoku borrowSudoku(final Sudoku sudoku) {
    try {
      setSudokuSize(sudoku.getSize());
      final Sudoku sudokuClone = borrowSudoku();
      for (Cell cell : sudoku.getCells()) {
        if (cell.isFixed()) {
          final Cell cellClone = sudokuClone.getCell(cell.getRowIndex(), cell.getColumnIndex());
          cellClone.setValue(cell.getValue());
        } else {
          final Cell cellClone = sudokuClone.getCell(cell.getRowIndex(), cell.getColumnIndex());
          cellClone.setValue(cell.getValue());
          cellClone.getCandidates().clear();
          cellClone.getCandidates().addAll(cell.getCandidates());
        }
      }
      return sudokuClone;
    } catch (Exception e) {
      throw new SudokuRuntimeException(e);
    }
  }

  /**
   * Gibt die Anzahl der verwendeten Sudokus aus dem Pool an.
   * 
   * @return
   */
  public int getNumActive() {
    return objectPool.getNumActive();
  }

  /**
   * Gibt die Anzahl der verfügbaren Sudokus im Pool an.
   * 
   * @return
   */
  public int getNumIdle() {
    return objectPool.getNumIdle();
  }

  /**
   * Legt ein Sudoku zurück in den Pool, dabei wird es zurückgesetzt.
   * 
   * @param sudoku
   * @throws Exception
   */
  public void returnSudoku(final Sudoku sudoku) {
    try {
      objectPool.returnObject(sudoku);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}

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
package de.jdufner.sudoku.dao;

import java.util.Date;
import java.util.List;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;

/**
 * Das <code>SudokuDao</code> stellt Dienste zum Lesen und Schreiben von Sudokus in die Datenbank zur Verfügung.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public interface SudokuDao {

  /**
   * 
   * @param size
   *          Größe der zu findenden Sudokus.
   * @param level
   *          Schwierigkeitsgrad der zu findenden Sudokus.
   * @param number
   *          Anzahl der maximal zu findenden Sudokus.
   * @return
   */
  List<SudokuData> findSudokus(SudokuSize size, Level level, int number, Boolean printed);

  /**
   * 
   * @return Gibt das aktuellste Sudoku zurück.
   */
  Sudoku loadSudokuOfDay();

  /**
   * Lädt ein Sudoku aus der Datenbank.
   * 
   * @param id
   *          Die ID des Sudoku.
   * @return Gibt das Sudoku zur ID zurück.
   */
  SudokuData loadSudoku(int id);

  /**
   * Speichert das übergebene Sudoku.
   * 
   * @param sudoku
   *          Das übergebene Sudoku.
   */
  void saveSudoku(Sudoku sudoku);

  /**
   * 
   * @param id
   * @param printedAt
   */
  void updatePrintedAt(int id, Date printedAt);

}

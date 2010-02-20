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
package de.jdufner.sudoku.builder;

import java.util.Map;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public interface Builder {

  /**
   * Bevor der {@link Builder} mittels {@link #build()} oder {@link #buildSudokus()} ausgef�hrt wird, muss die
   * gew�nschte Gr��e gesetzt werden.
   * 
   * @param sudokuSize
   */
  public void setSize(SudokuSize sudokuSize);

  /**
   * @return Gibt das letzte und damit das schwierigste {@link Sudoku} zur�ck.
   */
  public Sudoku build();

  /**
   * Gibt das letzte jeweils zu einem Schwierigkeitsgrad ({@link Level}) gefundene {@link Sudoku} zur�ck. Wird zu einem
   * {@link Level} kein Sudoku gefunden, wird daf�r eben auch keins zur�ckgegeben, mit anderen Worten es werden also
   * maximal soviele {@link Sudoku} zur�ckgegeben, wie es {@link Level} gibt.
   * 
   * @return Eine Map, bestehend aus dem {@link Level} und dem letzen zu diesem {@link Level} gefundenen
   *         {@link Solution} inkl. {@link Sudoku}.
   */
  public Map<Level, Solution> buildSudokus();

}

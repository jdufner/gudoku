// $Id: Solver.java,v 1.2 2009/11/28 00:25:13 jdufner Exp $

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
package de.jdufner.sudoku.solver.service;

import de.jdufner.sudoku.common.board.Sudoku;

/**
 * Einfache Service zum L�sen von Sudokus.
 * 
 * Es existieren zwei einfache Implementierungen dazu:
 * <ul>
 * <li>Backtracking-Variante (brute force)</li>
 * <li>Intelligente Variante (aktuell nur eine parallele Implementierung)</li>
 * </ul>
 * 
 * TODO Muss ich diese Liste selbst schreiben oder kann das javadoc selbst erledigen? Eigentlich geh�rt das ja nicht in
 * diese Klasse.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.2 $
 */
public interface Solver {

  /**
   * Liefert <code>true</code> zur�ck, wenn ein Sudoku �berhaupt eine L�sung, sonst <code>false</code>. Der
   * Rechenaufwand ist derselbe wie bei {@link #solve(Sudoku)}
   * 
   * TODO throws InvalidSudokuException
   * 
   * @param sudoku
   * @return <code>true</code> if Sudoku is solvable, else <code>false</code>
   */
  boolean isSolvable(Sudoku sudoku);

  /**
   * Finds a solution of the Sudoku, if exists. Maybe there are more than one solution.
   * 
   * TODO throws InvalidSudokuException
   * 
   * TODO throws NoSolutionFoundException
   * 
   * @param sudoku
   * @return A solution, if exists.
   */
  Sudoku solve(Sudoku sudoku);

  /**
   * Liefert <code>true</code> zur�ck, wenn genau eine L�sung existiert, sonst <code>false</code>.
   * 
   * TODO throws InvalidSudokuException
   * 
   * TODO throws NoSolutionFoundException
   * 
   * @param sudoku
   * @return <code>true</code>, wenn das Sudoku genau eine L�sung hat, sonst <code>false</code>.
   */
  boolean isUnique(Sudoku sudoku);

}

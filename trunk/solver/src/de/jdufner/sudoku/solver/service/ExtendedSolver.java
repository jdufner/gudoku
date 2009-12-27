// $Id: ExtendedSolver.java,v 1.2 2009/11/28 00:25:13 jdufner Exp $

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
 * Erweiterter Solver, speziell f�r eine intelligente L�sung, keine Brute Force-Methode.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.2 $
 */
public interface ExtendedSolver extends Solver {

  /**
   * Returns a {@link Cell} of the solved Sudoku, which is set in the given Sudoku.
   * 
   * @param sudoku
   * @return A Cell of the solved Sudoku. TODO Use interceptor? In Backtracking I'll use a complete solution, in
   *         Strategy, one new solved Cell is enough.
   */
  // Cell getHint(Sudoku sudoku);

  /**
   * Liefert ein {@link Solution} zu einem Sudoku zur�ck.
   * 
   * @param sudoku
   * @return
   */
  Solution getSolution(Sudoku sudoku);

}

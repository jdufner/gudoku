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
package de.jdufner.sudoku.solver.strategy.naked;

import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;
import de.jdufner.sudoku.solver.strategy.Strategy;

public final class NakedQuadSerialStrategyTest extends AbstractStrategyTestCase {

  @Override
  protected int getNumberCommands() {
    return 2;
  }

  @Override
  protected Strategy getStrategy() {
    return new NakedQuadSerialStrategy(sudoku);
  }

  @Override
  protected String getSudokuAsString() {
    return "1-3-7-8,3-4-6-8,3-4,1-3-4-5-7-8,3-4-5,2,1-3-4-5-6,1-3-4-5-6,9,1-3,3-4-6,2,1-3-4-5,3-4-5,9,7,1-3-4-5-6,8,1-3-7-8-9,3-4-8-9,5,1-3-4-7-8,3-4,6,1-3-4,1-3-4,2,6,1,8-9,2-4-5,2-4-5-9,7,2-5-8-9,5-8-9,3,4,2,8-9,5-6,5-6-9,3,1-5-6-8-9,1-5-6-8-9,7,3-5,3-5,7,2-6,1,8,2-6-9,6-9,4,2-3-5-8-9,7,1,2-3-6,2-3-6,4,3-8-9,3-8-9,5-6,2-3-5-9,3-4-5-9,3-4,2-3-6,8,1,3-4-9,7,5-6,3-8,3-4-8,6,9,7,5,3-4-8,2,1";
  }

}

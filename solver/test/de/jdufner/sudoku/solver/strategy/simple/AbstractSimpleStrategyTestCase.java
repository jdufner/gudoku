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
package de.jdufner.sudoku.solver.strategy.simple;

import de.jdufner.sudoku.solver.strategy.AbstractStrategyTestCase;

public abstract class AbstractSimpleStrategyTestCase extends AbstractStrategyTestCase {

  //private static final Logger LOG = Logger.getLogger(AbstractSimpleStrategyTestCase.class);

  protected String getSudokuAsString() {
    return "1-3-7-8,1-3-4-6-8,3-4-8,1-3-4-5-7-8,3-4-5-7,2,1-3-4-5-6,1-3-4-5-6,9,1-3-9,1-3-4-6-9,2,1-3-4-5,3-4-5-9,1-4-9,7,1-3-4-5-6,8,1-3-7-8-9,1-3-4-8-9,5,1-3-4-7-8,3-4-7-9,6,1-2-3-4,1-3-4,1-2,6,1-5-8-9,8-9,2-4-5-7-8,2-4-5-7-9,4-7-8-9,1-2-5-8-9,1-5-8-9,3,4,2,8-9,5-6-8,5-6-9,3,1-5-6-8-9,1-5-6-8-9,7,3-5-8-9,3-5-8-9,7,2-5-6-8,1,8-9,2-5-6-8-9,5-6-8-9,4,2-3-5-8-9,7,1,2-3-4-6,2-3-4-6,4,3-4-5-6-8-9,3-4-5-6-8-9,5-6,2-3-5-9,3-4-5-9,3-4-9,1-2-3-4-6-7,8,1-4-7,1-3-4-5-6-9,1-3-4-5-6-7-9,1-5-6,3-8,3-4-8,6,9,3-4-7,5,1-3-4-8,2,1";
  }

}

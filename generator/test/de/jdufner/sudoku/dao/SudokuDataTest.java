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
package de.jdufner.sudoku.dao;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.test.AbstractGeneratorTestCase;

public class SudokuDataTest extends AbstractGeneratorTestCase {

  private static final Logger LOG = Logger.getLogger(SudokuData.class);

  public void testEqualsAndHashCode() {
    SudokuData sudokuData1 = new SudokuData();
    sudokuData1.setId(1);
    SudokuData sudokuData2 = new SudokuData();
    sudokuData2.setId(1);
    SudokuData sudokuData3 = new SudokuData();
    sudokuData3.setId(1);
    assertEquals(sudokuData1.hashCode(), sudokuData2.hashCode());
    assertEquals(sudokuData2.hashCode(), sudokuData3.hashCode());
    assertEquals(sudokuData1.hashCode(), sudokuData3.hashCode());
    assertEquals(sudokuData1, sudokuData2);
    assertEquals(sudokuData2, sudokuData3);
    assertEquals(sudokuData1, sudokuData3);
    assertNotNull(sudokuData1.toString());
    LOG.debug(sudokuData1);
  }

}

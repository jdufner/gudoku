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
package de.jdufner.sudoku;

import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.generator.service.SudokuGeneratorService;

/**
 * Applikation zum Starten des SudokuGenerators. Der SudokuGenerator arbeitet selbst�ndig und speichert die erzeugten
 * Sudokus in der Datenbank ab.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-27
 * @version $Revision$
 */
public final class SudokuGenerator extends AbstractMainClass {

  /**
   * Instanziert und startet den SudokuGenerator.
   * 
   * @param args
   *          Es werden keine Parameter ausgewertet.
   */
  public static void main(String[] args) throws Exception {
    SudokuGenerator sudokuGenerator = new SudokuGenerator();
    sudokuGenerator.start();
  }

  /**
   * Implementiert die Logik und ruft den SudokuGeneratorService auf.
   */
  protected void run() {
    SudokuGeneratorService sudokuGeneratorService = (SudokuGeneratorService) GeneratorServiceFactory.INSTANCE
        .getBean(SudokuGeneratorService.class);
    while (true) {
      sudokuGeneratorService.generate();
    }
  }

}

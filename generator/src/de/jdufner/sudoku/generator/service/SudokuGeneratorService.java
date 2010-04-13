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
package de.jdufner.sudoku.generator.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.builder.LiteralEleminationBuilder;
import de.jdufner.sudoku.builder.RandomEleminationBuilder;
import de.jdufner.sudoku.builder.SymetricRandomEleminationBuilder;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.dao.SudokuDao;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * Erzeugt mindestens ein Sudoku und speichert es in der Datenbank ab.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SudokuGeneratorService {

  private static final Logger LOG = Logger.getLogger(SudokuGeneratorService.class);
  private static final Logger SUDOKU = Logger.getLogger("sudoku");

  private SudokuDao sudokuDao;

  public void generate() {
    generateLiteralEleminationBuilder();
    generateRandomEleminationBuilder();
    generateSymetricRandomEleminationBuilder();
  }

  public void generateLiteralEleminationBuilder() {
    Map<Level, Solution> map = getLiteralEleminationBuilder().buildSudokus();
    for (Level l : map.keySet()) {
      if (map.get(l).getQuest().getNumberOfFixed() <= 30) {
        sudokuDao.saveSolution(map.get(l));
        SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
      }
    }
  }

  public void generateRandomEleminationBuilder() {
    Map<Level, Solution> map = getRandomEleminationBuilder().buildSudokus();
    for (Level l : map.keySet()) {
      sudokuDao.saveSolution(map.get(l));
      SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
    }
  }

  public void generateSymetricRandomEleminationBuilder() {
    Map<Level, Solution> map = getSymetricRandomEleminationBuilder().buildSudokus();
    for (Level l : map.keySet()) {
      sudokuDao.saveSolution(map.get(l));
      SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
    }
  }

  private LiteralEleminationBuilder getLiteralEleminationBuilder() {
    return (LiteralEleminationBuilder) GeneratorServiceFactory.INSTANCE.getBean(LiteralEleminationBuilder.class);
  }

  private RandomEleminationBuilder getRandomEleminationBuilder() {
    return (RandomEleminationBuilder) GeneratorServiceFactory.INSTANCE.getBean(RandomEleminationBuilder.class);
  }

  private SymetricRandomEleminationBuilder getSymetricRandomEleminationBuilder() {
    return (SymetricRandomEleminationBuilder) GeneratorServiceFactory.INSTANCE
        .getBean(SymetricRandomEleminationBuilder.class);
  }

  //
  // Spring Wiring
  //

  public SudokuDao getSudokuDao() {
    return sudokuDao;
  }

  public void setSudokuDao(SudokuDao sudokuDao) {
    this.sudokuDao = sudokuDao;
  }

}

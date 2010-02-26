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
package de.jdufner.sudoku.generator.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.builder.LiteralEleminationBuilder;
import de.jdufner.sudoku.builder.RandomEleminationBuilder;
import de.jdufner.sudoku.builder.SymetricRandomEleminationBuilder;
import de.jdufner.sudoku.common.misc.Level;
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
  private LiteralEleminationBuilder literalEleminationBuilder;
  private RandomEleminationBuilder randomEleminationBuilder;
  private SymetricRandomEleminationBuilder symetricRandomEleminationBuilder;

  public void generate() {
    generateLiteralEleminationBuilder();
    generateRandomEleminationBuilder();
    generateSymetricRandomEleminationBuilder();
  }

  public void generateLiteralEleminationBuilder() {
    Map<Level, Solution> map = literalEleminationBuilder.buildSudokus();
    for (Level l : map.keySet()) {
      if (map.get(l).getQuest().getNumberOfFixed() <= 30) {
        sudokuDao.saveSolution(map.get(l));
        SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
      }
    }
  }

  public void generateRandomEleminationBuilder() {
    Map<Level, Solution> map = randomEleminationBuilder.buildSudokus();
    for (Level l : map.keySet()) {
      sudokuDao.saveSolution(map.get(l));
      SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
    }
  }

  public void generateSymetricRandomEleminationBuilder() {
    Map<Level, Solution> map = symetricRandomEleminationBuilder.buildSudokus();
    for (Level l : map.keySet()) {
      sudokuDao.saveSolution(map.get(l));
      SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
    }
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

  public LiteralEleminationBuilder getLiteralEleminationBuilder() {
    return literalEleminationBuilder;
  }

  public void setLiteralEleminationBuilder(LiteralEleminationBuilder literalEleminationBuilder) {
    this.literalEleminationBuilder = literalEleminationBuilder;
  }

  public RandomEleminationBuilder getRandomEleminationBuilder() {
    return randomEleminationBuilder;
  }

  public void setRandomEleminationBuilder(RandomEleminationBuilder randomEleminationBuilder) {
    this.randomEleminationBuilder = randomEleminationBuilder;
  }

  public SymetricRandomEleminationBuilder getSymetricRandomEleminationBuilder() {
    return symetricRandomEleminationBuilder;
  }

  public void setSymetricRandomEleminationBuilder(SymetricRandomEleminationBuilder symetricRandomEleminationBuilder) {
    this.symetricRandomEleminationBuilder = symetricRandomEleminationBuilder;
  }

}

// $Id: SudokuGenerator.java,v 1.13 2009/12/26 23:06:47 jdufner Exp $

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
package de.jdufner.sudoku;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.builder.Builder;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.context.GeneratorServiceFactory;
import de.jdufner.sudoku.dao.SudokuDao;

/**
 * Erzeugt mindestens ein Sudoku und speichert es in der Datenbank ab.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.13 $
 */
public final class SudokuGenerator {

  private static final Logger LOG = Logger.getLogger(SudokuGenerator.class);
  private static final Logger SUDOKU = Logger.getLogger("sudoku");

  public void generate() {
    generateLiteralEleminationBuilder();
    generateRandomEleminationBuilder();
    generateSymetricRandomEleminationBuilder();
  }

  public void generateLiteralEleminationBuilder() {
    SudokuDao dao = GeneratorServiceFactory.getInstance().getSudokuDao();
    Builder builder = GeneratorServiceFactory.getInstance().getLiteralEleminationBuilder();
    Map<Level, Sudoku> map = builder.buildSudokus();
    for (Level l : map.keySet()) {
      dao.saveSudoku(map.get(l));
      SUDOKU.info(StringUtils.leftPad(l.toString(), 11) + " " + map.get(l));
    }
  }

  public void generateRandomEleminationBuilder() {
    SudokuDao dao = GeneratorServiceFactory.getInstance().getSudokuDao();
    Builder builder = GeneratorServiceFactory.getInstance().getRandomEleminationBuilder();
    Map<Level, Sudoku> map = builder.buildSudokus();
    for (Level l : map.keySet()) {
      dao.saveSudoku(map.get(l));
    }
  }

  public void generateSymetricRandomEleminationBuilder() {
    SudokuDao dao = GeneratorServiceFactory.getInstance().getSudokuDao();
    Builder builder = GeneratorServiceFactory.getInstance().getSymetricRandomEleminationBuilder();
    Map<Level, Sudoku> map = builder.buildSudokus();
    for (Level l : map.keySet()) {
      dao.saveSudoku(map.get(l));
    }
  }

}

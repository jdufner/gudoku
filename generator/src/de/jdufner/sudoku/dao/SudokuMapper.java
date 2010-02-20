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
package de.jdufner.sudoku.dao;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;
import de.jdufner.sudoku.solver.service.Solution;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * 
 */
public final class SudokuMapper {

  private SudokuMapper() {
  }

  public static Sudoku map(SudokuData sudokuData) {
    return SudokuFactory.buildSudoku(sudokuData.getSudokuAsString());
  }

  public static SudokuData map(Solution solution) {
    SudokuData sudokuData = new SudokuData();
    sudokuData.setFixed(solution.getQuest().getNumberOfFixed());
    sudokuData.setLevel(solution.getLevel().getValue());
    sudokuData.setSize(solution.getQuest().getSize().getUnitSize());
    sudokuData.setSudokuAsString(solution.getQuest().toShortString());
    sudokuData.setStrategySimple(solution.getNumberSuccessfulCommand(StrategyNameEnum.SIMPLE));
    sudokuData.setStrategyHiddenSingle(solution.getNumberSuccessfulCommand(StrategyNameEnum.HIDDEN_SINGLE));
    sudokuData.setStrategyNakedPair(solution.getNumberSuccessfulCommand(StrategyNameEnum.NAKED_PAIR));
    sudokuData.setStrategyNakedTriple(solution.getNumberSuccessfulCommand(StrategyNameEnum.NAKED_TRIPLE));
    sudokuData.setStrategyNakedQuad(solution.getNumberSuccessfulCommand(StrategyNameEnum.NAKED_QUAD));
    sudokuData.setStrategyHiddenPair(solution.getNumberSuccessfulCommand(StrategyNameEnum.HIDDEN_PAIR));
    sudokuData.setStrategyHiddenTriple(solution.getNumberSuccessfulCommand(StrategyNameEnum.HIDDEN_TRIPLE));
    sudokuData.setStrategyHiddenQuad(solution.getNumberSuccessfulCommand(StrategyNameEnum.HIDDEN_QUAD));
    sudokuData.setStrategyIntersectionRemoval(solution
        .getNumberSuccessfulCommand(StrategyNameEnum.INTERSECTION_REMOVAL));
    // TODO Y-Wing
    //sudokuData.setStrategy(solution.getNumberSuccessfulCommand(StrategyNameEnum.YWING));
    sudokuData.setStrategyXwing(solution.getNumberSuccessfulCommand(StrategyNameEnum.XWING));
    // TODO Swordfish
    //sudokuData.setStrategy(solution.getNumberSuccessfulCommand(StrategyNameEnum.SWORDFISH));
    // TODO Jellyfish
    //sudokuData.setStrategy(solution.getNumberSuccessfulCommand(StrategyNameEnum.JELLYFISH));
    // TODO Backtracking
    //sudokuData.setStrategy(solution.getNumberSuccessfulCommand(StrategyNameEnum.BACKTRACKING));
    return sudokuData;
  }
}

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
package de.jdufner.sudoku.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.builder.transformation.TransformationUtil;
import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class EleminationBuilder extends AbstractBuilder {

  private static final Logger LOG = Logger.getLogger(EleminationBuilder.class);
  private static final int NUMBER_TRANSFORMATIONS = 123;

  protected Map<Level, Sudoku> level2SudokuMap = new HashMap<Level, Sudoku>();
  private int cellCounter = 0;
  private int eleminatedCellCounter = 0;

  public EleminationBuilder() {
    super();
  }

  protected void shuffleSudoku() {
    if (sudoku == null) {
      setSize(SudokuSize.DEFAULT);
    }
    // TODO Soll das noch gemacht werden?
    sudoku = TransformationUtil.arbitraryTransformation(sudoku, NUMBER_TRANSFORMATIONS);
    if (LOG.isDebugEnabled()) {
      LOG.debug(sudoku.toShortString());
    }
  }

  protected List<Cell> buildRandomPermutationOfFixed() {
    List<Cell> felder = new ArrayList<Cell>();
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        if (sudoku.getCell(i, j).isFixed()) {
          felder.add(sudoku.getCell(i, j));
        }
      }
    }
    RandomData randomData = new RandomDataImpl();
    Object[] objs = randomData.nextSample(felder, felder.size());
    List<Cell> arbitraryPermution = new ArrayList<Cell>();
    for (int i = 0; i < objs.length; i++) {
      arbitraryPermution.add((Cell) objs[i]);
    }
    return arbitraryPermution;
  }

  protected List<Cell> buildRandomPermutationOfFixedOfFirstHalf() {
    List<Cell> felder = new ArrayList<Cell>();
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        if (sudoku.getCell(i, j).isInFirstHalf() && sudoku.getCell(i, j).isFixed()) {
          felder.add(sudoku.getCell(i, j));
        }
      }
    }
    RandomData randomData = new RandomDataImpl();
    Object[] objs = randomData.nextSample(felder, felder.size());
    List<Cell> arbitraryPermution = new ArrayList<Cell>();
    for (int i = 0; i < objs.length; i++) {
      arbitraryPermution.add((Cell) objs[i]);
    }
    return arbitraryPermution;
  }

  protected void eleminateIfPossible(Cell cell) {
    Cell cellCandidate = (Cell) cell.clone();
    if (LOG.isDebugEnabled()) {
      LOG.debug("Leere Zelle " + cell);
    }
    sudoku.getCell(cell.getRowIndex(), cell.getColumnIndex()).reset();
    cellCounter++;
    Solution solution = strategySolverWithBacktracking.getSolution(sudoku);
    if (solution.isUnique()) {
      solution.getQuest().setLevel(solution.getLevel());
      level2SudokuMap.put(solution.getLevel(), solution.getQuest());
      eleminatedCellCounter++;
      if (LOG.isInfoEnabled()) {
        LOG.info("(" + eleminatedCellCounter + "/" + cellCounter + ") Zelle " + cellCandidate
            + " erfolgreich geleert. Neues Sudoku: " + sudoku.toShortString());
      }
    } else {
      sudoku.getCell(cellCandidate.getRowIndex(), cellCandidate.getColumnIndex()).setValue(cellCandidate.getValue());
      if (LOG.isInfoEnabled()) {
        LOG.info("(" + eleminatedCellCounter + "/" + cellCounter + ") Zelle " + cellCandidate
            + " konnte nicht geleert werden, setze deshalb zur�ck und mache weiter.");
      }
    }
  }

  protected abstract void executeBuilder();

  public Sudoku build() {
    shuffleSudoku();
    executeBuilder();
    if (LOG.isInfoEnabled()) {
      LOG.info("New Sudoku: " + sudoku);
    }
    return sudoku;
  }

  public Map<Level, Sudoku> buildSudokus() {
    shuffleSudoku();
    executeBuilder();
    return level2SudokuMap;
  }

}
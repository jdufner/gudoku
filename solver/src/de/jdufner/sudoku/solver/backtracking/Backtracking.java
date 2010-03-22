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
package de.jdufner.sudoku.solver.backtracking;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.Unit;
import de.jdufner.sudoku.common.factory.SudokuPool;
import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Backtracking {

  private static final Logger LOG = Logger.getLogger(Backtracking.class);
  private static final int MAX_SOLUTION_COUNTER = 10;

  private static SudokuPool sudokuPool = null;

  /**
   * Die vorige Instanz aus dem Stapel von {@link Backtracking}, wie das in Backtracking �blich ist, wird ein Stapel von
   * Backtracking-Instanzen aufgebaut.
   */
  private transient Backtracking previousInstance = null;

  /**
   * Das Backtracking wird solange durchgef�hrt, bis die Grenze erreicht ist. Ist der Wert kleiner oder gleich 0, werden
   * alle L�sungen gesucht. Ist der Wert 1 wird nur die erste L�sung gesucht, etc.
   */
  private int solutionLimit = 1;

  /**
   * Werden im Rahmen des Backtrackings mehrere L�sungen gefunden, werden diese in der untersten Instanz im Stapel
   * gespeichert.
   */
  private transient List<Sudoku> solutions = null;

  /**
   * Das {@link Sudoku} der aktuellen Instanz, also auf der obersten Instanz auf dem Stapel.
   */
  private final transient Sudoku sudoku;

  /**
   * Die H�he des Stapels.
   */
  private final transient int stackSize;

  private transient Sudoku solution = null;

  private transient int solutionCounter = 0;

  private transient int startPosition = 0;

  public Backtracking(final Sudoku sudoku, final int stackSize) {
    assert stackSize <= sudoku.getSize().getTotalSize() : "stackSize is " + stackSize
        + ", but must be smaller or equals " + sudoku.getSize().getTotalSize();
    this.sudoku = sudoku;
    this.sudoku.resetAndClearCandidatesOfNonFixed();
    this.stackSize = stackSize;
    if (!sudoku.isValid()) {
      throw new IllegalStateException("Das �bergebene Sudoku ist in keinem g�ltigen Zustand!");
    }
  }

  public Backtracking(final Backtracking previousInstance, final Sudoku sudoku, final int stackSize,
      final int solutionCounter, final int startPosition) {
    assert stackSize <= sudoku.getSize().getTotalSize() : "stackSize is " + stackSize
        + ", but must be smaller or equals " + sudoku.getSize().getTotalSize();
    this.previousInstance = previousInstance;
    this.sudoku = sudoku;
    this.stackSize = stackSize;
    this.solutionCounter = solutionCounter;
    this.startPosition = startPosition;
  }

  public int countSolutions() {
    setSolutionLimit(Integer.MAX_VALUE);
    findSolutions2();
    assert solutionCounter == getSolutions().size() : "Der Z�hler muss genau soviele L�sungen haben, wie gefunden wurden.";
    return solutionCounter;
  }

  public boolean isUnique() {
    setSolutionLimit(2);
    findSolutions2();
    assert solutionCounter == getSolutions().size() : "Der Z�hler muss genau soviele L�sungen haben, wie gefunden wurden.";
    return solutionCounter == 1;
  }

  /**
   * @return First (found) solution via backtracking algorithm, <code>null</code> if no solution exists.
   * @throws CloneNotSupportedException
   */
  public Sudoku firstSolution() {
    setSolutionLimit(1);
    final Sudoku mySolution = findSolutions2();
    assert solutionCounter == getSolutions().size() : "Der Z�hler muss genau soviele L�sungen haben, wie gefunden wurden.";
    return mySolution;
  }

  // TODO Dokumentieren und testen.
  public List<Sudoku> firstSolutions(final int maxNumberOfSolutions) {
    setSolutionLimit(maxNumberOfSolutions);
    findSolutions2();
    return getSolutions();
  }

  private Sudoku findSolutions2() {
    assert sudoku.isValid() : "Sudoku ist ung�ltig!";
    // assert !sudoku.isSolved() : "Sudoku ist bereits gel�st!";
    if (LOG.isDebugEnabled()) {
      LOG.debug("Stacksize=" + stackSize);
    }

    // Wenn das Sudoku gel�st ist, dann nicht weitermachen
    if (sudoku.isSolved()) {
      if (LOG.isInfoEnabled()) {
        LOG.info("Wird diese Stelle '1' in Praxis verwendet?");
      }
      // Eine Kopie der L�sung anlegen und zur�ckgeben
      // Muss die L�sung selbst zur�ck in den Pool?
      return (Sudoku) sudoku.clone();
    }

    // Finde Zelle, �ber deren Kandidaten iteriert wird
    final Cell cell = getFirstNonFixed(startPosition);
    // assert !cell.isFixed() : "Die Zelle darf nicht besetzt sein!";
    if (cell == null) {
      // Es ist keine weitere Zelle zu besetzen
      if (sudoku.isSolved()) {
        // Entweder ist das Sudoku gel�st
        if (LOG.isInfoEnabled()) {
          LOG.info("Wird diese Stelle '2' in Praxis verwendet?");
        }
        // Eine Kopie der L�sung anlegen und zur�ckgeben
        // Muss die L�sung selbst zur�ck in den Pool?
        return (Sudoku) sudoku.clone();
      } else {
        // Oder alle Zellen sind besetzt und das Sudoku ist ung�ltig
        LOG.equals("Alle Zellen sind besetzt, aber das Sudoku ist nicht gel�st.");
        throw new IllegalStateException("Alle Zellen sind besetzt, aber das Sudoku ist nicht gel�st.");
      }
    } else {
      // Diese Zelle muss einen Wert aus den Kandidaten haben
      for (Literal candidate : cell.getCandidates()) {
        Sudoku nextSudoku = null;
        Cell nextCell = null;
        nextSudoku = getSudokuPool().borrowSudoku(sudoku);
        nextCell = nextSudoku.getCell(cell.getNumber());
        nextCell.setValue(candidate);
        if (removeCandidate(nextSudoku, nextCell, candidate) && nextSudoku.isValid()) {
          // Zelle gesetzt
          // Sudoku gel�st
          if (nextSudoku.isSolvedByCheckSum()) {
            solution = (Sudoku) nextSudoku.clone();
            if (LOG.isDebugEnabled()) {
              LOG.debug("Found a solution #" + solutionCounter);
              LOG.debug(nextSudoku.toShortString());
            }
            increaseSolutionCounter();
            addSolutions(solution);
            if (getSolutionCounter() >= getSolutionLimit()) {
              getSudokuPool().returnSudoku(nextSudoku);
              return solution;
            }
          } else {
            if (nextSudoku.isValid()) {
              LOG.debug("Valid!");
            } else {
              LOG.debug("Not Valid!");
            }
            final Backtracking backtracking = new Backtracking(this, nextSudoku, stackSize + 1, solutionCounter,
                nextCell.getNumber());
            solution = backtracking.findSolutions2();
            if (solution == null) {
              LOG.debug("Totes Ende.");
              // teste n�chsten Kandidat
            } else {
              if (getSolutionCounter() >= getSolutionLimit()) {
                assert solution.isValid() : "Sudoku muss g�ltig sein!";
                assert solution.isSolved() : "Sudoku muss gel�st sein!";
                assert solution.isSolvedByCheckSum() : "Sudoku muss gel�st sein!";
                LOG.debug("Eine L�sung gefunden.");
                getSudokuPool().returnSudoku(nextSudoku);
                return solution;
              }
            }
          }
        } else {
          // nextSudoku verwerfen, Instanz zur�ck in Pool legen
          LOG.debug(nextSudoku.isValid());
        }
        getSudokuPool().returnSudoku(nextSudoku);
      }
    }
    return solution;
  }

  /**
   * Entfernt den Kandidat in allen unbesetzten Zellen desselben Blocks, Zeile und Spalte. Wenn der Kandidat erfolgreich
   * entfernt werden konnte, dann gibt die Methode <code>true</code> zur�ck, sonst <code>false</code>.
   * 
   * @param cell
   *          Die Zelle, die besetzt wurde.
   * @param candidate
   *          Der Kandidat, der entfernt werden soll.
   * @return <code>true</code>, wenn der Kandidat erfolgreich entfernt werden konnte und das Sudoku weiterhin g�ltig
   *         ist, sonst <code>false</code>.
   */
  private boolean removeCandidate(final Sudoku sudoku, final Cell cell, final Literal candidate) {
    return removeCandidateSerial(sudoku, cell, candidate);
  }

  private boolean removeCandidateSerial(final Sudoku sudoku, final Cell cell, final Literal candidate) {
    return removeCandidateAndTestValidityInUnit(sudoku.getBlock(cell.getBlockIndex()), candidate)
        && removeCandidateAndTestValidityInUnit(sudoku.getColumn(cell.getColumnIndex()), candidate)
        && removeCandidateAndTestValidityInUnit(sudoku.getRow(cell.getRowIndex()), candidate);
  }

  /**
   * Entfernt den Kandidat in allen unbesetzten Zellen der Einheit und gibt <code>true</code> zur�ck, wenn alle Zellen
   * noch g�ltig sind, sonst <code>false</code>.
   * 
   * @param unit
   *          Die Einheit, dessen Zellen vom Kandidat entfernt werden soll und dessen G�ltigkeit gepr�ft wird.
   * @param candidate
   *          Der Kandidat, der entfernt werden soll.
   * @return <code>true</code>, wenn nach dem Entfernen des Kandidaten die Einheit noch g�ltig ist, sonst
   *         <code>false</code>.
   */
  private boolean removeCandidateAndTestValidityInUnit(final Unit unit, final Literal candidate) {
    for (Cell cell : unit.getCells()) {
      if (!cell.isFixed()) {
        cell.getCandidates().remove(candidate);
        if (cell.getCandidates().size() <= 0) {
          return false;
        }
      }
    }
    return unit.isValid();
  }

  /**
   * @param number
   *          Nummer der Zelle mit 0 beginnend.
   * @return Die erste nicht besetzte Zelle ab der (einschlie�lich) angegebenen Nummer.
   */
  private Cell getFirstNonFixed(final int number) {
    assert number >= 0 : "Zellennummer muss gr��er oder gleich 0 sein.";
    assert number < sudoku.getSize().getTotalSize() : "Zellennummer muss kleiner als "
        + sudoku.getSize().getTotalSize() + " sein.";
    for (int i = number; i < sudoku.getSize().getTotalSize(); i++) {
      if (!sudoku.getCell(i).isFixed()) {
        return sudoku.getCell(i);
      }
    }
    return null;
  }

  public void increaseSolutionCounter() {
    if (previousInstance == null) {
      solutionCounter++;
      return;
    }
    previousInstance.increaseSolutionCounter();
  }

  public int getSolutionCounter() {
    if (previousInstance == null) {
      return solutionCounter;
    }
    return previousInstance.getSolutionCounter();
  }

  public int getSolutionLimit() {
    if (previousInstance == null) {
      return solutionLimit;
    }
    return previousInstance.getSolutionLimit();
  }

  public void setSolutionLimit(final int solutionLimit) {
    int mySolutionLimit = solutionLimit;
    if (previousInstance == null) {
      if (mySolutionLimit <= 0) {
        mySolutionLimit = MAX_SOLUTION_COUNTER;
      }
      this.solutionLimit = mySolutionLimit;
      return;
    }
    previousInstance.setSolutionLimit(mySolutionLimit);
  }

  public List<Sudoku> getSolutions() {
    if (previousInstance == null) {
      return solutions;
    }
    return previousInstance.getSolutions();
  }

  public void addSolutions(final Sudoku mySolution) {
    if (previousInstance == null) {
      if (solutions == null) {
        solutions = new ArrayList<Sudoku>();
      }
      solutions.add(mySolution);
      return;
    }
    previousInstance.addSolutions(mySolution);
  }

  private SudokuPool getSudokuPool() {
    if (sudokuPool == null) {
      sudokuPool = (SudokuPool) SolverServiceFactory.INSTANCE.getBean(SudokuPool.class);
    }
    return sudokuPool;
  }

}

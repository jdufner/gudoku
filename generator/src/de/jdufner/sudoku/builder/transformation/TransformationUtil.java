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
package de.jdufner.sudoku.builder.transformation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.BlockUtils;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.board.Unit;
import de.jdufner.sudoku.common.factory.SudokuFactory;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class TransformationUtil {
  private static final Logger log = Logger.getLogger(TransformationUtil.class);

  private static Random random;
  private static List<Method> transformationMethods;
  private static List<Method> symetricMethods;

  static {
    random = new Random();

    transformationMethods = new ArrayList<Method>();
    for (Method method : TransformationUtil.class.getMethods()) {
      for (Annotation annotation : method.getDeclaredAnnotations()) {
        if (annotation instanceof ArbitraryTransformation) {
          transformationMethods.add(method);
          break;
        }
      }
    }

    symetricMethods = new ArrayList<Method>();
    for (Method method : TransformationUtil.class.getMethods()) {
      for (Annotation annotation : method.getDeclaredAnnotations()) {
        if (annotation instanceof SymetricTransformation) {
          symetricMethods.add(method);
          break;
        }
      }
    }
  }

  /**
   * @param sudoku
   * @param column1
   * @param column2
   * @return Tauscht zwei Spalten innerhalb eines Blocks.
   */
  static Sudoku swapColumns(Sudoku sudoku, int column1, int column2) {
    assert sudoku.getBlock(0, column1) == sudoku.getBlock(0, column2) : column1 + " und " + column2
        + " sind nicht im selben Quandranten.";
    assert column1 < column2 : column1 + " ist nicht kleiner als " + column2;
    log.debug("Tausche Spalte " + column1 + " und " + column2);
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        if (j == column1) {
          newSudoku.getCell(i, column2).setValue(sudoku.getCell(i, column1).getValue());
        } else if (j == column2) {
          newSudoku.getCell(i, column1).setValue(sudoku.getCell(i, column2).getValue());
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  public static Sudoku swapArbitraryColumns(Sudoku sudoku) {
    int[] twoColumns = getTwoNumbersInOneColumnBlock(sudoku);
    return swapColumns(sudoku, twoColumns[0], twoColumns[1]);
  }

  private static int[] getTwoNumbersInOneColumnBlock(Sudoku sudoku) {
    int block = random.nextInt(sudoku.getSize().getBlockHeight()) * sudoku.getSize().getBlockWidth();
    int[] numbers = getTwoRandomNumbersByBlockWidth(sudoku);
    return new int[] { block + numbers[0], block + numbers[1] };
  }

  private static int[] getTwoNumbersInOneRowBlock(Sudoku sudoku) {
    int block = random.nextInt(sudoku.getSize().getBlockWidth()) * sudoku.getSize().getBlockHeight();
    int[] numbers = getTwoRandomNumbersByBlockHeight(sudoku);
    return new int[] { block + numbers[0], block + numbers[1] };
  }

  private static int[] getTwoRandomNumbersByBlockWidth(Sudoku sudoku) {
    return getTwoRandomNumbersBetween(0, sudoku.getSize().getBlockWidth());
  }

  private static int[] getTwoRandomNumbersByBlockHeight(Sudoku sudoku) {
    return getTwoRandomNumbersBetween(0, sudoku.getSize().getBlockHeight());
  }

  private static int[] getTwoRandomNumbersBetween(int min, int max) {
    assert max > min : "max: " + max + " muss größer als min: " + min + " sein.";
    int number1 = getRandomNumberBetween(min, max);
    int number2;
    do {
      number2 = getRandomNumberBetween(min, max);
    } while (number1 == number2);
    if (number1 > number2) {
      int swap = number1;
      number1 = number2;
      number2 = swap;
    }
    return new int[] { number1, number2 };
  }

  /**
   * @param min
   * @param max
   * @return A arbitrary integer value between min (inclusive) and max (exclusive).
   */
  private static int getRandomNumberBetween(int min, int max) {
    assert max > min : "max: " + max + " muss größer als min: " + min + " sein.";
    return random.nextInt(max - min) + min;
  }

  static Sudoku swapRows(Sudoku sudoku, int row1, int row2) {
    assert sudoku.getBlock(0, row1) == sudoku.getBlock(0, row2);
    assert row1 < row2;
    log.debug("Tausche Zeilen " + row1 + " und " + row2);
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        if (i == row1) {
          newSudoku.getCell(row2, j).setValue(sudoku.getCell(row1, j).getValue());
        } else if (i == row2) {
          newSudoku.getCell(row1, j).setValue(sudoku.getCell(row2, j).getValue());
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  public static Sudoku swapArbitraryRows(Sudoku sudoku) {
    int[] twoRows = getTwoNumbersInOneRowBlock(sudoku);
    return swapRows(sudoku, twoRows[0], twoRows[1]);
  }

  static Sudoku swapColumnBlock(Sudoku sudoku, int columnBlock1, int columnBlock2) {
    assert columnBlock1 >= 0 && columnBlock1 < sudoku.getSize().getBlockHeight();
    assert columnBlock2 >= 0 && columnBlock2 < sudoku.getSize().getBlockHeight();
    assert columnBlock1 < columnBlock2;
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        if (isColumnIndexInBlock(j, sudoku, columnBlock1)) {
          newSudoku.getCell(i, j + (columnBlock2 - columnBlock1) * sudoku.getSize().getBlockWidth()).setValue(
              sudoku.getCell(i, j).getValue());
        } else if (isColumnIndexInBlock(j, sudoku, columnBlock2)) {
          newSudoku.getCell(i, j - (columnBlock2 - columnBlock1) * sudoku.getSize().getBlockWidth()).setValue(
              sudoku.getCell(i, j).getValue());
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  public static Sudoku swapArbitraryColumnBlocks(Sudoku sudoku) {
    int[] twoColumnBlocks = getTwoRandomNumbersByBlockHeight(sudoku);
    return swapColumnBlock(sudoku, twoColumnBlocks[0], twoColumnBlocks[1]);
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku swapSymetricColumnBlocks(Sudoku sudoku) {
    int block = getRandomNumberBetween(0, (sudoku.getSize().getUnitSize() - 1) / 2);
    int[] columns = BlockUtils.getColumnsByBlock(block, sudoku.getSize());
    Sudoku result = sudoku;
    for (int i = 0; i < columns.length; i++) {
      result = swapColumns(result, columns[i], sudoku.getSize().getUnitSize() - columns[i] - 1);
    }
    return result;
  }

  static Sudoku swapRowBlock(Sudoku sudoku, int rowBlock1, int rowBlock2) {
    assert rowBlock1 >= 0 && rowBlock1 < sudoku.getSize().getBlockHeight();
    assert rowBlock2 >= 0 && rowBlock2 < sudoku.getSize().getBlockHeight();
    assert rowBlock1 < rowBlock2;
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        if (isRowIndexInBlock(i, sudoku, rowBlock1)) {
          newSudoku.getCell(i + (rowBlock2 - rowBlock1) * sudoku.getSize().getBlockHeight(), j).setValue(
              sudoku.getCell(i, j).getValue());
        } else if (isRowIndexInBlock(i, sudoku, rowBlock2)) {
          newSudoku.getCell(i - (rowBlock2 - rowBlock1) * sudoku.getSize().getBlockHeight(), j).setValue(
              sudoku.getCell(i, j).getValue());
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  public static Sudoku swapArbitraryRowBlocks(Sudoku sudoku) {
    int[] twoRowBlocks = getTwoRandomNumbersByBlockWidth(sudoku);
    return swapRowBlock(sudoku, twoRowBlocks[0], twoRowBlocks[1]);
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku swapSymetricRowBlocks(Sudoku sudoku) {
    int block = getRandomNumberBetween(0, (sudoku.getSize().getUnitSize() - 1) / 2);
    int[] columns = BlockUtils.getColumnsByBlock(block, sudoku.getSize());
    Sudoku result = sudoku;
    for (int i = 0; i < columns.length; i++) {
      result = swapRows(result, columns[i], sudoku.getSize().getUnitSize() - columns[i] - 1);
    }
    return result;
  }

  /**
   * @param columnIndex
   *          Spaltenindex
   * @param sudoku
   *          Ein {@link Sudoku}, das eine Referenz auf eine {@link SudokuSize} hat.
   * @param block
   *          Blockindex im Sinne von {@link Unit#getIndex()}
   * @return <code>true</code>, wenn der Spaltenindex im angegebenen Block ist, sonst <code>false</code>.
   */
  private static boolean isColumnIndexInBlock(int columnIndex, Sudoku sudoku, int block) {
    if (columnIndex >= getSmallestColumnIndexOfBlock(sudoku, block)
        && columnIndex <= getLargestColumnIndexOfBlock(sudoku, block)) {
      return true;
    }
    return false;
  }

  /**
   * @param rowIndex
   *          Zeilenindex
   * @param sudoku
   *          Ein {@link Sudoku}, das eine Referenz auf eine {@link SudokuSize} hat.
   * @param block
   *          Blockindex im Sinne von {@link Unit#getIndex()}
   * @return <code>true</code>, wenn der Zeilenindex im angegebenen Block ist, sonst <code>false</code>.
   */
  private static boolean isRowIndexInBlock(int rowIndex, Sudoku sudoku, int block) {
    if (rowIndex >= getSmallestRowIndexOfBlock(sudoku, block) && rowIndex <= getLargestRowIndexOfBlock(sudoku, block)) {
      return true;
    }
    return false;
  }

  /**
   * @param sudoku
   * @param block
   * @return Gibt den kleinsten Spaltenindex eines Blocks zurück.
   */
  private static int getSmallestColumnIndexOfBlock(Sudoku sudoku, int block) {
    assert block >= 0 && block < sudoku.getSize().getBlockWidth();
    return sudoku.getSize().getBlockWidth() * block;
  }

  /**
   * @param sudoku
   * @param block
   * @return Gibt den größten Spaltenindex eines Blocks zurück.
   */
  private static int getLargestColumnIndexOfBlock(Sudoku sudoku, int block) {
    assert block >= 0 && block < sudoku.getSize().getBlockWidth();
    return sudoku.getSize().getBlockWidth() * (block + 1) - 1;
  }

  /**
   * @param sudoku
   * @param block
   * @return Gibt den kleinsten Zeilenindex eines Blocks zurück.
   */
  private static int getSmallestRowIndexOfBlock(Sudoku sudoku, int block) {
    assert block >= 0 && block < sudoku.getSize().getBlockHeight();
    return sudoku.getSize().getBlockHeight() * block;
  }

  /**
   * @param sudoku
   * @param block
   * @return Gibt den größten Zeilenindex eines Blocks zurück.
   */
  private static int getLargestRowIndexOfBlock(Sudoku sudoku, int block) {
    assert block >= 0 && block < sudoku.getSize().getBlockHeight();
    return sudoku.getSize().getBlockHeight() * (block + 1) - 1;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku rotateBlockClockwise(Sudoku sudoku) {
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        newSudoku.getCell(j, sudoku.getSize().getUnitSize() - i - 1).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku rotateBlockCounterClockwise(Sudoku sudoku) {
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        newSudoku.getCell(sudoku.getSize().getUnitSize() - j - 1, i).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku rotateHalfClockwise(Sudoku sudoku) {
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        newSudoku.getCell(sudoku.getSize().getUnitSize() - i - 1, sudoku.getSize().getUnitSize() - j - 1).setValue(
            sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku mirrorVertically(Sudoku sudoku) {
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        newSudoku.getCell(i, sudoku.getSize().getUnitSize() - j - 1).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku mirrorHorizontally(Sudoku sudoku) {
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        newSudoku.getCell(sudoku.getSize().getUnitSize() - i - 1, j).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku mirrorDiagonally(Sudoku sudoku) {
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        newSudoku.getCell(sudoku.getSize().getUnitSize() - j - 1, sudoku.getSize().getUnitSize() - i - 1).setValue(
            sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku mirrorCounterDiagonally(Sudoku sudoku) {
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        newSudoku.getCell(j, i).setValue(sudoku.getCell(i, j).getValue());
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  @ArbitraryTransformation
  @SymetricTransformation
  public static Sudoku swapValues(Sudoku sudoku) {
    int randomNumber[] = getTwoRandomNumbersBetween(1, sudoku.getSize().getUnitSize());
    assert randomNumber[0] >= 1 && randomNumber[0] <= sudoku.getSize().getUnitSize();
    Literal l1 = Literal.getInstance(randomNumber[0]);
    assert randomNumber[1] >= 1 && randomNumber[1] <= sudoku.getSize().getUnitSize();
    Literal l2 = Literal.getInstance(randomNumber[1]);
    return swapValues(sudoku, l1, l2);
  }

  private static Sudoku swapValues(Sudoku sudoku, Literal l1, Literal l2) {
    Sudoku newSudoku = SudokuFactory.buildEmpty(sudoku.getSize());
    for (int i = 0; i < sudoku.getSize().getUnitSize(); i++) {
      for (int j = 0; j < sudoku.getSize().getUnitSize(); j++) {
        if (sudoku.getCell(i, j).getValue().equals(l1)) {
          newSudoku.getCell(i, j).setValue(l2);
        } else if (sudoku.getCell(i, j).getValue().equals(l2)) {
          newSudoku.getCell(i, j).setValue(l1);
        } else {
          newSudoku.getCell(i, j).setValue(sudoku.getCell(i, j).getValue());
        }
      }
    }
    newSudoku.resetAndClearCandidatesOfNonFixed();
    assert newSudoku.isValid();
    return newSudoku;
  }

  public static Sudoku arbitraryTransformation(Sudoku sudoku) {
    int methodNumber = 0;
    Method arbitraryTransformation = null;
    try {
      methodNumber = getRandomNumberBetween(0, transformationMethods.size());
      arbitraryTransformation = transformationMethods.get(methodNumber);
      log.debug("Führe beliebige Transformation aus: " + arbitraryTransformation.getName());
      return (Sudoku) arbitraryTransformation.invoke(null, new Object[] { sudoku });
    } catch (IllegalAccessException iae) {
      log.error(iae.getMessage() + " methodNumber=" + methodNumber + ", arbitraryTransformation="
          + arbitraryTransformation, iae);
    } catch (IllegalArgumentException iae) {
      log.error(iae.getMessage(), iae);
    } catch (InvocationTargetException ite) {
      log.error(ite.getMessage(), ite);
      throw new RuntimeException(ite.getCause());
    }
    return null;
  }

  public static Sudoku arbitraryTransformation(Sudoku sudoku, int numberTransformation) {
    Sudoku newSudoku = sudoku;
    for (int i = 0; i < numberTransformation; i++) {
      newSudoku = arbitraryTransformation(newSudoku);
    }
    return newSudoku;
  }

}

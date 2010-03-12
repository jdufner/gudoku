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
package de.jdufner.sudoku.common.factory;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.commons.pool.PoolableObjectFactory;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;

/**
 * Diese Klasse erzeugt verschiedene Spielfelder mit unterschiedlichen Schwierigkeitsgraden.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class SudokuFactory implements PoolableObjectFactory {

  private static final Pattern SIZE_PATTERN = Pattern.compile("^(\\d+):");
  private static final Pattern CELLS_PATTERN = Pattern.compile(":([0-9,]+)$");
  private static final Pattern CANDIDATES_PATTERN = Pattern.compile("^[0-9,\\-]+$");

  private SudokuSize size = SudokuSize.DEFAULT;

  public static Sudoku buildSudoku(final String sudokuAsString) {
    if (SIZE_PATTERN.matcher(sudokuAsString).find()) {
      final Sudoku sudoku = new Sudoku(getGroesseFromString(sudokuAsString), getFelderFromString(sudokuAsString));
      if (!sudoku.isValid()) {
        throw new IllegalArgumentException("Das eingegebene Sudoku ist nicht gültig.");
      }
      return sudoku;
    } else {
      if (sudokuAsString.contains(",") && CANDIDATES_PATTERN.matcher(sudokuAsString).matches()) {
        final String[] felderAsStrings = Pattern.compile(",").split(sudokuAsString);
        final Cell[] cells = new Cell[felderAsStrings.length];
        for (int i = 0; i < felderAsStrings.length; i++) {
          if (felderAsStrings[i].contains("-")) {
            final String[] candidatesAsStrings = Pattern.compile("-").split(felderAsStrings[i]);
            final Literal[] candidates = new Literal[candidatesAsStrings.length];
            for (int j = 0; j < candidatesAsStrings.length; j++) {
              candidates[j] = Literal.getInstance(Integer.parseInt(candidatesAsStrings[j]));
            }
            cells[i] = new Cell(i, null, Arrays.asList(candidates), SudokuSize.DEFAULT);
          } else {
            cells[i] = new Cell(i, Literal.getInstance(Integer.parseInt(felderAsStrings[i])), null, SudokuSize.DEFAULT);
          }
        }
        return new Sudoku(SudokuSize.DEFAULT, cells);
      } else {
        final Sudoku sudoku = buildSudokuFrom81Chars(sudokuAsString);
        if (!sudoku.isValid()) {
          throw new IllegalArgumentException("Das eingegebene Sudoku ist nicht gültig.");
        }
        return sudoku;
      }
    }
  }

  private static Sudoku buildSudokuFrom81Chars(final String sudokuAsString) {
    final char[] chars = sudokuAsString.toCharArray();
    Integer[] felder = new Integer[chars.length];
    for (int i = 0; i < chars.length; i++) {
      if (chars[i] == '.') {
        felder[i] = Integer.valueOf(0);
      } else {
        felder[i] = Integer.valueOf(String.valueOf(chars[i]));
      }
    }
    final Sudoku sudoku = new Sudoku(SudokuSize.NEUN, felder);
    return sudoku;
  }

  protected static SudokuSize getGroesseFromString(final String boardAsString) {
    final Matcher matcher = SIZE_PATTERN.matcher(boardAsString);
    if (matcher.find()) {
      final int unitSize = Integer.parseInt(matcher.group(1));
      return SudokuSize.getByUnitSize(unitSize);
    }
    return SudokuSize.DEFAULT;
  }

  protected static Integer[] getFelderFromString(final String spielfeldAsString) {
    final Matcher matcher = CELLS_PATTERN.matcher(spielfeldAsString);
    if (matcher.find()) {
      final String felderAsString = matcher.group(1);
      final String[] felderAsStrings = Pattern.compile(",").split(felderAsString);
      Integer[] felder = new Integer[felderAsStrings.length];
      for (int i = 0; i < felderAsStrings.length; i++) {
        felder[i] = Integer.valueOf(felderAsStrings[i]);
      }
      return felder;
    }
    throw new IllegalStateException("Sudoku-Zeichenkette konnte nicht geparst werden.");
  }

  public static Sudoku buildEmpty(final SudokuSize sudokuSize) {
    final Integer[] felder = new Integer[sudokuSize.getTotalSize()];
    for (int i = 0; i < sudokuSize.getTotalSize(); i++) {
      felder[i] = Integer.valueOf(0);
    }
    return new Sudoku(sudokuSize, felder);
  }

  /**
   * Creates a filled and valid Sudoku.
   * 
   * @param sudokuSize
   * @return
   */
  public static Sudoku buildFilled(final SudokuSize sudokuSize) {
    final Sudoku sudoku = buildEmpty(sudokuSize);
    for (int i = 0; i < sudokuSize.getUnitSize(); i++) {
      for (int j = 0; j < sudokuSize.getUnitSize(); j++) {
        final int shiftLeft = (i % sudokuSize.getBlockHeight()) * sudokuSize.getBlockWidth();
        final int block = i / sudokuSize.getBlockHeight();
        int column = j - shiftLeft + block;
        if (column >= sudokuSize.getUnitSize()) {
          column -= sudokuSize.getUnitSize();
        }
        if (column < 0) {
          column += sudokuSize.getUnitSize();
        }
        sudoku.getCell(i, column).setValue(Literal.getInstance(j + 1));
      }
    }
    return sudoku;
  }

  public static Sudoku buildShuffled(final SudokuSize sudokuSize, final RandomData randomData) {
    final Sudoku sudoku = buildEmpty(sudokuSize);
    final int[] literal = randomData.nextPermutation(sudokuSize.getUnitSize(), sudokuSize.getUnitSize());
    final int[] columnIndex = randomData.nextPermutation(sudokuSize.getUnitSize(), sudokuSize.getUnitSize());
    final int[] rowIndex = randomData.nextPermutation(sudokuSize.getUnitSize(), sudokuSize.getUnitSize());
    for (int i = 0; i < sudokuSize.getUnitSize(); i++) {
      sudoku.getCell(rowIndex[i], columnIndex[i]).setValue(Literal.getInstance(literal[i] + 1));
    }
    return sudoku;
  }

  /**
   * Nur für Tests verwenden, weil hier RandomDataImpl immer neu erzeugt wird.
   * 
   * @param sudokuSize
   * @return
   */
  public static Sudoku buildShuffled(final SudokuSize sudokuSize) {
    // TODO Aus Factory Singleton machen und RandomDataImpl halten
    return buildShuffled(sudokuSize, new RandomDataImpl());
  }

  @Override
  public void activateObject(final Object obj) {
    // Sudoku sudoku = (Sudoku) obj;
    // Nix zu machen!!!
  }

  @Override
  public void destroyObject(final Object obj) {
    // Sudoku sudoku = (Sudoku) obj;
    // Nix zu machen!!!
  }

  @Override
  public Object makeObject() {
    return SudokuFactory.buildEmpty(size);
  }

  @Override
  public void passivateObject(final Object obj) {
    final Sudoku sudoku = (Sudoku) obj;
    sudoku.clear();
  }

  @Override
  public boolean validateObject(final Object obj) {
    final Sudoku sudoku = (Sudoku) obj;
    return sudoku.isValid();
  }

  public SudokuSize getSize() {
    return size;
  }

  public void setSize(final SudokuSize size) {
    this.size = size;
  }

}

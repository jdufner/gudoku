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
package de.jdufner.sudoku.common.board;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public enum SudokuSize {

  VIER(2, 2), NEUN(3, 3), ZEHN(2, 5);

  public static final SudokuSize DEFAULT = NEUN;

  private final int blockWidth;

  private final int blockHeight;

  private final int unitSize;

  private final int totalSize;

  private final int unitChecksum;

  private SudokuSize(final int blockWidth, final int blockHeight) {
    this.blockWidth = blockWidth;
    this.blockHeight = blockHeight;
    this.unitSize = blockWidth * blockHeight;
    this.totalSize = unitSize * unitSize;
    this.unitChecksum = (unitSize * (unitSize + 1)) / 2;
  }

  /**
   * @return The width of one block.
   */
  public int getBlockWidth() {
    return blockWidth;
  }

  /**
   * @return The height of one block.
   */
  public int getBlockHeight() {
    return blockHeight;
  }

  /**
   * @return The number of cells in one unit.
   */
  public int getUnitSize() {
    return unitSize;
  }

  /**
   * @return The number of cells in the sudoku.
   */
  public int getTotalSize() {
    return totalSize;
  }

  /**
   * @return The checksum of all cells in one unit.
   */
  public int getCheckSum() {
    return unitChecksum;
  }

  /**
   * @param unitSize
   * @return The enumeration element of the unit size;
   */
  public static SudokuSize getByUnitSize(final int unitSize) {
    for (int i = 0; i < SudokuSize.values().length; i++) {
      if (SudokuSize.values()[i].unitSize == unitSize) {
        return SudokuSize.values()[i];
      }
    }
    throw new IllegalArgumentException("Sudoku in der gewünschten Größe (" + unitSize + ") nicht möglich!");
  }

  /**
   * @return A list of all candidates.
   */
  public Candidates<Literal> initializeCandidates() {
    final Candidates<Literal> candidates = new Candidates<Literal>();
    for (int i = 1; i <= getUnitSize(); i++) {
      candidates.add(Literal.getInstance(i));
    }
    return candidates;
  }

}

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-03-04
 * @version $Revision$
 * 
 */
public final class PdfGeneratorConfiguration {

  private List<Level> levels;
  private SudokuSize size;
  private int sudokusPerPage;

  private PdfGeneratorConfiguration(Builder builder) {
    this.levels = new ArrayList<Level>();
    this.levels.addAll(builder.levels);
    Collections.sort(this.levels);
    this.size = builder.size;
    this.sudokusPerPage = builder.sudokusPerPage;
  }

  public List<Level> getLevels() {
    return levels;
  }

  public SudokuSize getSize() {
    return size;
  }

  public int getSudokusPerPage() {
    return sudokusPerPage;
  }

  public static class Builder {

    private Set<Level> levels = new HashSet<Level>();
    private SudokuSize size = SudokuSize.DEFAULT;
    private int sudokusPerPage = 6;

    public Builder() {
    }

    public Builder level(Level level) {
      this.levels.add(level);
      return this;
    }

    public Builder size(SudokuSize size) {
      this.size = size;
      return this;
    }

    public Builder sudokusPerPage(int sudokusPerPage) {
      this.sudokusPerPage = sudokusPerPage;
      return this;
    }

    public PdfGeneratorConfiguration build() {
      if (levels.isEmpty()) {
        levels.addAll(Arrays.asList(Level.values()));
      }
      return new PdfGeneratorConfiguration(this);
    }
  }

}

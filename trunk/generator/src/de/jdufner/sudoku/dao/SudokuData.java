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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.factory.SudokuFactory;

/**
 * Das ist die persistente Repräsentation eines Sudokus. Diese Klasse enthält das {@link Sudoku} als {@link String},
 * welches mittels {@link SudokuFactory} erzeugt werden muss. Aus Gründen der Übersichtlichkeit ist die Persistenz von
 * der Klasse {@link Sudoku} getrennt.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
@Entity
@Table(name = "SUDOKUS")
public final class SudokuData {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "SDKS_ID")
  private int id;
  @Column(name = "SDKS_SUDOKU_STRING", length = 512, nullable = false, unique = true)
  private String sudokuAsString;
  @Column(name = "SDKS_LEVEL", nullable = false)
  private int level;
  @Column(name = "SDKS_FIXED", nullable = false)
  private int fixed;
  @Column(name = "SDKS_SIZE", nullable = false)
  private int size;
  @Column(name = "SDKS_GENERATED_AT", nullable = false)
  private Date generatedAt = new Date();
  @Column(name = "SDKS_PRINTED_AT", nullable = false)
  private Date printedAt = new Date();

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSudokuAsString() {
    return sudokuAsString;
  }

  public void setSudokuAsString(String sudokuAsString) {
    this.sudokuAsString = sudokuAsString;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public int getFixed() {
    return fixed;
  }

  public void setFixed(int fixed) {
    this.fixed = fixed;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public Date getGeneratedAt() {
    return generatedAt;
  }

  public void setGeneratedAt(Date generatedAt) {
    this.generatedAt = generatedAt;
  }

  public Date getPrintedAt() {
    return printedAt;
  }

  public void setPrintedAt(Date printedAt) {
    this.printedAt = printedAt;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj instanceof SudokuData) {
      SudokuData that = (SudokuData) obj;
      if (this.getId() == that.getId()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}

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
package de.jdufner.sudoku.builder.utils;

import java.util.Collection;
import java.util.HashSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-12-28
 * @version $Revision$
 */
public final class Nachbarschaft {

  private static final Logger LOG = Logger.getLogger(Nachbarschaft.class);

  private Literal subjekt;
  private Collection<Literal> nachbarn;

  public Nachbarschaft(Literal subjekt, Collection<Literal> nachbarn) {
    this.subjekt = subjekt;
    this.nachbarn = nachbarn;
  }

  public Nachbarschaft(Cell subjekt, Collection<Cell> nachbarn) {
    this.subjekt = subjekt.getValue();
    this.nachbarn = new HashSet<Literal>();
    for (Cell cell : nachbarn) {
      this.nachbarn.add(cell.getValue());
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof Nachbarschaft) {
      Nachbarschaft that = (Nachbarschaft) other;
      if (this.subjekt.equals(that.subjekt) //
          && this.nachbarn.size() == that.nachbarn.size() //
          && this.nachbarn.containsAll(that.nachbarn)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return subjekt.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(subjekt).append(" ").append(nachbarn);
    return sb.toString();
  }

  public Literal getSubjekt() {
    return subjekt;
  }

  public void setSubjekt(Literal subjekt) {
    this.subjekt = subjekt;
  }

  public Collection<Literal> getNachbarn() {
    return nachbarn;
  }

  public void setNachbarn(Collection<Literal> nachbarn) {
    this.nachbarn = nachbarn;
  }

}

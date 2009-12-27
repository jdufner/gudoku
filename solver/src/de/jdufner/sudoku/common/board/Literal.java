// $Id: Literal.java,v 1.1 2009/11/17 20:34:32 jdufner Exp $

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
package de.jdufner.sudoku.common.board;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Typesafe number for content of a cell.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.1 $
 */
public final class Literal implements Comparable<Literal> {

  private static final Logger LOG = Logger.getLogger(Literal.class);

  public static final Literal EMPTY = getInstance(0);

  private final transient int value;

  private static class SingletonHolder {
    private static Map<Integer, Literal> instances = new HashMap<Integer, Literal>();
  }

  public static Literal getInstance(final int value) {
    Literal literal = null;
    if ((literal = SingletonHolder.instances.get(value)) == null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Erzeuge neues Literal " + value);
      }
      literal = new Literal(value);
      SingletonHolder.instances.put(value, literal);
    }
    return literal;
  }

  private Literal(final int value) {
    this.value = value;
  }

  @Override
  public int compareTo(final Literal other) {
    Literal myOther = other;
    if (myOther == null) {
      myOther = getInstance(0);
    }
    return value - myOther.value;
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof Literal) {
      final Literal that = (Literal) other;
      if (value == that.value) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public int getValue() {
    return value;
  }

}

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
package de.jdufner.sudoku.common.collections;

import java.util.Collection;
import java.util.Iterator;

/**
 * Diese Klasse repr�sentiert die Kandidaten einer Zelle. Derzeit ist es praktisch nur ein Marker.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 * @param <T>
 */
public final class SortedCandidates<T extends Comparable<? super T>> extends ExtendedTreeSet<T> implements Cloneable {

  /**
   * 
   */
  private static final long serialVersionUID = -5655214244193603450L;

  /**
   * Standardkonstruktor
   */
  public SortedCandidates() {
    super();
  }

  /**
   * Konstruktor f�r eine Collection.
   * 
   * @param collection
   *          Collection mit Elementen, kann auch leer sein.
   */
  public SortedCandidates(final Collection<T> collection) {
    super(collection);
  }

  public T get(final int searchIndex) {
    if (searchIndex < 0 || searchIndex >= size()) {
      throw new IllegalArgumentException("Ung�ltiger Index " + searchIndex + " liegt nicht zwischen 0 (inklusiv) und "
          + size() + " (exklusiv).");
    }
    final Iterator<T> iterator = iterator();
    int index = 0;
    while (iterator.hasNext()) {
      final T type = iterator.next();
      if (index == searchIndex) {
        return type;
      }
      index++;
    }
    return null;
  }

}

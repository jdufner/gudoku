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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;


/**
 * Die Kombination erzeugt alle Kombinationen aus einer �bergebenen {@link Collection}. In der {@link Collection} k�nnen
 * doppelte Elemente enthalten sein, aber diese werden ignoriert. Besser w�re es hier ein {@link Set} im Konstruktor
 * {@link #Kombination(Collection, int)} zu verwenden.
 * 
 * @param <T>
 *          Eine Klasse die das Interface {@link Comparable} implementiert.
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class Kombination<T extends Comparable<? super T>> {
  // TODO Iterator implementieren!
  private boolean initialized = false;
  private final Collection<T> collection;
  private final int laenge;

  private final ExtendedTreeSet<T> kombination = new ExtendedTreeSet<T>();
  private final ExtendedTreeSet<T> rest = new ExtendedTreeSet<T>();
  private T current;

  /**
   * Der Konstruktor erzeugt eine Instanz von Kombination. Damit sind noch keine Kombinationen erzeugt. Diese m�ssen mit
   * {@link #getKombination()} oder {@link #buildNextKombination()} erzeugt werden.
   * 
   * @param collection
   * @param laenge
   */
  public Kombination(final Collection<T> collection, final int laenge) {
    assert collection.size() >= laenge;
    this.collection = collection;
    this.laenge = laenge;
  }

  private void initialize() {
    rest.addAll(collection);
    for (int i = 0; i < laenge; i++) {
      T collection = !rest.isEmpty() ? rest.first() : null;
      kombination.add(collection);
      rest.remove(collection);
    }
    initialized = true;
  }

  /**
   * Gibt die aktuelle Kombination zur�ck.
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public Collection<T> getKombination() {
    // assert initialized;

    // Implementierungsalternative 1
    // SortedSet<T> sortedSet = new ExtendedTreeSet<T>();
    // sortedSet.addAll(kombination);
    // return sortedSet;

    // Implementierungsalternative 2
    // ohne ExtendedTreeSet#clone()
    return (Collection<T>) kombination.clone();
  }

  /**
   * Erzeugt die n�chtste m�gliche Kombination. Mit {@link #getKombination()} kann die Kombination dann geholt werden.
   */
  public void buildNextKombination() {
    if (!initialized) { // NOPMD by J�rgen on 11.11.09 22:55
      initialize();
    } else {
      determineCurrent();
      replaceCurrentByNextGreaterElement();
    }
  }

  private void determineCurrent() {
    final List<T> list = new ArrayList<T>();
    list.addAll(kombination);
    for (int i = list.size() - 1; i >= 0; i--) {
      if (list.get(i).compareTo(rest.last()) < 0) {
        current = list.get(i);
        return;
      }
    }
  }

  private void replaceCurrentByNextGreaterElement() {
    assert current.compareTo(rest.last()) < 0;
    final ExtendedTreeSet<T> kombinationTailSet = new ExtendedTreeSet<T>();
    kombinationTailSet.addAll(kombination.tailSet(current));
    kombination.removeAll(kombinationTailSet);
    rest.addAll(kombinationTailSet);
    final ExtendedTreeSet<T> restTailSet = new ExtendedTreeSet<T>();
    restTailSet.addAll(rest.getSmallestElementsGreatherThan(current, kombinationTailSet.size()));
    rest.removeAll(restTailSet);
    kombination.addAll(restTailSet);
  }

  /**
   * Pr�ft ob die noch eine weitere, nicht bereits erzeugte, Kombination m�glich ist.
   * 
   * @return <code>true</code>, wenn eine weitere Kombination erzeugt werden kann, sonst <code>false</code>.
   */
  public boolean hasNextKombination() {
    T kleinstesInKombination;
    if (kombination.isEmpty()) {
      kleinstesInKombination = null;
    } else {
      kleinstesInKombination = kombination.first();
    }
    T groesstesInRest;
    if (rest.isEmpty()) {
      groesstesInRest = null;
    } else {
      groesstesInRest = rest.last();
    }
    if (kleinstesInKombination != null && groesstesInRest != null
        && kleinstesInKombination.compareTo(groesstesInRest) > 0) {
      return false;
    }
    if (kombination.size() == laenge && rest.isEmpty()) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder(); // NOPMD by J�rgen on 11.11.09 22:54
    sb.append("Kombination=").append(kombination).append("; ");
    sb.append("Rest=").append(rest).append("; ");
    return sb.toString();
  }

}

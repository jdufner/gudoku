// $Id: Candidates.java,v 1.3 2009/12/05 22:16:16 jdufner Exp $

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.collections.SortedCandidates;

/**
 * Die Klasse Candidates ist die normale Repr�sentation der Kandidaten einer Zelle {@link Cell}. Daneben gibt es nocht
 * die sortierten Kandidaten {@link Candidates}, die allerdings nur f�r spezielle Algorithmen ben�tigt werden. Diese
 * Implementierung wird standardm��ig verwendet, weil die {@link #add(Object)}-Operation schneller sind.
 * 
 * TODO K�nftig nicht mehr von {@link ArrayList} ableiten. Problem: Literale k�nnen doppelt hinzugef�gt werden.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.3 $
 */
public final class Candidates<T extends Comparable<? super T>> extends ArrayList<T> implements Cloneable {

  private static final Logger LOG = Logger.getLogger(Candidates.class);

  /**
   * 
   */
  private static final long serialVersionUID = -797343079629100287L;

  /**
   * Standardkonstruktor
   */
  public Candidates() {
    super();
  }

  /**
   * Erzeuge eine Kandidatenliste mit angegebener Anzahl Elementen.
   * 
   * @param initialCapacity
   */
  public Candidates(final int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Konstruktor f�r eine Collection.
   * 
   * @param collection
   *          Collection mit Elementen, kann auch leer sein.
   */
  public Candidates(final Collection<T> collection) {
    super(collection);
  }

  /**
   * 
   * @return Gibt die Kandidaten als {@link SortedSet} zur�ck.
   */
  public SortedCandidates<T> getSorted() {
    return new SortedCandidates<T>(this);
  }

  /**
   * @param cell
   * @return <code>true</code>, genau dann wenn die Kandidaten der mit denen des �bergebenen Felds gleich sind, sonst
   *         <code>false</code>.
   */
  public boolean isEquals(final Collection<T> that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (size() == that.size() && containsAll(that) && that.containsAll(this)) {
      return true;
    }
    return false;
  }

  /**
   * @param testCandidates
   * @return <code>true</code>, genau dann wenn h�chstens die �bergebenen Testkandidaten vorhanden in den Kandidaten der
   *         Zelle enthalten sind, sonst <code>false</code>, also es existiert mindestens ein Zellkandidat, der nicht in
   *         der Liste der Testkandidaten enthalten ist.
   */
  public boolean containsAtMost(final Collection<T> testCandidates) {
    for (T thisCandidate : this) {
      if (!testCandidates.contains(thisCandidate)) {
        return false;
      }
    }
    return true;
  }

  /**
   * @param testCandidates
   * @return <code>true</code>, genau dann wenn mindestens die �bergebenen Testkandidaten in den Kandidaten der Zelle
   *         enthalten sind, sonst <code>false</code>, also es existiert mindestens ein Testkandidat, der in der Liste
   *         der Zellkandidaten nicht enthalten ist.
   */
  public boolean containsAtLeast(final Collection<T> testCandidates) {
    for (T testCandidate : this) {
      if (!this.contains(testCandidate)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void add(final int index, final T element) {
    if (contains(element)) {
      LOG.info(this + " enth�lt bereits " + element + " und wird nicht erneut hinzugef�gt.");
    } else {
      super.add(index, element);
    }
  }

  @Override
  public boolean add(final T element) {
    if (contains(element)) {
      LOG.info(this + " enth�lt bereits " + element + " und wird nicht erneut hinzugef�gt.");
      return false;
    } else {
      return super.add(element);
    }
  }

  @Override
  public Candidates<T> clone() {
    return (Candidates<T>) super.clone();
  }

}

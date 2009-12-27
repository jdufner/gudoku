// $Id: Candidates.java,v 1.3 2009/12/05 22:16:16 jdufner Exp $

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.collections.SortedCandidates;

/**
 * Die Klasse Candidates ist die normale Repräsentation der Kandidaten einer Zelle {@link Cell}. Daneben gibt es nocht
 * die sortierten Kandidaten {@link Candidates}, die allerdings nur für spezielle Algorithmen benötigt werden. Diese
 * Implementierung wird standardmäßig verwendet, weil die {@link #add(Object)}-Operation schneller sind.
 * 
 * TODO Künftig nicht mehr von {@link ArrayList} ableiten. Problem: Literale können doppelt hinzugefügt werden.
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
   * Konstruktor für eine Collection.
   * 
   * @param collection
   *          Collection mit Elementen, kann auch leer sein.
   */
  public Candidates(final Collection<T> collection) {
    super(collection);
  }

  /**
   * 
   * @return Gibt die Kandidaten als {@link SortedSet} zurück.
   */
  public SortedCandidates<T> getSorted() {
    return new SortedCandidates<T>(this);
  }

  /**
   * @param cell
   * @return <code>true</code>, genau dann wenn die Kandidaten der mit denen des übergebenen Felds gleich sind, sonst
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
   * @return <code>true</code>, genau dann wenn höchstens die übergebenen Testkandidaten vorhanden in den Kandidaten der
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
   * @return <code>true</code>, genau dann wenn mindestens die übergebenen Testkandidaten in den Kandidaten der Zelle
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
      LOG.info(this + " enthält bereits " + element + " und wird nicht erneut hinzugefügt.");
    } else {
      super.add(index, element);
    }
  }

  @Override
  public boolean add(final T element) {
    if (contains(element)) {
      LOG.info(this + " enthält bereits " + element + " und wird nicht erneut hinzugefügt.");
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

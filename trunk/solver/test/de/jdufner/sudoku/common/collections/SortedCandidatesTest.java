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
package de.jdufner.sudoku.common.collections;

import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">Jürgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class SortedCandidatesTest extends AbstractSolverTestCase {

  public void testCloneEmpty() {
    SortedCandidates<String> empty = new SortedCandidates<String>();
    SortedCandidates<String> clone = (SortedCandidates<String>) empty.clone();
    assertEquals(empty, clone);
    assertEquals(clone, empty);
    assertNotSame(empty, clone);
    assertNotSame(clone, empty);
  }

  public void testCloneOne1() {
    SortedCandidates<String> one = new SortedCandidates<String>();
    one.add("a");
    SortedCandidates<String> clone = (SortedCandidates<String>) one.clone();
    assertEquals(one, clone);
    assertEquals(clone, one);
    assertNotSame(one, clone);
    assertNotSame(clone, one);
    assertEquals(1, one.size());
    assertEquals(1, clone.size());
    assertEquals(one.size(), clone.size());
  }

  public void testCloneOne2() {
    SortedCandidates<String> one = new SortedCandidates<String>();
    one.add("a");
    one.add("a");
    SortedCandidates<String> clone = (SortedCandidates<String>) one.clone();
    assertEquals(one, clone);
    assertEquals(clone, one);
    assertNotSame(one, clone);
    assertNotSame(clone, one);
    assertEquals(1, one.size());
    assertEquals(1, clone.size());
    assertEquals(one.size(), clone.size());
  }

  public void testCloneTwo() {
    SortedCandidates<String> two = new SortedCandidates<String>();
    two.add("a");
    two.add("b");
    SortedCandidates<String> clone = (SortedCandidates<String>) two.clone();
    assertEquals(two, clone);
    assertEquals(clone, two);
    assertNotSame(two, clone);
    assertNotSame(clone, two);
    assertEquals(2, two.size());
    assertEquals(2, clone.size());
    assertEquals(two.size(), clone.size());
  }

}

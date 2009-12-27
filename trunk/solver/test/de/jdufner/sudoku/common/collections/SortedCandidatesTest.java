// $Id: SortedCandidatesTest.java,v 1.1 2009/11/14 20:18:58 jdufner Exp $

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

import de.jdufner.sudoku.common.collections.SortedCandidates;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.1 $
 */
public class SortedCandidatesTest extends TestCase {

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

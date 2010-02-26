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

import de.jdufner.sudoku.common.collections.ExtendedTreeSet;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class ExtendedTreeSetTest extends TestCase {

  ExtendedTreeSet<Integer> set = new ExtendedTreeSet<Integer>();

  @Override
  public void setUp() {
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(4);
    set.add(5);
    set.add(6);
    set.add(7);
  }

  public void testGetSmallestElementGreatherThan1() {
    assertEquals(Integer.valueOf(5), set.getSmallestElementGreatherThan(4));
  }

  public void testGetSmallestElementGreatherThan2() {
    assertNull(set.getSmallestElementGreatherThan(7));
  }

  public void testGetGreatestElementSmallerThan1() {
    assertEquals(Integer.valueOf(4), set.getGreatestElementSmallerThan(5));
  }

  public void testGetGreatestElementSmallerThan2() {
    assertNull(set.getGreatestElementSmallerThan(1));
  }

  public void testHeadSetBySize1() {
    assertEquals(2, set.headSetBySize(2, true).size());
  }

  public void testHeadSetBySize2() {
    assertEquals(2, set.headSetBySize(2, false).size());
  }

  public void testHeadSetBySize3() {
    assertEquals(7, set.headSetBySize(10, true).size());
  }

  public void testHeadSetBySize4() {
    assertNull(set.headSetBySize(10, false));
  }

  public void testGetSmallestElementsGreaterThan1() {
    assertEquals(1, set.getSmallestElementsGreatherThan(4, 1).size());
    assertTrue(set.getSmallestElementsGreatherThan(4, 1).contains(5));
  }

  public void testGetSmallestElementsGreaterThan2() {
    assertNull(set.getSmallestElementsGreatherThan(7, 1));
  }

  public void testGetSmallestElementsGreaterThan3() {
    assertEquals(2, set.getSmallestElementsGreatherThan(4, 2).size());
    assertTrue(set.getSmallestElementsGreatherThan(4, 2).contains(5));
    assertTrue(set.getSmallestElementsGreatherThan(4, 2).contains(6));
  }

  public void testContainsGreaterElementThan1() {
    assertTrue(set.containsGreaterElementThan(0));
  }

  public void testContainsGreaterElementThan2() {
    assertTrue(set.containsGreaterElementThan(4));
  }

  public void testContainsGreaterElementThan3() {
    assertFalse(set.containsGreaterElementThan(7));
  }

  public void testContainsGreaterElementThan5() {
    assertFalse(set.containsGreaterElementThan(10));
  }

}
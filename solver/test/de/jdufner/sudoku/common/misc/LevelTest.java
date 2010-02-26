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
package de.jdufner.sudoku.common.misc;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class LevelTest extends AbstractSolverTestCase {

  private static final Logger LOG = Logger.getLogger(LevelTest.class);

  public void testCompareTo() {
    assertTrue(Level.SEHR_LEICHT.compareTo(Level.LEICHT) < 0);
    assertTrue(Level.SEHR_LEICHT.compareTo(Level.MITTEL) < 0);
    assertTrue(Level.SEHR_LEICHT.compareTo(Level.SCHWER) < 0);
    assertTrue(Level.SEHR_LEICHT.compareTo(Level.SEHR_SCHWER) < 0);
    assertTrue(Level.LEICHT.compareTo(Level.SEHR_LEICHT) > 0);
    assertTrue(Level.LEICHT.compareTo(Level.MITTEL) < 0);
    assertTrue(Level.LEICHT.compareTo(Level.SCHWER) < 0);
    assertTrue(Level.LEICHT.compareTo(Level.SEHR_SCHWER) < 0);
    assertTrue(Level.MITTEL.compareTo(Level.SEHR_LEICHT) > 0);
    assertTrue(Level.MITTEL.compareTo(Level.LEICHT) > 0);
    assertTrue(Level.MITTEL.compareTo(Level.SCHWER) < 0);
    assertTrue(Level.MITTEL.compareTo(Level.SEHR_SCHWER) < 0);
    assertTrue(Level.SCHWER.compareTo(Level.SEHR_LEICHT) > 0);
    assertTrue(Level.SCHWER.compareTo(Level.LEICHT) > 0);
    assertTrue(Level.SCHWER.compareTo(Level.MITTEL) > 0);
    assertTrue(Level.SCHWER.compareTo(Level.SEHR_SCHWER) < 0);
    assertTrue(Level.SEHR_SCHWER.compareTo(Level.SEHR_LEICHT) > 0);
    assertTrue(Level.SEHR_SCHWER.compareTo(Level.LEICHT) > 0);
    assertTrue(Level.SEHR_SCHWER.compareTo(Level.MITTEL) > 0);
    assertTrue(Level.SEHR_SCHWER.compareTo(Level.SCHWER) > 0);
  }

  public void testIntToEnum() {
    Level l;
    try {
      l = Level.valueOf(Level.LEICHT.getName());
      LOG.debug(l);
      fail();
    } catch (IllegalArgumentException iae) {
      LOG.debug(iae.getMessage(), iae);
    }
    l = Level.valueOf(2);
    assertEquals(Level.LEICHT, l);
  }

}

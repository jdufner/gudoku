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

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.collections.Kombination;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class KombinationTest extends TestCase {

  private static final Logger LOG = Logger.getLogger(KombinationTest.class);

  public void testB() {
    Collection<String> collection = new ArrayList<String>();
    collection.add("a");
    collection.add("b");
    collection.add("c");
    collection.add("d");
    collection.add("e");
    Kombination<String> kombination = new Kombination<String>(collection, 3);
    LOG.info(kombination);
    int i = 0;
    while (kombination.hasNextKombination() && i < 20) {
      i++;
      kombination.buildNextKombination();
      LOG.info(kombination);
    }
  }

  public void testC() {
    Collection<String> collection = new ArrayList<String>();
    collection.add("a");
    collection.add("b");
    Kombination<String> kombination = new Kombination<String>(collection, 2);
    LOG.info(kombination);
    int i = 0;
    while (kombination.hasNextKombination() && i < 20) {
      i++;
      kombination.buildNextKombination();
      LOG.info(kombination);
    }
  }

}

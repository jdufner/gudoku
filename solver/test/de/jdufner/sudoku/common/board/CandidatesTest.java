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
package de.jdufner.sudoku.common.board;

import java.util.ArrayList;
import java.util.Collection;

import de.jdufner.sudoku.test.AbstractSolverTestCase;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 13.03.2010
 * @version $Revision$
 */
public final class CandidatesTest extends AbstractSolverTestCase {

  public void testContainsAtLeastOneOf1() throws Exception {
    final Candidates<Literal> candidates = new Candidates<Literal>();
    candidates.add(Literal.getInstance(1));
    candidates.add(Literal.getInstance(2));
    candidates.add(Literal.getInstance(3));
    final Collection<Literal> testCandidates = new ArrayList<Literal>();
    testCandidates.add(Literal.getInstance(1));
    assertTrue("Testkandidat ist in Kandidatenliste enthalten", candidates.containsAtLeastOneOf(testCandidates));
  }

  public void testContainsAtLeastOneOf2() throws Exception {
    //    final Collection<Literal> c = new ArrayList<Literal>();
    //    c.add(Literal.getInstance(1));
    //    c.add(Literal.getInstance(2));
    //    c.add(Literal.getInstance(3));
    final Candidates<Literal> candidates = new Candidates<Literal>();
    candidates.add(Literal.getInstance(1));
    candidates.add(Literal.getInstance(2));
    candidates.add(Literal.getInstance(3));
    final Collection<Literal> testCandidates = new ArrayList<Literal>();
    testCandidates.add(Literal.getInstance(4));
    //    assertFalse(c.contains(Literal.getInstance(4)));
    assertFalse("Testkandidat ist in Kandidatenliste nicht enthalten", candidates.containsAtLeastOneOf(testCandidates));
  }
}

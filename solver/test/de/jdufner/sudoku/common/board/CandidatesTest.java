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

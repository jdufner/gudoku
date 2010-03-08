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
package de.jdufner.sudoku;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2010-03-06
 * @version $Revision$
 */
public abstract class AbstractMainClass {

  private static Logger LOG = Logger.getLogger(AbstractMainClass.class);

  /**
   * Startet die Applikation und f�hrt die allgemeine Logik vorher und nachher aus.
   * 
   * @throws Exception
   */
  protected void start() throws Exception {
    setUp();
    run();
    tearDown();
  }

  /**
   * Allgemeine Logik vor dem Ausf�hren der Applikation.
   */
  protected void setUp() {

  }

  /**
   * Allgemeine Logik nach dem Ausf�hren der Applikation.
   */
  protected void tearDown() {
    LOG.debug("ExecutorService runterfahren");
    SolverServiceFactory.INSTANCE.shutdown();
  }

  /**
   * Methode, welche die eigentliche Logik der Applikation enth�lt.
   * 
   * @throws Exception
   */
  protected abstract void run() throws Exception;

}

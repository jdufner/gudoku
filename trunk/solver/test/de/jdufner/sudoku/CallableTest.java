// $Id: CallableTest.java,v 1.3 2009/11/11 22:47:21 jdufner Exp $

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.3 $
 */
public final class CallableTest extends TestCase {
  private static final Logger LOG = Logger.getLogger(CallableTest.class);
  private static final ExecutorService ES = Executors.newFixedThreadPool(3);

  public void testIsDone() throws InterruptedException {
    long start = System.currentTimeMillis(), end = System.currentTimeMillis();
    LOG.info("a) " + (end - start) + " ms");
    List<WaitSeconds> waiter = new ArrayList<WaitSeconds>();
    waiter.add(new WaitSeconds(2));
    waiter.add(new WaitSeconds(4));
    waiter.add(new WaitSeconds(6));
    end = System.currentTimeMillis();
    LOG.info("b) " + (end - start) + " ms");
    List<Future<Boolean>> results = ES.invokeAll(waiter);
    end = System.currentTimeMillis();
    LOG.info("c) " + (end - start) + " ms");
    if (results.get(0).isDone() && results.get(1).isDone() && results.get(2).isDone()) {
      end = System.currentTimeMillis();
      LOG.info("d) " + (end - start) + " ms");
    }
    end = System.currentTimeMillis();
    LOG.info("e) " + (end - start) + " ms");
  }

  private class WaitSeconds implements Callable<Boolean> {
    private int seconds = 10;

    private WaitSeconds(int seconds) {
      this.seconds = seconds;
    }

    @Override
    public Boolean call() {
      long start = 0, end = 0;
      try {
        start = System.currentTimeMillis();
        Thread.sleep(seconds * 1000);
        end = System.currentTimeMillis();
      } catch (InterruptedException ie) {
        CallableTest.LOG.error(ie.getMessage(), ie);
      } finally {
        CallableTest.LOG.error((end - start) + " ms waited.");
      }
      return false;
    }
  }

}


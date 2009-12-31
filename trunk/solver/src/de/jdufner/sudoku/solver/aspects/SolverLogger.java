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
package de.jdufner.sudoku.solver.aspects;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.solver.service.Solution;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SolverLogger implements MethodInterceptor {

  private static final Logger LOG = Logger.getLogger(SolverLogger.class);
  private static final String ERGEBNIS = "R�tsel ";
  private static final String GELOEST = "gel�st!";
  private static final String NICHT_GELOEST = "nicht gel�st!";

  public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
    final Sudoku sudoku = (Sudoku) methodInvocation.getArguments()[0];
    LOG.info(ERGEBNIS + sudoku.toShortString());
    final Object obj = methodInvocation.proceed();
    if (obj instanceof Solution) {
      final Solution solution = (Solution) obj;
      LOG.info(solution);
    } else if (obj instanceof Sudoku) {
      final Sudoku result = (Sudoku) obj;
      if (result == null) {
        LOG.info(ERGEBNIS + NICHT_GELOEST);
      } else {
        LOG.info(ERGEBNIS + GELOEST + " " + result.toShortString());
      }
    } else {
      LOG.info(ERGEBNIS + obj.toString());
      // TODO Warum?
      // throw new IllegalStateException("Methode wurde mit unerwartetem Parameter (nicht " + Sudoku.class.getName()
      // + ", sondern " + obj.getClass().getName() + ") aufgerufen. " + obj.toString());
    }
    return obj;
  }
}

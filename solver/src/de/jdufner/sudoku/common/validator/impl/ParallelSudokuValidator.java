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
package de.jdufner.sudoku.common.validator.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.validator.SudokuValidator;
import de.jdufner.sudoku.context.SolverServiceFactory;

/**
 * Pr�ft mit einem parallel Algorithmus ob das Sudoku g�ltig ist.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class ParallelSudokuValidator implements SudokuValidator {

  private static final Logger LOG = Logger.getLogger(Sudoku.class);

  @Override
  public boolean isValid(final Sudoku sudoku) {
    try {
      final AtomicBoolean validity = new AtomicBoolean(true);
      final Collection<UnitValidChecker> checkTasks = new ArrayList<UnitValidChecker>();
      checkTasks.add(new UnitValidChecker(validity, sudoku.getBlocks()));
      checkTasks.add(new UnitValidChecker(validity, sudoku.getColumns()));
      checkTasks.add(new UnitValidChecker(validity, sudoku.getRows()));
      SolverServiceFactory.getInstance().getExecutorService().invokeAll(checkTasks);
      return validity.get();
    } catch (InterruptedException ie) {
      LOG.error(ie.getMessage(), ie);
      return false;
    }
  }

}

// $Id: UnitValidChecker.java,v 1.2 2009/11/17 20:34:33 jdufner Exp $

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

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import de.jdufner.sudoku.common.board.Unit;

/**
 * Die Klasse pr�ft ob die �bergebenen Einheit g�ltig sind.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.2 $
 */
public final class UnitValidChecker implements Callable<Boolean> {
  // private static final Logger LOG = Logger.getLogger(UnitValidChecker.class);

  private final transient AtomicBoolean validity;
  private final transient Collection<? extends Unit> units;

  public UnitValidChecker(final AtomicBoolean validity, final Collection<? extends Unit> units) {
    this.validity = validity;
    this.units = units;
  }

  @Override
  public Boolean call() {
    for (Unit unit : units) {
      if (validity.get()) {
        final boolean unitValidity = unit.isValid();
        if (!unitValidity) {
          validity.set(unitValidity);
          return unitValidity;
        }
      } else {
        return false;
      }
    }
    return true;
  }

}

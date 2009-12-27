// $Id: Strategy.java,v 1.10 2009/11/28 00:25:12 jdufner Exp $

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
package de.jdufner.sudoku.solver.strategy;

import de.jdufner.sudoku.commands.AbstractCommand;
import de.jdufner.sudoku.common.misc.Level;

/**
 * TODO
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since
 * @version $Revision: 1.10 $
 */
public interface Strategy {

  /**
   * @return Gibt den Schwierigkeitsgrad der Strategie zur�ck.
   */
  Level getLevel();

  /**
   * F�hrt eine konkrete Strategie.
   * 
   * @return Gibt beliebig viele {@link AbstractCommand}-Objekte zur�ck.
   */
  StrategyResult execute();

  /**
   * 
   * @return <code>true</code>, wenn f�r das bearbeitete Sudoku genau ein L�sung existiert, sonst <code>false</code>.
   */
  boolean isSudokuUnique();

}

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
package de.jdufner.sudoku.commands;

import de.jdufner.sudoku.common.board.Sudoku;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since
 * @version $Revision$
 */
public interface Command {

  /**
   * F�hrt den Befehl auf dem �bergebenen {@link Sudoku} aus.
   * 
   * TODO Pr�fen, dass nach Ausf�hrung eines Commands die Zelle, der Block, die Spalte und die Reihe valide sind.
   * 
   * @param sudoku
   *          Das Sudoku auf dem der Befehl ausgef�hrt werden soll.
   */
  void execute(Sudoku sudoku);

  /**
   * Macht den Befehl auf dem �bergebenen {@link Sudoku} r�ckg�ngig.
   * 
   * @param sodoku
   *          Das Sudoku auf dem der Befehl r�ckg�ngig gemacht werden soll.
   */
  void unexecute(Sudoku sudoku);

  /**
   * Gibt an, ob der Befehl r�ckg�ngig gemacht werden kann.
   * 
   * @return <code>true</code>, wenn der Befehl r�ckg�ngig gemacht werden kann, sonst <code>false</code>.
   */
  boolean reversible();

  /**
   * Gibt an, ob der Befehl erfolgreich (es wurde mindestens eine Zelle oder Kandidat ver�ndert) ausgef�hrt wurde.
   * 
   * @return <code>true</code>, wenn der Befehl erfolgreich ausgef�hrt wurde und mindestens ein Kandidat entfernt oder
   *         eine Zelle gesetzt wurde, sonst <code>false</code>.
   */
  boolean isSuccessfully();

  /**
   * Liefert das Ergebnis der {@link #toString()}-Methode zum Zeitpunkt der Erstellung des Befehls zur�ck.
   * 
   * @return Das Ergebnis der {@link #toString()}-Methode zum Zeitpunkt der Erstellung des Befehls.
   */
  String getFrozenString();

  /**
   * TODO Dokumentieren!
   * 
   * @return
   */
  int getRowIndex();

  /**
   * TODO Dokumentieren!
   * 
   * @return
   */
  int getColumnIndex();
}

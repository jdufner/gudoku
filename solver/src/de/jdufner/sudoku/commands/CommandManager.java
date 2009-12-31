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

import java.util.Stack;

import de.jdufner.sudoku.common.board.Sudoku;

/**
 * Der CommandManager f�hrt Befehle ({@link AbstractCommand}) aus oder macht sie r�ckg�ngig, wenn m�glich.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class CommandManager {

  /**
   * Die bereits ausgef�hrten Befehle ({@link Command})
   */
  private final transient Stack<Command> previousCommands = new Stack<Command>();
  /**
   * Die noch auszuf�hrenden Befehle ({@link Command}).
   */
  private final transient Stack<Command> nextCommands = new Stack<Command>();

  /**
   * F�hrt den n�chsten Befehl aus.
   * 
   * @param sudoku
   *          Das {@link Sudoku}, auf dem der Befehl ({@link AbstractCommand}) auszuf�hren ist.
   * @return Der oberste Befehl ({@link AbstractCommand}) auf dem Stapel.
   */
  public Command redoCommand(final Sudoku sudoku) {
    Command command = null;
    if (isRedoPossible()) {
      command = nextCommands.pop();
      command.execute(sudoku);
      previousCommands.push(command);
    }
    return command;
  }

  /**
   * @return <code>true</code>, wenn Befehle ({@link AbstractCommand}) zur Ausf�hrung vorliegen, sonst
   *         <code>false</code>.
   */
  public boolean isRedoPossible() {
    return !nextCommands.isEmpty();
  }

  /**
   * Macht den letzten Befehl ({@link AbstractCommand})r�ckg�ngig.
   * 
   * @param sudoku
   *          Das {@link Sudoku}, auf dem der Befehl ({@link AbstractCommand}) r�ckg�ngig gemacht werden soll.
   * @return Der der letzte Befehl ({@link AbstractCommand}), der r�ckg�ngig zu machen ist.
   */
  public Command undoCommand(final Sudoku sudoku) {
    Command command = null;
    if (isUndoPossible()) {
      command = previousCommands.pop();
      command.unexecute(sudoku);
      nextCommands.push(command);
    }
    return command;
  }

  /**
   * @return <code>true</code>, wenn Befehle ({@link AbstractCommand}) r�ckg�ngig gemacht werden k�nnen, sonst
   *         <code>false</code>.
   */
  public boolean isUndoPossible() {
    return !previousCommands.isEmpty();
  }

  /**
   * @param sudoku
   * @param command
   */
  public void doCommand(final Sudoku sudoku, final Command command) {
    command.execute(sudoku);
    previousCommands.push(command);
    nextCommands.clear();
  }

}

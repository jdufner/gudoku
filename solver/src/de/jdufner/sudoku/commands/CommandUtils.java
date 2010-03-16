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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 14.03.2010
 * @version $Revision$
 */
public final class CommandUtils {

  private CommandUtils() {
  }

  public static boolean isEqual(final Collection<Command> commands1, final Collection<Command> commands2) {
    return isEqualSet(commands1, commands2);
  }

  private static boolean isEqualCollection(final Collection<Command> commands1, final Collection<Command> commands2) {
    return (commands1.containsAll(commands2) && commands2.containsAll(commands1));
  }

  private static boolean isEqualSet(final Collection<Command> commands1, final Collection<Command> commands2) {
    final Set<Command> cmd1 = new HashSet<Command>(commands1);
    final Set<Command> cmd2 = new HashSet<Command>(commands2);
    return (cmd1.containsAll(cmd2) && cmd2.containsAll(cmd1));
  }

}

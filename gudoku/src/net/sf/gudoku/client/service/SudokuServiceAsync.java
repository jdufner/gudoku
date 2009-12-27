// $Id: SudokuServiceAsync.java,v 1.4 2009/11/11 23:11:22 jdufner Exp $

/*
 * Gudoku (http://sourceforge.net/projects/gudoku)
 * Sudoku-Implementierung auf Basis des Google Webtoolkit 
 * (http://code.google.com/webtoolkit/). Die Lösungsalgorithmen in Java laufen 
 * parallel. Die Sudoku-Rätsel werden mittels JDBC in einer Datenbank
 * gespeichert.
 * 
 * Copyright (C) 2008 Jürgen Dufner
 *
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der 
 * GNU General Public License, wie von der Free Software Foundation 
 * veröffentlicht, weitergeben und/oder modifizieren, entweder gemäß Version 3 
 * der Lizenz oder (nach Ihrer Option) jeder späteren Version.
 *
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen 
 * von Nutzen sein wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die 
 * implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN 
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem 
 * Programm erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
 *
 */
package net.sf.gudoku.client.service;

import net.sf.gudoku.client.dto.Cell;
import net.sf.gudoku.client.dto.Command;
import net.sf.gudoku.client.dto.Game;
import net.sf.gudoku.client.dto.SudokuSize;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.4 $
 */
public interface SudokuServiceAsync {

  public void getSudokuSize(AsyncCallback<SudokuSize> callback);

  public void newGame(int id, AsyncCallback<Game> callback);

  public void doCommand(Command command, AsyncCallback<Cell> callback);

  public void undo(AsyncCallback<Cell> callback);

  public void redo(AsyncCallback<Cell> callback);

}
/*
 * $Log: SudokuServiceAsync.java,v $
 * Revision 1.4  2009/11/11 23:11:22  jdufner
 * Migration to GWT 1.7
 * Revision 1.3 2008/05/16 03:46:25 jdufner Lade Sudoku jetzt aus Datei und zähle id
 * in einem Cookie
 * 
 * Revision 1.2 2008/05/09 22:48:46 jdufner Javadoc Version-Tag auf CVS keyword Revision gesetzt Revision 1.1.1.1
 * 2008/05/09 20:34:09 jdufner Initial Check-In
 */

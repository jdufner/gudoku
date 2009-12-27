// $Id: SudokuService.java,v 1.7 2009/11/11 23:11:22 jdufner Exp $

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
package net.sf.gudoku.client.service;

import net.sf.gudoku.client.dto.Cell;
import net.sf.gudoku.client.dto.Command;
import net.sf.gudoku.client.dto.Game;
import net.sf.gudoku.client.dto.SudokuSize;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Das sind die Services, die entfernt, also vom Client am Server, aufgerufen werden.
 * 
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.7 $
 */
@RemoteServiceRelativePath("sudoku")
public interface SudokuService extends RemoteService {

  /**
   * @return Die Gr��e des Sudokus.
   */
  public SudokuSize getSudokuSize();

  /**
   * @return Liefert das Sudoku zur zugeh�rigen ID aus der Datenbank zur�ck.
   */
  public Game newGame(int id);

  /**
   * F�hrt einen Zug aus.
   * 
   * @param command
   *          Der Zug, der ausgef�hrt werden soll.
   * @return Die Zellen, deren Wert gesetzt wurde.
   */
  public Cell doCommand(Command command);

  /**
   * Macht, falls m�glich, den letzten Zug r�ckg�ngig.
   * 
   * @return Die Zelle, deren Wert zur�ck gesetzt wurde.
   */
  public Cell undo();

  /**
   * Wiederholt, falls m�glich, den letzten Zug.
   * 
   * @return Die Zelle, deren Wert wieder gesetzt wurde.
   */
  public Cell redo();

  /**
   * @return Liefert einen Tipp, eine besetzte Zelle Cell.
   */
  // public Cell getHint();
}
/*
 * $Log: SudokuService.java,v $
 * Revision 1.7  2009/11/11 23:11:22  jdufner
 * Migration to GWT 1.7
 * Revision 1.6 2008/05/16 13:55:55 jdufner kleines Namensrefactoring
 * 
 * Revision 1.5 2008/05/16 13:24:39 jdufner Zeige Spiel-ID und Anzahl der gesetzten Felder, kleine Namensrefactorings
 * 
 * Revision 1.4 2008/05/16 03:46:23 jdufner Lade Sudoku jetzt aus Datei und z�hle id in einem Cookie
 * 
 * Revision 1.3 2008/05/14 04:02:55 jdufner Anpassung Pfad, Rechtschreibfehler korrigiert
 * 
 * Revision 1.2 2008/05/09 22:48:46 jdufner Javadoc Version-Tag auf CVS keyword Revision gesetzt
 * 
 * Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

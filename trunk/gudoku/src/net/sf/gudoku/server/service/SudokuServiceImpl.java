// $Id: SudokuServiceImpl.java,v 1.8 2009/11/17 20:34:23 jdufner Exp $

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
package net.sf.gudoku.server.service;

import javax.servlet.http.HttpSession;

import net.sf.gudoku.client.dto.Cell;
import net.sf.gudoku.client.dto.Command;
import net.sf.gudoku.client.dto.Game;
import net.sf.gudoku.client.dto.SudokuSize;
import net.sf.gudoku.client.service.SudokuService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.8 $
 */
public class SudokuServiceImpl extends RemoteServiceServlet implements SudokuService {

  private static final long serialVersionUID = 1L;

  private de.jdufner.sudoku.common.board.SudokuSize sudokuSize = de.jdufner.sudoku.common.board.SudokuSize.DEFAULT;

  public SudokuSize getSudokuSize() {
    return SudokuMapper.mapServer2Client(sudokuSize);
  }

  public Game newGame(int id) {
    HttpSession session = getThreadLocalRequest().getSession();
    de.jdufner.sudoku.Game game = new de.jdufner.sudoku.Game(id);
    session.setAttribute(Game.class.getSimpleName(), game);
    Game clientGame = SudokuMapper.mapServer2Client(game);
    return clientGame;
  }

  public Cell doCommand(Command command) {
    HttpSession session = getThreadLocalRequest().getSession();
    de.jdufner.sudoku.Game game = (de.jdufner.sudoku.Game) session.getAttribute(Game.class.getSimpleName());
    de.jdufner.sudoku.common.board.Cell serverCell = game.doCommand(SudokuMapper.mapClient2Server(command));
    Cell cell = SudokuMapper.mapServer2Client(serverCell);
    cell.setUndoPossible(game.isUndoPossible());
    cell.setRedoPossible(game.isRedoPossible());
    cell.setNumberFixed(game.getQuest().getNumberOfFixed());
    cell.setCorrect(game.isCorrect(serverCell.getRowIndex(), serverCell.getColumnIndex()));
    return cell;
  }

  public Cell redo() {
    HttpSession session = getThreadLocalRequest().getSession();
    de.jdufner.sudoku.Game game = (de.jdufner.sudoku.Game) session.getAttribute(Game.class.getSimpleName());
    Cell cell = SudokuMapper.mapServer2Client(game.redo());
    cell.setUndoPossible(game.isUndoPossible());
    cell.setRedoPossible(game.isRedoPossible());
    cell.setNumberFixed(game.getQuest().getNumberOfFixed());
    return cell;
  }

  public Cell undo() {
    HttpSession session = getThreadLocalRequest().getSession();
    de.jdufner.sudoku.Game game = (de.jdufner.sudoku.Game) session.getAttribute(Game.class.getSimpleName());
    Cell cell = SudokuMapper.mapServer2Client(game.undo());
    cell.setUndoPossible(game.isUndoPossible());
    cell.setRedoPossible(game.isRedoPossible());
    cell.setNumberFixed(game.getQuest().getNumberOfFixed());
    return cell;
  }

  // public Cell getHint() {
  // // TODO Auto-generated method stub
  // return null;
  // }

}
/*
 * $Log: SudokuServiceImpl.java,v $
 * Revision 1.8  2009/11/17 20:34:23  jdufner
 * Refactoring: Klassen verschoben
 *
 * Revision 1.7  2008/06/06 21:07:47  jdufner
 * Default-SerialUID eingebaut
 *
 * Revision 1.6  2008/06/02 17:46:54  jdufner
 * CVS Kommentar korrigiert, Game erweitert für Gudoku
 *
 * Revision 1.5  2008/05/16 13:24:41  jdufner
 * Zeige Spiel-ID und Anzahl der gesetzten Felder,
 * kleine Namensrefactorings
 *
 * Revision 1.4  2008/05/16 03:46:25  jdufner
 * Lade Sudoku jetzt aus Datei und zähle id in einem Cookie
 *
 * Revision 1.3  2008/05/15 21:41:37  jdufner
 * Code Formatter
 * Revision 1.2 2008/05/09 22:48:46 jdufner
 * Javadoc Version-Tag auf CVS keyword Revision gesetzt Revision 1.1.1.1
 * 2008/05/09 20:34:09 jdufner Initial Check-In
 */

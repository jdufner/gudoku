// $Id: SudokuMapper.java,v 1.15 2009/12/05 23:27:44 jdufner Exp $

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.gudoku.client.dto.Cell;
import net.sf.gudoku.client.dto.Command;
import net.sf.gudoku.client.dto.Game;
import net.sf.gudoku.client.dto.Sudoku;
import net.sf.gudoku.client.dto.SudokuSize;
import de.jdufner.sudoku.commands.AbstractCommand;
import de.jdufner.sudoku.commands.CommandFactory;
import de.jdufner.sudoku.common.board.Candidates;
import de.jdufner.sudoku.common.board.Literal;

/**
 * Diese Klasse transformiert die Serverklassen, die sehr viele Referenzen haben, in Clientklassen, die auch in
 * JavaScript abgebildet werden können. Alle Methoden in dieser Klasse sind als <code>mapServer2Client</code> oder
 * <code>mapClient2Server</code> benamt. Durch Überladen wird dann die richtige Methode bestimmt.
 * 
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.15 $
 */
public final class SudokuMapper {

  /**
   * Transformiert einen Befehl {@link Command} von Client- in Server-Repräsentation.
   * 
   * @param command
   *          Befehl in Client-Repräsentation.
   * @return Befehl in Server-Repräsentation.
   */
  public static AbstractCommand mapClient2Server(Command command) {
    if (Command.SET_VALUE.equals(command.getCommand())) {
      return (AbstractCommand) CommandFactory.buildSetValueCommand(SudokuMapper.class.getSimpleName(), Cell
          .mapClientRow2ServerRow(command.getRow()), Cell.mapClientColumn2ServerColumn(command.getColumn()), Literal
          .getInstance(command.getValue()));
    } else if (Command.UNSET_VALUE.equals(command.getCommand())) {
      return (AbstractCommand) CommandFactory.buildUnsetValueCommand(SudokuMapper.class.getSimpleName(), Cell
          .mapClientRow2ServerRow(command.getRow()), Cell.mapClientColumn2ServerColumn(command.getColumn()), Literal
          .getInstance(command.getValue()));
    } else if (Command.SET_CANDIDATE.equals(command.getCommand())) {
      return (AbstractCommand) CommandFactory.buildSetCandidateCommand(SudokuMapper.class.getSimpleName(), Cell
          .mapClientRow2ServerRow(command.getRow()), Cell.mapClientColumn2ServerColumn(command.getColumn()), Literal
          .getInstance(command.getValue()));
    } else if (Command.UNSET_CANDIDATE.equals(command.getCommand())) {
      return (AbstractCommand) CommandFactory.buildUnsetCandidateCommand(SudokuMapper.class.getSimpleName(), Cell
          .mapClientRow2ServerRow(command.getRow()), Cell.mapClientColumn2ServerColumn(command.getColumn()), Literal
          .getInstance(command.getValue()));
    } else {
      throw new IllegalArgumentException("Illegal Command Type!");
    }
  }

  /**
   * Transformiert ein Sudoku {@link Sudoku} von Server- in Client-Repräsentation.
   * 
   * @param serverSudoku
   *          Sudoku in Server-Repräsentation.
   * @return Sudoku in Client-Repräsentation.
   */
  public static Sudoku mapServer2Client(de.jdufner.sudoku.common.board.Sudoku serverSudoku) {
    Sudoku sudoku = new Sudoku();
    List<Cell> cells = new ArrayList<Cell>();
    Iterator<de.jdufner.sudoku.common.board.Cell> cellIterator = serverSudoku.getCells().iterator();
    while (cellIterator.hasNext()) {
      cells.add(mapServer2Client((de.jdufner.sudoku.common.board.Cell) cellIterator.next()));
    }
    sudoku.setCells(cells);
    sudoku.setNumberOfFixed(serverSudoku.getNumberOfFixed());
    sudoku.setSize(mapServer2Client(serverSudoku.getSize()));
    return sudoku;
  }

  /**
   * Transformiert ein Spiel {@link Game} von Server- in Client-Repräsentation.
   * 
   * @param serverGame
   *          Spiel in Server-Repräsentation.
   * @return Spiel in Client-Repräsentation.
   */
  public static Game mapServer2Client(de.jdufner.sudoku.Game serverGame) {
    Game game = new Game();
    game.setId(serverGame.getId());
    game.setSudoku(mapServer2Client(serverGame.getQuest()));
    return game;
  }

  /**
   * Transformiert eine Zelle {@link Cell} von Server- in Client-Repräsentation.
   * 
   * @param serverCell
   *          Zelle in Server-Repräsentation.
   * @return Zelle in Client-Repräsentation.
   */
  public static Cell mapServer2Client(de.jdufner.sudoku.common.board.Cell serverCell) {
    Cell cell = new Cell();
    cell.setRow(Cell.mapServerRow2ClientRow(serverCell.getRowIndex()));
    cell.setColumn(Cell.mapServerColumn2ClientColumn(serverCell.getColumnIndex()));
    cell.setFixed(serverCell.getValue().getValue());
    cell.setCandidates(mapServer2Client(serverCell.getCandidates()));
    return cell;
  }

  /**
   * Transformiert eine Liste von Kandidaten von Server- in Client-Repräsenation.
   * 
   * @param serverCandidates
   *          Liste von Kandidaten in Server-Repräsentation.
   * @return Liste von Kandidaten in Client-Repräsentation.
   */
  public static List<Integer> mapServer2Client(Candidates<Literal> serverCandidates) {
    List<Integer> candidates = new ArrayList<Integer>();
    Iterator<Literal> literalIterator = serverCandidates.iterator();
    while (literalIterator.hasNext()) {
      Literal literal = (Literal) literalIterator.next();
      candidates.add(new Integer(literal.getValue()));
    }
    return candidates;
  }

  /**
   * Transformiert die Sudokugröße {@link SudokuSize} von Server- in Client-Repräsentation.
   * 
   * @param serverSize
   *          Sudokugröße in Server-Repräsentation.
   * @return Sudokugröße in Client-Repräsentation.
   */
  public static SudokuSize mapServer2Client(de.jdufner.sudoku.common.board.SudokuSize serverSize) {
    SudokuSize size = new SudokuSize();
    size.setBlockHeight(serverSize.getBlockHeight());
    size.setBlockWidth(serverSize.getBlockWidth());
    size.setTotalSize(serverSize.getTotalSize());
    size.setChecksum(serverSize.getCheckSum());
    size.setUnitSize(serverSize.getUnitSize());
    return size;
  }

}
/*
 * $Log: SudokuMapper.java,v $
 * Revision 1.15  2009/12/05 23:27:44  jdufner
 * Umstellung von AbstractCommand auf Command-Interface
 * Revision 1.14 2009/12/05 21:38:50 jdufner Commands überarbeitet Revision 1.13 2009/11/27
 * 21:53:37 jdufner Command erweitert und StrategyResult ergänzt Revision 1.12 2009/11/17 20:34:23 jdufner Refactoring:
 * Klassen verschoben
 * 
 * Revision 1.11 2009/11/15 22:29:31 jdufner Refactoring: Klassen verschoben
 * 
 * Revision 1.10 2009/11/14 22:15:56 jdufner Refactoring und PMD review
 * 
 * Revision 1.9 2009/11/14 21:51:33 jdufner *** empty log message *** Revision 1.8 2009/11/11 23:11:22 jdufner Migration
 * to GWT 1.7 Revision 1.7 2008/06/02 17:46:54 jdufner CVS Kommentar korrigiert, Game erweitert für Gudoku
 * 
 * Revision 1.6 2008/05/28 22:46:50 jdufner Literal als Flyweight implementiert
 * 
 * Revision 1.5 2008/05/18 20:33:52 jdufner Sudoku-Projekt in Solver und Generator geteilt
 * 
 * Revision 1.4 2008/05/16 13:55:58 jdufner kleines Namensrefactoring
 * 
 * Revision 1.3 2008/05/16 13:24:41 jdufner Zeige Spiel-ID und Anzahl der gesetzten Felder, kleine Namensrefactorings
 * 
 * Revision 1.2 2008/05/09 22:48:46 jdufner Javadoc Version-Tag auf CVS keyword Revision gesetzt Revision 1.1.1.1
 * 2008/05/09 20:34:09 jdufner Initial Check-In
 */

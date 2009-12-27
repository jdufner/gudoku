// $Id: Command.java,v 1.3 2008/06/06 21:07:44 jdufner Exp $

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
package net.sf.gudoku.client.dto;

import java.io.Serializable;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.3 $
 */
public class Command implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String SET_VALUE = "setValue";
  public static final String UNSET_VALUE = "unsetValue";
  public static final String SET_CANDIDATE = "setCandidate";
  public static final String UNSET_CANDIDATE = "unsetCandidate";

  private String command;
  private char row;
  private char column;
  private int value;

  public Command() {
  }

  public Command(String command, char row, char column, int value) {
    this.command = command;
    this.row = row;
    this.column = column;
    this.value = value;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public char getRow() {
    return row;
  }

  public void setRow(char row) {
    this.row = row;
  }

  public char getColumn() {
    return column;
  }

  public void setColumn(char column) {
    this.column = column;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

}
/*
 * $Log: Command.java,v $
 * Revision 1.3  2008/06/06 21:07:44  jdufner
 * Default-SerialUID eingebaut
 *
 * Revision 1.2  2008/05/09 22:48:46  jdufner
 * Javadoc Version-Tag auf CVS keyword Revision gesetzt
 * Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

// $Id: Game.java,v 1.2 2008/06/06 21:07:45 jdufner Exp $

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
package net.sf.gudoku.client.dto;

import java.io.Serializable;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.2 $
 * 
 */
public class Game implements Serializable {

  private static final long serialVersionUID = 1L;

  private int id;
  private Sudoku sudoku;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Sudoku getSudoku() {
    return sudoku;
  }

  public void setSudoku(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

}

/*
 * $Log: Game.java,v $
 * Revision 1.2  2008/06/06 21:07:45  jdufner
 * Default-SerialUID eingebaut
 *
 * Revision 1.1  2008/05/16 13:24:39  jdufner
 * Zeige Spiel-ID und Anzahl der gesetzten Felder,
 * kleine Namensrefactorings
 *
 */

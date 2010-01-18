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

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;

/**
 * Ein Befehl ist eine Aktion, die auf einem Sudoku ausgef�hrt werden kann. Es sind mehrere Aktionen auf einem Sudoku
 * m�glich. Setzen einer Zelle, Elemieren einer oder mehrerer Kandidaten. Ein Befehl muss nicht immer erfolgreich sein,
 * das bedeutet, dass die Aktion nicht tats�chlich zu einer Elimination f�hrt oder ein Feld setzt.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractCommand implements Command {

  private static final Logger LOG = Logger.getLogger(AbstractCommand.class);

  private final transient String creator;
  private transient String frozenString = null;

  protected transient int rowIndex, columnIndex;
  protected transient Literal value;
  protected transient boolean successfully = false;

  /**
   * Konstrukter mit Information �ber den Erzeuger.
   * 
   * @param creator
   *          Der Erzeuger des Befehls.
   */
  protected AbstractCommand(final String creator) {
    this.creator = creator;
  }

  @Override
  public void execute(final Sudoku sudoku) {
    freeze(sudoku);
    executeCommand(sudoku);
    if (!getCell(sudoku).isValid()) {
      LOG.warn(getCell(sudoku) + " ist nach Ausf�hrung von " + this + " nicht mehr g�ltig.");
    }
  }

  /**
   * Interne Implementierung der {@link #execute(Sudoku)}-Methode, die von den Subklassen implementiert werden muss.
   * 
   * @param sudoku
   */
  protected abstract void executeCommand(Sudoku sudoku);

  @Override
  public void unexecute(final Sudoku sudoku) {
    unexecuteCommand(sudoku);
    if (!getCell(sudoku).isValid()) {
      LOG.warn(getCell(sudoku) + " ist nach R�cknahme von " + this + " nicht mehr g�ltig.");
    }
  }

  /**
   * Interne Implementierung der {@link #unexecute(Sudoku)}-Methode, die von den Subklassen implementiert werden muss.
   * 
   * @param sudoku
   */
  protected abstract void unexecuteCommand(Sudoku sudoku);

  @Override
  public abstract boolean reversible();

  @Override
  public boolean isSuccessfully() {
    return successfully;
  }

  /**
   * Gibt den Erzeuger an. Ist im allgemeinen der Name einer Strategie oder der Client.
   * 
   * @return Der Erzeuger des Befehls.
   */
  protected String getCreator() {
    return creator;
  }

  /**
   * Speichert den Inhalt der {@link #toString()}-Methode des Objekts im Attribut {@link #frozenString} ab und kann mit
   * {@link #getFrozenString()} abgerufen werden. Diese Methode ist nach Erzeugung des Objekts aufzurufen.
   */
  protected void freeze(Sudoku sudoku) {
    //if (LOG.isDebugEnabled()) {
    if (frozenString == null || frozenString.length() <= 0) {
      frozenString = this.toString(sudoku);
    }
    //}
  }

  protected abstract String toString(Sudoku sudoku);

  @Override
  public String getFrozenString() {
    return frozenString;
  }

  @Override
  public int getColumnIndex() {
    return columnIndex;
  }

  @Override
  public int getRowIndex() {
    return rowIndex;
  }

  protected Cell getCell(Sudoku sudoku) {
    return sudoku.getCell(getRowIndex(), getColumnIndex());
  }

}

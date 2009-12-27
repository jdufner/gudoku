// $Id: CellLabel.java,v 1.2 2008/05/09 22:48:45 jdufner Exp $

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
package net.sf.gudoku.client.widget;

import net.sf.gudoku.client.Gudoku;
import net.sf.gudoku.client.dto.Cell;
import net.sf.gudoku.client.dto.Command;
import net.sf.gudoku.client.dto.Sudoku;
import net.sf.gudoku.client.service.SudokuService;
import net.sf.gudoku.client.service.SudokuServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.2 $
 */
public class CellLabel extends DoubleClickLabel implements DoubleClickListener {

  // Services and Service-related
  private final SudokuServiceAsync sudokuService = (SudokuServiceAsync) GWT.create(SudokuService.class);
  private ServiceDefTarget endpoint = null;
  private String moduleRelativeURL = null;

  private Gudoku gudoku;
  private char row;
  private char column;
  private int value;
  private boolean initialValue = false;

  public CellLabel(Gudoku gudoku, char row, char column) {
    this.gudoku = gudoku;
    this.row = row;
    this.column = column;
    addStyleName("cell");
  }

  public CellLabel(Gudoku gudoku, char row, char column, int value) {
    this(gudoku, row, column);
    this.value = value;
    setText(String.valueOf(value));
    addDoubleClickListener(this);
  }

  private Sudoku getSudoku() {
    return gudoku.getSudoku();
  }

  public boolean isInitialValue() {
    return initialValue;
  }

  public void setInitialValue(boolean initialValue) {
    if (initialValue) {
      addStyleName("initialValue");
    } else {
      removeStyleName("initialValue");
    }
    this.initialValue = initialValue;
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

  public void onDoubleClick(Widget sender) {
    if (!initialValue) {
      unsetValue();
    }
  }

  private void executeCommand(Command command, AsyncCallback callback) {
    if (endpoint == null && moduleRelativeURL == null) {
      ServiceDefTarget endpoint = (ServiceDefTarget) sudokuService;
      String moduleRelativeURL = GWT.getModuleBaseURL() + "sudoku";
      endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    sudokuService.doCommand(command, callback);
  }

  private void unsetValue() {
    Command command = new Command(Command.UNSET_VALUE, row, column, value);

    executeCommand(command, new AsyncCallback() {
      public void onSuccess(Object result) {
        Cell cell = (Cell) result;
        getSudoku().setCell(cell);
      }

      public void onFailure(Throwable caught) {
      }
    });
  }

}
/*
 * $Log: CellLabel.java,v $
 * Revision 1.2  2008/05/09 22:48:45  jdufner
 * Javadoc Version-Tag auf CVS keyword Revision gesetzt
 * Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

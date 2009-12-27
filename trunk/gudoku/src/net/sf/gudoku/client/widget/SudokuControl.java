// $Id: SudokuControl.java,v 1.4 2008/05/16 13:55:58 jdufner Exp $

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
import net.sf.gudoku.client.dto.Observable;
import net.sf.gudoku.client.dto.Observer;
import net.sf.gudoku.client.service.SudokuService;
import net.sf.gudoku.client.service.SudokuServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.4 $
 */
public class SudokuControl extends SimplePanel implements Observer {

  // Services and Service-related
  private final SudokuServiceAsync sudokuService = (SudokuServiceAsync) GWT.create(SudokuService.class);
  private ServiceDefTarget endpoint = null;
  private String moduleRelativeURL = null;

  private Gudoku gudoku;

  // GUI-Widgets
  private FlexTable table = new FlexTable();
  private Button redo = new Button("Vor");
  private Button undo = new Button("Zur&uuml;ck");
  private Label sudokuId = new Label("Spiel Nr.");
  private Label status = new Label();

  public SudokuControl(Gudoku gudoku) {
    this.gudoku = gudoku;
    init();
  }

  private void init() {
    table.setCellPadding(5);
    table.setCellSpacing(0);
    sudokuId.setText("Spiel Nr." + gudoku.getGame().getId());
    table.setWidget(0, 0, sudokuId);
    table.setWidget(1, 0, undo);
    undo.setEnabled(false);
    undo.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        undo();
      }
    });
    table.setWidget(1, 1, redo);
    redo.setEnabled(false);
    redo.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        redo();
      }
    });
    updateStatus(gudoku.getGame().getSudoku().getNumberOfFixed(), gudoku.getGame().getSudoku().getSize().getTotalSize());
    table.setWidget(0, 1, status);
    add(table);
    RootPanel.get("slot3").clear();
    RootPanel.get("slot3").add(this);
    gudoku.getSudoku().addObserver(this);
  }

  private void undo() {
    if (endpoint == null && moduleRelativeURL == null) {
      ServiceDefTarget endpoint = (ServiceDefTarget) sudokuService;
      String moduleRelativeURL = GWT.getModuleBaseURL() + "sudoku";
      endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    AsyncCallback callback = new AsyncCallback() {
      public void onSuccess(Object result) {
        Cell cell = (Cell) result;
        gudoku.getSudoku().setCell(cell);
      }

      public void onFailure(Throwable caught) {
      }
    };
    sudokuService.undo(callback);
  }

  private void redo() {
    if (endpoint == null && moduleRelativeURL == null) {
      ServiceDefTarget endpoint = (ServiceDefTarget) sudokuService;
      String moduleRelativeURL = GWT.getModuleBaseURL() + "sudoku";
      endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    AsyncCallback callback = new AsyncCallback() {
      public void onSuccess(Object result) {
        Cell cell = (Cell) result;
        gudoku.getSudoku().setCell(cell);
      }

      public void onFailure(Throwable caught) {
      }
    };
    sudokuService.redo(callback);
  }

  private void enableButtons(Cell cell) {
    if (cell.isRedoPossible()) {
      redo.setEnabled(true);
    } else {
      redo.setEnabled(false);
    }
    if (cell.isUndoPossible()) {
      undo.setEnabled(true);
    } else {
      undo.setEnabled(false);
    }
  }

  private void updateStatus(int numberFixed, int sudokuSize) {
    gudoku.getSudoku().setNumberOfFixed(numberFixed);
    status.setText(numberFixed + " von " + sudokuSize + " gesetzt");
  }

  public Button getRedo() {
    return redo;
  }

  public Button getUndo() {
    return undo;
  }

  public void update(Observable observable, Object obj) {
    if (obj instanceof Cell) {
      Cell cell = (Cell) obj;
      enableButtons(cell);
      updateStatus(cell.getNumberFixed(), gudoku.getSudoku().getSize().getTotalSize());
    }
  }
}
/*
 * $Log: SudokuControl.java,v $
 * Revision 1.4  2008/05/16 13:55:58  jdufner
 * kleines Namensrefactoring
 *
 * Revision 1.3  2008/05/16 13:24:41  jdufner
 * Zeige Spiel-ID und Anzahl der gesetzten Felder,
 * kleine Namensrefactorings
 *
 * Revision 1.2  2008/05/09 22:48:46  jdufner
 * Javadoc Version-Tag auf CVS keyword Revision gesetzt
 * Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

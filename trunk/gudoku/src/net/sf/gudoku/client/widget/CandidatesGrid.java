// $Id: CandidatesGrid.java,v 1.3 2009/11/11 23:11:22 jdufner Exp $

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
import net.sf.gudoku.client.dto.Command;
import net.sf.gudoku.client.dto.Sudoku;
import net.sf.gudoku.client.service.SudokuService;
import net.sf.gudoku.client.service.SudokuServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.3 $
 */
public class CandidatesGrid extends Grid implements ClickListener, DoubleClickListener, MouseListener {

  // Services and Service-related
  private final SudokuServiceAsync sudokuService = (SudokuServiceAsync) GWT.create(SudokuService.class);
  private ServiceDefTarget endpoint = null;
  private String moduleRelativeURL = null;

  private Gudoku gudoku;
  private char row;
  private char column;

  public CandidatesGrid(Gudoku gudoku, char row, char column) {
    super(gudoku.getSudokuSize().getBlockHeight(), gudoku.getSudokuSize().getBlockWidth());
    this.gudoku = gudoku;
    this.row = row;
    this.column = column;
    addStyleName("candidatesGrid");
    addStyleName("cell");
    setCellPadding(0);
    setCellSpacing(0);
    int i = 1;
    for (int candidateRow = 0; candidateRow < gudoku.getSudokuSize().getBlockHeight(); candidateRow++) {
      for (int candidateColumn = 0; candidateColumn < gudoku.getSudokuSize().getBlockWidth(); candidateColumn++) {
        CandidateLabel cl = new CandidateLabel(i++, false);
        cl.addClickListener(this);
        cl.addDoubleClickListener(this);
        cl.addMouseListener(this);
        cl.addStyleName("cellCandidate");
        setWidget(candidateRow, candidateColumn, cl);
      }
    }
  }

  private Sudoku getSudoku() {
    return gudoku.getSudoku();
  }

  public void onClick(Widget sender) {
    if (sender instanceof CandidateLabel) {
      CandidateLabel cl = (CandidateLabel) sender;
      if (cl.isToggle()) {
        cl.setToggle(false);
        cl.setText("");
      } else {
        cl.setToggle(true);
        cl.setText(String.valueOf(cl.getValue()));
      }
    }
  }

  public void onDoubleClick(Widget sender) {
    if (sender instanceof CandidateLabel) {
      CandidateLabel cl = (CandidateLabel) sender;
      setValue(cl.getValue());
    }
  }

  public void onMouseDown(Widget sender, int x, int y) {
  }

  public void onMouseEnter(Widget sender) {
    sender.addStyleName("markierung");
    if (sender instanceof CandidateLabel) {
      CandidateLabel cl = (CandidateLabel) sender;
      cl.setText(String.valueOf(cl.getValue()));
    }
  }

  public void onMouseLeave(Widget sender) {
    sender.removeStyleName("markierung");
    if (sender instanceof CandidateLabel) {
      CandidateLabel cl = (CandidateLabel) sender;
      if (cl.isToggle()) {
        cl.setText(String.valueOf(cl.getValue()));
      } else {
        cl.setText("");
      }
    }
  }

  public void onMouseMove(Widget sender, int x, int y) {
  }

  public void onMouseUp(Widget sender, int x, int y) {
  }

  private void executeCommand(Command command, AsyncCallback callback) {
    if (endpoint == null && moduleRelativeURL == null) {
      ServiceDefTarget endpoint = (ServiceDefTarget) sudokuService;
      String moduleRelativeURL = GWT.getModuleBaseURL() + "sudoku";
      endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    sudokuService.doCommand(command, callback);
  }

  private void setValue(int value) {
    Command command = new Command(Command.SET_VALUE, row, column, value);
    executeCommand(command, new AsyncCallback() {
      public void onSuccess(Object result) {
        net.sf.gudoku.client.dto.Cell cell = (net.sf.gudoku.client.dto.Cell) result;
        getSudoku().setCell(cell);
      }

      public void onFailure(Throwable caught) {
      }
    });
  }

}
/*
 * $Log: CandidatesGrid.java,v $
 * Revision 1.3  2009/11/11 23:11:22  jdufner
 * Migration to GWT 1.7
 * Revision 1.2 2008/05/09 22:48:45 jdufner Javadoc Version-Tag auf CVS keyword Revision
 * gesetzt Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

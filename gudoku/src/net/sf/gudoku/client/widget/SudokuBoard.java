// $Id: SudokuBoard.java,v 1.3 2008/06/02 17:46:54 jdufner Exp $

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

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.3 $
 */
public class SudokuBoard implements Observer {

  private Gudoku gudoku;

  public SudokuBoard() {
  }

  public SudokuBoard(Gudoku gudoku) {
    init(gudoku);
  }

  private void init(Gudoku gudoku) {
    this.gudoku = gudoku;
    for (int rowInt = 0; rowInt < gudoku.getSudokuSize().getUnitSize(); rowInt++) {
      char row = Cell.mapServerRow2ClientRow(rowInt);
      for (int columnInt = 0; columnInt < gudoku.getSudokuSize().getUnitSize(); columnInt++) {
        char column = Cell.mapServerColumn2ClientColumn(columnInt);
        if (gudoku.getSudoku() == null) {
          setCandidatesGrid(row, column);
        } else {
          Cell cell = gudoku.getSudoku().getCell(rowInt, columnInt);
          cell.setInitialValue(true);
          setCell(cell);
          setChecksums(cell);
        }
      }
    }
    gudoku.getSudoku().addObserver(this);
  }

  public void setCell(Cell cell) {
    if (cell.isFixed()) {
      setFixed(cell.getRow(), cell.getColumn(), cell.getFixed(), cell.isInitialValue());
      setCorrectness(cell.getRow(), cell.getColumn(), cell.isInitialValue(), cell.isCorrect());
    } else {
      setCandidatesGrid(cell.getRow(), cell.getColumn());
    }
  }

  private CellLabel setFixed(char row, char column, int value, boolean initialValue) {
    CellLabel cl = setFixed(row, column, value);
    cl.setInitialValue(initialValue);
    return cl;
  }

  private CellLabel setFixed(char row, char column, int value) {
    CellLabel cl = new CellLabel(gudoku, row, column, value);
    RootPanel rp = RootPanel.get(String.valueOf(column) + String.valueOf(row));
    rp.clear();
    rp.add(cl);
    return cl;
  }

  private void setCorrectness(char row, char column, boolean initialValue, boolean correct) {
    CellLabel cl = null;
    RootPanel rp = RootPanel.get(String.valueOf(column) + String.valueOf(row));
    if (rp.getWidgetCount() > 0) {
      cl = (CellLabel) rp.getWidget(0);
      if (!initialValue) {
        cl.removeStyleName("correctnessFalse");
        cl.removeStyleName("correctnessTrue");
        if (correct) {
          cl.addStyleName("correctnessTrue");
        } else {
          cl.addStyleName("correctnessFalse");
        }
      }
    }
  }

  private void setCandidatesGrid(char row, char column) {
    CandidatesGrid cg = new CandidatesGrid(gudoku, row, column);
    RootPanel rp = RootPanel.get(String.valueOf(column) + String.valueOf(row));
    rp.clear();
    rp.add(cg);
  }

  protected void setChecksums(Cell cell) {
    setRowChecksum(cell);
    setColumnChecksum(cell);
  }

  protected void setRowChecksum(Cell cell) {
    int rowChecksum = gudoku.getSudoku().getRowChecksum(Cell.mapClientRow2ServerRow(cell.getRow()));
    Label l = null;
    RootPanel rp = RootPanel.get("Row_" + String.valueOf(cell.getRow()));
    if (rp != null && rp.getWidgetCount() > 0) {
      l = (Label) rp.getWidget(0);
    } else {
      l = new Label();
      l.addStyleName("rowChecksum");
      rp.clear();
      rp.add(l);
    }
    l.setText(String.valueOf(rowChecksum));
    l.setTitle(String.valueOf(rowChecksum) + " von " + gudoku.getSudokuSize().getUnitChecksum());
    setChecksumStyle(l, rowChecksum);
  }

  protected void setColumnChecksum(Cell cell) {
    int columnChecksum = gudoku.getSudoku().getColumnChecksum(Cell.mapClientColumn2ServerColumn(cell.getColumn()));
    Label l = null;
    RootPanel rp = RootPanel.get("Column_" + String.valueOf(cell.getColumn()));
    if (rp != null && rp.getWidgetCount() > 0) {
      l = (Label) rp.getWidget(0);
    } else {
      l = new Label();
      l.addStyleName("columnChecksum");
      rp.clear();
      rp.add(l);
    }
    l.setText(String.valueOf(columnChecksum));
    l.setTitle(String.valueOf(columnChecksum) + " vom " + gudoku.getSudokuSize().getUnitChecksum());
    setChecksumStyle(l, columnChecksum);
  }

  private void setChecksumStyle(Label l, int checksum) {
    final String chkOk = "checksumOk";
    final String chkNok = "checksumNok";
    l.removeStyleName(chkOk);
    l.removeStyleName(chkNok);
    if (checksum == gudoku.getSudokuSize().getUnitChecksum()) {
      l.addStyleName(chkOk);
    } else {
      l.addStyleName(chkNok);
    }
  }

  // implements Observer-Interface
  public void update(Observable observable, Object obj) {
    if (obj instanceof Cell) {
      Cell cell = (Cell) obj;
      setCell(cell);
      setChecksums(cell);
    }
  }

}
/*
 * $Log: SudokuBoard.java,v $
 * Revision 1.3  2008/06/02 17:46:54  jdufner
 * CVS Kommentar korrigiert, Game erweitert für Gudoku
 *
 * Revision 1.2  2008/05/09 22:48:45  jdufner
 * Javadoc Version-Tag auf CVS keyword Revision gesetzt
 * Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

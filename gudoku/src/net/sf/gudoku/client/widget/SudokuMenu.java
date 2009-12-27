// $Id: SudokuMenu.java,v 1.5 2008/06/02 17:46:54 jdufner Exp $

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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.5 $
 */
public class SudokuMenu extends SimplePanel {

  private Gudoku gudoku;

  public SudokuMenu(Gudoku gudoku) {
    this.gudoku = gudoku;
    buildMenuBar();
  }

  private void buildMenuBar() {
    addStyleName("menuBar");
    MenuBar menuBar = new MenuBar();

    MenuBar game = new MenuBar(true);
    game.addStyleName("menuBar");
    menuBar.addItem("Spiel", game);

    MenuItem newGame = new MenuItem("Spiel des Tages", new Command() {
      public void execute() {
        gudoku.newGame();
      }
    });
    newGame.addStyleName("menuItem");
    newGame.setTitle("Lade Spiel des Tages (das jeweils aktuellste Spiel, unabh&auml;ngig des Schwierigkeitsgrades)");
    game.addItem(newGame);

    MenuItem loadGame = new MenuItem("Laden", new Command() {
      public void execute() {
        System.out.println("Laden");
      }
    });
    loadGame.setTitle("Lade Spiel des Tages");
    loadGame.addStyleName("menuItem");
    game.addItem(loadGame);

    MenuItem emptyGame = new MenuItem("Leer", new Command() {
      public void execute() {
        gudoku.newGame();
      }
    });
    emptyGame.setTitle("Erzeuge leeres Spielfeld, um R&auml;tsel manuell zu erzeugen");
    emptyGame.addStyleName("menuItem");
    game.addItem(emptyGame);

    MenuItem saveGame = new MenuItem("Speichern", new Command() {
      public void execute() {
        System.out.println("Speichern");
      }
    });
    saveGame.setTitle("Speichere Spiel");
    saveGame.addStyleName("menuItem");
    game.addItem(saveGame);

    MenuBar options = new MenuBar(true);
    options.addStyleName("menuBar");
    options.addStyleName("menuItem");
    menuBar.addItem("Extras", options);

    MenuItem settings = new MenuItem("Einstellungen", new Command() {
      public void execute() {
        System.out.println("Einstellungen");

      }
    });
    settings.setTitle("Kandidaten zeigen, ...");
    settings.addStyleName("menuItem");
    options.addItem(settings);

    add(menuBar);
  }

}
/*
 * $Log: SudokuMenu.java,v $
 * Revision 1.5  2008/06/02 17:46:54  jdufner
 * CVS Kommentar korrigiert, Game erweitert für Gudoku
 *
 * Revision 1.4  2008/05/16 03:46:20  jdufner
 * Lade Sudoku jetzt aus Datei und zähle id in einem Cookie
 *
 * Revision 1.3  2008/05/15 21:41:16  jdufner
 * Menu überarbeitet, noch nicht final
 * Revision 1.2 2008/05/09 22:48:43 jdufner Javadoc
 * Version-Tag auf CVS keyword Revision gesetzt Revision 1.1.1.1 2008/05/09
 * 20:34:09 jdufner Initial Check-In
 */

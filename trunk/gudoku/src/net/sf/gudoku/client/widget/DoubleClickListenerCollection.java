// $Id: DoubleClickListenerCollection.java,v 1.2 2008/05/09 22:48:45 jdufner Exp $

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

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.2 $
 */
public class DoubleClickListenerCollection extends ArrayList {

  /**
   * Generated bei Eclipse.
   */
  private static final long serialVersionUID = -350172867219157161L;

  /**
   * Fires a click event to all listeners.
   * 
   * @param sender
   *          the widget sending the event.
   */
  public void fireDoubleClick(Widget sender) {
    for (Iterator it = iterator(); it.hasNext();) {
      DoubleClickListener listener = (DoubleClickListener) it.next();
      listener.onDoubleClick(sender);
    }
  }
}
/*
 * $Log: DoubleClickListenerCollection.java,v $
 * Revision 1.2  2008/05/09 22:48:45  jdufner
 * Javadoc Version-Tag auf CVS keyword Revision gesetzt
 * Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

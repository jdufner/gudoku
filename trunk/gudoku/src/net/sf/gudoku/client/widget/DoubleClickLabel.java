// $Id: DoubleClickLabel.java,v 1.2 2008/05/09 22:48:45 jdufner Exp $

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

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Label;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.2 $
 */
public class DoubleClickLabel extends Label {

  private DoubleClickListenerCollection doubleClickListeners;

  public DoubleClickLabel() {
    sinkEvents(Event.ONDBLCLICK);
  }

  public void addDoubleClickListener(DoubleClickListener listener) {
    if (doubleClickListeners == null) {
      doubleClickListeners = new DoubleClickListenerCollection();
    }
    doubleClickListeners.add(listener);
  }

  public void onBrowserEvent(Event event) {
    if ((DOM.eventGetType(event) & Event.ONDBLCLICK) == Event.ONDBLCLICK) {
      doubleClickListeners.fireDoubleClick(this);
    } else {
      super.onBrowserEvent(event);
    }
  }

}
/*
 * $Log: DoubleClickLabel.java,v $
 * Revision 1.2  2008/05/09 22:48:45  jdufner
 * Javadoc Version-Tag auf CVS keyword Revision gesetzt
 * Revision 1.1.1.1 2008/05/09 20:34:09 jdufner Initial Check-In
 */

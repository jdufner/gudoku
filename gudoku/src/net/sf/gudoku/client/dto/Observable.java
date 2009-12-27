// $Id: Observable.java,v 1.4 2009/11/11 23:11:22 jdufner Exp $

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:juergen@jdufner.de">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.4 $
 */
public abstract class Observable {

  private List<Observer> observers = new ArrayList<Observer>();

  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  protected void notifyObservers() {
    Iterator<Observer> observerIt = observers.iterator();
    while (observerIt.hasNext()) {
      Observer observer = (Observer) observerIt.next();
      observer.update(this, null);
    }
  }

  protected void notifyObservers(Object obj) {
    Iterator<Observer> observerIt = observers.iterator();
    while (observerIt.hasNext()) {
      Observer observer = (Observer) observerIt.next();
      observer.update(this, obj);
    }
  }

}
/*
 * $Log: Observable.java,v $
 * Revision 1.4  2009/11/11 23:11:22  jdufner
 * Migration to GWT 1.7
 * Revision 1.3 2008/05/15 21:40:20 jdufner toString-Methode implementiert Revision 1.2
 * 2008/05/09 22:48:46 jdufner Javadoc Version-Tag auf CVS keyword Revision gesetzt Revision 1.1.1.1 2008/05/09 20:34:09
 * jdufner Initial Check-In
 */

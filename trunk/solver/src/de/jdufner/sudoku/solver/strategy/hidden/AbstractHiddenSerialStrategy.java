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
package de.jdufner.sudoku.solver.strategy.hidden;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractSerialStrategy;

/**
 * Diese abstrakte Superklasse implementiert die Parallelsierung f�r <a
 * href="http://www.sudopedia.org/wiki/Hidden_Subset">Hidden Subset http://www.sudopedia.org/wiki/Hidden_Subset</a>.
 * 
 * Die Logik wird nicht redundant implementiert, sondern aus {@link AbstractHiddenStrategy} und Subklassen
 * wiederverwendet.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.2
 * @version $Revision$
 */
public abstract class AbstractHiddenSerialStrategy extends AbstractSerialStrategy implements Hidden {

  private static final Logger LOG = Logger.getLogger(AbstractHiddenSerialStrategy.class);

  protected AbstractHiddenSerialStrategy(final Sudoku sudoku) {
    super(sudoku);
    final HiddenBlockStrategy hiddenBlockStrategy = new HiddenBlockStrategy(sudoku);
    hiddenBlockStrategy.setSize(getSize());
    getStrategies().add(hiddenBlockStrategy);
    final HiddenColumnStrategy hiddenColumnStrategy = new HiddenColumnStrategy(sudoku);
    hiddenColumnStrategy.setSize(getSize());
    getStrategies().add(hiddenColumnStrategy);
    final HiddenRowStrategy hiddenRowStrategy = new HiddenRowStrategy(sudoku);
    hiddenRowStrategy.setSize(getSize());
    getStrategies().add(hiddenRowStrategy);
    if (LOG.isDebugEnabled()) {
      LOG.debug("Strategies erzeugt: " + getStrategies());
    }
  }

  @Override
  public Level getLevel() {
    return Level.MITTEL;
  }

  @Override
  public abstract int getSize();

}
// $Id: SimpleSerialStrategy.java,v 1.4 2009/12/05 23:27:47 jdufner Exp $

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
package de.jdufner.sudoku.solver.strategy.simple;

import java.util.Collection;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractSerialStrategy;

/**
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since
 * @version $Revision: 1.4 $
 */
public final class SimpleSerialStrategy extends AbstractSerialStrategy {

  private static final Logger LOG = Logger.getLogger(SimpleSerialStrategy.class);

  public SimpleSerialStrategy(final Sudoku sudoku) {
    super(sudoku);
    getStrategies().add(new SimpleBlockStrategy(sudoku));
    getStrategies().add(new SimpleColumnStrategy(sudoku));
    getStrategies().add(new SimpleRowStrategy(sudoku));
  }

  @Override
  public Collection<Command> executeStrategy() {
    gatherCommandsFromStrategies();
    return getCommands();
  }

  @Override
  public Level getLevel() {
    if (LOG.isDebugEnabled()) {
      LOG.debug(Level.SEHR_LEICHT);
    }
    return Level.SEHR_LEICHT;
  }

}

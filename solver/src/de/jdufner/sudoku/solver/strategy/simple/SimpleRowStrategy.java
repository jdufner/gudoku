// $Id: SimpleRowStrategy.java,v 1.14 2009/12/05 23:27:47 jdufner Exp $

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
import java.util.concurrent.Callable;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Row;
import de.jdufner.sudoku.common.board.RowHandler;
import de.jdufner.sudoku.common.board.Sudoku;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.14 $
 */
public final class SimpleRowStrategy extends AbstractSimpleStrategy implements RowHandler,
    Callable<Collection<Command>> {

  // private static final Logger LOG = Logger.getLogger(SimpleRowStrategy.class);

  public SimpleRowStrategy(final Sudoku sudoku) {
    super(sudoku);
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachRow(getSudoku(), this);
    return getCommands();
  }

  public void handleRow(final Row row) {
    handleUnit(row);
  }

  public Collection<Command> call() {
    return executeStrategy();
  }

}

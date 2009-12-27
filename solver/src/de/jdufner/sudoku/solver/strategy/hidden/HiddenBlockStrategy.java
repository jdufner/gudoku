// $Id: HiddenBlockStrategy.java,v 1.13 2009/12/05 23:27:47 jdufner Exp $

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
package de.jdufner.sudoku.solver.strategy.hidden;

import java.util.Collection;

import de.jdufner.sudoku.commands.Command;
import de.jdufner.sudoku.common.board.Block;
import de.jdufner.sudoku.common.board.BlockHandler;
import de.jdufner.sudoku.common.board.HandlerUtil;
import de.jdufner.sudoku.common.board.Sudoku;

/**
 * Führt die <a href="http://www.sudopedia.org/wiki/Hidden_Subset">Hidden Subset</a> Strategie konfigurierbar auf einem
 * <a href="http://www.sudopedia.org/wiki/Box">Block</a> aus.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.13 $
 */
public class HiddenBlockStrategy extends AbstractHiddenStrategy implements BlockHandler {

  //private static final Logger LOG = Logger.getLogger(HiddenBlockStrategy.class);

  protected HiddenBlockStrategy(final Sudoku sudoku) {
    super(sudoku);
  }

  @Override
  public Collection<Command> executeStrategy() {
    HandlerUtil.forEachBlock(getSudoku(), this);
    return getCommands();
  }

  /**
   * @see BlockHandler#handleBlock(Block)
   * @see AbstractHiddenStrategy#handleUnit(de.jdufner.sudoku.common.Unit)
   */
  public void handleBlock(final Block block) {
    handleUnit(block);
  }

}

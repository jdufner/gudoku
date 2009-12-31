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
package de.jdufner.sudoku.solver.strategy.naked;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;
import de.jdufner.sudoku.solver.strategy.AbstractParallelStrategy;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractNakedParallelStrategy extends AbstractParallelStrategy {

  //  private static final Logger LOG = Logger.getLogger(AbstractNakedParallelStrategy.class);

  protected AbstractNakedParallelStrategy(final Sudoku sudoku) {
    super(sudoku);
    final NakedBlockStrategy nakedBlockStrategy = new NakedBlockStrategy(sudoku);
    nakedBlockStrategy.setSize(getSize());
    getCallables().add(nakedBlockStrategy);
    final NakedColumnStrategy nakedColumnStrategy = new NakedColumnStrategy(sudoku);
    nakedColumnStrategy.setSize(getSize());
    getCallables().add(nakedColumnStrategy);
    final NakedRowStrategy nakedRowStrategy = new NakedRowStrategy(sudoku);
    nakedRowStrategy.setSize(getSize());
    getCallables().add(nakedRowStrategy);
  }

  @Override
  public Level getLevel() {
    return Level.LEICHT;
  }

  protected abstract int getSize();

}

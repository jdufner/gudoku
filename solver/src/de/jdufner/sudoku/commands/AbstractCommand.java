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
package de.jdufner.sudoku.commands;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import de.jdufner.sudoku.common.board.Cell;
import de.jdufner.sudoku.common.board.CellUtils;
import de.jdufner.sudoku.common.board.Literal;
import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.solver.strategy.configuration.StrategyNameEnum;

/**
 * Ein Befehl ist eine Aktion, die auf einem Sudoku ausgef�hrt werden kann. Es sind mehrere Aktionen auf einem Sudoku
 * m�glich. Setzen einer Zelle, Elemieren einer oder mehrerer Kandidaten. Ein Befehl muss nicht immer erfolgreich sein,
 * das bedeutet, dass die Aktion nicht tats�chlich zu einer Elimination f�hrt oder ein Feld setzt.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public abstract class AbstractCommand implements Command {

  private static final Logger LOG = Logger.getLogger(AbstractCommand.class);

  private transient String frozenString = null;

  protected transient StrategyNameEnum strategyNameEnum = null;
  protected transient int rowIndex, columnIndex;
  protected transient Literal value;
  protected transient boolean successfully = false;

  /**
   * Konstruktor mit Information �ber den Erzeuger.
   * 
   * @param strategyName
   *          Der Erzeuger des Befehls.
   */
  protected AbstractCommand(final StrategyNameEnum strategyNameEnum) {
    this.strategyNameEnum = strategyNameEnum;
  }

  @Override
  public void execute(final Sudoku sudoku) {
    freeze(sudoku);
    executeCommand(sudoku);
    if (!getCell(sudoku).isValid()) {
      LOG.warn(getCell(sudoku) + " ist nach Ausf�hrung von " + this + " nicht mehr g�ltig.");
    }
  }

  /**
   * Interne Implementierung der {@link #execute(Sudoku)}-Methode, die von den Subklassen implementiert werden muss.
   * 
   * @param sudoku
   */
  protected abstract void executeCommand(final Sudoku sudoku);

  @Override
  public void unexecute(final Sudoku sudoku) {
    unexecuteCommand(sudoku);
    if (!getCell(sudoku).isValid()) {
      LOG.warn(getCell(sudoku) + " ist nach R�cknahme von " + this + " nicht mehr g�ltig.");
    }
  }

  /**
   * Interne Implementierung der {@link #unexecute(Sudoku)}-Methode, die von den Subklassen implementiert werden muss.
   * 
   * @param sudoku
   */
  protected abstract void unexecuteCommand(final Sudoku sudoku);

  @Override
  public abstract boolean reversible();

  @Override
  public boolean isSuccessfully() {
    return successfully;
  }

  @Override
  public String getStrategyName() {
    if (strategyNameEnum == null) {
      return null;
    }
    return strategyNameEnum.name();
  }

  /**
   * Speichert den Inhalt der {@link #toString()}-Methode des Objekts im Attribut {@link #frozenString} ab und kann mit
   * {@link #getFrozenString()} abgerufen werden. Diese Methode ist nach Erzeugung des Objekts aufzurufen.
   */
  protected void freeze(final Sudoku sudoku) {
    if (frozenString == null || frozenString.length() <= 0) {
      frozenString = this.toString(sudoku);
    }
  }

  protected abstract String toString(Sudoku sudoku);

  @Override
  public String getFrozenString() {
    return frozenString;
  }

  @Override
  public int getColumnIndex() {
    return columnIndex;
  }

  @Override
  public int getRowIndex() {
    return rowIndex;
  }

  protected Cell getCell(final Sudoku sudoku) {
    return sudoku.getCell(getRowIndex(), getColumnIndex());
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (other instanceof AbstractCommand) {
      final AbstractCommand that = (AbstractCommand) other;
      if ((this.strategyNameEnum == that.strategyNameEnum || //
          (this.strategyNameEnum == null ? false : this.strategyNameEnum.equals(that.strategyNameEnum)))
          && this.rowIndex == that.rowIndex && this.columnIndex == that.columnIndex && this.value == that.value) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hashCode = 17;
    final int hashMultiplier = 31; // NOPMD J�rgen Dufner 14.03.2010
    hashCode *= hashMultiplier + (strategyNameEnum == null ? 0 : strategyNameEnum.hashCode());
    hashCode *= hashMultiplier + rowIndex;
    hashCode *= hashMultiplier + columnIndex;
    hashCode *= hashMultiplier + (value == null ? 0 : value.getValue());
    return hashCode;
  }

  protected boolean isEqual(final Collection<Literal> col1, final Collection<Literal> col2) {
    final Set<Literal> set1 = new HashSet<Literal>(col1);
    final Set<Literal> set2 = new HashSet<Literal>(col2);
    return set1.containsAll(set2) && set2.containsAll(set1);
  }

  protected String getJavascriptCellNumber() {
    return String.valueOf(CellUtils.getNumber(rowIndex, columnIndex, SudokuSize.NEUN) + 1);
  }

}

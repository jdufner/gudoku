// $Id: StrategyConfiguration.java,v 1.1 2009/11/24 20:58:55 jdufner Exp $

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
package de.jdufner.sudoku.solver.strategy.configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * Diese Klasse repr�sentiert die Konfiguration eines Sudoku-Knackers. Die Konfiguration ist selbst unabh�ngig von der
 * Implementierung der L�sungsstrategien. Sie kennt allerdings die L�sungstechniken, die f�r die L�sung von Sudokus
 * angewendet werden k�nnen.
 * 
 * Derzeit ist es noch unklar, ob die L�sungstechniken in einer bestimmten Reihenfolge ausgef�hrt werden m�ssen oder die
 * Auswahl der L�sungstechniken vollkommen frei ist. Das soll bedeuten, wenn in der Konfiguration die L�sungstechnik
 * {@link StrategyNameEnum#XWING} ausgew�hlt wurde, ob vorher irgendwelche anderen L�sungstechniken ausprobiert werden
 * m�ssen.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 2009-11-24
 * @version $Revision: 1.1 $
 */
public final class StrategyConfiguration {

  private final transient Set<StrategyNameEnum> strategies = new HashSet<StrategyNameEnum>();
  private final transient StrategyThreadingEnum threading;

  /**
   * Erzeugt eine Konfiguration. Es ist eine zwingende Angabe, ob die L�sung serial oder parallel berechnet werden soll.
   * 
   * @param strategyThreadingEnum
   */
  public StrategyConfiguration(final StrategyThreadingEnum strategyThreadingEnum) {
    threading = strategyThreadingEnum;
  }

  /**
   * F�gt eine L�sungstechnik ein.
   * 
   * @param strategyNameEnum
   * @return Die Konfiguration selbst, wodurch Fluent-API m�glich sein soll.
   */
  public StrategyConfiguration add(final StrategyNameEnum strategyNameEnum) {
    strategies.add(strategyNameEnum);
    return this;
  }

  /**
   * Entfernt eine L�sungstechnik.
   * 
   * @param strategyNameEnum
   * @return Die Konfiguration selbst, wodurch Fluent-API m�glich sein soll.
   */
  public StrategyConfiguration remove(final StrategyNameEnum strategyNameEnum) {
    strategies.remove(strategyNameEnum);
    return this;
  }

  /**
   * F�gt ein Array von L�sungstechniken ein.
   * 
   * @param strategyNameEnums
   * @return Die Konfiguration selbst, wodurch Fluent-API m�glich sein soll.
   */
  public StrategyConfiguration add(final StrategyNameEnum[] strategyNameEnums) {
    for (StrategyNameEnum strategyNameEnum : strategyNameEnums) {
      strategies.add(strategyNameEnum);
    }
    return this;
  }

  /**
   * @return Die Menge (keine doppelten) der aktuell ausgew�hlten L�sungstechniken. Wenn keine eingef�gt wurde, wird
   *         eine leere Menge zur�ck geliefert.
   */
  public Set<StrategyNameEnum> getStrategies() {
    return strategies;
  }

  /**
   * 
   * @return Die ausgew�hlte Nebenl�ufigkeit.
   */
  public StrategyThreadingEnum getThreading() {
    return threading;
  }

}

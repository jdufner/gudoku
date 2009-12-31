// $Id$

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
package de.jdufner.sudoku.solver.service;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;

/**
 * Diese Klasse beschreibt eine Lösung eines Sudokus. Eine Lösung setzt sich aus mehreren Einzelschritten
 * {@link SolutionStepImpl} zusammen. Eine vorhandene Lösung ist eine nur eine Lösung, möglicherweise existieren weitere
 * Lösungen, sowohl in dem Sinne, dass dieselbe Lösung anders erzeugt werden kann durch andere Lösungsschritte als auch,
 * dass möglicherweise das Sudoku unterbestimmt ist und weitere Lösungen existieren.
 * 
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public class Solution {

  private final transient Sudoku quest;
  private transient Sudoku result;
  private transient boolean unique;
  private transient Level level;

  /**
   * @param quest
   *          Das Start-Sudoku, für das die Lösung berechnet werden. Hier muss schon eine Kopie / Klon übergeben werden.
   */
  public Solution(final Sudoku quest) {
    this.quest = quest;
  }

  public Sudoku getQuest() {
    return quest;
  }

  public Sudoku getResult() {
    return result;
  }

  public void setResult(final Sudoku result) {
    this.result = result;
  }

  public boolean isUnique() {
    return unique;
  }

  public void setUnique(final boolean unique) {
    this.unique = unique;
  }

  public Level getLevel() {
    return level;
  }

  public void setLevel(final Level level) {
    this.level = level;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder(super.toString());
    sb.append(System.getProperty("line.separator")).append("Quest: ").append(getQuest());
    sb.append(System.getProperty("line.separator")).append("Result: ").append(getResult());
    sb.append(System.getProperty("line.separator")).append("Unique: ").append(isUnique());
    sb.append(System.getProperty("line.separator")).append("Level: ").append(getLevel());
    return sb.toString();
  }

}

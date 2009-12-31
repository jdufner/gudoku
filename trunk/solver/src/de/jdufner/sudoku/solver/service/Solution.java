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
package de.jdufner.sudoku.solver.service;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.misc.Level;

/**
 * Diese Klasse beschreibt eine L�sung eines Sudokus. Eine L�sung setzt sich aus mehreren Einzelschritten
 * {@link SolutionStepImpl} zusammen. Eine vorhandene L�sung ist eine nur eine L�sung, m�glicherweise existieren weitere
 * L�sungen, sowohl in dem Sinne, dass dieselbe L�sung anders erzeugt werden kann durch andere L�sungsschritte als auch,
 * dass m�glicherweise das Sudoku unterbestimmt ist und weitere L�sungen existieren.
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
   *          Das Start-Sudoku, f�r das die L�sung berechnet werden. Hier muss schon eine Kopie / Klon �bergeben werden.
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

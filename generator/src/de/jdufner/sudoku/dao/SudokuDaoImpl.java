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
package de.jdufner.sudoku.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import de.jdufner.sudoku.common.board.Sudoku;
import de.jdufner.sudoku.common.board.SudokuSize;
import de.jdufner.sudoku.common.misc.Level;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class SudokuDaoImpl implements SudokuDao {

  private static final Logger LOG = Logger.getLogger(SudokuDaoImpl.class);

  private HibernateTemplate hibernateTemplate;

  @Override
  public SudokuData deleteSudoku(int id) {
    SudokuData sudokuData = (SudokuData) hibernateTemplate.get(SudokuData.class, id);
    hibernateTemplate.delete(sudokuData);
    return sudokuData;
  }

  @Override
  public List<SudokuData> findSudokus(SudokuSize size, Level level, int number, Boolean printed) {
    hibernateTemplate.setFetchSize(number);
    hibernateTemplate.setMaxResults(number);
    StringBuilder sb = new StringBuilder("from SudokuData s where s.size = ? and s.level = ? ");
    if (printed != null) {
      if (printed.booleanValue()) {
        sb.append("and s.printedAt is not null ");
      }
      if (!printed.booleanValue()) {
        sb.append("and s.printedAt is null ");
      }
    }
    sb.append("order by s.generatedAt asc");
    List<SudokuData> sudokuDataList = hibernateTemplate.find(sb.toString(), new Object[] { size.getUnitSize(),
        level.getValue() });
    return sudokuDataList;
  }

  @Override
  public Sudoku loadSudokuOfDay() {
    hibernateTemplate.setFetchSize(1);
    hibernateTemplate.setMaxResults(1);
    List<SudokuData> sudokus = hibernateTemplate
        .find("from SudokuData s where s.size = 9 order by s.generatedAt desc, s.level desc, s.fixed desc");
    return SudokuMapper.map(sudokus.get(0));
  }

  @Override
  public SudokuData loadSudoku(int id) {
    return (SudokuData) hibernateTemplate.get(SudokuData.class, id);
  }

  @Override
  public SudokuData saveSudoku(Sudoku sudoku) {
    if (LOG.isDebugEnabled()) {
      LOG.debug("Save Sudoku.");
    }
    SudokuData sudokuData = SudokuMapper.map(sudoku);
    Serializable serializable = hibernateTemplate.save(sudokuData);
    if (LOG.isInfoEnabled()) {
      LOG.info("Sudoku saved.");
    }
    sudokuData.setId(((Integer) serializable).intValue());
    return sudokuData;
  }

  @Override
  public void updatePrintedAt(int id, Date printedAt) {
    hibernateTemplate.setFetchSize(1);
    hibernateTemplate.setMaxResults(1);
    List<SudokuData> sudokus = hibernateTemplate.find("from SudokuData s where s.id = ?", new Object[] { id });
    if (sudokus.size() >= 1) {
      SudokuData sudokuData = sudokus.get(0);
      sudokuData.setPrintedAt(printedAt);
      hibernateTemplate.update(sudokuData);
    }
  }

  //
  // Spring Wiring
  // 
  public void setSessionFactory(SessionFactory sessionFactory) {
    hibernateTemplate = new HibernateTemplate(sessionFactory);
  }

}

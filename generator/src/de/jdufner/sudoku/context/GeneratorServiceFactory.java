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
package de.jdufner.sudoku.context;

import java.util.Properties;

import org.apache.commons.math.random.RandomData;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import de.jdufner.sudoku.builder.Builder;
import de.jdufner.sudoku.dao.SudokuDao;
import de.jdufner.sudoku.pdf.PdfGenerator;
import de.jdufner.sudoku.pdf.PdfPrinter;
import de.jdufner.sudoku.text.ApproachPrinter;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class GeneratorServiceFactory {
  private static final Logger LOG = Logger.getLogger(GeneratorServiceFactory.class);

  private static final String RANDOM_ELEMINATION_BUILDER = "randomEleminationBuilder";
  private static final String SYMETRIC_RANDOM_ELEMINATION_BUILDER = "symetricRandomEleminationBuilder";
  private static final String LITERAL_ELEMINATION_BUILDER = "literalEleminationBuilder";

  private static final String SUDOKU_DAO = "sudokuDao";

  private static final String PDF_GENERATOR = "pdfGenerator";

  private ApplicationContext applicationContext;

  private static class SingletonHolder {
    static GeneratorServiceFactory instance = new GeneratorServiceFactory();
  }

  private GeneratorServiceFactory() {
    applicationContext = new ClassPathXmlApplicationContext("generator-context.xml");
    try {
      Log4jConfigurer.initLogging(Log4jConfigurer.CLASSPATH_URL_PREFIX + "log4j.properties", 10000);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    }
  }

  public static GeneratorServiceFactory getInstance() {
    return SingletonHolder.instance;
  }

  public Builder getRandomEleminationBuilder() {
    return (Builder) applicationContext.getBean(RANDOM_ELEMINATION_BUILDER);
  }

  public Builder getSymetricRandomEleminationBuilder() {
    return (Builder) applicationContext.getBean(SYMETRIC_RANDOM_ELEMINATION_BUILDER);
  }

  public Builder getLiteralEleminationBuilder() {
    return (Builder) applicationContext.getBean(LITERAL_ELEMINATION_BUILDER);
  }

  public SudokuDao getSudokuDao() {
    return (SudokuDao) applicationContext.getBean(SUDOKU_DAO);
  }

  public PdfGenerator getPdfGenerator() {
    return (PdfGenerator) applicationContext.getBean(PDF_GENERATOR);
  }

  public PdfPrinter getPdfPrinter() {
    return (PdfPrinter) applicationContext.getBean("pdfPrinter");
  }

  public Properties getPdfStyle() {
    return (Properties) applicationContext.getBean("pdfStyle");
  }

  public ApproachPrinter getApproachPrinter() {
    return (ApproachPrinter) applicationContext.getBean("approachPrinter");
  }

  public RandomData getRandomData() {
    return (RandomData) applicationContext.getBean("randomData");
  }

}

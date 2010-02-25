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
package de.jdufner.sudoku.context;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class GeneratorServiceFactory {
  private static final Logger LOG = Logger.getLogger(GeneratorServiceFactory.class);

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

  public Properties getPdfStyle() {
    return (Properties) applicationContext.getBean("pdfStyle");
  }

  public Object getBean(Class clazz) {
    String[] beanNames = applicationContext.getBeanNamesForType(clazz);
    if (beanNames.length == 0) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " nicht.");
    }
    if (beanNames.length > 1) {
      throw new IllegalStateException("Klasse " + clazz + " existiert im Kontext "
          + applicationContext.getDisplayName() + " mehrfach und ist nicht eindeutig.");
    }
    return applicationContext.getBean(beanNames[0]);
  }

}

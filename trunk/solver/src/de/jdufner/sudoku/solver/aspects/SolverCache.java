// $Id: SolverCache.java,v 1.8 2009/11/24 22:26:43 jdufner Exp $

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
package de.jdufner.sudoku.solver.aspects;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import de.jdufner.sudoku.common.board.Sudoku;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision: 1.8 $
 */
public final class SolverCache implements MethodInterceptor, InitializingBean {

  private static final Logger LOG = Logger.getLogger(SolverCache.class);

  private transient Cache cache;
  private transient int callCounter = 0;
  private transient int cacheHits = 0;

  public void afterPropertiesSet() {
    // Assert.notNull(cache, "Cache not set!");
    assert cache != null : "Cache not set";
  }

  public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
    callCounter += 1;
    final String cacheKey = methodInvocation.getMethod().getName() + "#"
        + getCacheKey((Sudoku) methodInvocation.getArguments()[0]);
    LOG.debug("Looking in Cache");
    Element element = cache.get(cacheKey);
    if (element == null) {
      LOG.debug("Calling Method");
      final Object result = methodInvocation.proceed();
      element = new Element(cacheKey, result);
      cache.put(element);
    } else {
      cacheHits += 1;
    }
    LOG.debug("Hitrate " + cacheHits + "/" + callCounter + " (" + (double) cacheHits / callCounter + ")");
    return element.getObjectValue();
  }

  private String getCacheKey(final Sudoku sudoku) {
    return sudoku.toShortString();
  }

  public void setCache(final Cache cache) {
    this.cache = cache;
  }

}

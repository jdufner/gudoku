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
package de.jdufner.sudoku.web;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import de.jdufner.sudoku.common.board.Sudoku;

/**
 * @author <a href="mailto:jdufner@users.sf.net">J&uuml;rgen Dufner</a>
 * @since 0.1
 * @version $Revision$
 */
public final class TemplateEngine {

  private Template singleTemplate;
  private Template pluralTemplate;

  private static class SingletonHolder {
    static TemplateEngine instance = new TemplateEngine();
  }

  public static TemplateEngine getInstance() {
    return SingletonHolder.instance;
  }

  private TemplateEngine() {
    try {
      Properties p = new Properties();
      p.setProperty("resource.loader", "class");
      p.setProperty("class.resource.loader.class",
          "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
      p.setProperty("velocimacro.permissions.allow.inline.local.scope", "true");
      p.setProperty("velocimacro.library", p.getProperty("velocimacro.library") + ", global.vm");
      Velocity.init(p);
      singleTemplate = Velocity.getTemplate("sudoku.vm");
      pluralTemplate = Velocity.getTemplate("sudokus.vm");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public String sudokuAsHtmlString(Sudoku sudoku, boolean printCandidates) throws ResourceNotFoundException,
      ParseErrorException, MethodInvocationException, IOException {
    StringWriter sw = new StringWriter();
    sudokuAsHtml(sudoku, printCandidates, sw);
    return sw.toString();
  }

  public void sudokuAsHtml(Sudoku sudoku, boolean printCandidates, Writer writer) throws ResourceNotFoundException,
      ParseErrorException, MethodInvocationException, IOException {
    VelocityContext ctx = new VelocityContext();
    ctx.put("sudoku", sudoku);
    ctx.put("printCandidates", new Boolean(printCandidates));
    singleTemplate.merge(ctx, writer);
  }

  public String sudokuAsHtmlString(List<Sudoku> sudokus, boolean printCandidates) throws ResourceNotFoundException,
      ParseErrorException, MethodInvocationException, IOException {
    StringWriter sw = new StringWriter();
    sudokuAsHtml(sudokus, printCandidates, sw);
    return sw.toString();
  }

  public void sudokuAsHtml(List<Sudoku> sudokus, boolean printCandidates, Writer writer)
      throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException {
    VelocityContext ctx = new VelocityContext();
    ctx.put("sudokus", sudokus);
    ctx.put("printCandidates", new Boolean(printCandidates));
    pluralTemplate.merge(ctx, writer);
  }

}

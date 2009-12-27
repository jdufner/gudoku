// $Id: Gudoku.java,v 1.5 2009/11/11 23:11:22 jdufner Exp $

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
package net.sf.gudoku.client;

import net.sf.gudoku.client.dto.Game;
import net.sf.gudoku.client.dto.Sudoku;
import net.sf.gudoku.client.dto.SudokuSize;
import net.sf.gudoku.client.service.SudokuService;
import net.sf.gudoku.client.service.SudokuServiceAsync;
import net.sf.gudoku.client.widget.SudokuBoard;
import net.sf.gudoku.client.widget.SudokuControl;
import net.sf.gudoku.client.widget.SudokuMenu;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gudoku implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network " + "connection and try again.";

  private static final String GAME_ID_COOKIE_NAME = "net.sf.gudoku.game.id";

  // Services
  public final SudokuServiceAsync sudokuService = (SudokuServiceAsync) GWT.create(SudokuService.class);
  private ServiceDefTarget endpoint = null;
  private String moduleRelativeURL = null;

  // Widgets
  private SudokuMenu sudokuMenu = new SudokuMenu(this);
  private SudokuBoard sudokuBoard = null;
  private SudokuControl sudokuControl = null;
  private final Label errorMessage = new Label("");
  private final Label infoMessage = new Label("");

  // Models
  private Game game = null;
  private SudokuSize sudokuSize = null;
  private Sudoku sudoku = null;

  private void init() {
    if (endpoint == null || moduleRelativeURL == null) {
      endpoint = (ServiceDefTarget) sudokuService;
      moduleRelativeURL = GWT.getModuleBaseURL() + "sudoku";
      endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
  }

  private int getNextGameIdFromCookie() {
    String gameIdAsString = Cookies.getCookie(GAME_ID_COOKIE_NAME);
    int gameId = 0;
    if (gameIdAsString != null && gameIdAsString.length() > 0) {
      gameId = Integer.parseInt(gameIdAsString);
    }
    gameId += 1;
    Cookies.setCookie(GAME_ID_COOKIE_NAME, String.valueOf(gameId));
    return gameId;
  }

  public void newGame() {
    init();

    sudokuService.newGame(getNextGameIdFromCookie(), new AsyncCallback() {
      public void onSuccess(Object result) {
        infoMessage.setText("success");
        infoMessage.setText(getBrowserType());
        game = (Game) result;
        sudoku = game.getSudoku();
        sudokuSize = sudoku.getSize();
        sudokuControl = new SudokuControl(Gudoku.this);
        sudokuBoard = new SudokuBoard(Gudoku.this);
      }

      public void onFailure(Throwable caught) {
        errorMessage.setText(caught.getMessage());
      }
    });
  }

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    RootPanel.get("slot1").add(sudokuMenu);
    RootPanel.get("slot4").add(errorMessage);
    RootPanel.get("slot5").add(infoMessage);
    newGame();
  }

  public Game getGame() {
    return game;
  }

  public Sudoku getSudoku() {
    return sudoku;
  }

  public SudokuSize getSudokuSize() {
    return sudokuSize;
  }

  public SudokuMenu getSudokuMenu() {
    return sudokuMenu;
  }

  public SudokuBoard getSudokuBoard() {
    return sudokuBoard;
  }

  public SudokuControl getSudokuControl() {
    return sudokuControl;
  }

  public Label getErrorMessage() {
    return errorMessage;
  }

  public Label getInfoMessage() {
    return infoMessage;
  }

  public static native String getBrowserType() /*-{
    var ua = navigator.userAgent.toLowerCase();
    if (ua.indexOf("opera") != -1) {
    return "opera";
    }
    else if (ua.indexOf("webkit") != -1) {
    return "safari";
    }
    else if ((ua.indexOf("msie 6.0") != -1) ||
    (ua.indexOf("msie 7.0") != -1)) {
    return "ie6";
    }
    else if (ua.indexOf("gecko") != -1) {
    var result = /rv:([0-9]+)\.([0-9]+)/.exec(ua);
    if (result && result.length == 3) {
    var version = (parseInt(result[1]) * 10) + parseInt(result[2]);
    if (version >= 18)
    return "gecko1_8";
    }
    return "gecko";
    }
    return "unknown";
  }-*/;

  // /**
  // * Create a remote service proxy to talk to the server-side Greeting service.
  // */
  // private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
  //
  // /**
  // * This is the entry point method.
  // */
  // public void onModuleLoad() {
  // final Button sendButton = new Button("Send");
  // final TextBox nameField = new TextBox();
  // nameField.setText("GWT User");
  //
  // // We can add style names to widgets
  // sendButton.addStyleName("sendButton");
  //
  // // Add the nameField and sendButton to the RootPanel
  // // Use RootPanel.get() to get the entire body element
  // RootPanel.get("nameFieldContainer").add(nameField);
  // RootPanel.get("sendButtonContainer").add(sendButton);
  //
  // // Focus the cursor on the name field when the app loads
  // nameField.setFocus(true);
  // nameField.selectAll();
  //
  // // Create the popup dialog box
  // final DialogBox dialogBox = new DialogBox();
  // dialogBox.setText("Remote Procedure Call");
  // dialogBox.setAnimationEnabled(true);
  // final Button closeButton = new Button("Close");
  // // We can set the id of a widget by accessing its Element
  // closeButton.getElement().setId("closeButton");
  // final Label textToServerLabel = new Label();
  // final HTML serverResponseLabel = new HTML();
  // VerticalPanel dialogVPanel = new VerticalPanel();
  // dialogVPanel.addStyleName("dialogVPanel");
  // dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
  // dialogVPanel.add(textToServerLabel);
  // dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
  // dialogVPanel.add(serverResponseLabel);
  // dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
  // dialogVPanel.add(closeButton);
  // dialogBox.setWidget(dialogVPanel);
  //
  // // Add a handler to close the DialogBox
  // closeButton.addClickHandler(new ClickHandler() {
  // public void onClick(ClickEvent event) {
  // dialogBox.hide();
  // sendButton.setEnabled(true);
  // sendButton.setFocus(true);
  // }
  // });
  //
  // // Create a handler for the sendButton and nameField
  // class MyHandler implements ClickHandler, KeyUpHandler {
  // /**
  // * Fired when the user clicks on the sendButton.
  // */
  // public void onClick(ClickEvent event) {
  // sendNameToServer();
  // }
  //
  // /**
  // * Fired when the user types in the nameField.
  // */
  // public void onKeyUp(KeyUpEvent event) {
  // if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
  // sendNameToServer();
  // }
  // }
  //
  // /**
  // * Send the name from the nameField to the server and wait for a response.
  // */
  // private void sendNameToServer() {
  // sendButton.setEnabled(false);
  // String textToServer = nameField.getText();
  // textToServerLabel.setText(textToServer);
  // serverResponseLabel.setText("");
  // greetingService.greetServer(textToServer, new AsyncCallback<String>() {
  // public void onFailure(Throwable caught) {
  // // Show the RPC error message to the user
  // dialogBox.setText("Remote Procedure Call - Failure");
  // serverResponseLabel.addStyleName("serverResponseLabelError");
  // serverResponseLabel.setHTML(SERVER_ERROR);
  // dialogBox.center();
  // closeButton.setFocus(true);
  // }
  //
  // public void onSuccess(String result) {
  // dialogBox.setText("Remote Procedure Call");
  // serverResponseLabel.removeStyleName("serverResponseLabelError");
  // serverResponseLabel.setHTML(result);
  // dialogBox.center();
  // closeButton.setFocus(true);
  // }
  // });
  // }
  // }
  //
  // // Add a handler to send the name to the server
  // MyHandler handler = new MyHandler();
  // sendButton.addClickHandler(handler);
  // nameField.addKeyUpHandler(handler);
  // }
}

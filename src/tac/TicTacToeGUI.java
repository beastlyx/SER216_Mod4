/**
 * Description: TicTacToe GUI application
 * Belongs to ui package
 * 
 * @author Catherine Button
 * @version 1.0
 */

package tac;

import tic.TicTacToeLogic;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.scene.text.*;

public class TicTacToeGUI extends Application {
	
	final static int ROWS = 3;
	final static int COLUMNS = 3;
	public final static Label MESSAGE = new Label(" ");
	public final static Label MOVE = new Label(" ");
	public final static Label[][] piece = new Label[ROWS][COLUMNS];
	
	TicTacToeLogic logic;
	boolean computerPlayerBoolean = false;
	Stage gameStage;
	String move = "";
	boolean moveMade = false;
	
	char currentPlayer = 'X';
	char nextPlayer = 'O';
	
	/**
	 * Entry point into the program.
	 * 
	 * @param args: used to launch GUI
	 */
	public static void main(String[] args) { 
	    launch(args);
	}
	
	/**
	 * Starts GUI application
	 * Allows player to choose the way they play the game
	 * 
	 * @param gameSelectStage: Stage to be shown at beginning of application
	 */
	@Override
	public void start(Stage gameSelectStage) {
		FlowPane gameTypePane = buildMessagePane("TicTacToe", Color.DARKOLIVEGREEN, 40); //set gameTypePane 
		//jumps to line 93
		
		Button textConsole = new Button("Text Console"); //create Text Console button
		textConsole.setPrefWidth(100); //set width
		textConsole.setOnAction(new ButtonHandler(gameSelectStage)); //set Action Listener
		//Action Listener: see line 131
		
		Button gui = new Button("GUI"); //create GUI button
		gui.setPrefWidth(100); //set width
		gui.setOnAction(new ButtonHandler(gameSelectStage)); //set Action Listener
		//Action Listener: see line 135
		
		HBox gameSelectButtonsPane = new HBox(20); //create buttons pane
		gameSelectButtonsPane.getChildren().add(textConsole); //add text console button to button pane
		gameSelectButtonsPane.getChildren().add(gui); //add gui button to button pane
		
		VBox gameSelectPane = new VBox(10); //create gameSelectPane
		gameSelectPane.setPadding(new Insets(10, 10, 10, 40)); //set padding
		gameSelectPane.getChildren().add(gameTypePane); //add gameTypePane to gameSelectPane
		gameSelectPane.getChildren().add(gameSelectButtonsPane); //add buttons pane to gameSelectPane
		
		gameSelectStage.setTitle("TicTacToe"); //set stage title
		gameSelectStage.setScene(new Scene(gameSelectPane, 300, 110)); //set scene(pane, width, height)
		gameSelectStage.show(); //show stage
	}
	
	/**
	 * Builds FlowPane with text inside.
	 * 
	 * @param messageIn: text to be displayed
	 * @param color: color to set text font to
	 * @param fontSize: size to set text font to
	 * @return FlowPane containing text set by function
	 */
	private FlowPane buildMessagePane(String messageIn, Color color, int fontSize) {
		Text message = new Text(messageIn); //create message from passed messageIn
		message.setFont(Font.font("Verdana", FontWeight.MEDIUM, fontSize)); //set message font
		message.setFill(color); //set message color
		
		FlowPane messagePane = new FlowPane(); //create messagePane
		messagePane.getChildren().add(message); //add message to messagePane
		return messagePane; //return messagePane
	}
	
	/**
	 * Handler class for Buttons
	 *
	 * @author Catherine Button
	 */
	class ButtonHandler implements EventHandler<ActionEvent> {
		Stage stage;
		String buttonLabel;
		
		/**
		 * Creates ButtonHandler for each button.
		 * 
		 * @param s: Stage where the button lies.
		 */
		public ButtonHandler(Stage s) {
			stage = s; //set Handler's stage to button's stage
		}
		
		/**
		 * Handles buttons
		 * 
		 * @param action: button clicked
		 */
		public void handle(ActionEvent action) {
			buttonLabel = ((Button)action.getSource()).getText(); //get buttonLabel from button
			
			switch(buttonLabel) { //Find which button to use
			//choosing UI buttons
				case "Text Console": //Text Console button
					stage.close(); //close stage button is on
					TicTacToeTextConsole.textConsoleTicTacToe(); //run text console
					break;
				case "GUI": //GUI button
					stage.close(); //close stage button is on
					playGuiGame(); //run GUI 
					break;
			//choosing opponent buttons
				case "The Computer": //computer opponent
					stage.close(); //close stage button is on
					logic.createComputerPlayer(); //create computer player
					computerPlayerBoolean = true; //set computerPlayerBoolean to true
					break;
				case "Another Player": //player vs player
					stage.close(); //close stage button is on
					computerPlayerBoolean = false; //set computerPlayerBoolean to false
					break;
			//no button implemented	
				default: //no such buttonLabel (you forgot to add this listener!)
					System.out.println("This button is not yet implemented"); //print to console
					break;
			}
		}
	}

	/**
	 * Calls methods to create game stage and opponent stage, then shows them.
	 */
	private void playGuiGame() {
		logic = new TicTacToeLogic(false); //create logic for GUI
		MESSAGE.setText("PlayerX - your turn\n"); //set MESSAGE
		gameStage = createGameStage(); //set game stage		see line 209
		Stage opponentStage = createOpponentStage(); //set opponent stage	see line 174
		gameStage.show(); //show game stage
		opponentStage.show(); //show opponent stage on top of game stage
	}
	
	/**
	 * Creates stage for the user to choose their opponent
	 * 
	 * @return created stage
	 */
	private Stage createOpponentStage() {
		FlowPane opponentPane = buildMessagePane("Pick your opponent", Color.CHARTREUSE, 24); //set opponentPane
		//jumps to line 93
		Stage opponentChoiceStage = new Stage(); //create stage
		
		Button player = new Button("Another Player"); //create player button
		player.setPrefWidth(100); //set width
		player.setOnAction(new ButtonHandler(opponentChoiceStage)); //set Action Listener
		//Action Listener: see line 145
		
		Button computer = new Button("The Computer"); //create computer button
		computer.setPrefWidth(100); //set width
		computer.setOnAction(new ButtonHandler(opponentChoiceStage)); //set Action Listener
		//Action Listener: see line 140
		
		HBox opponentSelectButtonsPane = new HBox(20); //create button pane
		opponentSelectButtonsPane.getChildren().add(player); //add player button to button pane
		opponentSelectButtonsPane.getChildren().add(computer); //add computer button to button pane
		
		VBox opponentSelectPane = new VBox(10); //create opponentSelectPane
		opponentSelectPane.setPadding(new Insets(20, 10, 10, 40)); //set padding
		opponentSelectPane.getChildren().add(opponentPane); //add opponentPane to opponentSelectPane
		opponentSelectPane.getChildren().add(opponentSelectButtonsPane); //add buttons pane to opponentSelectPane
		
		opponentChoiceStage.setTitle("TicTacToe"); //set stage title
		opponentChoiceStage.setScene(new Scene(opponentSelectPane, 300, 110)); //set stage scene (pane, width, height)
		
		return opponentChoiceStage; //return stage
	}
	
	/**
	 * Creates stage for TicTacToe game play.
	 * 
	 * @return created stage
	 */
	private Stage createGameStage() {
		Pane boardPane = new Pane(); //create board pane
		boardPane.setPrefWidth(285); //set width
		boardPane.setPrefHeight(285); //set height
		
		Rectangle boardBack = new Rectangle(); //create Rectangle
		boardBack.setX(5); //set X coordinate
		boardBack.setWidth(285); //set width
		boardBack.setHeight(285); //set height
		boardBack.setFill(Color.BLACK); //set color
		
		VBox rowsLabels = new VBox(75); //create row labels 
		rowsLabels.setAlignment(Pos.CENTER); //set alignments
		rowsLabels.setPadding(new Insets(0, 0, 0, 25)); //set padding
		rowsLabels.getChildren().add(new Label("3")); //add 3 at top
		rowsLabels.getChildren().add(new Label("2")); //add 2 in middle
		rowsLabels.getChildren().add(new Label("1")); //add 1 at bottom
		
		HBox colsLabels = new HBox(80); //create column labels
		colsLabels.setAlignment(Pos.CENTER); //set alignment
		colsLabels.setPadding(new Insets(5, 0, 0, 45)); //set padding
		colsLabels.getChildren().add(new Label("a")); //add a to left
		colsLabels.getChildren().add(new Label("b")); //add b to middle
		colsLabels.getChildren().add(new Label("c")); //add c to right
		
		GridPane board = createBoard(); //create board grid		see line 289
		
		boardPane.getChildren().add(boardBack); //add board rectangle to boardPane
		boardPane.getChildren().add(board); //add board grid to boardPane
		
		BorderPane gameBoard = new BorderPane(); //create game board
		gameBoard.setMaxWidth(305); //set width max
		gameBoard.setMaxHeight(305); //set height max
		gameBoard.setCenter(boardPane); //add boardPane to center of gameBoard
		gameBoard.setBottom(colsLabels); //add colsLabel to bottom of gameBoard
		gameBoard.setLeft(rowsLabels); //add rowsLabel to left of gameBoard
		
		MESSAGE.setPadding(new Insets(10, 10, 10, 20)); //set padding for MESSAGE
		MESSAGE.setFont(Font.font("Verdana", 20)); //set font for MESSAGE
		
		MOVE.setPadding(new Insets(10, 10, 10, 0)); //set padding for MOVE
		MOVE.setFont(Font.font("Verdana", 14)); //set font for MOVE
		
		FlowPane messagePane = buildMessagePane("Let's Play!", Color.DEEPSKYBLUE, 40); //create message pane  see line 93
		messagePane.setPadding(new Insets(10, 10, 10, 60)); //set padding
		
		Button newGame = new Button("New Game"); //create new game button 
		newGame.setPrefWidth(100); //set width
		newGame.setOnAction(bp -> {gameStage.close(); currentPlayer = 'X'; nextPlayer = 'O'; playGuiGame();}); //set Action Listener
		
		Button quit = new Button("Quit"); //create quit button
		quit.setPrefWidth(100);;
		//quit.setOnAction(new ButtonHandler(gameStage));
		quit.setOnAction(q -> {gameStage.close(); System.exit(0);});
		
		VBox buttonPane = new VBox(); //create buttonPane
		buttonPane.setPadding(new Insets(10, 20, 10, 10)); //set padding
		buttonPane.setAlignment(Pos.CENTER_RIGHT); //set alignment
		buttonPane.getChildren().add(newGame); //add newGame button to buttonPane
		buttonPane.getChildren().add(quit); //add quit button to buttonPane
		buttonPane.getChildren().add(MOVE); //add MOVE to buttonPane
		
		BorderPane gamePane = new BorderPane(); //create gamePane
		gamePane.setTop(messagePane); //add messagePane to top
		gamePane.setLeft(gameBoard); //add gameBoard to left
		gamePane.setBottom(MESSAGE); //add MESSAGE to bottom
		gamePane.setRight(buttonPane); //add buttonPane to right
		
		Stage gameStage = new Stage(); //create gameStage
		gameStage.setTitle("TicTacToe"); //set title
		gameStage.setScene(new Scene(gamePane, 500, 450)); //set scene(pane, width, height)
		
		return gameStage; //return game stage
	}
	
	/**
	 * Creates TicTacToe board with pieces, to be included in the game stage
	 * 
	 * @return GridPane of board
	 */
	private GridPane createBoard() {
		GridPane board = new GridPane(); //create grid pane called board
		board.setAlignment(Pos.CENTER); //set alignment
		board.setPadding(new Insets(10, 10, 10, 15)); //set padding
		board.setHgap(5); //set horizontal gap
		board.setVgap(5); //set vertical gap
		
		int rowIdentifier;
		
		Rectangle[][] square = new Rectangle[ROWS][COLUMNS]; //create 2-d array of Rectangles
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				
				square[row][col] = new Rectangle(); //create new Rectangle
				square[row][col].setX(15 + col * 35); //set X coordinate
				square[row][col].setY(15 + row * 35); //set Y coordinate
				square[row][col].setWidth(85); //set width
				square[row][col].setHeight(85); //set height
				square[row][col].setFill(Color.ALICEBLUE); //set color
				
				board.add(square[row][col], col, row); //add this Rectangle to board at col, row
				
			}
		}
		
		/* 
		 * This may get a little weird for some of you. In my Text Console, I print my board with a for loop
		 * starting at index 2 to index 0 for the rows. So, to counteract this on the GUI side, I used this
		 * rowIdentifier variable so that the piece array can be initialized using the correct index. 
		 */
		
		for (int row = 0; row < ROWS; row++) { //for each row
			for (int col = 0; col < COLUMNS; col++) { //for each column in each row
				//set rowIdentifier
				if (row == 2) rowIdentifier = 0;
				else if (row == 1) rowIdentifier = 1;
				else rowIdentifier = 2;
				
				piece[rowIdentifier][col] = new Label(" "); //initialize each piece to space
				
				piece[rowIdentifier][col].setFont(Font.font("Arial", 60)); //set piece font
				board.add(piece[rowIdentifier][col], col, rowIdentifier); //add piece to board
				board.setHalignment(piece[rowIdentifier][col], HPos.CENTER); //set horizontal alignment
				board.setValignment(piece[rowIdentifier][col], VPos.CENTER); //set vertical alignment
			}
		}
		
		/***************** LAMBDA EXPRESSIONS!!! *********************/
		
		square[0][0].setOnMouseClicked(mc -> {guiMove(board, 0, 0);}); //square[0][0]  Action Handler: Mouse Click
		square[0][1].setOnMouseClicked(mc -> {guiMove(board, 0, 1);}); //square[0][1]  Action Handler: Mouse Click 
		square[0][2].setOnMouseClicked(mc -> {guiMove(board, 0, 2);}); //square[0][2]  Action Handler: Mouse Click
		
		square[1][0].setOnMouseClicked(mc -> {guiMove(board, 1, 0);}); //square[1][0]  Action Handler: Mouse Click
		square[1][1].setOnMouseClicked(mc -> {guiMove(board, 1, 1);}); //square[1][1]  Action Handler: Mouse Click
		square[1][2].setOnMouseClicked(mc -> {guiMove(board, 1, 2);}); //square[1][2]  Action Handler: Mouse Click
		
		square[2][0].setOnMouseClicked(mc -> {guiMove(board, 2, 0);}); //square[2][0]  Action Handler: Mouse Click
		square[2][1].setOnMouseClicked(mc -> {guiMove(board, 2, 1);}); //square[2][1]  Action Handler: Mouse Click
		square[2][2].setOnMouseClicked(mc -> {guiMove(board, 2, 2);}); //square[2][2]  Action Handler: Mouse Click
		
		piece[0][0].setOnMouseClicked(mc -> {guiMove(board, 0, 0);}); //piece[0][0]  Action Handler: Mouse Click
		piece[0][1].setOnMouseClicked(mc -> {guiMove(board, 0, 1);}); //piece[0][1]  Action Handler: Mouse Click
		piece[0][2].setOnMouseClicked(mc -> {guiMove(board, 0, 2);}); //piece[0][2]  Action Handler: Mouse Click
		
		piece[1][0].setOnMouseClicked(mc -> {guiMove(board, 1, 0);}); //piece[1][0]  Action Handler: Mouse Click
		piece[1][1].setOnMouseClicked(mc -> {guiMove(board, 1, 1);}); //piece[1][1]  Action Handler: Mouse Click
		piece[1][2].setOnMouseClicked(mc -> {guiMove(board, 1, 2);}); //piece[1][2]  Action Handler: Mouse Click
		
		piece[2][0].setOnMouseClicked(mc -> {guiMove(board, 2, 0);}); //piece[2][0]  Action Handler: Mouse Click
		piece[2][1].setOnMouseClicked(mc -> {guiMove(board, 2, 1);}); //piece[2][1]  Action Handler: Mouse Click
		piece[2][2].setOnMouseClicked(mc -> {guiMove(board, 2, 2);}); //piece[2][2]  Action Handler: Mouse Click
		
		return board; //return the board
	}
	
	/**
	 * Called after click of piece/space on board. Handles game logic and playing the game.
	 * 
	 * @param board: TicTacToe board
	 * @param row: row of selected piece/space
	 * @param col: column of selected piece/space
	 */
	private void guiMove(GridPane board, int row, int col) {
		char rowChar = ' ', colChar;
			
		//convert row int to char (changes from number array knows to what the logic knows)
		switch(row) {
			case(0):
				rowChar = '3';
				break;
			case(1):
				rowChar = '2';
				break;
			default:
				rowChar = '1';
				break;
		}
		colChar = (char) (col + 97); //convert col int to char
		
		//move will be "" here always
		move = move + rowChar + colChar; //make into a string to pass to logic
	
		MOVE.setText(move); //set MOVE to represent move made
		boolean validMove = false;
		validMove = logic.makeMove(currentPlayer, nextPlayer, move, false); //call logic to make the move. Is it valid? (boolean answer)
		moveMade = validMove; //this could actually be omitted or omit the validMove variable but I was following the format I used in Text Console
		if (moveMade == true) { //if the move was valid
			updateGuiBoard(logic.getBoard()); //update the GUI board	see line 438
			if (logic.playerWon(currentPlayer, move) == true) { //checks if player won
				MOVE.setText(""); //set MOVE
				MESSAGE.setText("Player" + currentPlayer + " Won the Game"); //set MESSAGE
			}
			else if (logic.checkForTieGame()) { //checks for a tie game (all 9 spaces filled)
				MOVE.setText(""); //set MOVE
				MESSAGE.setText("Tie Game! No winner this time.\nPlease try again!"); //set MESSAGE
			}
			else { //current player has not won
				currentPlayer = logic.changePlayer(currentPlayer); //update currentPlayer
				nextPlayer = logic.changePlayer(nextPlayer); //update nextPlayer
				if (computerPlayerBoolean == false) { //no computer player
					MESSAGE.setText("Player" + currentPlayer + " - your turn!"); //set MESSAGE
				}
				else { //if player X is playing against the computer
					move = logic.getComputerPlayer().makeMove(logic, false); //computer takes turn
					updateGuiBoard(logic.getBoard()); //update the GUI board	see line 438
					if (logic.playerWon(currentPlayer, move) == true) { //if computer won
						MOVE.setText(""); //set MOVE
						MESSAGE.setText("The Computer Won the Game.\nBetter luck next time."); //set MESSAGE
					}
					else { //computer has not won
						currentPlayer = logic.changePlayer(currentPlayer); //update currentPlayer
						nextPlayer = logic.changePlayer(nextPlayer); //update nextPlayer
						MESSAGE.setText("Computer just took turn.\n" + move + "\nPlayer" + currentPlayer + " - your turn!"); //set MESSAGE
					}
				}
				moveMade = false; //set
			}
			
		}
		move = ""; //clear move string	

	}
	
	/**
	 * Updates the GUI board after a move has been made
	 * 
	 * @param pieces: pieces locations contained by TicTacToeLogic
	 */
	public static void updateGuiBoard(char[][] pieces) {
		int rowIdentifier; //same thing as before of the explanation of rowIdentifier	see line 314
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				//set rowIdentifier
				if (row == 2) rowIdentifier = 0;
				else if (row == 1) rowIdentifier = 1;
				else rowIdentifier = 2;
				
				if (pieces[row][col] == '_') //if space in logic contains "_"
					piece[rowIdentifier][col].setText(" "); //board in GUI contains " "
				else //otherwise
					piece[rowIdentifier][col].setText("" + pieces[row][col]); //set piece in GUI to be piece in logic (i.e. X or O)
			}
		}
	}
}

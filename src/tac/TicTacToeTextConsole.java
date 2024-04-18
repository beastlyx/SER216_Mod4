/**Description: Provides text based user interface for TicTacToe game.
 * Belongs to ui package.
 * 
 * @author Catherine Button
 * @version 1.0
 */

package tac;

import tic.TicTacToeLogic;
import java.util.Scanner;

public class TicTacToeTextConsole {
	private TicTacToeLogic logic;
	private Scanner scanner;
	
	/**
	 * Initializes TicTacToeTextConsole class variables needed
	 * outside of main method.
	 * scanner: Scanner for user input
	 * logic: CheckersLogic type that links text console to the logic
	 */
	public TicTacToeTextConsole() {
		scanner = new Scanner(System.in);
		logic = new TicTacToeLogic(true);
	}
	
	/**
	 * Method that runs the text console.
	 */
	public static void textConsoleTicTacToe() {
		TicTacToeTextConsole textBoard = new TicTacToeTextConsole();
		boolean quit = false;
		boolean computerPlayer = false;
		char currentPlayer = 'X';
		char nextPlayer = 'O';
		String move = "";
		
		printBoard(textBoard.logic.getBoard());
		computerPlayer = textBoard.getPlayAgainst();
		if (computerPlayer == true) {
			textBoard.logic.createComputerPlayer();
			textBoard.printStartGameAgainstComputer();
		}
		
		textBoard.printNewGameMessage();
		
		while (quit == false) {
			textBoard.printTakeTurnMessage(currentPlayer);
			if (currentPlayer == 'O' && computerPlayer == true) {
				move = textBoard.logic.getComputerPlayer().makeMove(textBoard.logic, true);
			}
			else {
				move = textBoard.getMove(currentPlayer, nextPlayer);
			}
			printBoard(textBoard.logic.getBoard());
			if (textBoard.logic.playerWon(currentPlayer, move) == true) {
				textBoard.printWinner(currentPlayer);
				quit = true;
			} else if (textBoard.logic.checkForTieGame() == true) {
				textBoard.printTieGame();
				quit = true;
			} else {
				currentPlayer = textBoard.logic.changePlayer(currentPlayer);
				nextPlayer = textBoard.logic.changePlayer(nextPlayer);
			}
		}
		textBoard.scanner.close();
	}
	
	/**
	 * Prints TicTacToe board with pieces.
	 * 
	 * @param board: char[3][3] of pieces and blank spaces
	 */
	public static void printBoard(char[][] board) {
		for (int i = 2; i >= 0; i--) {
			System.out.print((i + 1));
			for (int j = 0; j < 3; j++) {
				System.out.print("|" + board[i][j]);
			}
			System.out.println("|");
		}
		System.out.println("  a b c");
	}
	
	/**
	 * Prints "Begin Game. "
	 */
	public void printNewGameMessage() {
		System.out.print("Begin Game. ");
	}
	
	/**
	 * Prints "Enter 'P' if you want to play against another player; enter 'C' to play against computer."
	 */
	public void printPlayAgainst() {
		System.out.println("Enter 'P' if you want to play against another player; enter 'C' to play against computer.");
	}
	
	/**
	 * Prints "Start game against computer. You are Player X and Computer is Player O."
	 */
	public void printStartGameAgainstComputer() {
		System.out.println("Start game against computer. You are Player X and Computer is Player O.\n");
	}
	
	/**
	 * Prints message for player whose turn it is.
	 * 
	 * @param player: char of player whose turn it is
	 */
	public void printTakeTurnMessage(char player) {
		System.out.println("Player" + player + " - your turn.");
		System.out.println("Choose a cell position to place your piece. e.g., 3a for the top left corner.");
	}
	
	/**
	 * Prints message that player has won.
	 * 
	 * @param player: char of player who has won
	 */
	public void printWinner(char player) {
		System.out.println("Player" + player + " Won the Game");
	}
	
	/**
	 * Prints tie game message.
	 */
	public void printTieGame() {
		System.out.println("Tie game! No winner this time!");
	}
	
	/**
	 * Gets player's choice of whom they wish to play against.
	 * 
	 * @return boolean to set computerPlayer boolean in main
	 */
	public boolean getPlayAgainst() {
		boolean correctInput = false;
		boolean computerPlayer = false;
		while (correctInput == false) {
			printPlayAgainst();
			String play = scanner.next();
			
			if (play.compareTo("C") == 0) {
				computerPlayer = true;
				correctInput = true;
			}
			else if (play.compareTo("P") == 0) {
				computerPlayer = false;
				correctInput = true;
			}
		}
		return computerPlayer;
	}
	
	/**
	 * This is the player's turn.
	 * Intakes player's move and calls logic to check: validity of move and, if valid, makes move.
	 * 
	 * @param player: char of player whose turn it is
	 * @param opponent: char of player who is the opponent of the player whose turn it is.
	 */
	public String getMove(char player, char opponent) {
		String move = "";;
		boolean validMove = false;
		while (validMove == false) {
			move = scanner.next();
			validMove = logic.makeMove(player, opponent, move, false);
		}
		return move;
	}
}


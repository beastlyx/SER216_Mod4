/**Description: Provides game logic for TicTacToe game.
 * Belongs to core package.
 * 
 * @author Catherine Button
 * @version 1.0
 */
package tic;

import tac.TicTacToeGUI;

public class TicTacToeLogic {

	private char[][] board;
	private char playerX;
	private char playerO;
	private int turnsPlayed;
	private TicTacToeComputerPlayer computer;
	private boolean textConsole;

	public TicTacToeLogic(boolean textConsole) {
		setBoard();
		playerX = 'X';
		playerO = 'O';
		turnsPlayed = 0;
		this.textConsole = textConsole;
	}
	
	/**
	 * Initializes computer player.
	 */
	public void createComputerPlayer() {
		computer = new TicTacToeComputerPlayer();
	}
	
	/**
	 * Accessor method for private variable board.
	 * 
	 * @return char[][] this.board
	 */
	public char[][] getBoard() {
		return this.board;
	}
	
	/**
	 * Private Method.
	 * Initializes board.
	 */
	private void setBoard() {
		char[][] board = new char[3][3];
		
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				board[row][col] = '_';
			}
		}
		
		this.board = board;
	}
	
	/**
	 * Private method.
	 * Changes one space on the board based on the player's move.
	 * 
	 * @param player: char of player whose turn it is
	 * @param move: char array of row and col for the space of which the player wishes to place their piece
	 */
	private void updateBoard(char player, char[] move) {
		int moveRow = ((int) move[0]) - 49;
		int moveCol = ((int) move[1]) - 97;
		
		this.board[moveRow][moveCol] = player;
	}
	
	/**
	 * Makes player's move.
	 * 
	 * @param player : char of current player that is making a move
	 * @param opponent : char that can lose a piece during a jump
	 * @param move : String that contains move to be made
	 * @param computer : boolean that indicates whether the computer player is making a move
	 * @return boolean that indicates whether the move entered by the player was valid
	 */
	public boolean makeMove(char player, char opponent, String move, boolean computer) {
		boolean validMove;
		char[] moveArray = new char[2];
		
		moveArray = move.toCharArray();
		
		validMove = validMove(player, opponent, moveArray);
		if (validMove == true) {
			updateBoard(player, moveArray);
		}
		return validMove;
	}
	
	/**
	 * Check validity of move made by current player.
	 * 
	 * @param player: char of current player
	 * @param opponent: char of current player's opponent
	 * @param move: char[2] that holds the  coordinate of the move
	 * @return boolean value indicating if move is valid
	 */
	public boolean validMove(char player, char opponent, char[] move) {
		try {
			if (board[(int)(move[0] - 49)][(int)(move[1] - 97)] == player || board[(int)(move[0] - 49)][(int)(move[1] - 97)] == opponent) {
				if (textConsole == true) {
					System.out.println("The space " + move[0] + move[1] + " is occupied");
					System.out.println("Player" + player + " please make a different selection.");
				}
				else { //GUI
					TicTacToeGUI.MESSAGE.setText("The space " + move[0] + move[1] + " is occupied" +
							"\nPlayer" + player + " please make a different selection.");
				}
				return false;
			}
		} catch (IndexOutOfBoundsException indexOutOfBounds) {
			if (textConsole == true) {
				System.out.println("That is not the correct format.");
				System.out.println("Player " + " please use the format 3a.");
			}
			else { //GUI
				TicTacToeGUI.MESSAGE.setText("Invalid move. Player" + player + " please try again.");
			}
			return false;
		}
		
		this.turnsPlayed++;
		return true;
	}
	
	/**
	 * Determines if a player has won the game.
	 * 
	 * @param player: char of current player
	 * @param opponent: char of current player's opponent
	 * @return boolean value indicating whether player is the winner
	 */
	public boolean playerWon(char player, String move) {
		int row = ((int) move.charAt(0)) - 49;
		int col = ((int) move.charAt(1)) - 97;
		
		if (row == 0 && col == 0 && ((board[0][1] == player && board[0][2] == player) || (
				board[1][0] == player && board[2][0] == player) || (board[1][1] == player && board[2][2] == player))) {
			return true;
		} else if (row == 1 && col == 0 && ((board[1][1] == player && board[1][2] == player) || (
				board[0][0] == player && board[2][0] == player))) {
			return true;
		} else if (row == 2 && col == 0 && ((board[2][1] == player && board[2][2] == player) || (
				board[0][0] == player && board[1][0] == player) || (board[1][1] == player && board[0][2] == player))) {
			return true;
		} else if (row == 0 && col == 1 && ((board[1][1] == player && board[1][2] == player) || (
				board[0][0] == player && board[0][2] == player))) {
			return true;
		} else if (row == 1 && col == 1 && ((board[1][0] == player && board[1][2] == player) || 
				(board[0][1] == player && board[2][1] == player) || (board[0][0] == player && board[2][2] == player) || 
				(board[2][0] == player && board[0][2] == player))) {
			return true;
		}  else if (row == 2 && col == 1 && ((board[2][0] == player && board[2][2] == player) || (
				board[0][1] == player && board[1][1] == player))) {
			return true;
		} else if (row == 0 && col == 2 && ((board[0][0] == player && board[0][1] == player) || (
				board[1][2] == player && board[2][2] == player) || (board[1][1] == player && board[2][0] == player))) {
			return true;
		} else if (row == 1 && col == 2 && ((board[0][2] == player && board[2][2] == player) || (
				board[1][0] == player && board[1][1] == player))) {
			return true;
		} else if (row == 2 && col == 2 && ((board[0][2] == player && board[1][2] == player) || (
				board[2][0] == player && board[2][1] == player) || (board[0][0] == player && board[1][1] == player))) {
			return true;
		}
		return false;
	}
	
	/**
	 * If all nine spaces are filled and no player has won then a tie game has occurred. 
	 * 
	 * @return true if turnsPlayed = 9 false if turnsPlayed < 9
	 */
	public boolean checkForTieGame() {
		if (this.turnsPlayed == 9) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Changes player for user interface
	 * 
	 * @param player: char of current player
	 * @return char of opposite player
	 */
	public char changePlayer(char player) {
		if (player == 'X')
			return 'O';
		else
			return 'X';
	}
	
	/**
	 * Gives UI access to TicTacToeComputerPlayer object computer.
	 * 
	 * @return this.computer object of type TicTacToeComputerPlayer
	 */
	public TicTacToeComputerPlayer getComputerPlayer() {
		return computer;
	}
}

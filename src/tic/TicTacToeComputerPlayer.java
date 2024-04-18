/**Description: Provides Computer Player for Tic Tac Toe game.
 * Belongs to core package.
 * 
 * @author Catherine Button
 * @version 1.0
 */

package tic;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;

import tac.TicTacToeGUI;

public class TicTacToeComputerPlayer {
	
	char player;
	char opponent;
	
	public TicTacToeComputerPlayer() {
		player = 'O';
		opponent = 'X';
	}
	
	/**
	 * Private method.
	 * Chooses random move for computer player of the available moves.
	 * 
	 * @param logic: game logic being passed in
	 * @return string of move that was chosen
	 */
	private String findMove(TicTacToeLogic logic) {
		List<String> moves = new LinkedList<String>();
		String move = "";
		char[][] board = logic.getBoard();
		char row, col;
		Random rand = new Random();
		int randomMove;
		
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (board[r][c] == '_') {
					row = (char) (r + 49);
					col = (char) (c + 97);
					move += row;
					move += col;
					moves.add(move);
					move = "";
				}
			}
		}
		
		randomMove = rand.nextInt(100) % moves.size();
		return moves.get(randomMove);
	}

	/**
	 * Makes computer player move.
	 * This is a random move with no strategy behind it.
	 * 
	 * @param logic: game logic being passed in
	 * @param textConsole: tells the computer player if it is playing on a text console or GUI (for print statement use)
	 * @return string of the move made
	 */
	public String makeMove(TicTacToeLogic logic, boolean textConsole) {
		String move;
		move = findMove(logic);
		
		if (textConsole == true) {
			System.out.println("\n>> " + move + "\n");
		} else {
			TicTacToeGUI.MOVE.setText(">> " + move);
		}
		
		logic.makeMove(player, opponent, move, textConsole);
		
		return move;
	}
}

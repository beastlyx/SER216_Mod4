package core;

import java.util.Random;
/**
 * handles the computer logic for the Connect4 game.
 * It is responsible for generating a random column number for the computer's move.
 * 
 * @author Borys Banaszkiewicz
 * @version 1.0
 * 
 */
public class Connect4ComputerPlayer {
    /**
     * Custom exception for handling invalid moves made by computer.
     */
    class InvalidMoveException extends Exception {
        public InvalidMoveException() {
            super();
        }
    }
    /** 
     * Instance of Connect4Logic to check the validity of moves.
     */
    private Connect4Logic connect4;
    
    /**
     * Constructor for Connect4ComputerPlayer.
     * Initializes a new game logic instance.
     */
    public Connect4ComputerPlayer() {
        this.connect4 = new Connect4Logic();
    }
    
    /**
     * Selects a random column to place a piece.
     * If the selected column is full or if the move is not valid (not in range 
     * 1 - 7) it recursively continues to select another column until a valid
     * move is made.
     * 
     * @return the column number (1-7) where the computer will place its piece.
     */
    public int getColumn() {
        Random random = new Random();
        int col = random.nextInt(7) + 1;
        try {
            if (this.connect4.checkMoveValidity(col) == -1) {
                throw new InvalidMoveException();
            }
        }
        catch (InvalidMoveException ime) {
            return getColumn();
        }
        return col;
    }
}

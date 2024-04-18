package core;

/**
 * Handles core functionality of the connect4 game. Includes game rules, 
 * tracking state of the board, determining win conditions, and managing player 
 * turns
 * 
 * @author Borys Banaszkiewicz
 * @version 1.2
 * 
 */
public class Connect4Logic {
    
    /**
     * Tracks total number of moves made in the game so far. This count is used
     * to determine player turn as well as determining that a draw occurred if it
     * reached the maximum moves allowed (42).
     */
    private int turnCount;
    
    /**
     * Represents the state of the Connect4 game board as a 2D array of chars.
     * Each element in the array represents an empty spot, an 'X' for a move 
     * made by player X, or a 'O' for a move made by player O.
     */
    private char[][] board;
    
    /**
     * Constructs a new Connect4Logic instance. Initializes the game by setting
     * turnCount to 0 and board to a new empty board of size 6x7.
     */
    public Connect4Logic() {
        this.turnCount = 0;
        this.board = new char[6][7];
    }
    
    /**
     * Checks move validity by making sure that the col variable is a value 
     * between 1-7 and by ensuring that the column specified by col is not full.
     * 
     * @param col column specified by user that signifies where the player wants
     * to place their game piece
     * 
     * @return -1 if the move is not valid (not in range 1-7 or column is full),
     * or the next empty row in col.
     */
    public int checkMoveValidity(int col) {
        if (col > 7 || col < 1) return -1;
            
        for (int row = this.board.length - 1; row >= 0; row--) {
            if (this.board[row][col - 1] == 'X' || this.board[row][col - 1] == 'O') continue;
            else return row;
        }
        return -1;
    }
    
    /**
     * Updates the 2D char array board which tracks the state of the game. First
     * checks which players turn it is, then updates corresponding spot with the
     * symbol that indicates the player ('X' for player x and 'O' for player O).
     * 
     * @param turn indicates which players turn it is (0 for player X and 1 for
     * player O)
     * @param row indicates the row in which the player wants to place their 
     * piece
     * @param col indicates the column in which the player wants to place their 
     * piece
     */
    public void updateBoard(int turn, int row, int col) {
        if (turn == 0) this.board[row][col - 1] = 'X';
        else this.board[row][col - 1] = 'O';
    }

    /**
     * Checks the game board for a winner. There are 4 ways to win the game, each
     * way to win requires 4 concurrent pieces: horizontally, vertically, across 
     * to the left, and across to the right.
     * 
     * @return 0 if no winner (game continues), 1 if player x won, otherwise 2 
     * if player o won.
     */
    public int checkForWin() {
        int[][] horizontal = {{0,0}, {0,1}, {0,2}, {0,3}};
        int[][] vertical = {{0,0}, {1,0}, {2,0}, {3,0}};
        int[][] across_left = {{0,0}, {-1,-1}, {-2,-2}, {-3,-3}};
        int[][] across_right = {{0,0}, {-1,1}, {-2,2}, {-3,3}};

        for (int row = board.length - 1; row >= 0; row--) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == 'X' || board[row][col] == 'O') {
                    int win_horizontal = checkForWin(horizontal, row, col);
                    if (win_horizontal != 0) return win_horizontal;
                    
                    int win_vertical = checkForWin(vertical, row, col);
                    if (win_vertical != 0) return win_vertical;
                    
                    int win_across_left = checkForWin(across_left, row, col);
                    if (win_across_left != 0) return win_across_left;
                    
                    int win_across_right = checkForWin(across_right, row, col);
                    if (win_across_right != 0) return win_across_right;
                }
            }
        }
        return 0;
    }
    
    /**
     * private helper method that checks for winning conditions.
     * 
     * @param direction array representing direction that can result in a valid 
     * win.
     * @param row starting row index from which to check for a win.
     * @param col starting column index from which to check for a win.
     * 
     * @return 0 if no winner (game continues), 1 if player x won, otherwise 2 
     * if player o won.
     */
    private int checkForWin(int[][] direction, int row, int col) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int new_row = row + direction[i][0];
            int new_col = col + direction[i][1];
            if (new_row < 0 || new_row >= board.length || new_col < 0 || new_col >= board[0].length) continue; 
            if (board[new_row][new_col] == board[row][col]) count++;
        } 
        if (count == 4) {
            if (board[row][col] == 'X') return 1;
            else return 2;
        }
        return 0;
    }
    
    /**
     * Calculates current players turn by using modulo operator.
     * @return 0 if player x turn (turnCount % 2 == 0) or 1 if player o turn 
     * (turnCount % 2 != 0)
     */
    public int getPlayerTurn() {
        return turnCount % 2;
    }
    
    /**
     * Increments the turnCount variable to signify a valid turn was made and a
     * piece was placed on the board.
     */
    public void incrementTurn() {
        this.turnCount++;
    }
    
    /**
     * Resets the board to original state (empty board).
     */
    public void resetBoard() {
        this.turnCount = 0;
        this.board = new char[6][7];
    }
    
    /**
     * Returns the current state of the board.
     * @return current state of the board.
     */
    public char[][] getBoardState() {
        return this.board;
    }
}
package ui;

import core.Connect4Logic;
import core.Connect4ComputerPlayer;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * handles the user interactions through the console. Responsible for 
 * displaying game board, accepting user inputs for column selections, and 
 * displaying game status messages. Interacts with Connect4Logic class to
 * manage the states and rules of the game.
 * 
 * @author Borys Banaszkiewicz
 * @version 1.3
 * 
 */
public class Connect4TextConsole {
    /**
     * Uses Connect4Logic object as the game logic handler for Connect4.
     */
    private Connect4Logic connect4Logic;

    /**
     * Constructs new instance of Connect4TextConsole. Initializes the game 
     * logic and sets up the environment to start a new game
     */
    public Connect4TextConsole() {
        this.connect4Logic = new Connect4Logic();
    }

    /**
     * Starts and manages the Connect4 game session. Uses a while loop to 
     * control and display different parts of the game, ending when a player 
     * wins or the game is drawn. Uses return statements of called methods
     * to track game session. (Computer player logic and PVP logic handled here)
     */
    public void startGame() {
        displayBoard();
        
        System.out.println("Begin Game. Enter 'P' if you want to play against another player; enter 'C' to play against computer.");
        
        Scanner scnr = new Scanner(System.in);
        char game_type = scnr.next().charAt(0);
        
        System.out.println();
        
        int turns = 0;
        
        if (game_type == 'C') {
            Connect4ComputerPlayer computerPlayer = new Connect4ComputerPlayer();
            
            System.out.println("Start game against computer.");
            
            while (true) {
                System.out.println("It is your turn, choose a column number from 1-7.");
                while (!processUserInput()) {
                    System.out.println("Not a valid move, please try again");
                }
                turns++;
                displayBoard();
                if (turns == 42) {
                    System.out.println("The board is full, the game is a draw!");
                    break;
                }
                if (displayResults() != 0) break;
                
                int computer_col = computerPlayer.getColumn();
                
                int row = this.connect4Logic.checkMoveValidity(computer_col);
                
                while (row < 0) {
                    computer_col = computerPlayer.getColumn();
                    row = this.connect4Logic.checkMoveValidity(computer_col);
                }

                System.out.println("It is the computers turn, column picked by computer is: " + computer_col);
                
                this.connect4Logic.updateBoard(turns, row, computer_col);
                displayBoard();
                
                if (displayResults() != 0) break;
            }
        }
        else {
            while (true) {
                displayPlayerTurn();
                while (!processUserInput()) {
                    System.out.println("Not a valid move, please try again");
                }
                connect4Logic.incrementTurn();
                turns++;
                displayBoard();
                if (turns == 42) {
                    System.out.println("The board is full, the game is a draw!");
                    break;
                }
                if (displayResults() != 0) break;
            }
        }
    }

    /**
     * Displays the current players turn based on turn count.
     */
    public void displayPlayerTurn() {
        int turn = this.connect4Logic.getPlayerTurn();
        if (turn == 0) {
            System.out.print("PlayerX-your turn.");
        }
        else if (turn == 1) {
            System.out.print("PlayerO-your turn.");
        }
        System.out.println(" Choose a column number from 1-7.");
    }

    /**
     * Displays the game board for user by printing the current state of the 
     * board to the console.
     */
    public void displayBoard() {
        char[][] temp_board = this.connect4Logic.getBoardState();
        for (char[] board : temp_board) {
            for (char c : board) {
                if (c != 'X' && c != 'O') {
                    System.out.print("|   ");
                }
                else System.out.printf("|%2c ", c);
            }
            System.out.println("|");
        }
        System.out.println();
    }

    /**
     * Reads in user input as an int and checks if move is valid. A valid move
     * is a column number in range 1-7 and a column that is not already full.
     * 
     * @return true if user input was valid, otherwise false
     * @throws InputMismatchException if user input is not an int and will retry
     * by recursively returning method until there is a valid input.
     */
    public boolean processUserInput() {
        Scanner scnr = new Scanner(System.in);
        int col = 0;
        try {
            col = scnr.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Not a valid move, please try again");
            scnr.nextLine();  // Clear the invalid input
            return processUserInput();  // Retry
        }
        
        int row = this.connect4Logic.checkMoveValidity(col);
        
        if (row >= 0) {
            int turn = this.connect4Logic.getPlayerTurn();
            this.connect4Logic.updateBoard(turn, row, col);
            return true;
        }
        return false;
    }

    /**
     * Determines the game's end state based on the win condition. If there is 
     * no winner and the game is still ongoing, nothing is displayed to console. 
     * Will only display to console when there is a winner.
     * 
     * @return 0 if game is still ongoing (no winner), 1 if player X won, and 
     * 2 if player O won.
     */
    public int displayResults() {
        int win = this.connect4Logic.checkForWin();
        
        switch (win) {
            case 0:
                return 0;
            case 1:
                System.out.println("Player X Won the Game");
                return 1;
            case 2:
                System.out.println("Player O Won the Game");
                return 2;
        }
        
        return 0;
    }
    
    /**
     * main method responsible for starting and resetting the Connect4 game
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connect4TextConsole game = new Connect4TextConsole();
        game.startGame();
        game.connect4Logic.resetBoard();
    }
}
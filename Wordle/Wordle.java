
// Import: ArrayList, and Scanner
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Wordle in Java, done for fun.
 * 
 * @author Xander Reyes
 * @version 1.0
 */

public class Wordle {

    // Text Colors.
    public static final String textReset = "\u001B[0m";
    public static final String textGreen = "\u001B[32m";
    public static final String textYellow = "\u001B[33m";

    /**
     * Main Method - Runs code loop.
     * 
     * @param args
     */
    public static void main(String[] args) {

        // Init Default Variables.
        String answer = "wacky";
        int tries = 0;
        boolean win = false;
        ArrayList<String> board = new ArrayList<String>();
        Scanner input = new Scanner(System.in);
        initBoard(board);

        // Game Loop.
        while (tries < 5 && win == false) {
            printBoard(board, answer);
            System.out.printf("Guess %d: ", tries + 1);
            String currentGuess = input.nextLine().toLowerCase();
            Boolean validGuess = isValidGuess(currentGuess);

            if (validGuess) {
                win = guess(board, currentGuess, answer, tries);
                tries++;
            } else {
                System.out.println("Invalid Guess");
            }
        }

        if (win) {
            printBoard(board, answer);
            System.out.println("\nFinal Board:\n");
            printBoard(board, answer);
            System.out.printf("\nYou Win! The word was %s%s\n", textGreen, answer);
        } else {
            System.out.println("\nFinal Board:\n");
            printBoard(board, answer);
            System.out.printf("\nYou Lose! The word was %s%s", textGreen, answer);
        }
        System.out.println(textReset);
        input.close();
    }

    /**
     * Initializes the board.
     * 
     * @param board
     */
    public static void initBoard(ArrayList<String> board) {
        for (int i = 0; i < 5; i++) {
            board.add("_____");
        }
    }

    /**
     * Prints the board and changes the text color according to the answer.
     * 
     * @param board  board to print.
     * @param answer Answer to the game.
     */
    public static void printBoard(ArrayList<String> board, String answer) {
        for (String word : board) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == answer.charAt(i)) {
                    System.out.printf("%s %c", textGreen, word.charAt(i));
                } else if (answer.contains(String.valueOf(word.charAt(i)))) {
                    System.out.printf("%s %c ", textYellow, word.charAt(i));
                } else {
                    System.out.printf("%s %c", textReset, word.charAt(i));
                }

            }
            System.out.println();
        }
    }

    /**
     * Checks if the guess is valid.
     * 
     * @param guess Guess to check.
     * @return Boolean if the guess is valid.
     */
    public static boolean isValidGuess(String guess) {
        if (guess.length() == 5) {
            if (guess.matches(".*\\d.*")) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Checks if the guess is correct.
     * 
     * @param board  board to update.
     * @param guess  players guess.
     * @param answer answer to the game.
     * @param tries  number of tries for index of board.
     * @return Boolean if the guess is correct.
     */
    public static boolean guess(ArrayList<String> board, String guess, String answer, int tries) {
        board.set(tries, guess);
        return guess.equals(answer);
    }

}
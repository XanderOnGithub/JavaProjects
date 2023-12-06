
// Import: ArrayList, and Scanner
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Wordle in Java, done for fun.
 * Created 12/5/2023 - 9:30 PM - 10:30 PM
 * @author Xander Reyes
 * @version 1.0
 */

public class Wordle {

    /** 
     * Empty Constructor to fix error.
    */
    Wordle() {
        // Empty Constructor
    }

    // Text Colors (Made them static for easy access)

    /** Default Text Color. */
    private static final String textReset = "\u001B[0m";
    /** Green Text Color. */
    private static final String textGreen = "\u001B[32m";
    /** Yellow Text Color. */
    private static final String textYellow = "\u001B[33m";

    // File paths (Made them static for easy access)

    /** Filename of Allowed Guesses file. */
    private static final String allowedGuessesFilename = "AllowedGuesses.txt";
    /** Filename of Answers file. */
    private static final String answersFilename = "Answers.txt";

    // Variables for Game (Made static for readability)

    /** Random Object. */
    private static final Random random = new Random();
    /** Max Tries of Game. */
    private static final int maxTries = 5;
    /** Length of Word to Guess. */
    private static final int wordLength = 5;

    /**
     * Main Method - Runs code loop.
     * 
     * @param args
     */
    public static void main(String[] args) {

        // Answers and allowed Guesses
        ArrayList<String> allowedGuesses = readFileAsListOfStrings(allowedGuessesFilename);
        ArrayList<String> answers = readFileAsListOfStrings(answersFilename);
        allowedGuesses.addAll(answers);

        // Init Default Game Variables.
        String answer = answers.get(random.nextInt(answers.size()));
        int tries = 0;
        boolean win = false;
        ArrayList<String> board = new ArrayList<String>();
        Scanner input = new Scanner(System.in);
        initBoard(board);

        // Game Loop.
        while (tries < maxTries && win == false) {
            printBoard(board, answer);
            System.out.printf("Guess %d: ", tries + 1);
            String currentGuess = input.nextLine().toLowerCase().replaceAll(" ", "");
            Boolean validGuess = isValidGuess(allowedGuesses, currentGuess);

            if (validGuess) {
                win = guess(board, currentGuess, answer, tries);
                tries++;
            } else {
                System.out.println("Invalid Guess");
            }
        } // End of Game Loop

        // Checking win condition.
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
    } // End of Main Method

    /**
     * Initializes the board.
     * 
     * @param board board to initialize.
     */
    public static void initBoard(ArrayList<String> board) {
        String blankBoard = "_".repeat(wordLength);
        for (int i = 0; i < maxTries; i++) {
            board.add(blankBoard);
        }
    } // End of initBoard method

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
                    System.out.printf("%s %c", textYellow, word.charAt(i));
                } else {
                    System.out.printf("%s %c", textReset, word.charAt(i));
                }

            }
            System.out.println();
        } 
    } // End of printBoard method

    /**
     * Checks if the guess is valid.
     * 
     * @param guess          Guess to check.
     * @param allowedGuesses List of allowed guesses.
     * @return Boolean if the guess is valid.
     */
    public static boolean isValidGuess(ArrayList<String> allowedGuesses, String guess) {
        return guess.length() == wordLength && allowedGuesses.contains(guess) && !guess.matches(".*\\d.*");
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
    } // End of guess method

    /**
     * 
     * @param filename Filepath to read.
     * @return List of strings from file.
     */
    public static ArrayList<String> readFileAsListOfStrings(String filename) {
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            System.out.println("Having trouble reading file" + filename);
            e.printStackTrace();
        }
        return data;
    } // End of readFileAsListOfStrings method

} // End of Wordle Class

package a_wordle.main;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class Main {

    private static List<String> possibleWords;
    private static Set<String> validGuesses;

    private static String preprocessGuess(String guess) {
        return guess.trim().toLowerCase();
    }

    private static boolean validateGuess(String guess) {
        boolean rightLength = guess.length() == 5;
        boolean isValidGuess = getValidGuesses().contains(guess);
        return rightLength && isValidGuess;
    }

    private static String getRandomWord() {
        List<String> words = getPossibleWords();
        Random rand = new Random();
        int randomIndex = rand.nextInt(words.size());
        return words.get(randomIndex);
    }

    private static List<String> getPossibleWords() {
        if (possibleWords == null) {
            try {
                possibleWords = Files.readAllLines(new File("src/main/java/a_wordle/resource/possibleWords.txt").toPath(), Charset.defaultCharset());
            } catch (IOException e) {
                System.out.println("Unable to read file");
                throw new RuntimeException(e);
            }
        }
        return possibleWords;
    }

    private static Set<String> getValidGuesses() {
        if (validGuesses == null) {
            try {
                validGuesses = new HashSet<>(Files.readAllLines(new File("src/main/java/a_wordle/resource/validGuesses.txt").toPath(), Charset.defaultCharset()));
            } catch (IOException e) {
                System.out.println("Unable to read file");
                throw new RuntimeException(e);
            }
        }
        return validGuesses;
    }

    private static void printWordForUser(String guess, Set<Integer> correctIndices, Set<Integer> yellowIndices) {
        for (int i = 0; i < guess.length(); i++) {
            if (correctIndices.contains(i)) {
                System.out.print(EAnsiColor.stringInColor(guess.charAt(i), EAnsiColor.GREEN));
            } else if (yellowIndices.contains(i)) {
                System.out.print(EAnsiColor.stringInColor(guess.charAt(i), EAnsiColor.YELLOW));
            } else {
                System.out.print(guess.charAt(i));
            }
        }
        System.out.println();
    }

    private static void markYellowIndices(String wordToGuess, String guess, List<Integer> incorrectIndices, Set<Integer> indicesNotToCheck, Set<Integer> yellowIndices) {
        for (int indexOfGuess : incorrectIndices) {
            // for each letter in the word, if we should check it, then see if matches the letter in question
            // if it does, make it a yellow index
            for (int indexOfWordToGuess = 0; indexOfWordToGuess < wordToGuess.length(); indexOfWordToGuess++) {
                if (!indicesNotToCheck.contains(indexOfWordToGuess)) {
                    if (wordToGuess.charAt(indexOfWordToGuess) == guess.charAt(indexOfGuess)) {
                        indicesNotToCheck.add(indexOfWordToGuess);
                        yellowIndices.add(indexOfGuess);
                    }
                }
            }
        }
    }

    private static void populateCorrectAndIncorrectIndices(String wordToGuess, String guess, Set<Integer> correctIndices, List<Integer> incorrectIndices) {
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guess.charAt(i)) {
                correctIndices.add(i);
            } else {
                incorrectIndices.add(i);
            }
        }
    }

    @NotNull
    private static String getGuessFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you guess?");
        String playerInputtedGuess = scanner.nextLine(); // do validation to check if guess is of size 5
        String guess = preprocessGuess(playerInputtedGuess);
        boolean isGuessValid = validateGuess(guess);
        while (!isGuessValid) {
            System.out.println("Make sure you enter a 5 letter word. Also make sure it's not gibberish and a valid 5 letter word.");
            playerInputtedGuess = scanner.nextLine();
            guess = preprocessGuess(playerInputtedGuess);
            isGuessValid = validateGuess(guess);
        }
        return guess;
    }

    private static boolean playerWannaPlayAgain() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play again? (y/n)");
        String playerInput = scanner.nextLine();
        return playerInput.trim().equalsIgnoreCase("y");
    }

    public static void main(String[] args) {
        String wordToGuess = getRandomWord();
        int numGuesses = 0;

        boolean isGameOver = false;
        while (!isGameOver) {
            String guess = getGuessFromUser();
            numGuesses++;

            // do a check to see which indices were correct
            Set<Integer> correctIndices = new HashSet<>();
            List<Integer> incorrectIndices = new ArrayList<>();
            populateCorrectAndIncorrectIndices(wordToGuess, guess, correctIndices, incorrectIndices);

            // don't check indices that were already deemed correct
            Set<Integer> indicesNotToCheck = new HashSet<>(correctIndices);
            // for all the incorrect indices, see if the wordToGuess contains the letter
            Set<Integer> yellowIndices = new HashSet<>();
            markYellowIndices(wordToGuess, guess, incorrectIndices, indicesNotToCheck, yellowIndices);
            // print the word back to the user
            printWordForUser(guess, correctIndices, yellowIndices);
            // check if ending game
            if (wordToGuess.equalsIgnoreCase(guess)) {
                System.out.println("You guessed the word! It took you " + numGuesses + " guesses.");
                if (playerWannaPlayAgain()) {
                    // reset board
                    wordToGuess = getRandomWord();
                    numGuesses = 0;
                } else {
                    isGameOver = true;
                }
            }
        }
        System.out.println("Thanks for playing!");
    }

}

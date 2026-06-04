
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final int EASY_MAX = 50;
    private static final int EASY_ATTEMPTS = 10;

    private static final int MEDIUM_MAX = 100;
    private static final int MEDIUM_ATTEMPTS = 7;

    private static final int HARD_MAX = 500;
    private static final int HARD_ATTEMPTS = 5;

    private Random random;
    private Scanner scanner;
    private ScoreManager scoreManager;

    private int secretNumber;
    private int attemptsRemaining;
    private int totalAttempts;
    private int guessCount;
    private String currentDifficulty;

    public Game() {
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.scoreManager = new ScoreManager();
    }
    public void startGame(String difficulty, Player player) {
        // Initialize game based on difficulty
        setupDifficulty(difficulty);
        this.currentDifficulty = difficulty;
        this.guessCount = 0;

        // Generate secret number
        this.secretNumber = random.nextInt(getMaxRange()) + 1;

        // Display game start message
        Display.clearScreen();
        Display.showGameStart(difficulty, getMaxRange(), attemptsRemaining);

        // Main game loop - play until player wins or runs out of attempts
        boolean gameWon = false;
        while (attemptsRemaining > 0 && !gameWon) {
            gameWon = playRound();
        }

        // Handle game result
        if (gameWon) {
            handleWin(player);
        } else {
            handleLoss(player);
        }
    }

    private void setupDifficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                this.attemptsRemaining = EASY_ATTEMPTS;
                break;
            case "medium":
                this.attemptsRemaining = MEDIUM_ATTEMPTS;
                break;
            case "hard":
                this.attemptsRemaining = HARD_ATTEMPTS;
                break;
            default:
                this.attemptsRemaining = EASY_ATTEMPTS;
        }
        this.totalAttempts = this.attemptsRemaining;
    }
    private int getMaxRange() {
        switch (currentDifficulty.toLowerCase()) {
            case "easy":
                return EASY_MAX;
            case "medium":
                return MEDIUM_MAX;
            case "hard":
                return HARD_MAX;
            default:
                return EASY_MAX;
        }
    }

    private boolean playRound() {
        // Get player's guess with validation
        int playerGuess = getPlayerGuess();
        guessCount++;

        // Check if guess is correct
        int result = compareGuess(playerGuess);

        if (result == 0) {
            // Correct guess!
            Display.showCorrectGuess(guessCount);
            return true;
        } else if (result > 0) {
            // Guess is too high
            attemptsRemaining--;
            Display.showTooHigh(attemptsRemaining);
        } else {
            // Guess is too low
            attemptsRemaining--;
            Display.showTooLow(attemptsRemaining);
        }

        return false;
    }

    private int getPlayerGuess() {
        while (true) {
            try {
                System.out.print("\n🎯 Enter your guess: ");
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    Display.showInvalidInput();
                    continue;
                }

                int guess = Integer.parseInt(input);

                // Validate guess is within range
                if (guess < 1 || guess > getMaxRange()) {
                    System.out.printf("❌ Please enter a number between 1 and %d\n", getMaxRange());
                    continue;
                }

                return guess;
            } catch (NumberFormatException e) {
                Display.showInvalidInput();
            }
        }
    }

    private int compareGuess(int guess) {
        if (guess == secretNumber) {
            return 0; // Correct
        } else if (guess > secretNumber) {
            return 1; // Too high
        } else {
            return -1; // Too low
        }
    }

    private void handleWin(Player player) {
        // Calculate score based on attempts used
        int score = calculateScore(guessCount);
        player.setScore(score);
        player.setGuessesRemaining(attemptsRemaining);

        // Display win message and save score
        Display.showGameWon(guessCount, attemptsRemaining, score, getMaxRange(), secretNumber);
        scoreManager.saveScore(player);
    }

    private void handleLoss(Player player) {
        player.setScore(0);
        player.setGuessesRemaining(0);

        // Display loss message
        Display.showGameLost(secretNumber, getMaxRange());
    }
    private int calculateScore(int attemptsUsed) {
        int baseScore = (totalAttempts - attemptsUsed + 1) * 10;
        int difficultyMultiplier = getDifficultyMultiplier();
        return baseScore * difficultyMultiplier;
    }
    private int getDifficultyMultiplier() {
        switch (currentDifficulty.toLowerCase()) {
            case "easy":
                return 1;
            case "medium":
                return 2;
            case "hard":
                return 3;
            default:
                return 1;
        }
    }
    public String getGameState() {
        return String.format(
            "Secret: %d, Attempts Left: %d, Guesses Made: %d, Difficulty: %s",
            secretNumber, attemptsRemaining, guessCount, currentDifficulty
        );
    }
}

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Game game;
    private static ScoreManager scoreManager;

    public static void main(String[] args) {
        try {
            // Initialize game components
            game = new Game();
            scoreManager = new ScoreManager();

            // Display welcome banner
            Display.showWelcome();

            // Main game loop - runs until player exits
            boolean playMore = true;
            while (playMore) {
                displayMainMenu();
                int choice = getValidMenuChoice(1, 4);

                switch (choice) {
                    case 1:
                        playGame();
                        break;
                    case 2:
                        scoreManager.displayHighScores();
                        break;
                    case 3:
                        Display.showInstructions();
                        break;
                    case 4:
                        Display.showExit();
                        playMore = false;
                        break;
                    default:
                        Display.showInvalidInput();
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("❌ An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           📊 MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. ▶️  Play Game");
        System.out.println("2. 🏆 View High Scores");
        System.out.println("3. 📖 Instructions");
        System.out.println("4. ❌ Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-4): ");
    }

    /**
     * Manages the complete game play flow
     * Handles difficulty selection and game execution
     */
    private static void playGame() {
        try {
            // Get player information
            Display.clearScreen();
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine().trim();

            if (playerName.isEmpty()) {
                playerName = "Player";
            }

            // Display difficulty menu
            displayDifficultyMenu();
            int difficultyChoice = getValidMenuChoice(1, 3);

            String difficulty;
            switch (difficultyChoice) {
                case 1:
                    difficulty = "Easy";
                    break;
                case 2:
                    difficulty = "Medium";
                    break;
                case 3:
                    difficulty = "Hard";
                    break;
                default:
                    difficulty = "Easy";
            }

            // Start the game
            Player player = new Player(playerName, difficulty);
            game.startGame(difficulty, player);

            // Ask if player wants to play again
            if (askPlayAgain()) {
                playGame(); // Recursive call for another game
            }

        } catch (Exception e) {
            Display.showError("Error during game play: " + e.getMessage());
        }
    }

    /**
     * Displays the difficulty selection menu
     */
    private static void displayDifficultyMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           🎯 SELECT DIFFICULTY");
        System.out.println("=".repeat(50));
        System.out.println("1. 🟢 Easy   (1-50, 10 attempts)");
        System.out.println("2. 🟡 Medium (1-100, 7 attempts)");
        System.out.println("3. 🔴 Hard   (1-500, 5 attempts)");
        System.out.println("=".repeat(50));
        System.out.print("Choose difficulty (1-3): ");
    }

    private static boolean askPlayAgain() {
        while (true) {
            System.out.print("\n🎮 Play another game? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes") || response.equals("y")) {
                return true;
            } else if (response.equals("no") || response.equals("n")) {
                return false;
            } else {
                Display.showInvalidInput();
            }
        }
    }

    private static int getValidMenuChoice(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);

                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.printf("❌ Please enter a number between %d and %d: ", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input! Please enter a valid number: ");
            }
        }
    }
}

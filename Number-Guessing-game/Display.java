
public class Display {
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clear fails, just print newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
    public static void showWelcome() {
        clearScreen();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("     ╔═══════════════════════════════════════╗");
        System.out.println("     ║   🎮 NUMBER GUESSING GAME 🎮          ║");
        System.out.println("     ║      By Java Developer                ║");
        System.out.println("     ║   © 2024 All Rights Reserved          ║");
        System.out.println("     ╚═══════════════════════════════════════╝");
        System.out.println("=".repeat(60));
        System.out.println("\n👋 Welcome to the Number Guessing Game!");
        System.out.println("   Test your luck and strategy skills!\n");
    }
    public static void showInstructions() {
        clearScreen();
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                         📖 HOW TO PLAY 📖");
        System.out.println("=".repeat(70));
        System.out.println("""
            1️⃣  SELECT DIFFICULTY
                • Easy   : Guess a number between 1-50 (10 attempts)
                • Medium : Guess a number between 1-100 (7 attempts)
                • Hard   : Guess a number between 1-500 (5 attempts)

            2️⃣  MAKE YOUR GUESS
                • Enter a number in the valid range
                • The game will tell you if it's too high or too low
                • Keep guessing until you find the number!

            3️⃣  SCORING SYSTEM
                • Easy   : 1 point multiplier
                • Medium : 2 point multiplier
                • Hard   : 3 point multiplier
                • Score = (Attempts Left + 1) × 10 × Multiplier

            4️⃣  TRY TO GET ON THE HIGH SCORES BOARD!
                • Top 10 scores are saved permanently
                • Beat the competition and become the champion!

            💡 TIPS:
               • Start with a guess in the middle of the range
               • Use the "too high" / "too low" hints wisely
               • Hard difficulty is very challenging - give it a try!
            """);
        System.out.println("=".repeat(70));
        System.out.print("\nPress Enter to return to menu... ");
        new java.util.Scanner(System.in).nextLine();
    }

    /**
     * Displays the starting message for a new game
     * 
     * @param difficulty The selected difficulty level
     * @param maxNumber The maximum number in the range
     * @param attempts The number of attempts allowed
     */
    public static void showGameStart(String difficulty, int maxNumber, int attempts) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    🎯 GAME STARTED! 🎯");
        System.out.println("=".repeat(60));
        System.out.printf("📊 Difficulty  : %s\n", difficulty);
        System.out.printf("🔢 Range       : 1 - %d\n", maxNumber);
        System.out.printf("❤️  Attempts    : %d\n", attempts);
        System.out.println("=".repeat(60));
        System.out.println("\n💭 Think of a number... I'll try to guess it!\n");
    }

    /**
     * Displays message when guess is too high
     * 
     * @param attemptsLeft Number of attempts remaining
     */
    public static void showTooHigh(int attemptsLeft) {
        System.out.println("❌ Too High! The secret number is LOWER.");
        System.out.printf("   Attempts left: %d\n", attemptsLeft);
        if (attemptsLeft <= 2) {
            System.out.println("   ⚠️  You're running out of attempts!");
        }
    }

    /**
     * Displays message when guess is too low
     * 
     * @param attemptsLeft Number of attempts remaining
     */
    public static void showTooLow(int attemptsLeft) {
        System.out.println("❌ Too Low! The secret number is HIGHER.");
        System.out.printf("   Attempts left: %d\n", attemptsLeft);
        if (attemptsLeft <= 2) {
            System.out.println("   ⚠️  You're running out of attempts!");
        }
    }

    /**
     * Displays message when player wins
     * Shows the correct answer, attempts used, and score
     * 
     * @param attemptsUsed Number of attempts needed to win
     * @param attemptsLeft Attempts remaining
     * @param score The calculated score
     * @param maxNumber The maximum range number
     * @param secretNumber The correct number
     */
    public static void showCorrectGuess(int attemptsUsed) {
        System.out.println("\n✅ CORRECT! You found the number!");
        System.out.printf("   Guesses made: %d\n", attemptsUsed);
    }

    /**
     * Displays the game won message with final statistics
     * 
     * @param attemptsUsed Number of guesses made
     * @param attemptsLeft Attempts remaining
     * @param score Final score
     * @param maxNumber Maximum number range
     * @param secretNumber The correct number
     */
    public static void showGameWon(int attemptsUsed, int attemptsLeft, 
                                   int score, int maxNumber, int secretNumber) {
        System.out.println("\n" + "🎉".repeat(30));
        System.out.println("                    🏆 YOU WIN! 🏆");
        System.out.println("🎉".repeat(30));
        System.out.printf("\n🔢 The secret number was: %d\n", secretNumber);
        System.out.printf("📊 Total attempts: %d\n", attemptsUsed);
        System.out.printf("❤️  Attempts left: %d\n", attemptsLeft);
        System.out.printf("⭐ Score earned: %d points\n", score);
        System.out.println("\n" + "🎉".repeat(30));
    }

    /**
     * Displays the game lost message
     * Shows the correct number that player couldn't find
     * 
     * @param secretNumber The correct number
     * @param maxNumber Maximum range number
     */
    public static void showGameLost(int secretNumber, int maxNumber) {
        System.out.println("\n" + "❌".repeat(30));
        System.out.println("                    GAME OVER! 💀");
        System.out.println("❌".repeat(30));
        System.out.println("\n😢 You ran out of attempts!");
        System.out.printf("   The secret number was: %d\n", secretNumber);
        System.out.printf("   Range was: 1 - %d\n", maxNumber);
        System.out.println("\n💪 Try again with a different difficulty level!");
        System.out.println("❌".repeat(30));
    }

    /**
     * Shows message when score is saved to high scores
     * 
     * @param player The player whose score was saved
     */
    public static void showScoreSaved(Player player) {
        System.out.println("\n✅ Score saved to High Scores!");
        System.out.printf("   Player: %s\n", player.getName());
        System.out.printf("   Difficulty: %s\n", player.getDifficulty());
        System.out.printf("   Score: %d points\n", player.getScore());
    }

    /**
     * Shows exit message when player quits
     */
    public static void showExit() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              👋 Thanks for playing! 👋");
        System.out.println("        Come back soon to beat your high score!");
        System.out.println("=".repeat(60));
    }

    /**
     * Shows generic invalid input message
     */
    public static void showInvalidInput() {
        System.out.print("❌ Invalid input! Please try again: ");
    }

    /**
     * Shows error message
     * 
     * @param message The error message to display
     */
    public static void showError(String message) {
        System.out.println("\n❌ Error: " + message);
    }
}

import java.io.*;
import java.util.*;

public class ScoreManager {
    private static final String SCORES_FILE = "highscores.txt";
    private static final int TOP_SCORES_COUNT = 10;
    private List<Player> highScores;

    /**
     * Constructor - Initializes ScoreManager and loads existing scores
     */
    public ScoreManager() {
        this.highScores = new ArrayList<>();
        loadHighScores();
    }

    /**
     * Saves a player's score to the high scores list and file
     * Automatically sorts scores and keeps only top scores
     * 
     * @param player The player whose score should be saved
     */
    public void saveScore(Player player) {
        if (player.getScore() > 0) {
            highScores.add(player);
            sortScores();
            // Keep only top 10 scores
            if (highScores.size() > TOP_SCORES_COUNT) {
                highScores = new ArrayList<>(
                    highScores.subList(0, TOP_SCORES_COUNT)
                );
            }
            saveToFile();
            Display.showScoreSaved(player);
        }
    }

    /**
     * Loads high scores from the file
     * Creates file if it doesn't exist
     * Handles file not found gracefully
     */
    private void loadHighScores() {
        File file = new File(SCORES_FILE);

        // If file doesn't exist, create empty list
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Player player = parsePlayerFromLine(line);
                if (player != null) {
                    highScores.add(player);
                }
            }
            sortScores();
        } catch (IOException e) {
            System.out.println("⚠️  Could not load high scores: " + e.getMessage());
        }
    }

    /**
     * Parses a player from a file line
     * Format: name|difficulty|score|guessesRemaining
     * 
     * @param line The line to parse
     * @return Player object or null if parsing fails
     */
    private Player parsePlayerFromLine(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length == 4) {
                Player player = new Player(parts[0], parts[1]);
                player.setScore(Integer.parseInt(parts[2]));
                player.setGuessesRemaining(Integer.parseInt(parts[3]));
                return player;
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠️  Error parsing score line: " + line);
        }
        return null;
    }

    /**
     * Saves all high scores to file
     * Overwrites existing file with current scores
     */
    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORES_FILE))) {
            for (Player player : highScores) {
                writer.write(player.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving scores: " + e.getMessage());
        }
    }

    /**
     * Sorts high scores in descending order by score
     * Uses Collections.sort with custom comparator
     */
    private void sortScores() {
        Collections.sort(highScores, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                // Sort in descending order (highest score first)
                return Integer.compare(p2.getScore(), p1.getScore());
            }
        });
    }

    /**
     * Gets the top scores (up to TOP_SCORES_COUNT)
     * 
     * @return List of top players by score
     */
    public List<Player> getTopScores() {
        int limit = Math.min(TOP_SCORES_COUNT, highScores.size());
        return new ArrayList<>(highScores.subList(0, limit));
    }

    /**
     * Displays high scores in a formatted table
     * Shows top 10 scores with rank, name, difficulty, and score
     */
    public void displayHighScores() {
        Display.clearScreen();
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                      🏆 HIGH SCORES TABLE 🏆");
        System.out.println("=".repeat(70));

        if (highScores.isEmpty()) {
            System.out.println("📊 No scores yet. Play a game to get on the board!");
        } else {
            System.out.println(String.format("%-6s | %-20s | %-8s | %-10s", 
                                            "RANK", "PLAYER NAME", "DIFFICULTY", "SCORE"));
            System.out.println("-".repeat(70));

            List<Player> topScores = getTopScores();
            for (int i = 0; i < topScores.size(); i++) {
                Player player = topScores.get(i);
                System.out.printf("%-6d | %-20s | %-8s | %-10d\n",
                    i + 1,
                    truncateName(player.getName()),
                    player.getDifficulty(),
                    player.getScore()
                );
            }
        }
        System.out.println("=".repeat(70));
        System.out.print("\nPress Enter to return to menu... ");
        new Scanner(System.in).nextLine();
    }

    /**
     * Truncates player name to fit in table
     * 
     * @param name Original player name
     * @return Truncated name if necessary
     */
    private String truncateName(String name) {
        if (name.length() > 20) {
            return name.substring(0, 17) + "...";
        }
        return name;
    }

    /**
     * Checks if a score qualifies for the high scores list
     * 
     * @param score The score to check
     * @return true if score is in top 10 or list has less than 10 entries
     */
    public boolean isHighScore(int score) {
        if (highScores.size() < TOP_SCORES_COUNT) {
            return true;
        }
        Player lastPlayer = highScores.get(highScores.size() - 1);
        return score > lastPlayer.getScore();
    }

    /**
     * Gets the rank of a score if it were added
     * Useful for showing where a new score ranks
     * 
     * @param score The score to rank
     * @return The rank position (1-based) or -1 if not in top scores
     */
    public int getRankForScore(int score) {
        for (int i = 0; i < highScores.size(); i++) {
            if (highScores.get(i).getScore() < score) {
                return i + 1;
            }
        }
        if (highScores.size() < TOP_SCORES_COUNT) {
            return highScores.size() + 1;
        }
        return -1;
    }
}
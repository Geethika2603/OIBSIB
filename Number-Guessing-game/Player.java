
public class Player {
    private String name;
    private String difficulty;
    private int score;
    private int guessesRemaining;

    public Player(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
        this.score = 0;
        this.guessesRemaining = 0;
    }

    /**
     * Gets the player's name
     * 
     * @return Player name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the player's name
     * 
     * @param name New player name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the difficulty level
     * 
     * @return Difficulty level (Easy, Medium, Hard)
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Sets the difficulty level
     * 
     * @param difficulty New difficulty level
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Gets the player's score
     * 
     * @return Current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score
     * 
     * @param score New score value
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the remaining guesses after game completion
     * 
     * @return Number of guesses remaining
     */
    public int getGuessesRemaining() {
        return guessesRemaining;
    }

    /**
     * Sets the remaining guesses
     * 
     * @param guessesRemaining New value for remaining guesses
     */
    public void setGuessesRemaining(int guessesRemaining) {
        this.guessesRemaining = guessesRemaining;
    }

    /**
     * Returns string representation of player data
     * Format: name|difficulty|score|guessesRemaining
     * Used for saving/loading from file
     * 
     * @return Pipe-delimited string of player data
     */
    @Override
    public String toString() {
        return name + "|" + difficulty + "|" + score + "|" + guessesRemaining;
    }

    /**
     * Returns formatted string for display purposes
     * Shows player information in readable format
     * 
     * @return Formatted player information
     */
    public String toDisplayString() {
        return String.format(
            "%-20s | %-8s | Score: %6d | Guesses Left: %d",
            name, difficulty, score, guessesRemaining
        );
    }
}
public class GameMaster {

    private int score = 0;
    private boolean gameOver = false;

    public void addScore(int value) {
        score += value;
    }

    public int getScore() {
        return score;
    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}

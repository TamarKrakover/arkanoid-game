package gameSprites;

import biuoop.DrawSurface;
import game.Game;
import game.Sprite;

/**
 * A ScoreIndicator is a Sprite that displays the current game score on the screen.
 */
public class ScoreIndicator implements Sprite {
    private Game game;
    private String textScore;

    /**
     * Creates a ScoreIndicator linked to the specified game.
     *
     * @param game the game instance from which to get the current score
     */
    public ScoreIndicator(Game game) {
        this.game = game;
        this.textScore = "Score: 0";
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.drawText(350, 17, textScore, 15);
    }

    @Override
    public void timePassed() {
        this.textScore = "Score: " + game.getScore();
    }

    @Override
    public void addToGame(Game g) {

    }
}

package game;
import biuoop.DrawSurface;

/**
 * The Sprite interface represents any object that can be drawn on the screen
 * and whose state can change over time in the game. This includes objects
 * like balls, paddles, and blocks.
 */
public interface Sprite {
    /**
     * Draws the sprite on the given draw surface.
     *
     * @param d the draw surface to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that time has passed, so it can update its state.
     */
    void timePassed();

    /**
     * Adds the sprite to the game, so it will be managed and rendered by the game.
     *
     * @param g the game to add the sprite to
     */
    void addToGame(Game g);
}

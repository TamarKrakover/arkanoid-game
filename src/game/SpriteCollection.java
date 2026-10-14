package game;

import biuoop.DrawSurface;

import java.util.ArrayList;
import java.util.List;

/**
 * The SpriteCollection class manages a collection of sprite objects,
 * allowing the game to update and draw all sprites in the collection.
 */
public class SpriteCollection {
    private List<Sprite> sprites = new ArrayList<>();

    /**
     * Adds a sprite to the collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Notifies all sprites in the collection that time has passed.
     * This calls the `timePassed()` method on each sprite.
     */
    public void notifyAllTimePassed() {
        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite s : copy) {
            s.timePassed();
        }
    }

    /**
     * Draws all sprites in the collection on the provided DrawSurface.
     * This calls the `drawOn(d)` method on each sprite to draw it.
     *
     * @param d the DrawSurface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        List<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite s : copy) {
            s.drawOn(d);
        }
    }

    /**
     * Removes the specified Sprite from the collection of sprites.
     * After removal, the sprite will no longer be updated or drawn in the game.
     *
     * @param s the Sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}

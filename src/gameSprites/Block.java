package gameSprites;

import biuoop.DrawSurface;
import collision.Collidable;
import game.Game;
import game.HitListener;
import game.HitNotifier;
import game.Sprite;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Block} class represents a rectangular object that can be both drawn on the screen
 * and participate in collision detection. It implements both the {@link Collidable} and {@link Sprite}
 * interfaces, making it a game object with behavior and graphical representation.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle block;
    private Color color = Color.white;
    private boolean isDynamic = false;
    private List<HitListener> hitListeners = new ArrayList<>();


    /**
     * Constructs a block with a given {@link Rectangle} as its collision shape.
     *
     * @param block the rectangle representing the block's shape and position
     */
    public Block(Rectangle block) {
        this.block = block;
    }

    /**
     * Returns whether the block is dynamic (i.e., can move).
     *
     * @return {@code true} if the block is dynamic; {@code false} otherwise
     */
    @Override
    public boolean getIsDynamic() {
        return this.isDynamic;
    }

    /**
     * Sets the color of the block.
     *
     * @param color the new color to be used for the block
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Draws the block onto the given {@link DrawSurface}. Fills the block with its color
     * and draws a black border around it.
     *
     * @param surface the drawing surface
     */
    @Override
    public void drawOn(DrawSurface surface) {
        int x = (int) this.getCollisionRectangle().getUpperLeft().getX();
        int y = (int) this.getCollisionRectangle().getUpperLeft().getY();
        int width = (int) this.getCollisionRectangle().getWidth();
        int height = (int) this.getCollisionRectangle().getHeight();
        surface.setColor(this.color);
        surface.fillRectangle(x, y, width, height);
        surface.setColor(Color.black);
        surface.drawRectangle(x, y, width, height);
    }

    /**
     * Called every frame to notify that time has passed.
     * Currently empty since a basic block has no time-based behavior.
     */
    @Override
    public void timePassed() {

    }

    /**
     * Adds the block to the specified game as both a sprite and a collidable.
     *
     * @param g the game to add this block to
     */
    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Returns the collision rectangle of the block.
     *
     * @return the rectangle defining the block's shape
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.block;
    }

    /**
     * Handles the collision with this block. Determines which side of the block was hit
     * and returns a new {@link Velocity} that reflects the change caused by the collision.
     *
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the velocity before the collision
     * @return the new velocity after the collision
     */
    @Override
    // Notice that we changed the hit method to include a "Ball hitter" parameter -- update the
    // Collidable interface accordingly.
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        boolean hitY = false;
        boolean hitX = false;
        for (Line l : this.block.limits()) {
            if (l.checkOnLine(collisionPoint)
                    && l.isXBetween(collisionPoint.getX()) && l.isYBetween(collisionPoint.getY())) {
                if (l.isHorizontal() && !hitY) {
                    dy = -dy;
                    hitY = true;
                } else if (l.isVertical() && !hitX) {
                    dx = -dx;
                    hitX = true;
                }
            }
        }
        Velocity newVelocity = new Velocity(dx, dy);
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return newVelocity;
    }

    /**
     * Checks if the color of the given ball matches the color of this ball.
     *
     * @param ball the Ball to compare color with
     * @return true if both balls have the same color, false otherwise
     */
    public boolean ballColorMatch(Ball ball) {
        return (ball.getColor().equals(this.color));
    }

    /**
     * Removes this ball from the specified game.
     * It removes the ball both from the list of collidables and sprites,
     * so it will no longer participate in collisions or be drawn/updated.
     *
     * @param game the game instance from which to remove the ball
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    /**
     * Returns the current color of the ball.
     *
     * @return the Color of this ball
     */
    public Color getColor() {
        return this.color;
    }
}

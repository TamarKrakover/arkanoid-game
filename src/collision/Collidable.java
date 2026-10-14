package collision;

import gameSprites.Ball;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * The {@code Collidable} interface represents objects that can be collided with,
 * such as blocks, paddles, or walls.
 * Any object implementing this interface must provide information about its collision shape,
 * and define how it responds to a collision.
 */
public interface Collidable {
    /**
     * Returns the collision shape of the object as a {@link Rectangle}.
     *
     * @return the collision {@link Rectangle} of the object
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that it has been hit by a ball at a specific collision point,
     * and computes the resulting velocity after the collision.
     *
     * <p>This method is typically used by collidable objects (like blocks or paddles)
     * to determine how the ball's velocity should change after a collision occurs.</p>
     *
     * @param hitter          the Ball that hit the object
     * @param collisionPoint  the Point at which the collision occurred
     * @param currentVelocity the current velocity of the ball before the collision
     * @return the new Velocity of the ball after the collision, based on the object's response
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Indicates whether the object is dynamic (can move) or static.
     *
     * @return {@code true} if the object is dynamic; {@code false} otherwise
     */
    boolean getIsDynamic();
}

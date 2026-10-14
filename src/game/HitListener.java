package game;

import gameSprites.Ball;
import gameSprites.Block;

/**
 * The HitListener interface should be implemented by any class that wants to be notified
 * when a Block is hit by a Ball.
 *
 * <p>Listeners are used to trigger actions such as removing blocks, updating scores,
 * or responding visually to collisions.</p>
 */
public interface HitListener {
    /**
     * This method is called whenever the specified block is hit by a ball.
     *
     * @param beingHit the Block that was hit
     * @param hitter   the Ball that hit the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}

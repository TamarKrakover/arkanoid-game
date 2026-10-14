package game;

import gameSprites.Ball;
import gameSprites.Block;

/**
 * The BallRemover class is a HitListener that removes balls from the game
 * when they hit a designated block (typically the "death region").
 *
 * <p>It is also responsible for updating the counter of remaining balls
 * in the game.</p>
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructs a BallRemover.
     *
     * @param game           the game from which balls will be removed
     * @param remainingBalls a counter tracking the number of balls currently in the game
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        remainingBalls.decrease(1);
    }
}

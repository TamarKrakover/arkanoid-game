package game;

import gameSprites.Ball;
import gameSprites.Block;
/**
 * The ScoreTrackingListener class is a HitListener that updates the game score
 * when a block is hit.
 *
 * <p>Each time a block is hit, this listener increases the score by 5 points.</p>
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;


    /**
     * Constructs a ScoreTrackingListener with a reference to the game's score counter.
     *
     * @param scoreCounter the counter used to track the current score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }
}

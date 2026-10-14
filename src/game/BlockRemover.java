package game;

import gameSprites.Ball;
import gameSprites.Block;

/**
 * The BlockRemover class is a HitListener that removes blocks from the game
 * when they are hit, and updates a counter that tracks the remaining number of blocks.
 *
 * <p>This listener is typically attached to blocks that should disappear after being hit.</p>
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructs a BlockRemover.
     *
     * @param game           the game from which blocks will be removed
     * @param remainingBlocks a counter tracking the number of blocks remaining in the game
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    // Blocks that are hit should be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(this.game);
        beingHit.removeHitListener(this);
        this.remainingBlocks.decrease(1);
        hitter.setColor(beingHit.getColor());
    }
}

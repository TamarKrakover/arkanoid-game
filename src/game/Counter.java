package game;
/**
 * The Counter class is used for tracking a numerical value that can be increased or decreased.
 *
 * <p>It is commonly used in games to keep track of things like score, remaining lives, blocks, or balls.</p>
 */
public class Counter {
    private int counter = 0;

    /**
     * Increases the current count by the specified number.
     *
     * @param number the amount to add to the counter
     */
    // add number to current count.
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * Decreases the current count by the specified number.
     *
     * @param number the amount to subtract from the counter
     */
    // subtract number from current count.
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * Returns the current value of the counter.
     *
     * @return the current count
     */
    // get current count.
    public int getValue() {
        return this.counter;
    }
}

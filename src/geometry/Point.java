package geometry;
/**
 * The {@code Point} class represents a point in a 2D plane with x and y coordinates.
 * It provides methods to calculate distance, compare equality, and retrieve coordinates.
 */
public class Point {
    private double x;
    private double y;

    /**
     * Constructs a point with the specified x and y coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /** The threshold for comparing double values for equality. */
     static final double COMPARISON_THRESHOLD = 0.00001;
    /**
     * Compares two double values for equality within a small threshold.
     *
     * @param a the first double value
     * @param b the second double value
     * @return {@code true} if the values are approximately equal, {@code false} otherwise
     */
    public static boolean doubleEquals(double a, double b) {
        return  Math.abs(a - b) < Point.COMPARISON_THRESHOLD;
    }

    /**
     * Returns the distance from this point to another point.
     *
     * @param other the other point
     * @return the Euclidean distance between this point and the other point
     */
    public double distance(Point other) {
        return Math.sqrt(((x - other.x) * (x - other.x)) + ((y - other.y) * (y - other.y)));
    }

    /**
     * Checks if this point is equal to another point.
     *
     * @param other the other point to compare
     * @return {@code true} if the points have approximately equal coordinates, {@code false} otherwise
     */
    public boolean equals(Point other) {
        return (doubleEquals(x, other.x) && doubleEquals(y, other.y));
    }

    /**
     * Returns the x-coordinate of this point.
     *
     * @return the x-coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this point.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return y;
    }
}

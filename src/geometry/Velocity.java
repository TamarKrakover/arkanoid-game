package geometry;
/**
 * The {@code Velocity} class represents a velocity vector with horizontal (dx) and vertical (dy) components.
 * It is used to define how a point moves in 2D space.
 */
// Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructs a new {@code Velocity} with the specified dx and dy components.
     *
     * @param dx the change in x-axis (horizontal direction)
     * @param dy the change in y-axis (vertical direction)
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the horizontal velocity component.
     *
     * @return the dx value
     */
    public double getDx() {
        return dx;
    }

    /**
     * Returns the vertical velocity component.
     *
     * @return the dy value
     */
    public double getDy() {
        return dy;
    }

    /**
     * Sets the horizontal velocity component.
     *
     * @param dx the new dx value
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * Sets the vertical velocity component.
     *
     * @param dy the new dy value
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * Creates a new {@code Velocity} instance from an angle (in radians) and a speed.
     *
     * @param angle the angle of motion in radians
     * @param speed the magnitude of the velocity
     * @return a new {@code Velocity} object
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double radians = Math.toRadians(angle);
        double dx = speed * Math.sin(radians);
        // minus because Y increases downward on screen
        double dy = -speed * Math.cos(radians);
        return new Velocity(dx, dy);
    }

    /**
     * Applies the velocity to a given point, returning a new point that results from moving the original point
     * by (dx, dy).
     *
     * @param p the original point
     * @return a new {@code Point} after applying the velocity
     */
    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)
    public Point applyToPoint(Point p) {
        double newX = p.getX() + this.dx;
        double newY = p.getY() + this.dy;
        Point newPoint = new Point(newX, newY);
        return newPoint;
    }
}

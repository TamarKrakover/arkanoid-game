package geometry;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Rectangle} class represents a rectangle defined by its upper-left point,
 * width, and height. It provides methods to retrieve its edges, check for point inclusion,
 * and compute intersections with lines.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private Line top;
    private Line bottom;
    private Line right;
    private Line left;

    /**
     * Constructs a new rectangle with the specified upper-left point, width, and height.
     *
     * @param upperLeft the upper-left corner of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        Point bottomRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
        this.top = new Line(upperLeft, new Point(bottomRight.getX(), upperLeft.getY()));
        this.bottom = new Line(new Point(upperLeft.getX(), bottomRight.getY()), bottomRight);
        this.right = new Line(new Point(bottomRight.getX(), upperLeft.getY()), bottomRight);
        this.left = new Line(upperLeft, new Point(upperLeft.getX(), bottomRight.getY()));
    }

    /**
     * Returns a list of intersection points between this rectangle and the specified line.
     *
     * @param line the line to check for intersections
     * @return a list of intersection points (possibly empty)
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersections = new ArrayList<>();
        Line[] limits = new Line[]{this.top, this.bottom, this.right, this.left};
        Point intersection;
        for (Line limit : limits) {
            if (limit.isIntersecting(line)) {
                intersection = limit.intersectionWith(line);
                if (intersection != null) {
                    intersections.add(intersection);
                }
            }
        }
        return intersections;
    }

    // Return the width and height of the rectangle

    /**
     * Returns the width of the rectangle.
     *
     * @return the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height
     */
    public double getHeight() {
        return this.height;
    }

    // Returns the upper-left point of the rectangle.

    /**
     * Returns the upper-left point of the rectangle.
     *
     * @return the upper-left point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Returns the top edge of the rectangle.
     *
     * @return the top line
     */
    public Line getTop() {
        return this.top;
    }

    /**
     * Returns the bottom edge of the rectangle.
     *
     * @return the bottom line
     */
    public Line getBottom() {
        return this.bottom;
    }

    /**
     * Returns the right edge of the rectangle.
     *
     * @return the right line
     */
    public Line getRight() {
        return this.right;
    }

    /**
     * Returns the left edge of the rectangle.
     *
     * @return the left line
     */
    public Line getLeft() {
        return this.left;
    }

    /**
     * Returns an array of the rectangle's four boundary lines: top, bottom, right, and left.
     *
     * @return an array of the rectangle's edges
     */
    public Line[] limits() {
        Line[] limits = new Line[]{this.top, this.bottom, this.right, this.left};
        return limits;
    }

    /**
     * Updates the rectangle's upper-left corner and recalculates its edges accordingly.
     *
     * @param newLocation the new upper-left point
     */
    public void setUpperLeft(Point newLocation) {
        this.upperLeft = newLocation;
        setLimits(newLocation);
    }

    /**
     * Updates the boundary lines based on a new upper-left point.
     *
     * @param newLocation the new upper-left point
     */
    public void setLimits(Point newLocation) {
        Point bottomRight = new Point(newLocation.getX() + width, newLocation.getY() + height);
        this.top = new Line(newLocation, new Point(bottomRight.getX(), newLocation.getY()));
        this.bottom = new Line(new Point(newLocation.getX(), bottomRight.getY()), bottomRight);
        this.right = new Line(new Point(bottomRight.getX(), newLocation.getY()), bottomRight);
        this.left = new Line(newLocation, new Point(newLocation.getX(), bottomRight.getY()));
    }

    /**
     * Determines whether a point lies strictly within the rectangle (excluding the edges).
     *
     * @param p the point to check
     * @return {@code true} if the point is strictly inside the rectangle; {@code false} otherwise
     */
    public boolean isInside(Point p) {
        double x = p.getX();
        double y = p.getY();
        return (x > left.start().getX() && x < right.start().getX()
                && y > top.start().getY() && y < bottom.start().getY());
    }
}

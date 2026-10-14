package geometry;
import java.util.List;

/**
 * The {@code Line} class represents a line segment in a 2D plane defined by two points.
 * It provides methods to calculate length, middle point, intersection detection,
 * and equality checks.
 */
public class Line {
    public static final double COMPARISON_THRESHOLD = 0.00001;
    private Point start;
    private Point end;

    /**
     * Compares two double values for equality within a small threshold.
     *
     * @param a the first double value
     * @param b the second double value
     * @return {@code true} if the values are approximately equal, {@code false} otherwise
     */
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < Line.COMPARISON_THRESHOLD;
    }

    /**
     * Constructs a line with specified start and end points.
     *
     * @param start the starting point of the line
     * @param end   the ending point of the line
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a line using coordinates for the start and end points.
     *
     * @param x1 the x-coordinate of the starting point
     * @param y1 the y-coordinate of the starting point
     * @param x2 the x-coordinate of the ending point
     * @param y2 the y-coordinate of the ending point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Sets the start and end points of this line.
     *
     * @param p1 the new start point of the line
     * @param p2 the new end point of the line
     */
    public void setLine(Point p1, Point p2) {
        this.start = p1;
        this.end = p2;
    }

    /**
     * Returns the length of the line segment.
     *
     * @return the Euclidean distance between the start and end points
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the midpoint of the line segment.
     *
     * @return a {@code Point} representing the middle of the line
     */
    public Point middle() {
        double xMiddle = (start.getX() + end.getX()) / 2;
        double yMiddle = (start.getY() + end.getY()) / 2;
        return new Point(xMiddle, yMiddle);
    }

    /**
     * Returns the starting point of the line.
     *
     * @return the start point
     */
    public Point start() {
        return start;
    }

    /**
     * Returns the ending point of the line.
     *
     * @return the end point
     */
    public Point end() {
        return end;
    }

    /**
     * Checks if a given x-coordinate lies within the x-range of the line segment.
     *
     * @param x the x-coordinate to check
     * @return {@code true} if x is within the range, {@code false} otherwise
     */
    public boolean isXBetween(double x) {
        // Check if the x is between the min and the max of the line or equal to one of them
        return ((!(Math.min(start.getX(), end.getX()) > x))
                || (doubleEquals(x, Math.min(start.getX(), end.getX()))))
                && ((!(Math.max(start.getX(), end.getX()) < x))
                || (doubleEquals(x, Math.max(start.getX(), end.getX()))));
    }

    /**
     * Checks if a given y-coordinate lies within the y-range of the line segment.
     *
     * @param y the y-coordinate to check
     * @return {@code true} if y is within the range, {@code false} otherwise
     */
    public Boolean isYBetween(double y) {
        return ((!(Math.min(start.getY(), end.getY()) > y))
                || (doubleEquals(y, Math.min(start.getY(), end.getY()))))
                && ((!(Math.max(start.getY(), end.getY()) < y))
                || (doubleEquals(y, Math.max(start.getY(), end.getY()))));
    }

    /**
     * Checks if two lines are both vertical.
     *
     * @param other the other line to compare
     * @return {@code true} if both lines are vertical, {@code false} otherwise
     */
    public Boolean isVerticals(Line other) {
        // Check if the two lines are verticals
        return doubleEquals(start.getX(), end.getX()) && doubleEquals(other.start.getX(), other.end.getX());
    }

    /**
     * Finds the y-coordinate of a given x-value based on the line's equation.
     *
     * @param x the x-coordinate
     * @return the corresponding y-coordinate
     */
    public double findY(double x) {
        double m = (end.getY() - start.getY()) / (end.getX() - start.getX());
        double b = start.getY() - m * start.getX();
        return m * x + b;
    }

    /**
     * Determines whether this line intersects with another line.
     *
     * @param other the other line to check for intersection
     * @return {@code true} if the lines intersect, {@code false} otherwise
     */
    public boolean isIntersecting(Line other) {
        // Check if the two lines are verticals
        if (this.isVerticals(other)) {
            // If the x is not the same
            if (!doubleEquals(start.getX(), other.start.getX())) {
                return false;
                // If the x is the same
                // Check if the lines have at least one common point, check the y
            } else {
                return ((!(Math.min(start.getY(), end.getY()) > Math.max(other.start.getY(), other.end.getY())))
                        || doubleEquals(Math.min(start.getY(), end.getY()),
                        Math.max(other.start.getY(), other.end.getY())))
                        && ((!(Math.min(other.start.getY(), other.end.getY()) > Math.max(start.getY(), end.getY())))
                        || doubleEquals(Math.min(other.start.getY(), other.end.getY()),
                        Math.max(start.getY(), end.getY())));
            }
            // Check if this line is vertical
        } else if (doubleEquals(start.getX(), end.getX())) {
            // Check if the x of the line is in the other line
            if (!other.isXBetween(this.start.getX())) {
                return false;
            }
            // Check if the line is above or under the line
            return this.isYBetween(other.findY(this.start.getX()));
            // Check if the other line is vertical
        } else if (doubleEquals(other.start.getX(), other.end.getX())) {
            if (!isXBetween(other.start.getX())) {
                return false;
            }
            return other.isYBetween(this.findY(other.start.getX()));
            // If the lines aren't vertical
        } else {
            // Calculate the slopes
            double m1 = (end.getY() - start.getY()) / (end.getX() - start.getX());
            double m2 = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
            // Calculate the const
            double b1 = start.getY() - m1 * start.getX();
            double b2 = other.start.getY() - m2 * other.start.getX();

            if (this.start().equals(this.end())) {
                return other.checkOnLine(this.start());
            } else if (other.start().equals(other.end())) {
                return this.checkOnLine(other.start());
            }

            // Check if the two lines are parallel
            if (doubleEquals(m1, m2)) {
                // Check if the lines aren't connected
                if (!(doubleEquals(b1, b2))) {
                    return false;
                    // If the lines are parallel on the same line
                } else {
                    return ((!(Math.min(start.getX(), end.getX()) > Math.max(other.start.getX(), other.end.getX())))
                            || doubleEquals(Math.min(start.getX(), end.getX()),
                            Math.max(other.start.getX(), other.end.getX())))
                            && ((!(Math.min(other.start.getX(), other.end.getX()) > Math.max(start.getX(), end.getX())))
                            || doubleEquals(Math.max(start.getX(), end.getX()),
                            Math.min(other.start.getX(), other.end.getX())));
                }
                // If the slopes are not equals
            } else {
                double x = (b2 - b1) / (m1 - m2);
                // Check if the x is in the range of the lines
                return other.isXBetween(x) && this.isXBetween(x);
            }
        }
    }

    /**
     * Checks if a given point lies on this line.
     *
     * @param other the point to check
     * @return {@code true} if the point is on the line, {@code false} otherwise
     */
    public boolean checkOnLine(Point other) {
        if (doubleEquals(start.getX(), end.getX())) {
            return doubleEquals(other.getX(), start.getX());
        }
        return doubleEquals(this.findY(other.getX()), other.getY());
    }

    /**
     * Determines whether this line intersects with two other lines.
     *
     * @param other1 the first line
     * @param other2 the second line
     * @return {@code true} if this line intersects both, {@code false} otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }

    /**
     * Finds the intersection point of this line with another line.
     *
     * @param other the other line
     * @return the intersection point if the lines intersect, or {@code null} if they do not
     */
    public Point intersectionWith(Line other) {
        // If the lines aren't meets at all
        if (!this.isIntersecting(other)) {
            return null;
        }
        // Two vertical lines
        if (this.isVerticals(other)) {
            // More than one common point
            if (doubleEquals(Math.min(start.getY(), end.getY()), Math.max(other.start.getY(), other.end.getY()))) {
                return this.start();
            }
            if (doubleEquals(Math.min(other.start.getY(), other.end.getY()), Math.max(start.getY(), end.getY()))) {
                return this.end();
            }
            if (Math.min(this.start.getY(), this.end.getY()) < Math.max(other.start.getY(), other.end.getY())
                    || Math.max(this.start.getY(), this.end.getY()) > Math.min(other.start.getY(), other.end.getY())) {
                return null;
            }
            // One common point
            if (doubleEquals(this.start.getX(), other.start.getX())) {
                return this.start;
            }
            if (doubleEquals(this.end.getX(), other.end.getX())) {
                return this.end;
            }
            if (doubleEquals(this.start.getX(), other.end.getX())) {
                return this.end;
            }
            if (doubleEquals(this.end.getX(), other.start.getX())) {
                return this.end;
            }
            // One vertical line
            // this one is vertical
        } else if (doubleEquals(this.start.getX(), this.end.getX())) {
            // If there isn't common X
            // Take the x from the vertical one
            double x = this.start.getX();
            // Take the y from the other one
            double m = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
            double b = other.end.getY() - m * other.end.getX();
            double y = m * this.start.getX() + b;
            Point p = new Point(x, y);
            return p;
            // The other is vertical
        } else if (doubleEquals(other.start.getX(), other.end.getX())) {
            // If there isn't common X
            if (!this.isXBetween(other.start.getX())) {
                return null;
            }
            // Take the x from the vertical one
            double x = other.start.getX();
            // Take the y from the other one
            double m = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
            double b = this.end.getY() - m * this.end.getX();
            double y = m * other.start.getX() + b;
            Point p = new Point(x, y);
            return p;
        }
        // Two not vertical lines
        double m1 = (end.getY() - start.getY()) / (end.getX() - start.getX());
        double m2 = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
        // Calculate the const
        double b1 = start.getY() - m1 * start.getX();
        double b2 = other.start.getY() - m2 * other.start.getX();

        if (doubleEquals(m1, m2) && doubleEquals(b1, b2)) {
            if (((Math.min(start.getY(), end.getY()) < Math.max(other.start.getY(), other.end.getY()))
                    && !doubleEquals(Math.min(start.getY(), end.getY()),
                    Math.max(other.start.getY(), other.end.getY())))
                    || ((Math.min(other.start.getY(), other.end.getY()) < Math.max(start.getY(), end.getY()))
                    && !doubleEquals(Math.min(other.start.getY(), other.end.getY()),
                    Math.max(start.getY(), end.getY())))) {
                return null;
            }
            if (doubleEquals(Math.min(start.getY(), end.getY()), Math.max(other.start.getY(), other.end.getY()))) {
                return this.start();
            }
            if (doubleEquals(Math.min(other.start.getY(), other.end.getY()), Math.max(start.getY(), end.getY()))) {
                return this.end();
            }
        }
        // Find the x with both lines
        double x = (b2 - b1) / (m1 - m2);
        // Find the y using one line
        double y = m1 * x + b1;
        // Return the point
        return new Point(x, y);
    }

    /**
     * Checks if this line is equal to another line.
     * Two lines are considered equal if they have the same start and end points (order-independent).
     *
     * @param other the other line to compare
     * @return {@code true} if the lines are equal, {@code false} otherwise
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * Returns the corner point shared by this line and another line, if one exists.
     * A corner is defined as a point where the two lines share exactly one endpoint.
     *
     * @param other the other line to compare with
     * @return the shared corner point if one exists; {@code null} otherwise
     */
    public Point getCorner(Line other) {
        Point corner = null;
        if (this.start().equals(other.start())) {
            corner = this.start();
        } else if (this.start().equals(other.end())) {
            corner = this.start();
        } else if (this.end().equals(other.start())) {
            corner = this.end();
        } else if (this.end().equals(other.end())) {
            corner = this.end();
        }
        return corner;
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.
    /**
     * Finds the closest intersection point between the line and the given rectangle.
     * It iterates through all intersection points between the line and the rectangle,
     * and returns the point closest to the starting point of the line.
     *
     * @param rect the rectangle to check for intersections with the line
     * @return the closest intersection point to the start of the line, or null if no intersection exists
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections.isEmpty()) {
            return null;
        }
        Point close = intersections.get(0);
        for (Point p : intersections) {
            if (p.distance(this.start) < close.distance(this.start)) {
                close = p;
            }
        }
        return close;
    }
    /**
     * Checks if the line is vertical. A line is vertical if the x-coordinate of its start point
     * is equal to the x-coordinate of its end point.
     *
     * @return true if the line is vertical, false otherwise
     */
    public boolean isVertical() {
        if (doubleEquals(this.start.getX(), this.end().getX())) {
            return true;
        }
        return false;
    }
    /**
     * Checks if the line is horizontal. A line is horizontal if the y-coordinate of its start point
     * is equal to the y-coordinate of its end point.
     *
     * @return true if the line is horizontal, false otherwise
     */
    public boolean isHorizontal() {
        if (doubleEquals(this.start.getY(), this.end().getY())) {
            return true;
        }
        return false;
    }
}
package gameSprites;

import biuoop.DrawSurface;
import collision.Collidable;
import collision.CollisionInfo;
import collision.GameEnvironment;
import game.Game;
import game.Sprite;
import geometry.Line;
import geometry.Point;
import geometry.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code Ball} class represents a ball with a position, radius, color, and velocity.
 * It can move within defined rectangular boundaries and detect collisions with them,
 * including collisions with rectangle corners using vector-based reflection.
 * This class is suitable for simple 2D physics simulations where balls bounce
 * within a confined space and around obstacles.
 */
public class Ball implements Sprite {
    private int radius;
    private Color color;
    private Point location;
    private Velocity velocity;
    private ArrayList<Line[]> limits;
    private GameEnvironment gameEnvironment = new GameEnvironment();
    /**
     * The threshold for comparing double values for equality.
     */
    static final double COMPARISON_THRESHOLD = 0.00001;

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
     * Constructs a {@code Ball} with a given center point, radius, and color.
     * The initial velocity is set to (1, 1).
     *
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, Color color) {
        this.radius = r;
        this.color = color;
        this.location = center;
        this.velocity = new Velocity(1, 1);
    }

    /**
     * Constructs a {@code Ball} with a center at the given x and y coordinates, a radius, and a color.
     * The initial velocity is set to (1, 1).
     *
     * @param x     the x-coordinate of the ball's center
     * @param y     the y-coordinate of the ball's center
     * @param r     the radius of the ball
     * @param color the color of the ball
     */
    public Ball(int x, int y, int r, Color color) {
        this.radius = r;
        this.color = color;
        this.location = new Point(x, y);
        this.velocity = new Velocity(1, 1);
    }

    // accessors

    /**
     * Returns the x-coordinate of the ball's center.
     *
     * @return the x-coordinate as an integer
     */
    public int getX() {
        return (int) this.location.getX();
    }

    /**
     * Returns the y-coordinate of the ball's center.
     *
     * @return the y-coordinate as an integer
     */
    public int getY() {
        return (int) this.location.getY();
    }

    /**
     * Returns the radius of the ball.
     *
     * @return the radius
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Returns the color of the ball.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Returns the current velocity of the ball.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Gets the current game environment associated with the ball.
     * The game environment contains all the collidables and sprites in the game.
     *
     * @return the current GameEnvironment of the ball
     */
    public GameEnvironment getGameEnvironment() {
        return this.gameEnvironment;
    }

    /**
     * Sets the ball's velocity.
     *
     * @param v the new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the ball's velocity using dx and dy components.
     *
     * @param dx the change in x-direction
     * @param dy the change in y-direction
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Sets the ball's center location.
     *
     * @param center the new center point
     */
    public void setLocation(Point center) {
        this.location = center;
    }

    /**
     * Adds a rectangular boundary to the list of collision limits for the ball.
     * The rectangle is defined by its top-left and bottom-right corners.
     *
     * @param topLeft   the top-left point of the rectangle
     * @param downRight the bottom-right point of the rectangle
     */
    public void setLimits(Point topLeft, Point downRight) {
        Line[] newRectangle = new Line[]{
                new Line(topLeft.getX(), topLeft.getY(), topLeft.getX(), downRight.getY()),
                new Line(downRight.getX(), topLeft.getY(), downRight.getX(), downRight.getY()),
                new Line(topLeft.getX(), topLeft.getY(), downRight.getX(), topLeft.getY()),
                new Line(topLeft.getX(), downRight.getY(), downRight.getX(), downRight.getY())
        };
        if (this.limits == null) {
            this.limits = new ArrayList<>();
        }
        this.limits.add(newRectangle);
    }

    /**
     * Sets the game environment for this object.
     * The {@link GameEnvironment} contains all the collidable objects in the game
     * and is used for collision detection and response.
     *
     * @param environment the {@link GameEnvironment} to associate with this object
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }

    /**
     * Draws the ball on the given {@code DrawSurface}.
     *
     * @param surface the surface on which to draw the ball
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.location.getX(), (int) this.location.getY(), this.radius);
        surface.setColor(Color.black);
        surface.drawCircle((int) this.location.getX(), (int) this.location.getY(), this.radius);
    }

    /**
     * Notifies the ball that time has passed.
     * This causes the ball to move one step according to its current velocity
     * and handle possible collisions.
     */
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    /**
     * Adds the ball to the given game.
     * Registers the ball as a sprite so that it will be drawn and updated.
     *
     * @param g the game to add this ball to
     */
//    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
    }


    /**
     * Moves the ball one step, checking for collisions along the path.
     * The movement is divided into sub-steps for higher precision.
     * Handles standard collisions and special cases where the ball gets inside dynamic objects (e.g., paddle).
     */
    public void moveOneStep() {
        double dx = velocity.getDx();
        double dy = velocity.getDy();
        double speed = Math.sqrt(dx * dx + dy * dy);
        // Small backstep to avoid being exactly on the collision point (helps prevent getting "stuck").
        double moveBackX = velocity.getDx() / speed * 0.1;
        double moveBackY = velocity.getDy() / speed * 0.1;
        // Divide velocity into smaller steps for accurate collision checking.
        int subSteps = 50;
        double subDx = this.velocity.getDx() / subSteps;
        double subDy = this.velocity.getDy() / subSteps;
        for (int i = 0; i < subSteps; i++) {
            Point endTrajectory = new Point(this.location.getX() + subDx, this.location.getY() + subDy);
            Line trajectory = new Line(this.location, endTrajectory);
            CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
            // Collision detected — move slightly before the collision point to avoid overlap.
            if (collisionInfo != null) {
                Point collisionPoint = collisionInfo.collisionPoint();
                Point newLocation = new Point(collisionPoint.getX() - moveBackX, collisionPoint.getY() - moveBackY);
                Velocity newVelocity = collisionInfo.collisionObject().hit(this, collisionPoint, this.velocity);
                this.setVelocity(newVelocity);
                this.setLocation(newLocation);
                break;
            } else {
                // If no direct collision, check if the ball is inside any dynamic object (like paddle).
                for (Collidable c : this.gameEnvironment.getCollidables()) {
                    if (c.getIsDynamic() && c.getCollisionRectangle().isInside(this.location)) {
                        Velocity v = c.hit(this, this.location, this.getVelocity());
                        this.setVelocity(v);
                        // Move the ball slightly above the paddle to unstick it.
                        double newY = c.getCollisionRectangle().getUpperLeft().getY() - 10;
                        this.location = new Point(this.location.getX(), newY);
                        return;
                    } else {
                        // If no collision and not stuck — proceed to next substep.
                        this.setLocation(endTrajectory);
                    }
                }
            }
        }
    }

    /**
     * Sets the ball’s initial location and velocity so that it is placed inside a given outer rectangle
     * without overlapping with any internal rectangles (frames). The radius is adjusted to fit inside.
     *
     * @param topLeft   the top-left corner of the bounding rectangle
     * @param downRight the bottom-right corner of the bounding rectangle
     * @param maxRadius the maximum allowed radius for the ball
     */
    public void setBallOutFrames(Point topLeft, Point downRight, int maxRadius) {
        Random rand = new Random();
        double width = downRight.getX() - topLeft.getX();
        double height = downRight.getY() - topLeft.getY();
        while (this.radius >= maxRadius) {
            this.radius /= 2;
        }
        double speed = Math.max(50 - radius, 1);
        double angle = rand.nextInt(360);
        Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
        this.setVelocity(v.getDx(), v.getDy());
        boolean validLocation;
        do {
            double x = rand.nextDouble(topLeft.getX() + this.radius, topLeft.getX() + width - radius);
            double y = rand.nextDouble(topLeft.getY() + this.radius, topLeft.getY() + height - radius);
            Point location = new Point(x, y);
            this.setLocation(location);
            validLocation = true;
            for (Line[] rectangle : this.limits) {
                if (this.isBallTouchingRectangle(rectangle)) {
                    validLocation = false;
                    break;
                }
            }
        } while (!validLocation);
        this.setLimits(topLeft, downRight);
    }

    /**
     * Checks whether the ball touches or intersects a given rectangle.
     * This includes full containment within the rectangle or partial overlap.
     *
     * @param rectangle the rectangle represented by an array of 4 lines
     * @return true if the ball touches or is inside the rectangle, false otherwise
     */
    public boolean isBallTouchingRectangle(Line[] rectangle) {
        double minX = rectangle[0].start().getX();
        double maxX = minX;
        double minY = rectangle[0].start().getY();
        double maxY = minY;
        for (Line line : rectangle) {
            for (Point p : new Point[]{line.start(), line.end()}) {
                minX = Math.min(minX, p.getX());
                maxX = Math.max(maxX, p.getX());
                minY = Math.min(minY, p.getY());
                maxY = Math.max(maxY, p.getY());
            }
        }
        Point topLeft = new Point(minX, minY);
        Point bottomRight = new Point(maxX, maxY);
        if (isBallInsideRectangle(topLeft, bottomRight)) {
            return true;
        }
        if (this.location.getX() + this.radius > minX && this.location.getX() - this.radius < maxX
                && this.location.getY() + this.radius > minY && this.location.getY() - this.radius < maxY) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the ball is fully contained within a rectangle.
     *
     * @param topLeft   the top-left point of the rectangle
     * @param downRight the bottom-right point of the rectangle
     * @return true if the ball is completely inside the rectangle, false otherwise
     */
    public boolean isBallInsideRectangle(Point topLeft, Point downRight) {
        return this.location.getX() - this.radius >= topLeft.getX()
                && this.location.getX() + this.radius <= downRight.getX()
                && this.location.getY() - this.radius >= topLeft.getY()
                && this.location.getY() + this.radius <= downRight.getY();
    }

    /**
     * Creates a new ball inside the given rectangle frame with a valid radius and random position.
     * The ball will have a random color and a velocity based on its radius.
     *
     * @param radius    the initial radius of the ball
     * @param topLeft   the top-left corner of the rectangle
     * @param downRight the bottom-right corner of the rectangle
     * @return a new Ball instance placed inside the given frame
     */
    public static Ball createBallInFrame(int radius, Point topLeft, Point downRight) {
        Random rand = new Random();
        double width = downRight.getX() - topLeft.getX();
        double height = downRight.getY() - topLeft.getY();
        double minLimit = Math.min(width, height);
        if (radius < 0) {
            radius = -1 * radius;
        }
        while (radius * 2 > minLimit || doubleEquals(minLimit, radius * 2)) {
            radius = radius / 2;
        }
        double x = rand.nextDouble(topLeft.getX() + radius, topLeft.getX() + width - radius);
        double y = rand.nextDouble(topLeft.getY() + radius, topLeft.getY() + height - radius);
        double speed = Math.max(50 - radius, 1);
        double angle = rand.nextInt(360);
        Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
        Color color = Color.getHSBColor(rand.nextFloat(), 1, rand.nextFloat() * 0.5f + 0.5f);
        Ball newBall = new Ball(new Point(x, y), radius, color);
        newBall.setVelocity(v.getDx(), v.getDy());
        newBall.setLimits(topLeft, downRight);
        return newBall;
    }

    /**
     * Sets the color of this ball.
     *
     * @param color the new color to assign to the ball
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Removes this ball from the specified game.
     * After calling this method, the ball will no longer be drawn or updated in the game.
     *
     * @param game the game instance from which to remove the ball
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }
}
package gameSprites;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import collision.Collidable;
import game.Game;
import game.Sprite;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

import java.awt.Color;

/**
 * The Paddle class represents the player's paddle in the game. It implements both
 * the Sprite and Collidable interfaces. The paddle is controlled by the player
 * using the left and right arrow keys. It reflects balls when they collide with
 * the top of the paddle and handles collisions with the sides and bottom of the paddle.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle paddle;
    private boolean isDynamic = true;

    /**
     * Constructs a paddle at the bottom of the screen.
     */
    public Paddle() {
        this.paddle = new Rectangle(new Point(350, 570), 100, 10);
    }

    /**
     * Gets whether the paddle is dynamic (i.e., able to move).
     *
     * @return true if the paddle is dynamic, false otherwise
     */
    @Override
    public boolean getIsDynamic() {
        return this.isDynamic;
    }

    /**
     * Initializes the keyboard sensor for the paddle, which is used to control
     * the movement of the paddle using the arrow keys.
     *
     * @param gui the GUI used to get the keyboard input
     */
    public void initKeyboard(GUI gui) {
        this.keyboard = gui.getKeyboardSensor();
    }

    /**
     * Moves the paddle to the left. The paddle will wrap around to the right side
     * of the screen if it goes beyond the left edge.
     */
    public void moveLeft() {
        if (this.paddle.getUpperLeft().getX() - 5 < 20) {
            this.paddle.setUpperLeft(new Point(680, this.paddle.getUpperLeft().getY()));
        } else {
            this.paddle.setUpperLeft(new Point(this.paddle.getUpperLeft().getX() - 5,
                    this.paddle.getUpperLeft().getY()));
        }
    }

    /**
     * Moves the paddle to the right. The paddle will wrap around to the left side
     * of the screen if it goes beyond the right edge.
     */
    public void moveRight() {
        if (this.paddle.getUpperLeft().getX() + this.paddle.getWidth() + 5 > 780) {
            this.paddle.setUpperLeft(new Point(20, this.paddle.getUpperLeft().getY()));
        } else {
            this.paddle.setUpperLeft(new Point(this.paddle.getUpperLeft().getX() + 5,
                    this.paddle.getUpperLeft().getY()));
        }
    }

    // Sprite
    /**
     * Updates the paddle's position based on the current input from the keyboard.
     * Moves the paddle left or right depending on whether the left or right arrow
     * keys are pressed.
     */
    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    /**
     * Draws the paddle on the screen.
     *
     * @param d the DrawSurface on which to draw the paddle
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(new Color(152, 193, 217));
        int x = (int) this.getCollisionRectangle().getUpperLeft().getX();
        int y = (int) this.getCollisionRectangle().getUpperLeft().getY();
        int width = (int) this.getCollisionRectangle().getWidth();
        int height = (int) this.getCollisionRectangle().getHeight();
        d.fillRectangle(x, y, width, height);
        d.setColor(Color.black);
        d.drawRectangle(x, y, width, height);
    }

    /**
     * Adds the paddle to the game, both as a sprite and a collidable object.
     *
     * @param g the game to which the paddle is added
     */
    @Override
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    // Collidable

    /**
     * Gets the collision rectangle of the paddle.
     *
     * @return the rectangle representing the paddle's bounds
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle;
    }

    /**
     * Handles the collision between the paddle and a ball. The velocity of the
     * ball is reflected based on the point of collision with the paddle.
     * If the ball hits the top of the paddle, the ball is reflected at different
     * angles based on which region of the paddle it collided with.
     * If the ball hits the sides or bottom of the paddle, it reflects accordingly.
     *
     * @param hitter          the ball that hit the paddle
     * @param collisionPoint  the point where the ball collided with the paddle
     * @param currentVelocity the current velocity of the ball before the collision
     * @return the new velocity of the ball after the collision
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Line top = this.paddle.getTop();
        Line bottom = this.paddle.getBottom();
        Line left = this.paddle.getLeft();
        Line right = this.paddle.getRight();
        if (top.checkOnLine(collisionPoint)
                && top.isXBetween(collisionPoint.getX())
                && top.isYBetween(collisionPoint.getY())) {
            double regionWidth = this.paddle.getWidth() / 5;
            double distanceFromLeft = collisionPoint.getX() - this.paddle.getUpperLeft().getX();
            int region = (int) (distanceFromLeft / regionWidth) + 1;
            double dx = currentVelocity.getDx();
            double dy = currentVelocity.getDy();
            double angle;
            switch (region) {
                case 1:
                    angle = 300;
                    break;
                case 2:
                    angle = 330;
                    break;
                case 3:
                    angle = 0;
                    break;
                case 4:
                    angle = 30;
                    break;
                case 5:
                    angle = 60;
                    break;
                default:
                    angle = 0;
                    break;
            }
            double speed = Math.sqrt(dx * dx + dy * dy);
            return Velocity.fromAngleAndSpeed(angle, speed);
        } else if (bottom.checkOnLine(collisionPoint)
                && bottom.isXBetween(collisionPoint.getX())
                && bottom.isYBetween(collisionPoint.getY())) {
            return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
        } else if (left.checkOnLine(collisionPoint)
                && left.isXBetween(collisionPoint.getX())
                && left.isYBetween(collisionPoint.getY())) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        } else if (right.checkOnLine(collisionPoint)
                && right.isXBetween(collisionPoint.getX())
                && right.isYBetween(collisionPoint.getY())) {
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        return currentVelocity;
    }

}
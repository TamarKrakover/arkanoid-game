package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import collision.Collidable;
import collision.GameEnvironment;
import gameSprites.Ball;
import gameSprites.Block;
import gameSprites.Paddle;
import gameSprites.ScoreIndicator;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;


import java.awt.Color;

/**
 * The Game class manages the entire game environment, including the creation of objects,
 * the game loop, and the interaction between sprites and collidables.
 * It sets up the game environment, initializes the screen, and handles user input.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Counter blocksCounter;
    private Counter ballsCounter;
    private Counter score;

    /**
     * Constructor for creating a new Game object.
     * Initializes the sprite collection and game environment.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.blocksCounter = new Counter();
        this.ballsCounter = new Counter();
        this.score = new Counter();
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the game.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Creates a new ball with the given position, radius, angle, and speed,
     * sets its velocity and game environment, and adds it to the game.
     *
     * @param x      the x-coordinate of the ball's center
     * @param y      the y-coordinate of the ball's center
     * @param r      the radius of the ball
     * @param angle  the angle of the ball's velocity in degrees
     * @param speed  the speed of the ball
     */
    public void createBall(int x, int y, int r, int angle, int speed) {
        Ball ball = new Ball(x, y, r, Color.black);
        Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
        ball.setVelocity(v);
        ball.setGameEnvironment(this.environment);
        ball.addToGame(this);
    }

    /**
     * Initializes the game, including setting up blocks, the paddle, balls, and other game elements.
     */
    public void initialize() {
        this.gui = new GUI("game", 800, 600);
//        Set up screen boundaries and add to the game
        Block screen = new Block(new Rectangle(new Point(0, 0), 800, 600));
        screen.setColor(new Color(250, 250, 250));
        screen.addToGame(this);
//      Create and add 3 balls
        createBall(400, 550, 6, 30, 7);
        createBall(400, 550, 6, 210, 7);
        createBall(400, 550, 6, 180, 7);
        this.ballsCounter.increase(3);
//       Set the limits and the death zone
        Color limitsColor = new Color(180, 180, 180);
        Block bottomScreen = new Block(new Rectangle(new Point(0, 580), 800, 20));
        bottomScreen.setColor(limitsColor);
        bottomScreen.addToGame(this);
        BallRemover ballRemover = new BallRemover(this, this.ballsCounter);
        bottomScreen.addHitListener(ballRemover);

        Block leftScreen = new Block(new Rectangle(new Point(0, 20), 20, 600));
        leftScreen.setColor(limitsColor);
        leftScreen.addToGame(this);
        Block rightScreen = new Block(new Rectangle(new Point(780, 20), 20, 600));
        rightScreen.setColor(limitsColor);
        rightScreen.addToGame(this);
        Block topScreen = new Block(new Rectangle(new Point(0, 20), 800, 20));
        topScreen.setColor(limitsColor);
        topScreen.addToGame(this);

        // Initialize and add the paddle
        Paddle paddle = new Paddle();
        paddle.initKeyboard(gui);
        paddle.addToGame(this);

        // Create and add colored blocks
        Color[] colors = new Color[]{
                new Color(244, 213, 141),
                new Color(173, 216, 230),
                new Color(186, 228, 179),
                new Color(255, 192, 203),
                new Color(221, 160, 221),
                new Color(250, 250, 210)
        };
        BlockRemover blockRemover = new BlockRemover(this, this.blocksCounter);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(score);
        for (int i = 0; i < 6; i++) {
            int j = 12 - i;
            for (int k = 0; k < j; k++) {
                Point upperLeft = new Point(780 - (k + 1) * 50, 200 + i * 20);
                Block block = new Block(new Rectangle(upperLeft, 50, 20));
                block.setColor(colors[i]);
                block.addToGame(this);
                block.addHitListener(blockRemover);
                this.blocksCounter.increase(1);
                block.addHitListener(scoreTrackingListener);
            }
        }
        ScoreIndicator scoreIndicator = new ScoreIndicator(this);
        this.addSprite(scoreIndicator);
    }

    /**
     * Starts the game loop, updating the game state and redrawing the screen every frame.
     * The loop runs continuously, calling timePassed() on all sprites and drawing them on the screen.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
            if (this.blocksCounter.getValue() == 0) {
                this.score.increase(100);
                System.out.println("You Win!\nYour score is: " + this.getScore());
                gui.close();
                break;
            }
            if (this.ballsCounter.getValue() == 0) {
                System.out.println("Game Over.\nYour score is: " + this.getScore());
                gui.close();
                break;
            }
        }
    }

    /**
     * Removes the specified Collidable object from the game environment.
     * After removal, it will no longer be checked for collisions.
     *
     * @param c the Collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes the specified Sprite from the game's sprite collection.
     * The sprite will no longer be updated or drawn.
     *
     * @param s the Sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Returns the current score of the game.
     *
     * @return the current score as an integer
     */
    public int getScore() {
        return this.score.getValue();
    }
}
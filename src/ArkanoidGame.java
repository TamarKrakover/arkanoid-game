/******************
 Name: Tamar Krakover
 ID: 213273105
 Assignment: ass5
 *******************/

import game.Game;

/**
 * The {@code Ass3Game} class serves as the entry point for the game application.
 * It creates a {@link Game} instance, initializes it, and starts the game loop.
 */
public class ArkanoidGame {
    /**
     * The main method which launches the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}

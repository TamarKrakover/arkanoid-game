package collision;

import geometry.Line;
import geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameEnvironment class represents the environment in which the game takes place,
 * managing a collection of collidable objects and checking for potential collisions.
 */
public class GameEnvironment {
    private List<Collidable> collidables = new ArrayList<>();

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Returns the list of collidable objects in the environment.
     *
     * @return list of collidable objects
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    /**
     * Checks the closest collision point between a given trajectory (line) and the collidables.
     * If a collision occurs, the method returns information about the closest collision.
     * If no collision occurs, the method returns null.
     *
     * @param trajectory the trajectory of an object
     * @return the information of the closest collision, or null if no collision occurs
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestCollidable = null;

        for (Collidable c : collidables) {
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (collisionPoint != null) {
                // If a collision is detected, check if it's the closest one
                if (closestPoint == null
                        || trajectory.end().distance(collisionPoint) < trajectory.end().distance(closestPoint)) {
                    closestPoint = collisionPoint;
                    closestCollidable = c;
                }
            }
        }
        // Return the closest collision information or null if no collision was detected
        if (closestPoint == null) {
            return null;
        }
        return new CollisionInfo(closestPoint, closestCollidable);
    }

    /**
     * Removes a Collidable object from the collection of collidables.
     *
     * <p>After removal, the specified object will no longer be checked for collisions
     * by the environment or relevant game logic.</p>
     *
     * @param c the Collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
}

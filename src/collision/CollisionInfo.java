package collision;

import geometry.Point;

/**
 * The CollisionInfo class stores information about a collision, including the
 * point at which the collision occurs and the collidable object involved in
 * the collision. This class provides methods to access these details.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;
    /**
     * Constructs a CollisionInfo object with a specific collision point and
     * collidable object.
     *
     * @param collisionPoint the point at which the collision occurs
     * @param collisionObject the collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    // the point at which the collision occurs.
    /**
     * Gets the point at which the collision occurs.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }
    // the collidable object involved in the collision.
    /**
     * Gets the collidable object involved in the collision.
     *
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}

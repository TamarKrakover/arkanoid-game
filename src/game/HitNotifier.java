package game;

/**
 * The HitNotifier interface should be implemented by any class that wants to be able to
 * notify registered HitListener objects when a hit event occurs.
 *
 * <p>Implementing classes manage a list of HitListeners and call their hitEvent method
 * when an appropriate event (like a collision) happens.</p>
 */
public interface HitNotifier {

    /**
     * Adds a HitListener to the list of listeners.
     * The listener will be notified when a hit event occurs.
     *
     * @param hl the HitListener to be added
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a HitListener from the list of listeners.
     * The listener will no longer be notified of hit events.
     *
     * @param hl the HitListener to be removed
     */
    void removeHitListener(HitListener hl);
}
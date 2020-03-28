package pt.ua.deti.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import pt.ua.deti.common.Bag;

/**
 * Baggage Collection Point.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class BaggageCollectionPoint {
    /** {@link List} of bags from the passengers */
    private final List<Bag> bags;
    /** {@link Lock} used by the entities to change the internal {@link list} */
    private final Lock lock = new ReentrantLock();
    /** {@link Condition} used by the passenger to check again for bags */
    private final Condition cond = lock.newCondition();

    /**
     * Create a Baggage Collection location.
     */
    public BaggageCollectionPoint() {
        bags = new ArrayList<>();
    }

    /**
     * Method used by the porter to store bags in the conveyor belt.
     * 
     * @param b {@link Bag} carry be the {@link Porter}
     */
    public void storeBag(final Bag b) {
        lock.lock();
        try {
            bags.add(b);
            cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Method used by the {@link Passenger} to collect the bags
     * @param bagId the id of the {@link Bag}
     * @return True if the bag was collected, otherwise false
     */
    public boolean goCollectBag(final int bagId) {
        lock.lock();
        boolean found = false;
        try {
            // check if the bag is in the conveyor belt
            // the bag is the same if the ids mathc
            for (Bag b : bags) {
                if (b.id() == bagId) {
                    found = true;
                }
            }
            // If the bag is not found, wait...
            if (!found) {
                cond.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return found;
    }
}
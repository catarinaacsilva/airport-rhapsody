package pt.ua.deti.shared;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Departure Terminal Entrance.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class DepartureTerminalEntrance {
    /** {@link Lock} used by the entities to change the internal state */
    private final Lock lock = new ReentrantLock();
    /** {@link Condition} used by the passenger to check again for bags */
    private final Condition cond = lock.newCondition();
    /** blocked {@link Passenger} */
    private int blocked = 0;
    /** Total number of {@link Passenger} */
    private final int total;
    /** {@link ArrivalTerminalExit} */
    private ArrivalTerminalExit ate;

    public DepartureTerminalEntrance(final int total) {
        this.total = total;
    }

    public void setATE(final ArrivalTerminalExit ate) {
        this.ate = ate;
    }

    /**
     * Reset the {@link DepartureTerminalEntrance} by setting the blocked to 0.
     */
    public void reset() {
        lock.lock();
        try {
            blocked = 0;
        } finally {
            lock.unlock();
        }
    }

    public void prepareNextLeg() {
        lock.lock();
        try {
            blocked++;
            boolean done = false;
            while (!done) {
                int ateBlocked = ate.getBlocked();
                if ((ateBlocked + blocked) < total) {
                    cond.await();
                } else {
                    done = true;
                    cond.signalAll();
                    ate.signal();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signal() {
        lock.lock();
        try {
            cond.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int getBlocked() {
        int rv = 0;
        lock.lock();
        try {
            rv = blocked;
        } finally {
            lock.unlock();
        }
        return rv;
    }
}

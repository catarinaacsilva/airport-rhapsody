package pt.ua.deti.shared;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Arrival Terminal Exit.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class ArrivalTerminalExit {
    /** {@link Lock} used by the entities to change the internal state */
    private final Lock lock = new ReentrantLock();
    /** {@link Condition} used by the passenger to check again for bags */
    private final Condition cond = lock.newCondition();
    /** blocked {@link Passenger} */
    private int blocked = 0;
    /** Total number of {@link Passenger} */
    private final int total;
    /** {@link ArrivalTerminalExit} */
    private DepartureTerminalEntrance dte;

    /**
     * 
     */
    public ArrivalTerminalExit(final int total) {
        this.total = total;
        
    }

    public void setDTE(final DepartureTerminalEntrance dte) {
        this.dte = dte;
    }

    /**
     * 
     */
    public void goHome() {
        lock.lock();
        try {
            blocked++;
            boolean done = false;
            while (!done) {
                int ateBlocked = dte.getBlocked();
                if ((ateBlocked + blocked) < total) {
                    cond.await();
                } else {
                    done = true;
                    cond.signalAll();
                    dte.signal();
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

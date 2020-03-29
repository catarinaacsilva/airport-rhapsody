package pt.ua.deti.shared;

import java.util.concurrent.TimeUnit;
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
    /** Blocked {@link Passenger} */
    private int blocked = 0;
    /** Total number of {@link Passenger} */
    private final int total;
    /** {@link DepartureTerminalEntrance} */
    private DepartureTerminalEntrance dte;
    /** Flag used to identify if is the last plane */
    private boolean last = false;
    /** Flag used to identify if the simulation is done */
    private boolean hasDaysWorkEnded = false;
    private final GeneralRepositoryInformation gri;

    /**
     * Create a {@link ArrivalTerminalExit}
     * 
     * @param total total number of {@link Passenger}
     */
    public ArrivalTerminalExit(final int total, final GeneralRepositoryInformation gri) {
        this.total = total;
        this.gri = gri;
    }

    /**
     * Define the other exit point (@{link DepartureTerminalEntrance}).
     * 
     * @param dte the other exit point (@{link DepartureTerminalEntrance})
     */
    public void setDTE(final DepartureTerminalEntrance dte) {
        this.dte = dte;
    }

    /**
     * Reset the {@link DepartureTerminalEntrance} by setting the blocked to 0.
     */
    public void reset(final boolean last) {
        lock.lock();
        try {
            blocked = 0;
            this.last = last;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Go Home.
     */
    public void goHome(final int id) {
        lock.lock();
        try {
            blocked++;
            boolean done = false;
            while (!done) {
                int dteBlocked = dte.getBlocked();
                if ((dteBlocked + blocked) < total) {
                    cond.await(100, TimeUnit.MILLISECONDS);
                } else {
                    done = true;
                    // mark the end of the simulation
                    if (last) {
                        hasDaysWorkEnded = true;
                    }
                    cond.signalAll();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        gri.debbug("Done -> " + id);
    }

    /**
     * Returns the number of blocked {@link Passenger}.
     * 
     * @return the number of blocked {@link Passenger}
     */
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

    /**
     * Has days work endend.
     */
    public boolean hasDaysWorkEnded() {
        boolean rv = false;
        lock.lock();
        try {
            int dteBlocked = dte.getBlocked();
            rv = ((dteBlocked + blocked) == total) && last;
        } finally {
            lock.unlock();
        }
        return rv;
    }
}

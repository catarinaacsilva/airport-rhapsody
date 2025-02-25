package pt.ua.deti.shared;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Departure Terminal Transfer Quay
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class DepartureTerminalTransferQuay {
    /** {@link Lock} used by the entities to change the internal state */
    private final Lock lock = new ReentrantLock();
    /**
     * {@link Condition} used by the {@link pt.ua.deti.entities.Passenger} to wait
     * for the {@link pt.ua.deti.entities.BusDriver}
     */
    private final Condition pCond = lock.newCondition();
    /**
     * {@link Condition} used by the {@link pt.ua.deti.entities.BusDriver} to wait
     * for all {@link pt.ua.deti.entities.Passenger}
     */
    private final Condition bCond = lock.newCondition();
    /** {@link GeneralRepositoryInformation} serves as log */
    private final GeneralRepositoryInformation gri;
    /** Flag that indicates if the Bus has arrived */
    private boolean arrived = false;
    /** Count how many {@link pt.ua.deti.entities.Passenger} left the bus */
    private int left = 0;

    /**
     * Create a {@link DepartureTerminalTransferQuay}.
     * 
     * @param gri {@link GeneralRepositoryInformation} serves as log
     */
    public DepartureTerminalTransferQuay(final GeneralRepositoryInformation gri) {
        this.gri = gri;
    }

    /**
     * Leave the bus.
     * 
     * @param id the identification from the {@link pt.ua.deti.entities.Passenger}
     */
    public void leaveTheBus(final int id) {
        
        lock.lock();
        try {
            while (!arrived) {
                pCond.await();
            }
            left += 1;
            bCond.signal();
            gri.updateSeatRemove(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Park The Bus and Let {@link pt.ua.deti.entities.Passenger} off.
     * 
     * @param numberPassengers the current number of passenger for this trip
     */
    public void parkTheBusAndLetPassOff(final int numberPassengers) {
        lock.lock();
        try {
            arrived = true;
            pCond.signalAll();
            while (left < numberPassengers) {
                bCond.await();
            }
            left = 0;
            arrived = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

package pt.ua.deti.shared;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Arrival Lounge location.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class ArrivalLounge {
    private final Lock lock = new ReentrantLock();
    private final Condition cond  = lock.newCondition();
    private int disembark, totalPassengers;

    public ArrivalLounge(final int totalPassengers){
       disembark = 0;
       this.totalPassengers = totalPassengers;
    }

    public void takeARest() {
        lock.lock();
        try{
            while(disembark < totalPassengers) {
                cond.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void whatShouldIDo() {
        cond.signalAll();
    }
}

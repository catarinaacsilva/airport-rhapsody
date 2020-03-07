package pt.ua.deti;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArrivalLounge {

    private final Lock lock_bus = new ReentrantLock();
    private final Condition enterOnBus  = lock_bus.newCondition();

    /**
     * Number of passengers that will grab their bag
     */
    private int numPersonBag;

    /**
     * Number of passengers that will catch the bus
     */
    private int numPersonBus;

    public ArrivalLounge(int numPersonBag, int numPersonBus){
        this.numPersonBag = numPersonBag;
        this.numPersonBus = numPersonBus;
    }

}

package pt.ua.deti;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Temporary Storage Area.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class TemporaryStorageArea {

    /**
     * Quando as mala são enviadas para o storeroom
     */
    private final List<Bag> bags;

    /** */
    private final Lock lock = new ReentrantLock();

    public TemporaryStorageArea(){
        bags = new ArrayList<>();
    }

    /**
     * Method used by the porter to store bags in the Temporary Storage Area.
     * 
     * @param b {@link Bag} carry be the {@link Porter}
     */
    public void storeBag(final Bag b) {
        lock.lock();
        try {
            bags.add(b);
        } finally {
            lock.unlock();
        }
    }
}

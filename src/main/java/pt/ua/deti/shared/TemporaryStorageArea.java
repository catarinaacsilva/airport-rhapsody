package pt.ua.deti.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import pt.ua.deti.common.Bag;

/**
 * Temporary Storage Area.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class TemporaryStorageArea {
    /** {@link List} of bags from the passengers */
    private final List<Bag> bags = new ArrayList<>();
    /** {@link Lock} used by the entities to change the internal state */
    private final Lock lock = new ReentrantLock();
    /** {@link GeneralRepositoryInformation} serves as log */
    private final GeneralRepositoryInformation gri;

    /**
     * Create a Temporary Storage Area.
     * @param gri {@link GeneralRepositoryInformation}
     */
    public TemporaryStorageArea(final GeneralRepositoryInformation gri) {
        this.gri = gri;
    }

    /**
     * Method used by the porter to store bags in the Temporary Storage Area.
     * 
     * @param b {@link pt.ua.deti.common.Bag} carry be the {@link pt.ua.deti.entities.Porter}
     */
    public void storeBag(final Bag b) {
        lock.lock();
        try {
            bags.add(b);
            gri.updateStoreroom(bags.size());
        } finally {
            lock.unlock();
        }
    }
}

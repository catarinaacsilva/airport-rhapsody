package pt.ua.deti;

import java.util.List;

/**
 * Class that represents a plane
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class Plane {
    /** {@link List} to the passengers of the plane */
    private final List<Passenger> passengers;

    /** {@link List} of the bags of the passengers */
    private final List<Bag> bags;
    
    /** Represents the flight number */
    private final int fn;

    /**
     * Creates a plane object
     * 
     * @param fn flight number
     * @param passengers {@link List} of passengers
     * @param bags {@link List} of bags
     */
    public Plane(final int fn, final List<Passenger> passengers, final List<Bag> bags) {
        this.fn = fn;
        this.passengers = passengers;
        this.bags = bags;
    }

    /**
     * Returns the flight number
     * @return the flight number
     */
    public int fn() {
        return fn;
    }

    /**
     * Returns the number of bags.
     * @return the number of bags
     */
    public synchronized int bn() {
        return bags.size();
    }

    /**
     * Returns the {@link List} of passengers.
     * @return the {@link List} of passengers
     */
    public List<Passenger> getPassengers() {
        return passengers;
    }
}

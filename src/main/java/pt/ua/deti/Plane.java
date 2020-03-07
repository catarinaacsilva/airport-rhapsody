package pt.ua.deti;

import java.util.List;

public class Plane {
    /**
     * Represents the flight number
     */
    private final int fn;

    /**
     * Represents the number of bags
     */
    private int bn;

    /**
     * Creates a plane object
     * @param fn flight number
     * @param passengers list of passengers
     */
    public Plane(final int fn, List<Passenger> passengers) {
        this.fn = fn;
        int tmp = 0;
        for (Passenger p : passengers) {
            tmp += p.numBags;
        }
        this.bn = tmp;
    }

    /**
     * Creates a plane object
     * @param fn flight number
     * @param bn list of passengers
     */
    public Plane(final int fn, final int bn) {
        this.fn = fn;
        this.bn = bn;
    }

    /**
     * Returns the flight number
     * @return the flight number
     */
    public int fn() {
        return fn;
    }

    /**
     * Returns the number of bags
     * @return the number of bags
     */
    public synchronized int bn() {
        return bn;
    }

    /**
     * Remove a bag from aeroplane
     */
    public synchronized void removeBag() {
        bn -= 1;
    }
}

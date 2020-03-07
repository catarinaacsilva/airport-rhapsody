package pt.ua.deti;

import java.util.List;

public class Plane {
    private final int fn;
    private int bn;

    public Plane(final int fn, List<Passenger> passengers) {
        this.fn = fn;
        int tmp = 0;
        for (Passenger p : passengers) {
            tmp += p.numBags;
        }
        this.bn = tmp;
    }

    public Plane(final int fn, final int bn) {
        this.fn = fn;
        this.bn = bn;
    }

    public int fn() {
        return fn;
    }

    public synchronized int bn() {
        return bn;
    }

    public synchronized void removeBag() {
        bn -= 1;
    }
}

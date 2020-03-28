package pt.ua.deti.shared;

import java.util.List;

import pt.ua.deti.common.Bag;

/**
 * This class represnts a bag.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class PlaneHold {
    /** {@link List} of the bags of the passengers */
    private final List<Bag> bags;
    
    /**
     * Create a Plane Hold location.
     * @param bags {@link List} of bags
     */
    public PlaneHold(final List<Bag> bags) {
        this.bags = bags;
    }

    /**
     * Removes and returns one {@link Bag} from this location
     * @return {@link Bag}
     */
    public Bag getBag() {
        Bag b = bags.get(0);
        bags.remove(0);
        return b;
    }

    /**
     * Returns true is there are bags to collect; otherwise false.
     * @return true is there are bags to collect; otherwise false
     */
    public boolean hasBags() {
        return !bags.isEmpty();
    }
}
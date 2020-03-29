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
    private List<Bag> bags;
    /** {@link GeneralRepositoryInformation} serves as log */
    private final GeneralRepositoryInformation gri;
    /** Flag used to identify if this is the last plane */
    private boolean lastPlane = false;
    
    /**
     * Create a Plane Hold location.
     * @param gri {@link GeneralRepositoryInformation}
     */
    public PlaneHold(final GeneralRepositoryInformation gri) {
        this.bags = null;
        this.gri = gri;
    }

    /**
     * Unload bags from the {@link Plane} and place them into the {@link PlaneHold}.
     * @param bags {@link List} of {@link Bag}
     */
    public void loadBags(final List<Bag> bags, final boolean lastPlane) {
        this.bags = bags;
        this.lastPlane = lastPlane;
    }

    /**
     * Removes and returns one {@link Bag} from this location
     * @return {@link Bag}
     */
    public Bag getBag() {
        if(bags.size() > 0) {
            Bag b = bags.get(0);
            bags.remove(0);
            gri.updatePlaneHold(bags.size());
            return b;
        } else {
            return null;
        }
    }

    /**
     * Returns true is there are bags to collect; otherwise false.
     * @return true is there are bags to collect; otherwise false
     */
    public boolean hasBags() {
        if (bags == null) {
            return false;
        } else if(bags.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean done() {
        return lastPlane;
    }
}
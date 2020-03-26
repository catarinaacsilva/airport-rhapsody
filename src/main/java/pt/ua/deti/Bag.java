package pt.ua.deti;

/**
 * This class represnts a bag.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class Bag {
    /** {@link Bag} id */
    private final int id;
    /** flags if a bag is in transit */
    private final boolean transit;

    /**
     * Create a bag.
     * @param id id of the {@link Bag}
     */
    public Bag (final int id, final boolean transit){
        this.id = id;
        this.transit = transit;
    }

    /** Returns the id from the {@link Bag}.
     *  @return the id from the {@link Bag}
     */
    public int id() {
        return id;
    }

    /**
     * Returns true if the bag bellows to a {@link Passenge} in transit; otherwise false.
     * @return true if the bag bellows to a {@link Passenge} in transit; otherwise false
     */
    public boolean transit() {
        return transit;
    }
}

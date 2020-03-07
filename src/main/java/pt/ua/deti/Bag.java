package pt.ua.deti;

public class Bag {
    /**
     * Bag id
     */
    public int id;

    /**
     * Given information about state of passenger (transit ou not)
     */
    public int transitOrNot;

    public Bag (final int id, final int transitOrNot){
        this.id = id;
        this.transitOrNot = transitOrNot;
    }
}

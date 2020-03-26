package pt.ua.deti;/*enum State{
                   ;
                   }*/

/**
 * Porter entity.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class Porter implements Runnable {
    /** States that descibre the life cycle of a {@link Porter} */
    protected static enum State {
        WAITING_FOR_A_PLANE_TO_LAND, AT_THE_PLANES_HOLD,
        AT_THE_LUGGAGE_BELT_CONVEYOR, AT_THE_STOREROOM
    }

    /** {@link State} the state of the {@link Porter} */
    private State state;
    /** {@link GeneralRepositoryInformation} stores all the common data structures */
    private final GeneralRepositoryInformation gri;
    /** Temporary storage to move {@link Bag} */
    private Bag temp = null;

    /**
     * Creates a new {@link Porter}
     */
    public Porter(final GeneralRepositoryInformation gri) {
        this.state = State.WAITING_FOR_A_PLANE_TO_LAND;
        this.gri = gri;
    }

    @Override
    public void run() {
        while (!isDone()) {
            switch (state) {
                case WAITING_FOR_A_PLANE_TO_LAND:
                    takeARest();
                    break;
                case AT_THE_PLANES_HOLD:
                    tryToCollectABag();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Returns true if the entity is done; otherwise false.
     * @return true if the entity is done; otherwise false
     */
    private boolean isDone() {
        return false;
    }

    /**
     * take a rest
     */
    private void takeARest() {
        ArrivalLounge al = gri.arrivalLounge;
        al.takeARest();
        state = State.AT_THE_PLANES_HOLD;
    }

    /**
     * Try to collect a bag.
     */
    private void tryToCollectABag() {
        PlaneHold pl = gri.planeHold;
        temp = pl.getBag();
        if (temp.transit()) {
            state = State.AT_THE_STOREROOM;
        } else {
            state = State.AT_THE_LUGGAGE_BELT_CONVEYOR;
        }
    }
}

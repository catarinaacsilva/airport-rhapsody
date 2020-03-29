package pt.ua.deti.entities;

import pt.ua.deti.common.Bag;
import pt.ua.deti.shared.ArrivalLounge;
import pt.ua.deti.shared.BaggageCollectionPoint;
import pt.ua.deti.shared.GeneralRepositoryInformation;
import pt.ua.deti.shared.PlaneHold;
import pt.ua.deti.shared.TemporaryStorageArea;

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
        WAITING_FOR_A_PLANE_TO_LAND, AT_THE_PLANES_HOLD, AT_THE_LUGGAGE_BELT_CONVEYOR, AT_THE_STOREROOM
    }

    /** {@link State} the state of the {@link Porter} */
    private State state;
    /** {@link GeneralRepositoryInformation} serves as log */
    private final GeneralRepositoryInformation gri;
    /** {@link ArrivalLounge} */
    private final ArrivalLounge al;
    /** {@link PlaneHold} */
    private final PlaneHold pl;
    /** {@link BaggageCollectionPoint} */
    private final BaggageCollectionPoint bcp;
    /** {@link TemporaryStorageArea} */
    private final TemporaryStorageArea tsa;
    /** Temporary storage to move {@link Bag} */
    private Bag temp = null;
    private boolean done = false;

    /**
     * Creates a new {@link Porter}
     * 
     * @param gri {@link GeneralRepositoryInformation}
     * @param al  {@link ArrivalLounge}
     * @param pl  {@link PlaneHold}
     */
    public Porter(final ArrivalLounge al, final PlaneHold pl, final BaggageCollectionPoint bcp,
            final TemporaryStorageArea tsa, final GeneralRepositoryInformation gri) {
        this.state = State.WAITING_FOR_A_PLANE_TO_LAND;
        this.al = al;
        this.pl = pl;
        this.bcp = bcp;
        this.tsa = tsa;
        this.gri = gri;
    }

    @Override
    public void run() {
        while (!done) {
            switch (state) {
                case WAITING_FOR_A_PLANE_TO_LAND:
                    takeARest();
                    break;
                case AT_THE_PLANES_HOLD:
                    if (pl.hasBags()) {
                        tryToCollectABag();
                    } else {
                        noMoreBagsToCollect();
                    }
                    break;
                case AT_THE_LUGGAGE_BELT_CONVEYOR:
                    carryItToAppropriateStore();
                    break;
                case AT_THE_STOREROOM:
                    carryItToAppropriateStore();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Returns true if the entity is done; otherwise false.
     * 
     * @return true if the entity is done; otherwise false
     */
    private boolean isDone() {
        return false;
    }

    /**
     * Take a rest.
     */
    private void takeARest() {
        al.takeARest();
        state = State.AT_THE_PLANES_HOLD;
        gri.updatePStat(state.ordinal());
    }

    /**
     * Try to collect a {@link Bag}.
     */
    private void tryToCollectABag() {
        temp = pl.getBag();
        if (temp.transit()) {
            state = State.AT_THE_STOREROOM;
        } else {
            state = State.AT_THE_LUGGAGE_BELT_CONVEYOR;
        }
        gri.updatePStat(state.ordinal());
    }

    /**
     * No more bags to collect.
     */
    private void noMoreBagsToCollect() {
        bcp.noMoreBags();
        state = State.WAITING_FOR_A_PLANE_TO_LAND;
        gri.updatePStat(state.ordinal());
        if(pl.done()) {
            done = true;
        }
    }

    /**
     * Carry it to the Appropriate Store.
     */
    private void carryItToAppropriateStore() {
        if (state == State.AT_THE_STOREROOM) {
            tsa.storeBag(temp);
        } else {
            bcp.storeBag(temp);
        }
        temp = null;
        state = State.AT_THE_PLANES_HOLD;
        gri.updatePStat(state.ordinal());
    }
}

package pt.ua.deti.entities;

import java.util.ArrayList;
import java.util.List;

import pt.ua.deti.shared.ArrivalLounge;
import pt.ua.deti.shared.ArrivalTerminalExit;
import pt.ua.deti.shared.BaggageCollectionPoint;
import pt.ua.deti.shared.BaggageReclaimOffice;
import pt.ua.deti.shared.GeneralRepositoryInformation;

/**
 * Passenger entity.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class Passenger implements Runnable {
    /** States that descibre the life cycle of a {@link Passenger} */
    protected static enum State {
        AT_THE_DISEMBARKING_ZONE, AT_THE_LUGGAGE_COLLECTION_POINT, AT_THE_BAGGAGE_RECLAIM_OFFICE,
        EXITING_THE_ARRIVAL_TERMINAL, AT_THE_ARRIVAL_TRANSFER_TERMINAL, TERMINAL_TRANSFER,
        AT_THE_DEPARTURE_TRANSFER_TERMINAL, ENTERING_THE_DEPARTURE_TERMINAL
    }

    /**
     * Situation of the {@link Passenger}: TRT (in transit); FDT (has this airport
     * as her final destination)
     */
    protected static enum Situation {
        TRT, FDT
    };

    /**
     * number of pieces of luggage the passenger carried at the start of her journey
     */
    private final int nr;
    /** {@link List} of {@link Bag} ids */
    private final List<Integer> bagsId;
    /** {@link List} of {@link Bag} already collected */
    private final List<Integer> bagsColledted = new ArrayList<>(0);
    /** {@link List} of {@link Bag} missing */
    private final List<Integer> bagsMissing = new ArrayList<>(0);
    /** {@link State} the state of the {@link Passenger} */
    private State state;
    /** {@link Situation} of the {@link Passenger} */
    private final Situation situation;
    /** represents the id of this passenger */
    private final int id;
    /** {@link ArrivalLounge} */
    private final ArrivalLounge al;
    /** {@link BaggageCollectionPoint} */
    private final BaggageCollectionPoint bcp;
    /** {@link BaggageReclaimOffice} */
    private final BaggageReclaimOffice bro;
    /** {@link ArrivalTerminalExit} */
    private final ArrivalTerminalExit ate;
    /** {@link GeneralRepositoryInformation} serves as log */
    private final GeneralRepositoryInformation gri;
    /** Flag used to indicate if the life cycle is done */
    private boolean done = false;

    /**
     * Create a new {@link Passenger}
     * 
     * @param id     identification
     * @param bagsId {@List} with all the ids from its bags
     */
    public Passenger(final int id, final List<Integer> bagsId, final boolean transit, final ArrivalLounge al,
            final BaggageCollectionPoint bcp, final BaggageReclaimOffice bro, final ArrivalTerminalExit ate, final GeneralRepositoryInformation gri) {
        this.bagsId = bagsId;
        this.id = id;
        state = State.AT_THE_DISEMBARKING_ZONE;
        if (transit) {
            situation = Situation.TRT;
        } else {
            situation = Situation.FDT;
        }
        this.al = al;
        this.bcp = bcp;
        this.bro = bro;
        this.ate = ate;
        this.gri = gri;
        nr = bagsId.size();
    }

    @Override
    public void run() {
        while (!done) {
            switch (state) {
                case AT_THE_DISEMBARKING_ZONE:
                    whatShouldIDo();
                    break;
                case AT_THE_LUGGAGE_COLLECTION_POINT:
                    goCollectABag();
                    break;
                case AT_THE_BAGGAGE_RECLAIM_OFFICE:
                    reportMissingBags();
                    break;
                case EXITING_THE_ARRIVAL_TERMINAL:
                    goHome();
                    break;
                default:
                    break;
            }
        }
    }

    private void whatShouldIDo() {
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsColledted.size());
        al.whatShouldIDo();
        if (situation == Situation.TRT) {
            state = State.AT_THE_ARRIVAL_TRANSFER_TERMINAL;
        } else {
            if (bagsId.size() > 0) {
                state = State.AT_THE_LUGGAGE_COLLECTION_POINT;
            } else {
                state = State.EXITING_THE_ARRIVAL_TERMINAL;
            }
        }
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsColledted.size());
    }

    private void goCollectABag() {
        if (bagsId.size() > 0) {
            int bagId = bagsId.get(0);
            bagsId.remove(0);
            boolean collected = bcp.goCollectBag(bagId);
            if (collected) {
                bagsColledted.add(bagId);
            } else {
                bagsMissing.add(bagId);
            }
            state = State.AT_THE_LUGGAGE_COLLECTION_POINT;
        } else if (bagsMissing.size() > 0) {
            state = State.AT_THE_BAGGAGE_RECLAIM_OFFICE;
        } else {
            state = State.EXITING_THE_ARRIVAL_TERMINAL;
        }
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsColledted.size());
    }

    private void reportMissingBags() {
        final int missing = bagsMissing.size();
        for(Integer bagId : bagsMissing) {
            bro.reportMissingBag(bagId);
        }
        bagsMissing.clear();
        state = State.EXITING_THE_ARRIVAL_TERMINAL;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsColledted.size());
        gri.updateMissing(missing);
    }

    private void goHome(){
        ate.goHome();
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsColledted.size());
        done = true;
        gri.updateFDT(1);
    }
}

package pt.ua.deti.entities;

import java.util.ArrayList;
import java.util.List;

import pt.ua.deti.shared.ArrivalLounge;
import pt.ua.deti.shared.ArrivalTerminalExit;
import pt.ua.deti.shared.ArrivalTerminalTransferQuay;
import pt.ua.deti.shared.BaggageCollectionPoint;
import pt.ua.deti.shared.BaggageReclaimOffice;
import pt.ua.deti.shared.DepartureTerminalEntrance;
import pt.ua.deti.shared.DepartureTerminalTransferQuay;
import pt.ua.deti.shared.GeneralRepositoryInformation;

/**
 * Passenger entity.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class Passenger implements Runnable {
    /**
     * States that descibre the life cycle of a
     * {@link pt.ua.deti.entities.Passenger}
     */
    protected static enum State {
        AT_THE_DISEMBARKING_ZONE, AT_THE_LUGGAGE_COLLECTION_POINT, AT_THE_BAGGAGE_RECLAIM_OFFICE,
        EXITING_THE_ARRIVAL_TERMINAL, AT_THE_ARRIVAL_TRANSFER_TERMINAL, TERMINAL_TRANSFER,
        AT_THE_DEPARTURE_TRANSFER_TERMINAL, ENTERING_THE_DEPARTURE_TERMINAL
    }

    /**
     * Situation of the {@link pt.ua.deti.entities.Passenger}: TRT (in transit); FDT
     * (has this airport as her final destination)
     */
    protected static enum Situation {
        TRT, FDT
    };

    /**
     * number of pieces of luggage the passenger carried at the start of her journey
     */
    private final int nr;
    /** {@link List} of {@link pt.ua.deti.common.Bag} ids */
    private final List<Integer> bagsId;
    /** {@link List} of {@link pt.ua.deti.common.Bag} already collected */
    private final List<Integer> bagsCollected = new ArrayList<>(0);
    /** {@link List} of {@link pt.ua.deti.common.Bag} missing */
    private final List<Integer> bagsMissing = new ArrayList<>(0);
    /** {@link State} the state of the {@link pt.ua.deti.entities.Passenger} */
    private State state;
    /** {@link Situation} of the {@link pt.ua.deti.entities.Passenger} */
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
    /** {@link DepartureTerminalEntrance} */
    private final DepartureTerminalEntrance dte;
    /** {@link ArrivalTerminalTransferQuay} */
    private final ArrivalTerminalTransferQuay attq;
    /** {@link DepartureTerminalTransferQuay} */
    private final DepartureTerminalTransferQuay dttq;
    /** {@link GeneralRepositoryInformation} serves as log */
    private final GeneralRepositoryInformation gri;
    /** Flag used to indicate if the life cycle is done */
    private boolean done = false;

    /**
     * Create a new {@link pt.ua.deti.entities.Passenger}
     * 
     * @param id      identification
     * @param bagsId  {@link List} with all the ids from its bags
     * @param transit flags if the {@link pt.ua.deti.entities.Passenger} is in
     *                transit
     * @param al      {@link ArrivalLounge}
     * @param bcp     {@link BaggageCollectionPoint}
     * @param bro     {@link BaggageReclaimOffice}
     * @param ate     {@link ArrivalTerminalExit}
     * @param dte     {@link DepartureTerminalEntrance}
     * @param attq    {@link ArrivalTerminalTransferQuay}
     * @param dttq    {@link DepartureTerminalTransferQuay}
     * @param gri     {@link GeneralRepositoryInformation} serves as log
     */
    public Passenger(final int id, final List<Integer> bagsId, final boolean transit, final ArrivalLounge al,
            final BaggageCollectionPoint bcp, final BaggageReclaimOffice bro, final ArrivalTerminalExit ate,
            final DepartureTerminalEntrance dte, final ArrivalTerminalTransferQuay attq,
            final DepartureTerminalTransferQuay dttq, final GeneralRepositoryInformation gri) {
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
        this.dte = dte;
        this.attq = attq;
        this.dttq = dttq;
        this.gri = gri;
        nr = bagsId.size();
    }

    @Override
    public void run() {
        while (!done) {
            switch (state) {
                case AT_THE_DISEMBARKING_ZONE:
                    int action = whatShouldIDo();
                    if (action == 0) {
                        goHome();
                    } else if (action == 1) {
                        goCollectABag();
                    } else {
                        takeABus();
                    }
                    break;
                case AT_THE_LUGGAGE_COLLECTION_POINT:
                    if (bagsId.size() > 0) {
                        goCollectABag();
                    } else if (bagsMissing.size() > 0) {
                        reportMissingBags();
                    } else {
                        goHome();
                    }
                    break;
                case AT_THE_BAGGAGE_RECLAIM_OFFICE:
                    goHome();
                    break;
                case EXITING_THE_ARRIVAL_TERMINAL:
                    done = true;
                    break;
                case AT_THE_ARRIVAL_TRANSFER_TERMINAL:
                    enterTheBus();
                    break;
                case TERMINAL_TRANSFER:
                    leaveTheBus();
                    break;
                case AT_THE_DEPARTURE_TRANSFER_TERMINAL:
                    prepareNextLeg();
                    break;
                case ENTERING_THE_DEPARTURE_TERMINAL:
                    done = true;
                default:
                    break;
            }
        }
    }

    /**
     * What should i do.
     * 
     * @return the action that the {@link pt.ua.deti.entities.Passenger} must run.
     */
    private int whatShouldIDo() {
        int action = 0;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());
        al.whatShouldIDo();
        if (situation == Situation.TRT) {
            // state = State.AT_THE_ARRIVAL_TRANSFER_TERMINAL;
            action = 2;
        } else {
            if (bagsId.size() > 0) {
                //
                action = 1;
            } else {
                // state = State.EXITING_THE_ARRIVAL_TERMINAL;
                action = 0;
            }
        }
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());
        return action;
    }

    /**
     * Go collect a bag.
     */
    private void goCollectABag() {
        state = State.AT_THE_LUGGAGE_COLLECTION_POINT;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());

        int bagId = bagsId.get(0);
        bagsId.remove(0);
        boolean collected = bcp.goCollectBag(bagId);
        if (collected) {
            bagsCollected.add(bagId);
        } else {
            bagsMissing.add(bagId);
        }
        state = State.AT_THE_LUGGAGE_COLLECTION_POINT;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());
    }

    /**
     * Report missing bags.
     */
    private void reportMissingBags() {
        state = State.AT_THE_BAGGAGE_RECLAIM_OFFICE;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());
        final int missing = bagsMissing.size();
        for (Integer bagId : bagsMissing) {
            bro.reportMissingBag(bagId);
        }
        bagsMissing.clear();
        gri.updateMissing(missing);
    }

    /**
     * Go home.
     */
    private void goHome() {
        state = State.EXITING_THE_ARRIVAL_TERMINAL;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());
        ate.goHome(id);
        gri.updateFDT(1);
    }

    /**
     * Take a bus.
     */
    private void takeABus() {
        state = State.AT_THE_ARRIVAL_TRANSFER_TERMINAL;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());
        attq.takeABus(id);
    }

    /**
     * Enter the bus.
     */
    private void enterTheBus() {
        state = State.TERMINAL_TRANSFER;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());
        attq.enterTheBus(id);
    }

    /**
     * Leave the bus.
     */
    private void leaveTheBus() {
        state = State.AT_THE_DEPARTURE_TRANSFER_TERMINAL;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());
        dttq.leaveTheBus(id);
    }

    /**
     * Prepare next leg.
     */
    private void prepareNextLeg() {
        state = State.ENTERING_THE_DEPARTURE_TERMINAL;
        gri.updatePassenger(id, state.ordinal(), situation.ordinal(), nr, bagsCollected.size());
        dte.prepareNextLeg(id);
        gri.updateTRT(1);
    }
}

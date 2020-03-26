package pt.ua.deti;

import java.util.List;

/**
 * Passenger entity.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class Passenger implements Runnable {
    /** States that descibre the life cycle of a {@link Passenger}*/
    protected static enum State {
        AT_THE_DISEMBARKING_ZONE, AT_THE_LUGGAGE_COLLECTION_POINT, AT_THE_BAGGAGE_RECLAIM_OFFICE,
        EXITING_THE_ARRIVAL_TERMINAL, AT_THE_ARRIVAL_TRANSFER_TERMINAL, TERMINAL_TRANSFER,
        AT_THE_DEPARTURE_TRANSFER_TERMINAL, ENTERING_THE_DEPARTURE_TERMINAL
    }
    /** {@link List} of bag ids */
    private final List<Integer> bagIds;
    /** {@link State} the state of the {@link Passenger} */
    private State state;
    /** represents the id of this passenger */
    private final int id;

    /**
     * Create a new {@link Passenger}
     * @param id identification
     * @param bagIds {@List} with all the ids from its bags 
     */
    public Passenger(final int id, final List<Integer> bagIds) {
        this.bagIds = bagIds;
        this.id = id;
        // Initial state
        state = State.AT_THE_DISEMBARKING_ZONE;
    }

    @Override
    public void run() {
        while (!isDone()) {
            
        }
    }

    
    private boolean isDone() {
        return false;
    }
}

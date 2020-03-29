package pt.ua.deti.entities;

import pt.ua.deti.shared.GeneralRepositoryInformation;

/**
 * Bus Driver entity.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class BusDriver implements Runnable {
    /** States that descibre the life cycle of a {@link BusDriver} */
    protected static enum State {
        PARKING_AT_THE_ARRIVAL_TERMINAL, DRIVING_FORWARD, PARKING_AT_THE_DEPARTURE_TERMINAL, DRIVING_BACKWARD
    }

    /** {@link State} the state of the {@link Porter} */
    private State state;
    /** {@link GeneralRepositoryInformation} serves as log */
    private final GeneralRepositoryInformation gri;
    /** Flag used to indicate if the life cycle is done */
    private boolean done = false;

    /**
     * @param gri
     */
    public BusDriver(final GeneralRepositoryInformation gri) {
        this.gri = gri;
    }

    @Override
    public void run() {
        while (!done) {
            switch (state) {
                case PARKING_AT_THE_ARRIVAL_TERMINAL:
                    break;
                case DRIVING_FORWARD:
                    break;
                case PARKING_AT_THE_DEPARTURE_TERMINAL:
                    break;
                case DRIVING_BACKWARD:
                    break;
                default:
                    break;
            }
        }
    }
}

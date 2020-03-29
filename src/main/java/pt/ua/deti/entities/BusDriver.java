package pt.ua.deti.entities;

import pt.ua.deti.shared.ArrivalTerminalExit;
import pt.ua.deti.shared.ArrivalTerminalTransferQuay;
import pt.ua.deti.shared.DepartureTerminalTransferQuay;
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
    /** {@link ArrivalTerminalTransferQuay} */
    private final ArrivalTerminalTransferQuay attq;
    /** {@link DepartureTerminalTransferQuay} */
    private final DepartureTerminalTransferQuay dttq;
    /** {@link ArrivalTerminalExit} */
    private final ArrivalTerminalExit ate;
    /** {@link GeneralRepositoryInformation} serves as log */
    private final GeneralRepositoryInformation gri;
    /** Flag used to indicate if the life cycle is done */
    private boolean done = false;
    /** The number of passengers for each trip */
    private int numberPassengers = 0;

    /**
     * @param gri
     */
    public BusDriver(final ArrivalTerminalTransferQuay attq, final DepartureTerminalTransferQuay dttq,
            final ArrivalTerminalExit ate, final GeneralRepositoryInformation gri) {
        this.attq = attq;
        this.dttq = dttq;
        this.ate = ate;
        this.gri = gri;
        state = State.PARKING_AT_THE_ARRIVAL_TERMINAL;
    }

    @Override
    public void run() {
        while (!done) {
            switch (state) {
                case PARKING_AT_THE_ARRIVAL_TERMINAL:
                    // check if the day has finnished
                    done = hasDaysWorkEnded();
                    if (!done) {
                        // announce the passengers that bus can go
                        numberPassengers = announcingBusBoarding();
                        gri.debbug("numberPassengers = "+numberPassengers);
                        if (numberPassengers > 0) {
                            goToDepartureTerminal(numberPassengers);
                        }
                    }
                    break;
                case DRIVING_FORWARD:
                    parkTheBusAndLetPassOff(numberPassengers);
                    break;
                case PARKING_AT_THE_DEPARTURE_TERMINAL:
                    goToArrivalTerminal();
                    break;
                case DRIVING_BACKWARD:
                    parkTheBus();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Park the bus.
     */
    private void parkTheBus() {
        state = State.PARKING_AT_THE_ARRIVAL_TERMINAL;
        gri.updateBusDriver(state.ordinal());
    }

    /**
     * Go To Arrival Terminal.
     */
    private void goToArrivalTerminal() {
        state = State.DRIVING_BACKWARD;
        gri.updateBusDriver(state.ordinal());
    }

    /**
     * Park The Bus and Let {@link Passenger} off.
     */
    private void parkTheBusAndLetPassOff(final int numberPassengers) {
        state = State.PARKING_AT_THE_DEPARTURE_TERMINAL;
        gri.updateBusDriver(state.ordinal());
        dttq.parkTheBusAndLetPassOff(numberPassengers);
    }

    /**
     * Announcing Bus Boarding.
     */
    private int announcingBusBoarding() {
        // announce the passengers that bus can go
        state = State.PARKING_AT_THE_ARRIVAL_TERMINAL;
        gri.updateBusDriver(state.ordinal());
        return attq.announcingBusBoarding();
    }

    /**
     * Go to Departure Terminal.
     */
    private void goToDepartureTerminal(final int numberPassengers) {
        // await for the passengers to enter the bus
        state = State.DRIVING_FORWARD;
        gri.updateBusDriver(state.ordinal());
        attq.goToDepartureTerminal(numberPassengers);
    }

    /**
     * Has days work endend.
     */
    private boolean hasDaysWorkEnded() {
        boolean rv = ate.hasDaysWorkEnded();
        return rv;
    }
}

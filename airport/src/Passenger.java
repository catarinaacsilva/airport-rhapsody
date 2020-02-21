public class Passenger implements Runnable{
    protected final int AT_THE_DISEMBARKING_ZONE = 0;
    protected final int AT_THE_LUGGAGE_COLLECTION_POINT = 1;
    protected final int AT_THE_BAGGAGE_RECLAIM_OFFICE = 2;
    protected final int EXITING_THE_ARRIVAL_TERMINAL = 3;
    protected final int AT_THE_ARRIVAL_TRANSFER_TERMINAL = 4;
    protected final int TERMINAL_TRANSFER = 5;
    protected final int AT_THE_DEPARTURE_TRANSFER_TERMINAL = 6;
    protected final int ENTERING_THE_DEPARTURE_TERMINAL = 7;

    private boolean bag;
    private boolean takeBus;

    private int state;



    public Passenger (boolean bag, boolean takeBus){
        this.bag = bag;
        this.takeBus = takeBus;
        state = 0; // Estado inicial

    }


    /**
     * Goes to the next state and verify if it is the end state.
     */
    @Override
    public void run(){
        boolean done = false;

        while (!done) {
            state = nextState(state);
            if (state == 7 || state == 3) {
                done = true;
            }
            System.out.println(this);
        }
    }

    /**
     * Next state based on state machie
     * @param state
     * @return int that corresponds to next state
     */
    public int nextState(int state){
        int nextState = 0;

        switch (state){
            case 0:
                if (bag == true){
                    goCollectABag(); //Arrival lounge
                    state = 1;
                }
                else if (takeBus == true){
                    takeABus(); //Arrival lounge
                    state = 3;
                }
                else{
                    goHome(); //ArrivalTerminalExit --> vai para casa
                    state = 4;
                }
                break;
            case 1:
                if(getInfoBag() == true){ //BaggageCollection -> saber se o pssageiro ja tem a mala
                    goHome(); // ArrivalTerminalExit -->Se tiver vai para casa
                    state = 4;
                }
                else if(getInfoBag() == false){
                    goCollectABag(); //BaggageCollection --> continua a espera da mala
                    state = 1;
                }
                else{
                    reportMissingBags(); //BaggageReclaim --> reclama a falta da mala
                    state = 2;
                }
                break;
            case 2:
                if(getInfoReclaimBag() == true){
                    state = 2;
                }
                else{
                    goHome(); //ArrivalTerminalExit
                }
                break;
            case 3:
                if(busAvailable == true){
                    enterTheBus();
                    state = 5;
                }
                else{
                    state = 3;
                }
                break;
            case 4:
                break;
            case 5:
                if(endBus == true){
                    leaveTheBus();
                    state = 6;
                }
                else{
                    state = 5;
                }
                break;
            case 6:
                if(nextLegAvailable == true){
                    prepareNextLeg();
                    state = 7;
                }
                else{
                    state = 6;
                }
        }
        return nextState;
    }

    /**
     * Present state
     * @param state
     * @return Name of the state
     */
    public String statePassenger(int state){
        if (state == 0)
            return "AT_THE_DISEMBARKING_ZONE";
        else if (state == 1)
            return "AT_THE_LUGGAGE_COLLECTION_POINT";
        else if (state == 2)
            return "AT_THE_BAGGAGE_RECLAIM_OFFICE";
        else if (state == 3)
            return "EXITING_THE_ARRIVAL_TERMINAL";
        else if (state == 4)
            return "AT_THE_ARRIVAL_TRANSFER_TERMINAL";
        else if (state == 5)
            return "TERMINAL_TRANSFER";
        else if (state == 6)
            return "AT_THE_DEPARTURE_TRANSFER_TERMINAL";
        else if (state == 7)
            return "ENTERING_THE_DEPARTURE_TERMINAL";
        else
            return "Invalid state";
    }
}

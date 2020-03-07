package pt.ua.deti;

import java.util.concurrent.locks.ReentrantLock;

public class Passenger implements Runnable{

    public int numBags;
    public int state;
    public int id;

    public Passenger (int numBags){
        this.numBags = numBags;
        state = 0; // Estado inicial
        id = 0; //TODO: tem que ser um número sequecial (contador genérico)
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

    //TODO: usar o tipo de dados enumerado para ver o estado presente e seguinte
    // Guardar estados ??
    /**
     * Next state based on state machine
     * @param state
     * @return int that corresponds to next state
     */
    public int nextState(int state){
        int nextState = 0;

        /*switch (state){
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
                    goHome(); //pt.ua.deti.ArrivalTerminalExit --> vai para casa
                    state = 4;
                }
                break;
            case 1:
                if(getInfoBag() == true){ //pt.ua.deti.BaggageCollection -> saber se o pssageiro ja tem a mala
                    goHome(); // pt.ua.deti.ArrivalTerminalExit -->Se tiver vai para casa
                    state = 4;
                }
                else if(getInfoBag() == false){
                    goCollectABag(); //pt.ua.deti.BaggageCollection --> continua a espera da mala
                    state = 1;
                }
                else{
                    reportMissingBags(); //pt.ua.deti.BaggageReclaim --> reclama a falta da mala
                    state = 2;
                }
                break;
            case 2:
                if(getInfoReclaimBag() == true){
                    state = 2;
                }
                else{
                    goHome(); //pt.ua.deti.ArrivalTerminalExit
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
        }*/
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

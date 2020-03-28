package pt.ua.deti.entities;

public class BusDriver implements Runnable{
    public String stateBusDriver(int state){
        if (state == 0)
            return "PARKING_AT_THE_ARRIVAL_TERMINAL";
        else if (state == 1)
            return "DRIVING_FORWARD";
        else if (state == 2)
            return "PARKING_AT_THE_DEPARTURE_TERMINAL";
        else if (state == 3)
            return "DRIVING_BACKWARD";
        else
            return "Invalid code";
    }


    @Override
    public void run() {

    }
}

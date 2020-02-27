public class BusDriver implements Runnable{
    protected final int PARKING_AT_THE_ARRIVAL_TERMINAL = 0,
    protected final int DRIVING_FORWARD = 1;
    protected final int PARKING_AT_THE_DEPARTURE_TERMINAL = 2;
    protected final int DRIVING_BACKWARD = 3;

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


}

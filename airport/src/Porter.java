/*enum State{
    WAITING_FOR_A_PLANE_TO_LAND, AT_THE_PLANES_HOLD, AT_THE_LUGGAGE_BELT_CONVEYOR, AT_THE_STOREROOM;
}*/

public class Porter implements Runnable{
    protected final int WAITING_FOR_A_PLANE_TO_LAND = 0;
    protected final int AT_THE_PLANES_HOLD = 1;
    protected final int AT_THE_LUGGAGE_BELT_CONVEYOR = 2;
    protected final int AT_THE_STOREROOM = 3;

    public String statePorter(int state){
        if (state == 0)
            return "WAITING_FOR_A_PLANE_TO_LAND";
        else if (state == 1)
            return "AT_THE_PLANES_HOLD";
        else if (state == 2)
            return "AT_THE_LUGGAGE_BELT_CONVEYOR";
        else if (state == 3)
            return "AT_THE_STOREROOM";
        else
            return "Invalid state";
    }

}

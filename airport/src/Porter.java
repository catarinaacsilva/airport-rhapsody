/*enum State{
    WAITING_FOR_A_PLANE_TO_LAND, AT_THE_PLANES_HOLD, AT_THE_LUGGAGE_BELT_CONVEYOR, AT_THE_STOREROOM;
}*/

public class Porter implements Runnable{

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

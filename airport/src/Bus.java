import java.awt.desktop.QuitEvent;
import java.net.PasswordAuthentication;
import java.util.LinkedList;
import java.util.Queue;

public class Bus {

    /**
     * Numero máximo de lugares do autocarro
     */
    public int numSeats;

    public Queue<Passenger> p;

    public Bus(final int numSeats){
        this.numSeats = numSeats;
        this.p = new LinkedList<>();
    }

    /**
     * O autocarro é uma fila. Os passageiros são adicionados ao autocarro.
     * @param auxP
     */
    public void addPersonBus(Passenger auxP){
        int freeSeats = numSeats;
        while (freeSeats > 0){
            p.add(auxP);
            freeSeats --;
        }
    }

    /**
     * Os passageiros quando acabam a viagem de autocarro são adicionados a uma fila que representa que eles acabaram a viagem de autocarro
     * @return
     */
    public Queue<Passenger> leaveBus(){
        Queue<Passenger> leaveBus = new LinkedList<>();
        while(p.size() > 0)
            leaveBus.add(p.peek());
        return leaveBus;
    }
}

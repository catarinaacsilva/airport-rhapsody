import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArrivalLounge {

    private final Lock lock_bus = new ReentrantLock();
    private final Condition enterOnBus  = lock_bus.newCondition();

    /**
     * Numero de pessoas que vao buscar a mala
     */
    private int numPersonBag;

    /**
     * Numero de pessoas que vao apanhar autocarro
     */
    private int numPersonBus;

    public ArrivalLounge(int numPersonBag, int numPersonBus){
        this.numPersonBag = numPersonBag;
        this.numPersonBus = numPersonBus;
    }

    public void goCollectABag(){

    }

    public void goHome(){

    }

    /**
     * Pessoas à espera de um autocarro
     */
    //TODO: confirmar
    public int takeABus(){
        lock.lock();
        try{

        }
        return numPersonBus;
    }
}

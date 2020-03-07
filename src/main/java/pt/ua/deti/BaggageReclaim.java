package pt.ua.deti;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BaggageReclaim {

    private boolean reclaimBag;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BaggageReclaim(boolean reclaimBag){

        this.reclaimBag = reclaimBag;
    }

    // saber se uma mala foi reclamada ou nao
    public boolean getInfoReclaimBag(){
        if (reclaimBag == true){
            return true;
        }
        return false;
    }

    public void reportMissingBags(){
        lock.lock();
        try {
            if(reclaimBag == true) {
                //action to that

            }
        }finally {
            lock.unlock();
        }
    }

}

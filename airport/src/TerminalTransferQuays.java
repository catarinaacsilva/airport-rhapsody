//Bus

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TerminalTransferQuays {

    // TODO: verificar se esta algum numero no enunciado
    private final int numSeatsBus = 5;
    /**
     * Numero de pessoas que entram no autocarro
     */
    private int numPeople;

    private final Lock lock = new ReentrantLock();
    private final Condition goOnBus  = lock.newCondition();

    public TerminalTransferQuays(int numPeople){
        this.numPeople = numPeople;
    }

    /**
     * Se nao hoouver mais lugares tem que esperar pelo proximo autocarro
     * @return
     */
    public void enterTheBus() throws InterruptedException {
        lock.lock();
        try{
            //Se houver pessoas para entrar incrementa -> takeABus vem do ArrivalLounge
            if (takeABus() > 0){
                numPeople ++;
                while (numSeatsBus == numPeople)
                    goOnBus.await();
            }
        }finally {
            lock.unlock();
        }
    }

    
}

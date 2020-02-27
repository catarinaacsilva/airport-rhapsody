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
     * Se nao houver mais lugares tem que esperar pelo proximo autocarro
     * @return
     */

    public void enterTheBus() throws InterruptedException {
        lock.lock();
        try{
            if (takeABus() > 0){ //TODO: confirmar
                numPeople ++;
                while (numSeatsBus == numPeople)
                    goOnBus.await(); // Nota: é obrigatorio estar sempre dentro de um ciclo
            }
        }finally {
            lock.unlock();
        }
    }
    
}

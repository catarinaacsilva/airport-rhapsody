package pt.ua.deti.shared;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import pt.ua.deti.entities.Passenger;

/**
 * This class represnts a Terminal Transfer Quay
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class TerminalTransferQuay {
    /** */
    private final Lock lock = new ReentrantLock();
    /** */
    private final Condition cond  = lock.newCondition();
    /** */
    private final Queue<Passenger> queue = new ArrayDeque<>();
    /** */
    private final Queue<Passenger> seats = new ArrayDeque<>();
    /**  */
    private final int numberSeats;

    public TerminalTransferQuay(final int numberSeats){
        this.numberSeats = numberSeats;
    }

    public void enterOnBus() {
        lock.lock();
        try{
            
        }finally {
            lock.unlock();
        }
    }
}

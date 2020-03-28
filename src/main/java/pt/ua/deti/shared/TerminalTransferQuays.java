package pt.ua.deti.shared;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This class represnts a bag.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class TerminalTransferQuays {

    private final Lock lock = new ReentrantLock();
    private final Condition cond  = lock.newCondition();

    public TerminalTransferQuays(){
        
    }

    public void enterOnBus() {
        lock.lock();
        try{
            
        }finally {
            lock.unlock();
        }
    }
}

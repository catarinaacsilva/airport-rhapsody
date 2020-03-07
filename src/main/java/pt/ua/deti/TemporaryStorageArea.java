package pt.ua.deti;

import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TemporaryStorageArea {

    /**
     * Quando as mala são enviadas para o storeroom
     */
    Stack<Bag> stackStore;

    private final Lock lock = new ReentrantLock();

    public TemporaryStorageArea(){
        stackStore = new Stack<>();

    }

    /**
     * pt.ua.deti.Porter coloca malas no storeroom
     * @param count
     * @param bags
     */
    public void addStoreroom(int count, Bag bags){
        //se não houver malas nao faz nada
        if(count == 0){
            return;
        }
        lock.lock();
        try{
            while(count > 0){
                if(bags.transitOrNot == 1){ // malas em transito
                    stackStore.push(bags);
                    count --;
                }
            }
        }finally{
            lock.unlock();
        }
    }
}

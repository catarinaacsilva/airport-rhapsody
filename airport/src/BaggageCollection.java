import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BaggageCollection {

    /**
     * Cada utilizador tem uma ou mais malas associadas
     */
    Map<Passenger, ArrayList<Bag>> BagsToPassenger;

    /**
     * Quando as mala são enviadas para o tapete para serem recolhias
     */
    Stack<Bag> stackBags;

    /**
     * Quando as mala são enviadas para o storeroom
     */
    Stack<Bag> stackStore;

    public boolean total;

    private final Lock lock = new ReentrantLock();
    //private final Condition missing  = lock.newCondition();

    public BaggageCollection(){
        BagsToPassenger = new HashMap<>();
        stackBags = new Stack();
        stackStore = new Stack<>();
        total = false;
    }

    /**
     * Passageiro vai buscar a mala
     * @param p
     */
    public void goCollectBag(Passenger[] p){
        Bag auxBag = null;
        for(int i=0; i<p.length; i++) {
            auxBag = stackBags.pop();
            while(!stackBags.empty()){
                ArrayList<Bag> b = BagsToPassenger.get(p[i]);
                for(int j=0; j<b.size(); j++){
                    if(b.get(j).id == auxBag.id)
                        p[i].numBags--;
                        if(p[i].numBags == 0) {
                            p[i] = null;
                            total = true;
                        }
                }
            }
        }
    }


    public boolean haveTotalBags(){
        return total;
    }

    /**
     *  Porter coloca malas no tapete
     * @param count
     * @param bags
     */
    public void addBags(int count, Bag bags){
        //se não houver malas nao faz nada
        if(count == 0){
            return;
        }

        lock.lock();
        try{
            while(count > 0){
                if(bags.transitOrNot == 0){ //malas para recolher
                    stackBags.push(bags);
                    count --;
                }

            }
        }finally{
            lock.unlock();
        }
    }

    /**
     * Porter coloca malas no storeroom
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

// Como só ha um porter, se ele estiver a colocar malas no tapete não poe estar a guardar no storeroom
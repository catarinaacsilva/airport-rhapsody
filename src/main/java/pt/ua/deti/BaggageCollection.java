package pt.ua.deti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BaggageCollection {

    /**
     * Each user has one or more bags
     */
    Map<Passenger, ArrayList<Bag>> BagsToPassenger;

    /**
     * Data struct to store the bags while the passengers are exiting the aeroplane
     */
    Stack<Bag> stackBags;

    /**
     * Por passageiro para saber se ja tem as malas todas
     */
    public boolean total;

    private final Lock lock;
    //private final Condition missing  = lock.newCondition();

    public BaggageCollection(){
        BagsToPassenger = new HashMap<>();
        stackBags = new Stack();
        total = false;
        lock = new ReentrantLock();
    }

    /**
     * Passenger grabs the bag
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
     * Porter adds bags
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





}

// Como só ha um porter, se ele estiver a colocar malas no tapete não poe estar a guardar no storeroom
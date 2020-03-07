package pt.ua.deti;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TerminalTransferQuays {

    /**
     * Instanciar um autocarro
     */
    public Bus b;

    /**
     * Passageiros que querem entrar
     */
    public Queue<Passenger> queueP;

    /**
     * Lista de inteiros que possui os identificadores dos passageiros
     */
    public LinkedList<Integer> listIdP;


    private final Lock lock = new ReentrantLock();
    private final Condition goOnBus  = lock.newCondition();

    public TerminalTransferQuays(){
        b =  new Bus(3); //TODO: O valor o numero de lugares deve ser passado como parametro ou estar num ficheiro e configuração
        queueP = new LinkedList<>();
        listIdP = new LinkedList<>();
    }

    public void enterOnBus() throws InterruptedException {
        lock.lock();
        try{
            while(queueP.size() > 0){
                b.addPersonBus(queueP.peek());
                listIdP.add(queueP.peek().id);
                goOnBus.await();
            }
        }finally {
            lock.unlock();
        }
    }

    public LinkedList<Integer> getListIdP(){
        return listIdP;
    }

    //TODO: ver se é necessário devolver uma lista com as pessoas que sairam do autocarro
    /**
     * Como as pessoas saem todas entao pode ser sinalizado que o autocarro esta novamente livre
     */
    public Queue<Passenger> leaveBus(){
        Queue<Passenger> leaveP = b.leaveBus();
        //TODO: delay para simular o autocarro a deslocar-se para ir buscar as pessoas??
        goOnBus.signalAll();
        return leaveP;
    }

}

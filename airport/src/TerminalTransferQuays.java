import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TerminalTransferQuays {

    //TODO: O valor o numero de lugares deve ser passado como parametro ou estar num ficheiro e configuração
    /**
     * Instanciar um autocarro
     */
    Bus b = new Bus(3);

    /**
     * Passageiros que querem entrar
     */
    Queue<Passenger> queueP = new LinkedList<>();

    private final Lock lock = new ReentrantLock();
    private final Condition goOnBus  = lock.newCondition();

    public TerminalTransferQuays(Bus b){
        this.b = b;
    }

    public void enterOnBus() throws InterruptedException {
        lock.lock();
        try{
            while(queueP.size() > 0){
                b.addPersonBus(queueP.peek());
                goOnBus.await();
            }
        }finally {
            lock.unlock();
        }
    }

    //TODO: ver se é necessário devolver uma lista com as pessoas que sairam do autocarro
    /**
     * Como as pessoas saem todas entao pode ser sinalizado que o autocarro esta novamente livre
     */
    public Queue<Passenger> leaveBus(){
        Queue<Passenger> leaveP = b.leaveBus();
        //TODO: delay para simular o autocarro a deslocar-se para ir buscar as pessoas??
        goOnBus.signal();
        return leaveP;
    }

}

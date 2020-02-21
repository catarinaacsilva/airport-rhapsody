import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BaggageCollection {
    /**
     * Foi buscar a mala e ja a tem
     */
    private boolean hasBag;
    /**
     * Contar o numero de malas
     */
    private int count;

    private final Lock lock = new ReentrantLock();
    private final Condition missing  = lock.newCondition();

    /**
     * Coleção de malas por voo
     * @param hasBag
     */
    public BaggageCollection(boolean hasBag){
        this.hasBag = hasBag;
        count = 0;
    }

    /**
     * Incrementa o numero de malas à medida que estao a ser guardadas
     */
    public void BagsToCollect(){
        lock.lock();
        try{
            count ++;
        }finally {
            lock.unlock();
        }
    }

    /**
     * Apenas para saber se um passageiro tem malas
     * @return
     */
    public boolean getInfoBag(){
        if (hasBag == true){
            return true;
        }
        return false;
    }

    /**
     * Ir buscar uma mala
     */
    public void goCollectABag() throws InterruptedException { // execpeção porque estava a dar erro por causa do await
        lock.lock();
        try{
            if(count == 0){
                missing.await(); //tem que esperar que a mala seja encontrada
            }
            else{
                count --;
                missing.signal(); //notifica quando o numero de malas for superior a 0 para que volte a tentar ver se a mala apareceu. TODO: Dar um id a malas e depois verificar???
            }
        }finally {
            lock.unlock();
        }
    }

    public void goHome(){

    }
}

public class BaggageReclaim {

    private boolean reclaimBag;

    public BaggageReclaim(boolean reclaimBag){
        this.reclaimBag = reclaimBag;
    }

    // saber se uma mala foi reclamada ou nao
    public boolean getInfoReclaimBag(){
        if (reclaimBag == true){
            return true;
        }
        return false;
    }

    public void goHome(){

    }
}

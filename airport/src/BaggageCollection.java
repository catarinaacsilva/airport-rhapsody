public class BaggageCollection {
    /**
     * Foi buscar a mala e ja a tem
     */
    private boolean hasBag;

    public BaggageCollection(boolean hasBag){
        this.hasBag = hasBag;
    }

    public boolean getInfoBag(){
        if (hasBag == true){
            return true;
        }
        return false;
    }

    public void goCollectABag(){

    }

    public void reportMissingBags(){

    }

    public void goHome(){

    }
}

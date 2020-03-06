public class Bag {
    /**
     * Identificador da mala
     */
    public int id;

    /**
     * Fornece informação de se esta mala vai ser recolhida pelo utilizador ou se vai entrar novamente no avião (escala)
     */
    public int transitOrNot;

    public Bag (final int id, final int transitOrNot){
        this.id = id;
        this.transitOrNot = transitOrNot;
    }
}

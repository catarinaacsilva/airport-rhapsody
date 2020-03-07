package pt.ua.deti;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GeneralRepositoryInformation {
    private final int K, N, M, T;
    private final double P;

    public GeneralRepositoryInformation() {
        this("config.properties");
    }

    public GeneralRepositoryInformation(final String propFileName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        Properties prop = new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        K = Integer.parseInt(prop.getProperty("K"));
        N = Integer.parseInt(prop.getProperty("M"));
        M = Integer.parseInt(prop.getProperty("N"));
        T = Integer.parseInt(prop.getProperty("T"));
        P = Double.parseDouble(prop.getProperty("P"));
    }

    public final int planeLandings(){
        return K;
    }

    public final int numberPassengers(){
        return N;
    }

    public final int piecesLuggage(){
        return M;
    }

    public final int busSeatingPlaces(){
        return T;
    }

    public final double missingBagProbability(){
        return P;
    }

    @Override
    public final String toString() {
        return String.format("planeLandings %d%nnumberPassengers %d%npiecesLuggage %d%nbusSeatingPlaces %d%nmissingBagProbability %f%n",
                K, N, M, T, P);
    }
}

package pt.ua.deti;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class GeneralRepositoryInformation {
    private final int K, N, M, T;
    private final double P;
    private final String L;
    private final List<Passenger> passengers;
    private final Porter porter;
    private final BusDriver busDriver;


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
        N = Integer.parseInt(prop.getProperty("N"));
        M = Integer.parseInt(prop.getProperty("M"));
        T = Integer.parseInt(prop.getProperty("T"));
        P = Double.parseDouble(prop.getProperty("P"));
        L = prop.getProperty("L");
        
        //TODO: Fix me....
        passengers = null;
        porter = null;
        busDriver = null;
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

    public final String logFile() {
        return L;
    }

    public final List<Passenger> getPassengers() {
        return passengers;
    }

    public final Porter getPorter() {
        return porter;
    }

    public final BusDriver getBusDriver() {
        return busDriver;
    }

    @Override
    public final String toString() {
        return String.format("planeLandings %d%nnumberPassengers %d%npiecesLuggage %d%nbusSeatingPlaces %d%nmissingBagProbability %f%n",
                K, N, M, T, P);
    }
}

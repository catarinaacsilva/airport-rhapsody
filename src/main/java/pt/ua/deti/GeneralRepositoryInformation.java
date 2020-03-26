package pt.ua.deti;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * General Repository of Information
 * <p>
 * This will work as a global repository for every entity in the simulation
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class GeneralRepositoryInformation {
    protected final int K, N, M, T;
    protected final double P;
    protected final String L;
    /** {@link List} of the planes */
    protected final List<Plane> planes;
    protected final Porter porter;
    protected final BusDriver busDriver;
    protected final PlaneHold planeHold;
    protected final ArrivalLounge arrivalLounge;


    /**
     * Creates a GeneralRepositoryInformation
     * <p>
     * It loads the "config.properties" in the resources folder.
     */
    public GeneralRepositoryInformation() {
        this("config.properties");
    }

    /**
     * Creates a GeneralRepositoryInformation
     * <p>
     * It loads the configuration file from the resources folder.
     * @param filename the filename of the configuration file
     */
    public GeneralRepositoryInformation(final String filename) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
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
        planes = null;
        porter = null;
        busDriver = null;
        planeHold = null;
        arrivalLounge = null;
    }
}

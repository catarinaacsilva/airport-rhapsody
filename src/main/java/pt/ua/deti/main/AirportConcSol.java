package pt.ua.deti.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import pt.ua.deti.common.Bag;
import pt.ua.deti.common.Plane;
import pt.ua.deti.entities.BusDriver;
import pt.ua.deti.entities.Passenger;
import pt.ua.deti.entities.Porter;
import pt.ua.deti.shared.ArrivalLounge;
import pt.ua.deti.shared.ArrivalTerminalExit;
import pt.ua.deti.shared.BaggageCollectionPoint;
import pt.ua.deti.shared.BaggageReclaimOffice;
import pt.ua.deti.shared.DepartureTerminalEntrance;
import pt.ua.deti.shared.GeneralRepositoryInformation;
import pt.ua.deti.shared.PlaneHold;
import pt.ua.deti.shared.TemporaryStorageArea;

/**
 * Main execution program.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class AirportConcSol {
    public static void main(final String[] args) throws IOException {
        // Read the configuration file
        final Properties prop = new Properties();
        final InputStream stream = AirportConcSol.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            prop.load(stream);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        final int K = Integer.parseInt(prop.getProperty("K"));
        final int N = Integer.parseInt(prop.getProperty("N"));
        final int M = Integer.parseInt(prop.getProperty("M"));
        final int T = Integer.parseInt(prop.getProperty("T"));
        final double P = Double.parseDouble(prop.getProperty("P"));
        final String L = prop.getProperty("L");

        // Create the Information Sharing Regions
        final GeneralRepositoryInformation gri = new GeneralRepositoryInformation(L);
        final ArrivalLounge al = new ArrivalLounge(N);
        final PlaneHold pl = new PlaneHold(gri);
        final BaggageCollectionPoint bcp = new BaggageCollectionPoint(gri);
        final BaggageReclaimOffice bro = new BaggageReclaimOffice();
        final TemporaryStorageArea tsa = new TemporaryStorageArea(gri);
        final ArrivalTerminalExit ate = new ArrivalTerminalExit(N);
        final DepartureTerminalEntrance dte = new DepartureTerminalEntrance(N);

        ate.setDTE(dte);
        dte.setATE(ate);

        // Write initial line of the log
        gri.writeHeader();

        // Create the list of planes
        final List<Plane> planes = createPlanes(K, N, M, P, gri, al, bcp, bro, ate);

        // Start porter
        final Porter porter = new Porter(al, pl, bcp, tsa, gri);
        final Thread tporter = new Thread(porter);
        tporter.start();

        // Start bus driver
        final BusDriver busDriver = new BusDriver(gri);
        final Thread tbusdriver = new Thread(busDriver);
        tbusdriver.start();

        // Get a list of planes
        for (int i = 0; i < planes.size(); i++) {
            Plane plane = planes.get(i);
            // Update the plane information
            gri.updatePlane(plane.fn(), plane.bn());
            // Reset Arrival lounge
            al.reset();
            // Load the Plane Hold
            pl.loadBags(plane.getBags(), (i + 1) == planes.size());
            // Star the passengers of the plane
            final List<Passenger> passengers = plane.getPassengers();
            final List<Thread> tpassengers = new ArrayList<>(passengers.size());
            for (final Passenger p : passengers) {
                final Thread t = new Thread(p);
                tpassengers.add(t);
                t.start();
            }

            // Wait for the passengers to finnish them lifecycle
            try {
                for (final Thread t : tpassengers) {
                    t.join();
                }
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wait for all entities
        try {
            tbusdriver.join();
            tporter.join();

        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

        gri.writeReport();
        gri.close();
    }

    /**
     * Create a {@link List} of {@link Plane} for the simulation.
     * 
     * @param K   the number of {@link Plane}
     * @param N   the number of {@link Passenger} per {@link Plane}
     * @param M   the maximum number of {@link Bag} per {@link Passenger}
     * @param P   the probability of losing a bag in the trip
     * @param gri {@link GeneralRepositoryInformation}
     */
    private static List<Plane> createPlanes(final int K, final int N, final int M, final double P,
            final GeneralRepositoryInformation gri, final ArrivalLounge al, final BaggageCollectionPoint bcp,
            final BaggageReclaimOffice bro, final ArrivalTerminalExit ate) {
        final ArrayList<Plane> planes = new ArrayList<>(K);
        int globalBagId = 0;

        for (int i = 0; i < K; i++) {
            List<Passenger> passengers = new ArrayList<>();
            List<Bag> bags = new ArrayList<>();

            for (int j = 0; j < N; j++) {
                List<Integer> bagIds = new ArrayList<>();
                int numberBags = ThreadLocalRandom.current().nextInt(1, M + 1);

                boolean transit = getRandomBoolean(.5);
                for (int k = 0; k < numberBags; k++) {

                    bagIds.add(globalBagId);
                    if (getRandomBoolean(1.0 - P)) {
                        bags.add(new Bag(globalBagId, transit));
                    }
                    globalBagId++;
                }

                Passenger passenger = new Passenger((j + 1), bagIds, transit, al, bcp, bro, ate, gri);
                passengers.add(passenger);
            }

            Plane plane = new Plane((i + 1), passengers, bags);
            planes.add(plane);
        }

        return planes;
    }

    /**
     * Returns true if the random value is less than the probability; otherwise
     * false.
     * 
     * @param probability the probability of returning True
     * @return true if the random value is less than the probability; otherwise
     *         false
     */
    private static boolean getRandomBoolean(double probability) {
        return ThreadLocalRandom.current().nextDouble() <= probability;
    }
}
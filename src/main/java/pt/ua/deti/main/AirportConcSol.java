package pt.ua.deti.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.ua.deti.shared.GeneralRepositoryInformation;

/**
 * Main execution program.
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class AirportConcSol {
    public static void main(String[] args) throws IOException {
        GeneralRepositoryInformation gri = new GeneralRepositoryInformation("test.log");

        // Write initial line of the log
        gri.writeHeader();
        gri.writeLine();

        /*// Start porter
        Porter porter = gri.porter;
        Thread tporter = new Thread(porter);
        tporter.start();

        // Start bus driver
        BusDriver busDriver = gri.busDriver;
        Thread tbusdriver = new Thread(busDriver);
        tbusdriver.start();

        // Get a list of planes
        List<Plane> planes = gri.planes;
        for (Plane plane : planes) {
            // Star the passengers of the plane
            List<Passenger> passengers = plane.getPassengers();
            List<Thread> tpassengers = new ArrayList<>(passengers.size());
            for (Passenger p : passengers) {
                Thread t = new Thread(p);
                tpassengers.add(t);
                t.start();
            }

            // Wait for the passengers to finnish them lifecycle
            try {
                for (Thread t : tpassengers) {
                    t.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wait for all entities
        try {
            tbusdriver.join();
            tporter.join();
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        gri.writeReport();
        gri.close();
    }
}

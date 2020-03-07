package pt.ua.deti;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GeneralRepositoryInformation gri = new GeneralRepositoryInformation();
        System.out.println(gri);
        Logger log = new Logger(gri);

        // Write initial line of the log
        log.write();

        // Star all passenger
        List<Passenger> passengers = gri.getPassenger();
        List<Thread> tpassengers = new ArrayList<>(passengers.size());
        for(Passenger p : passengers) {
            Thread t = new Thread(p);
            tpassengers.add(t);
            t.start();
        }

        // Start porter
        Porter porter = gri.getPorter();
        Thread tporter = new Thread(porter);
        tporter.start();

        // Start bus driver
        BusDriver busDriver = gri.getBusDriver();
        Thread tbusdriver = new Thread(busDriver);
        tbusdriver.start();

        // Wait for all entities
        try {
            tbusdriver.join();
            tporter.join();
            for (Thread t : tpassengers) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.write_report();
    }
}

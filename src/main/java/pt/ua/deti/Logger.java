package pt.ua.deti;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Logger {

    private final GeneralRepositoryInformation gri;
    private final BufferedWriter writer;

    public Logger(final GeneralRepositoryInformation gri) throws IOException {
        this.gri = gri;
        final Path path = Paths.get(gri.logFile());
        writer = Files.newBufferedWriter(path);
    }

    public synchronized void init() throws IOException {
        // First Line
        int space = 2 + (4 + 3 * (gri.numberPassengers() + gri.busSeatingPlaces())) / 2;
        final StringBuilder sb = new StringBuilder();
        
        sb.append("PLANE    PORTER");
        for(int i = 0; i < space; i++) {
            sb.append(' ');
        }
        sb.append("DRIVER");
        
        final String str0 = sb.toString();
        
        // Second Line
        sb.setLength(0);
        sb.append("FN BN  Stat CB SR   Stat ");
        for (int i = 0; i < gri.numberPassengers(); i++) {
            sb.append("Q" + (i + 1) + " ");
        }
        for (int i = 0; i < gri.busSeatingPlaces(); i++) {
            sb.append("S" + (i + 1) + " ");
        }
        final String str1 = sb.toString();

        // Third line
        space = 3 * (gri.numberPassengers() + gri.busSeatingPlaces()) + 30;
        sb.setLength(0);
        for(int i = 0; i < space; i++) {
            sb.append(' ');
        }
        sb.append("PASSENGERS");
        String str2 = sb.toString();

        // Fourth line
        sb.setLength(0);
        for (int i = 0; i < gri.numberPassengers(); i++) {
            sb.append(String.format("St%d Si%d NR%d NA%d ", (i+1), (i+1), (i+1), (i+1)));
        }
        String str4 = sb.toString();

        // Complete the header
        final String str = String.format("%s%n%s%n%s%n%s", str0, str1, str2, str4);
        
        // Ouput the header
        System.out.println(str);
        writer.append(str);
        writer.flush();
    }

    /**
     * TODO: Como imprimir o log por causa dos aviões
     */
    public synchronized void write() {

    }

    /**
     *
     */
    public synchronized void write_report() {

    }
}

package pt.ua.deti.shared;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * General Repository of Information
 * <p>
 * The General Repository of Information works solely as the place where the
 * visible internal state of the problem is stored. The visible internal state
 * is defined DETI by the set of variables whose value is printed in the logging
 * file.
 * </p>
 * <p>
 * Whenever an entity (porter, passenger, bus driver) executes an operation that
 * changes the values of some of these variables, the fact must be reported so
 * that a new line group is printed in the logging file. The report operation
 * must be atomic, that is, when two or more variables are changed, the report
 * operation must be unique so that the new line group reflects all the changes
 * that have taken place.
 * </p>
 * 
 * @author Catarina Silva
 * @author Duarte Dias
 * @version 1.0
 */
public class GeneralRepositoryInformation implements Closeable {
    /** Flight number */
    private int fn;
    /** Number of pieces of luggage presently at the plane's hold */
    private int bn;
    /** State of the porter */
    private int pstat;
    /** Number of pieces of luggage presently on the conveyor belt */
    private int cb;
    /**
     * number of pieces of luggage belonging to passengers in transit presently
     * stored at the storeroom
     */
    private int sr;
    /** State of the driver */
    private int bstat;
    /** Occupation state for the waiting queue (passenger id / - (empty)) */
    private int[] q = new int[6];
    /** Occupation state for seat in the bus (passenger id / - (empty)) */
    private int[] s = new int[3];
    /** State of passenger # (# - 0 .. 5) */
    private int[] st = new int[6];
    /**
     * Situation of passenger # (# - 0 .. 5) – TRT (in transit) / FDT (has this
     * airport as her final destination)
     */
    private int[] si = new int[6];
    /**
     * Number of pieces of luggage the passenger # (# - 0 .. 5) carried at the start
     * of her journey
     */
    private int[] nr = new int[6];
    /**
     * number of pieces of luggage the passenger # (# - 0 .. 5) she has presently
     * collected
     */
    private int[] na = new int[6];
    /** {@link BufferedWriter} used to write on a file */
    private PrintWriter writer;
    /** Number of passengers which have this airport as their final destination */
    private int nFDT;
    /** Number of passengers in transit */
    private int nTRT;
    /** Number of bags that should have been transported in the the planes hold */
    private int nBags;
    /** Number of bags that were lost */
    private int nMissing;

    /**
     * Creates a {@link GeneralRepositoryInformation}
     * 
     * @param filename the filename to the log file
     */
    public GeneralRepositoryInformation(final String filename) {
        Arrays.fill(st, -1);
        Arrays.fill(si, -1);
        Arrays.fill(nr, -1);
        Arrays.fill(na, -1);

        try {
            writer = new PrintWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the initial header.
     */
    public void writeHeader() {
        // Create the text for all the lines
        final String str0 = "PLANE    PORTER                  DRIVER";
        final String str1 = "FN BN  Stat CB SR   Stat  Q1 Q2 Q3 Q4 Q5 Q6  S1 S2 S3";
        final String str2 = "                                                             PASSENGERS";
        final String str3 = "St1 Si1 NR1 NA1 St2 Si2 NR2 NA2 St3 Si3 NR3 NA3 St4 Si4 NR4 NA4 St5 Si5 NR5 NA5 St6 Si6 NR6 NA6";

        // Complete the header
        final String str = String.format("%s%n%s%n%s%n%s", str0, str1, str2, str3);

        // Ouput the header
        System.out.println(str);
        writer.println(str);
    }

    /**
     * Write one line.
     */
    public void writeLine() {
        // format the first line (Porter and Bus Driver)
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%2d ", fn));
        sb.append(String.format("%2d  ", bn));
        sb.append(String.format("%s ", pState2str(pstat)));
        sb.append(String.format("%2d ", cb));
        sb.append(String.format("%2d   ", sr));
        sb.append(String.format("%s  ", bState2str(bstat)));
        for (int i = 0; i < q.length; i++) {
            if (q[i] > 0) {
                sb.append(String.format("%2d ", q[i]));
            } else {
                sb.append(" - ");
            }
        }
        sb.append(" ");
        for (int i = 0; i < s.length; i++) {
            if (s[i] > 0) {
                sb.append(String.format("%2d ", s[i]));
            } else {
                sb.append(" - ");
            }
        }
        final String str0 = sb.toString();

        // format the second line (Passenger)
        sb.setLength(0);
        for (int i = 0; i < st.length; i++) {
            sb.append(String.format("%s ", st2str(st[i])));
            sb.append(String.format("%s ", situation2str(si[i])));
            if (nr[i] >= 0) {
                sb.append(String.format("%2d  ", nr[i]));
            } else {
                sb.append(" -  ");
            }
            if (na[i] >= 0) {
                sb.append(String.format("%2d  ", na[i]));
            } else {
                sb.append(" -  ");
            }
        }
        final String str1 = sb.toString();

        // Complete the lines
        final String str = String.format("%s%n%s", str0, str1);

        // Ouput the lines
        System.out.println(str);
        writer.println(str);
    }

    /**
     * Write the final report.
     */
    public void writeReport() {
        final String str0 = String.format("N. of passengers which have this airport as their final destination = %2d",
                nFDT);
        final String str1 = String.format("N. of passengers in transit = %2d", nTRT);
        final String str2 = String.format("N. of bags that should have been transported in the the planes hold = %2d",
                nBags);
        final String str3 = String.format("N. of bags that were lost = %2d", nMissing);

        final String str = String.format("%nFinal report%n%s%n%s%n%s%n%s", str0, str1, str2, str3);

        // Ouput the lines
        System.out.println(str);
        writer.println(str);
    }

    /**
     * Update the number of passengers which have this airport as their final
     * destination.
     * 
     * @param inc the amount to increase
     */
    public synchronized void updateTRT(final int inc) {
        nTRT += inc;
    }

    /**
     * Update the number of passengers in transit.
     * 
     * @param inc the amount to increase
     */
    public synchronized void updateFDT(final int inc) {
        nFDT += inc;
    }

    /**
     * Update de number of bags that should have been transported in the the planes
     * hold.
     * 
     * @param inc the amount to increase
     */
    public synchronized void updateBags(final int inc) {
        nBags += inc;
    }

    /**
     * Update de number of bags that were lost.
     * 
     * @param inc the amount to increase
     */
    public synchronized void updateMissing(final int inc) {
        nMissing += inc;
    }

    /**
     * Update the {@link Plane} state.
     * 
     * @param fn flight number
     * @param bn number of pieces of luggage presently at the plane's hold
     */
    public synchronized void updatePlane(final int fn, final int bn) {
        this.fn = fn;
        this.bn = bn;
        writeLine();
    }

    /**
     * Update the {@link PlaneHold} state.
     * 
     * @param bn number of pieces of luggage presently at the plane's hold
     */
    public synchronized void updatePlaneHold(final int bn) {
        this.bn = bn;
        writeLine();
    }

    /**
     * Update the {@link Porter} state.
     * 
     * @param pstat the {@link Porter} state
     */
    public synchronized void updatePStat(final int pstat) {
        this.pstat = pstat;
        writeLine();
    }

    /**
     * Update the number of pieces of luggage presently on the conveyor belt.
     * 
     * @param cb number of pieces of luggage presently on the conveyor belt
     */
    public synchronized void updateConveyorBelt(final int cb) {
        this.cb = cb;
        writeLine();
    }

    /**
     * Update the number of pieces of luggage belonging to passengers in transit
     * presently stored at the storeroom.
     * 
     * @param sr number of pieces of luggage belonging to passengers in transit
     *           presently stored at the storeroom
     */
    public synchronized void updateStoreroom(final int sr) {
        this.sr = sr;
        writeLine();
    }

    /**
     * Update the {@link Passenger} information
     * 
     * @param id        the identification of the {@link Passenger}
     * @param state     the new state of the {@link Passenger}
     * @param situation the situation of the {@link Passenger}
     * @param bags      number of pieces of luggage the {@link Passenger} carried at
     *                  the start of her journey
     * @param collected number of pieces of luggage the {@link Passenger} she has
     *                  presently collected
     */
    public synchronized void updatePassenger(final int id, final int state, final int situation, final int bags,
            final int collected) {
        st[id - 1] = state;
        si[id - 1] = situation;
        nr[id - 1] = bags;
        na[id - 1] = collected;
        writeLine();
    }

    /**
     * Returns the State of the {@link Porter} converted into a {@link String}
     * 
     * @param state State of the {@link Porter}
     * @return the State of the {@link Porter} converted into a {@link String}
     */
    private static String pState2str(final int state) {
        switch (state) {
            case 0:
                return "WPTL";
            case 1:
                return "APLH";
            case 2:
                return "ALCB";
            case 3:
                return "ASTR";
            default:
                return "WPTL";
        }
    }

    /**
     * Returns the State of the {@link BusDriver} converted into a {@link String}
     * 
     * @param state State of the {@link BusDriver}
     * @return the State of the {@link BusDriver} converted into a {@link String}
     */
    private static String bState2str(final int state) {
        switch (state) {
            case 0:
                return "PKAT";
            case 1:
                return "DRFW";
            case 2:
                return "PKDT";
            case 3:
                return "DRBW";
            default:
                return "PKAT";
        }
    }

    /**
     * Returns the State of the {@link Passenger} converted into a {@link String}
     * 
     * @param state State of the {@link Passenger}
     * @return the State of the {@link Passenger} converted into a {@link String}
     */
    public static String st2str(final int state) {
        switch (state) {
            case 0:
                return "WSD";
            case 1:
                return "LCP";
            case 2:
                return "BRO";
            case 3:
                return "EAT";
            case 4:
                return "ATT";
            case 5:
                return "TRT";
            case 6:
                return "DTT";
            case 7:
                return "EDT";
            default:
                return "---";
        }
    }

    /**
     * Returns the situation of the {@link Passenger} converted into a
     * {@link String}
     * 
     * @param situaion situation of the {@link Passenger}
     * @return the situation of the {@link Passenger} converted into a
     *         {@link String}
     */
    private static String situation2str(final int situaion) {
        switch (situaion) {
            case 0:
                return "TRT";
            case 1:
                return "FDT";
            default:
                return "---";
        }
    }

    @Override
    public void close() {
        writer.close();
    }
}

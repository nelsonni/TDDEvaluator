import TDDSessionsLibrary.Session;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/15/14.
 */
public class SessionComparison {

    public Session s1;
    public Session s2;
    private DecimalFormat df;

    public SessionComparison(Session session1, Session session2) {
        s1 = session1;
        s2 = session2;
        df = new DecimalFormat("0.00%");
    }

    public double compareTimelines() {
        int size = (s1.numEvents() > s2.numEvents()) ? s1.numEvents() : s2.numEvents();
        int match = 0;

        for (int i = 0; i < size; i++) {
            if (i >= s1.numEvents()) {
                System.out.println("--EVENT MISMATCH (INDEX:" + i + ")--");
                System.out.println("Session 1:\tNO EVENT");
                System.out.println("Session 2:\t" + s2.getEvent(i).toString());
            }
            else if (i >= s2.numEvents()) {
                System.out.println("--EVENT MISMATCH (INDEX:" + i + ")--");
                System.out.println("Session 1:\t" + s1.getEvent(i).toString());
                System.out.println("Session 2:\tNO EVENT");
            }
            else if (s1.getEvent(i).equals(s2.getEvent(i))) {
                match++;
            }
            else {
                System.out.println("--EVENT MISMATCH (INDEX:" + i + ")--");
                System.out.println("Session 1:\t" + s1.getEvent(i).toString());
                System.out.println("Session 2:\t" + s2.getEvent(i).toString());
            }
        }

        System.out.print(match + " of " + size + " events (");
        System.out.print(df.format((double) match / (double) size));
        System.out.println(") match");
        System.out.println();

        return (double) match / (double) size;
    }

    public double compareCycles() {
        int size = (s1.numCycles() > s2.numCycles()) ? s1.numCycles() : s2.numCycles();
        int match = 0;

        for (int i = 0; i < size; i++) {
            if (i >= s1.numCycles()) {
                System.out.println("--CYCLE MISMATCH (INDEX:" + i + ")--");
                System.out.println("Session 1:\tNO CYCLE");
                System.out.println("Session 2:\t" + s2.getCycle(i).toString());
            }
            else if (i >= s2.numCycles()) {
                System.out.println("--CYCLE MISMATCH (INDEX:" + i + ")--");
                System.out.println("Session 1:\t" + s1.getCycle(i).toString());
                System.out.println("Session 2:\tNO CYCLE");
            }
            else if (s1.getCycle(i).equals(s2.getCycle(i))) {
                match++;
            }
            else {
                System.out.println("--CYCLE MISMATCH (INDEX:" + i + ")--");
                System.out.println("Session 1:\t" + s1.getCycle(i).toString());
                System.out.println("Session 2:\t" + s2.getCycle(i).toString());
            }
        }

        System.out.print(match + " of " + size + " cycles (");
        System.out.print(df.format((double) match / (double) size));
        System.out.println(") match");
        System.out.println();

        return (double) match / (double) size;
    }

    public double comparePhases() {
        int cycleListSize = (s1.numCycles() > s2.numCycles()) ? s1.numCycles() : s2.numCycles();
        int match = 0;
        int size = 0;

        for (int i = 0; i < cycleListSize; i++) {
            if (i >= s1.numCycles()) {
                System.out.println("--CYCLE MISMATCH (INDEX:" + i + ")--");
                System.out.println("Session 1:\tNO CYCLE");
                System.out.println("Session 2:\t" + s2.getCycle(i).toString());
            }
            else if (i >= s2.numCycles()) {
                System.out.println("--CYCLE MISMATCH (INDEX:" + i + ")--");
                System.out.println("Session 1:\t" + s1.getCycle(i).toString());
                System.out.println("Session 2:\tNO CYCLE");
            }
            else {
                int phaseListSize = (s1.getCycle(i).size() > s2.getCycle(i).size()) ? s1.getCycle(i).size() : s2.getCycle(i).size();

                for (int j = 0; j < phaseListSize; j++) {
                    size++;
                    if (j >= s1.getCycle(i).size()) {
                        System.out.println("--PHASE MISMATCH (CYCLE: " + i + ", INDEX: " + j +")--");
                        System.out.println("Session 1:\tNO PHASE");
                        System.out.println("Session 2:\t" + s2.getCycle(i).get(j).toString());
                    }
                    else if (j >= s2.getCycle(i).size()) {
                        System.out.println("--PHASE MISMATCH (CYCLE: " + i + ", INDEX: " + j +")--");
                        System.out.println("Session 1:\t" + s1.getCycle(i).get(j).toString());
                        System.out.println("Session 2:\tNO PHASE");
                    }
                    else if (s1.getCycle(i).get(j).equals(s2.getCycle(i).get(j))) {
                        match++;
                    }
                }
            }
        }

        System.out.print(match + " of " + size + " phases (");
        System.out.print(df.format((double) match / (double) size));
        System.out.println(") match");
        System.out.println();

        return (double) match / (double) size;
    }

}

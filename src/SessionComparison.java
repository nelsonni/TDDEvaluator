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

    public double compareEvents() {
        int additions = Math.abs(s1.numEvents()-s2.numEvents());
        int limit = (s1.numEvents() < s2.numEvents()) ? s1.numEvents() : s2.numEvents();
        int correct = 0;

        for (int i = 0; i < limit; i++) {
            if (s1.getEvent(i).equals(s2.getEvent(i))) {
                correct++;
            }
            else {
                System.out.println("--MISMATCH--");
                System.out.println(s1.getEvent(i).toString());
                System.out.println("  -vs-  ");
                System.out.println(s2.getEvent(i).toString());
            }
        }

        System.out.print(correct + " of " + (limit + additions) + " events (");
        System.out.print(df.format((double) correct / (double) (limit + additions)));
        System.out.println(") match");

        return (double) correct / (double) (limit + additions);
    }

    public double compareCycles() {
        int additions = Math.abs(s1.numCycles() - s2.numCycles());
        int limit = (s1.numCycles() < s2.numCycles()) ? s1.numCycles() : s2.numCycles();
        int correct = 0;

        for (int i = 0; i < limit; i++) {
            if (s1.getCycle(i).equals(s2.getCycle(i))) {
                correct++;
            }
            else {
                System.out.println("--MISMATCH--");
                System.out.println(s1.getCycle(i).toString());
                System.out.println("  -vs-  ");
                System.out.println(s2.getCycle(i).toString());
            }
        }

        System.out.print(correct + " of " + (limit + additions) + " cycles (");
        System.out.print(df.format((double) correct / (double) (limit + additions)));
        System.out.println(") match");

        return (double) correct / (double) (limit + additions);
    }

    public double comparePhases() {
        int cAdditions = Math.abs(s1.numCycles() - s2.numCycles());
        int cLimit = (s1.numCycles() < s2.numCycles()) ? s1.numCycles() : s2.numCycles();
        int correct = 0;
        int total = 0;

        for (int i = 0; i < cLimit; i++) {
            int pAdditions = Math.abs(s1.getCycle(i).size() - s2.getCycle(i).size());
            int pLimit = (s1.getCycle(i).size() < s2.getCycle(i).size()) ? s1.getCycle(i).size() : s2.getCycle(i).size();

            for (int j = 0; j < pLimit; j++) {
                total++;
                if (s1.getCycle(i).get(j).equals(s2.getCycle(i).get(j))) {
                    correct++;
                }
                else {
                    System.out.println("--MISMATCH--");
                    System.out.println(s1.getCycle(i).get(j).toString());
                    System.out.println("  -vs-  ");
                    System.out.println(s2.getCycle(i).get(j).toString());
                }
            }
            total += pAdditions;
        }

        if (s1.numCycles() < s2.numCycles()) {
            for (int i = cLimit; i < (cLimit + cAdditions); i++) {
                total += s2.getCycle(i).size();
            }
        }
        else {
            for (int i = cLimit; i < (cLimit + cAdditions); i++) {
                total += s1.getCycle(i).size();
            }
        }

        System.out.print(correct + " of " + (total) + " phases (");
        System.out.print(df.format((double) correct / (double) total));
        System.out.println(") match");

        return (double) correct / (double) total;
    }

}

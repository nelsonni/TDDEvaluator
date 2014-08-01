import TDDSessionsLibrary.Event;
import TDDSessionsLibrary.Session;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/15/14.
 */
public class SessionComparison {

    public Session s1;
    public Session s2;

    public SessionComparison(Session session1, Session session2) {
        s1 = session1;
        s2 = session2;
    }

    public double getEventPercentage() {
        Event e1 = s1.getCycle(0).getEvent(0);
        Event e2 = s1.getCycle(0).getEvent(3);

        System.out.println("event 1:");
        System.out.println(e1.toString());
        System.out.println("event 2:");
        System.out.println(e2.toString());

        System.out.println("comparison result: " + e1.equals(e2));

        return 0.00;
    }

}

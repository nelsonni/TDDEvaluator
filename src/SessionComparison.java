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

    public double eventPercentage() {
        int correct = 0;
        int incorrect = 0;

        System.out.println("s1 session:");

        for (int i = 0; i < s1.numEvents(); i++) {
            if (s1.getEvent(i).equals(s2.getEvent(i))) {
                correct++;
            }
            else {
                incorrect++;
            }
        }

        System.out.println("correct: " + correct + ", incorrect: " + incorrect);
        double percentage = correct / (correct + incorrect);

        return percentage;
    }

}

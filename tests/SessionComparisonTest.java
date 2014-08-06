import TDDSessionsLibrary.Event;
import TDDSessionsLibrary.FileIO;
import TDDSessionsLibrary.Session;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class SessionComparisonTest {

    Session s1;
    Session s2;

    @Before
    public void setUp() throws Exception {
        Path eventFile = getEmptyTempFile();
        Path phaseFile = getEmptyTempFile();

        // prepare phases file for processing
        String phaseSet = "[{\"CycleType\":\"red\",\"CycleEnd\":\"344\",\"id\":\"0344\",\"CycleStart\":\"0\"},\n" +
                "{\"CycleType\":\"green\",\"CycleEnd\":\"381\",\"id\":\"345381\",\"CycleStart\":\"345\"},\n" +
                "{\"CycleType\":\"blue\",\"CycleEnd\":\"408\",\"id\":\"382408\",\"CycleStart\":\"382\"},\n" +
                "{\"CycleType\":\"red\",\"CycleEnd\":\"496\",\"id\":\"409496\",\"CycleStart\":\"409\"},\n" +
                "{\"CycleType\":\"green\",\"CycleEnd\":\"710\",\"id\":\"497710\",\"CycleStart\":\"497\"},\n" +
                "{\"CycleType\":\"red\",\"CycleEnd\":\"795\",\"id\":\"711795\",\"CycleStart\":\"711\"},\n" +
                "{\"CycleType\":\"green\",\"CycleEnd\":\"1038\",\"id\":\"7961038\",\"CycleStart\":\"796\"},\n" +
                "{\"CycleType\":\"blue\",\"CycleEnd\":\"1120\",\"id\":\"10391120\",\"CycleStart\":\"1039\"},\n" +
                "{\"CycleType\":\"red\",\"CycleEnd\":\"1168\",\"id\":\"11211168\",\"CycleStart\":\"1121\"},\n" +
                "{\"CycleType\":\"green\",\"CycleEnd\":\"1281\",\"id\":\"11691281\",\"CycleStart\":\"1169\"},\n" +
                "{\"CycleType\":\"red\",\"CycleEnd\":\"1374\",\"id\":\"12821374\",\"CycleStart\":\"1282\"},\n" +
                "{\"CycleType\":\"green\",\"CycleEnd\":\"1904\",\"id\":\"13751904\",\"CycleStart\":\"1375\"},\n" +
                "{\"CycleType\":\"blue\",\"CycleEnd\":\"1947\",\"id\":\"19051947\",\"CycleStart\":\"1905\"}]";
        FileIO.writeToFile(phaseFile.toString(), phaseSet);

        // prepare events file for processing
        List<String> eventLines = new ArrayList<>();
        eventLines.add("[{\"timestamp\":\"1400549103294\",\"text\":\"startingEvent\",\"changeOrigin\":\"user\"},");
        for (int i = 0; i < 2000; i++) {
            eventLines.add("{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"},");
        }
        eventLines.add("{\"timestamp\":\"1400549143894\",\"text\":\"endingEvent\",\"changeOrigin\":\"user\"}]");
        String convertedEventLines = FileIO.arrayToString(eventLines);
        FileIO.writeToFile(eventFile.toString(), convertedEventLines);

        s1 = new Session(eventFile.toString(), phaseFile.toString());
        s2 = new Session(eventFile.toString(), phaseFile.toString());
    }

    @Test
    public void testConstructor() throws Exception {
        SessionComparison comparator = new SessionComparison(s1, s2);
        assertNotNull(comparator);
    }

    @Test
    public void testTotalCorrect() throws Exception {
        SessionComparison comparator = new SessionComparison(s1, s2);

        System.out.println(comparator.eventPercentage());

        s2.add(new Event("{\"timestamp\":\"1902249319894\",\"text\":\"different\",\"changeOrigin\":\"system\"}"));

        System.out.println(comparator.eventPercentage());

        // Because it is not a s1==s2 and s2==s1 comparison, this is passing right now.
        // To fix this issue, an equal() method needs to be implemented for Session objects.

        /*

        Cycle c1 = s2.getCycle(s2.size()-2);
        System.out.println("session: s2, cycle: 2nd to last");
        System.out.println("\tstart: " + c1.start() + ", end: " + c1.end());
        System.out.println("\teventSize: " + c1.eventSize() + ", phaseSize: " + c1.phaseSize());

        Event e1 = c1.getEvent(c1.eventSize()-1);
        System.out.println("session: s2, cycle: 2nd to last, event: last");
        System.out.println(e1.toString());
        System.out.println();

        s2.getCycle(s2.size()-2).addEvent("{\"timestamp\":\"1400549106694\",\"text\":\"other\",\"changeOrigin\":\"sys\"},");

        Cycle c2 = s2.getCycle(s2.size()-2);
        System.out.println("session: s2, cycle: 2nd to last");
        System.out.println("\tstart: " + c2.start() + ", end: " + c2.end());
        System.out.println("\teventSize: " + c2.eventSize() + ", phaseSize: " + c2.phaseSize());

        Event e2 = c2.getEvent(c2.eventSize()-1);
        System.out.println("session: s2, cycle: 2nd to last, event: last");
        System.out.println(e2.toString());

        SessionComparison comparator = new SessionComparison(s1, s2);

        System.out.println("events percentage: " + comparator.eventPercentage());

        //assertEquals(100.00, comparator.totalCorrect());
        */
    }

    // utility method; only used for constructing empty temporary files
    private Path getEmptyTempFile() {
        Path temp = null;

        try {
            temp = Files.createTempFile(null, ".tmp");
            System.out.println("created temporary file: " + temp.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }

}
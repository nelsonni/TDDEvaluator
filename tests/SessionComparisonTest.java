import TDDSessionsLibrary.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    public void testCompareEvents() throws Exception {
        SessionComparison comparator = new SessionComparison(s1, s2);

        Double result = comparator.compareEvents();
        assertEquals(0, result.compareTo(1.0000000000000000));

        s2.add(new Event("{\"timestamp\":\"1902249319894\",\"text\":\"different\",\"changeOrigin\":\"system\"}"));

        result = comparator.compareEvents();
        assertEquals(0, result.compareTo(0.9995007488766849));
    }

    @Test
    public void testCompareCycles() throws Exception {
        SessionComparison comparator = new SessionComparison(s1, s2);

        Double result = comparator.compareCycles();
        assertEquals(0, result.compareTo(1.0000000000000000));

        Phase p1 = new Phase("{\"CycleType\":\"red\",\"CycleEnd\":\"1374\",\"id\":\"12821374\",\"CycleStart\":\"1282\"}");
        Phase p2 = new Phase("{\"CycleType\":\"green\",\"CycleEnd\":\"1904\",\"id\":\"13751904\",\"CycleStart\":\"1375\"}");
        s2.add(new Cycle(p1, p2));

        result = comparator.compareCycles();
        assertEquals(0, result.compareTo(0.8333333333333334));
    }

    @Test
    public void testComparePhases() throws Exception {
        SessionComparison comparator = new SessionComparison(s1, s2);

        Double result = comparator.comparePhases();
        assertEquals(0, result.compareTo(1.0000000000000000));

        Phase p1 = new Phase("{\"CycleType\":\"red\",\"CycleEnd\":\"1374\",\"id\":\"12821374\",\"CycleStart\":\"1282\"}");
        Phase p2 = new Phase("{\"CycleType\":\"green\",\"CycleEnd\":\"1904\",\"id\":\"13751904\",\"CycleStart\":\"1375\"}");
        s2.add(new Cycle(p1, p2));

        result = comparator.comparePhases();
        assertEquals(0, result.compareTo(0.8666666666666667));
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
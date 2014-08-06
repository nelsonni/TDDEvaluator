package TDDSessionsLibrary;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SessionTest {

    @Test
    public void testConstructors() throws Exception {
        Path eventTemp = getEmptyTempFile();
        Path phaseTemp = getEmptyTempFile();

        try {
            Session s1 = new Session();
            Session s2 = new Session(eventTemp.toString(), phaseTemp.toString());
            assertTrue(s1.numCycles() >= 0);
            assertTrue(s1.numEvents() >= 0);
            assertTrue(s2.numCycles() >= 0);
            assertTrue(s2.numEvents() >= 0);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testAddEvent() throws Exception {
        Session s = new Session();
        Event e = new Event("{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}");
        assertTrue(s.add(e));
    }

    @Test
    public void testAddCycle() throws Exception {
        Session s = new Session();
        Phase p1 = new Phase("{\"CycleType\":\"red\",\"CycleStart\":\"0\",\"CycleEnd\":\"10\"}");
        Phase p2 = new Phase("{\"CycleType\":\"green\",\"CycleStart\":\"11\",\"CycleEnd\":\"20\"}");
        Cycle c = new Cycle(p1, p2);
        assertTrue(s.add(c));
    }

    @Test
    public void testAddPhases() throws Exception {
        Session s = new Session();
        Phase p1 = new Phase("{\"CycleType\":\"red\",\"CycleStart\":\"0\",\"CycleEnd\":\"10\"}");
        Phase p2 = new Phase("{\"CycleType\":\"green\",\"CycleStart\":\"11\",\"CycleEnd\":\"20\"}");
        Phase p3 = new Phase("{\"CycleType\":\"blue\",\"CycleStart\":\"21\",\"CycleEnd\":\"35\"}");

        assertTrue(s.add(p1, p2));
        assertTrue(s.add(p1, p2, p3));
    }

    @Test
    public void testNumEvents() throws Exception {
        Session s = new Session();
        assertEquals(0, s.numEvents());

        s.add(new Event("{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}"));
        assertEquals(1, s.numEvents());
    }

    @Test
    public void testNumCycles() throws Exception {
        Session s = new Session();
        assertEquals(0, s.numCycles());

        Phase p1 = new Phase("{\"CycleType\":\"red\",\"CycleStart\":\"0\",\"CycleEnd\":\"10\"}");
        Phase p2 = new Phase("{\"CycleType\":\"green\",\"CycleStart\":\"11\",\"CycleEnd\":\"20\"}");

        s.add(p1, p2);
        assertEquals(1, s.numCycles());
    }

    @Test
    public void testGetCycle() throws Exception {
        Session s = new Session();
        Phase p1 = new Phase("{\"CycleType\":\"red\",\"CycleStart\":\"0\",\"CycleEnd\":\"10\"}");
        Phase p2 = new Phase("{\"CycleType\":\"green\",\"CycleStart\":\"11\",\"CycleEnd\":\"20\"}");
        Cycle c = new Cycle(p1, p2);

        s.add(c);
        assertEquals(c, s.getCycle(0));
    }

    @Test
    public void testGetEvent() throws Exception {
        Session s = new Session();
        Event e = new Event("{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}");

        s.add(e);
        assertEquals(e, s.getEvent(0));
    }

    @Test
    public void testParseFiles() throws Exception {
        Path eventTemp = getEmptyTempFile();
        Path phaseTemp = getEmptyTempFile();

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
        FileIO.writeToFile(phaseTemp.toString(), phaseSet);

        // prepare events file for processing
        List<String> eventLines = new ArrayList<>();
        eventLines.add("[{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"},");
        for (int i = 0; i < 2000; i++) {
            eventLines.add("{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"},");
        }
        eventLines.add("{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}]");
        String convertedEventLines = FileIO.arrayToString(eventLines);
        FileIO.writeToFile(eventTemp.toString(), convertedEventLines);

        // create new session and processing events and phases files into it
        Session s = new Session();
        s.parseFiles(eventTemp.toString(), phaseTemp.toString());
        assertEquals(5, s.numCycles());
    }

    // utility method; only used for constructing temporary files with content
    private Path getTempFile(String data) {
        Path temp = getEmptyTempFile();
        List<String> content = Arrays.asList(data.split("\\r?\\n"));

        for (String s : content) {
            FileIO.writeToFile(temp.toString(), s);
        }

        return temp;
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
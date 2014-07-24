package TDDSessionsLibrary;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SessionTest {

    List<Event> events;
    List<Phase> phases;
    final String oneEvent = "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}";
    final String onePhase = "{\"CycleType\":\"red\",\"CycleStart\":\"32\",\"CycleEnd\":\"41\"}";

    @Before
    public void setUp() throws Exception {
        events = new ArrayList<>();
        phases = new ArrayList<>();
    }

    @Test
    public void testConstructors() throws Exception {
        Path eventTemp = getEmptyTempFile();
        Path phaseTemp = getEmptyTempFile();

        try {
            Session s1 = new Session();
            Session s2 = new Session(eventTemp.toString(), phaseTemp.toString());
            assertTrue(s1.size() >= 0);
            assertTrue(s2.size() >= 0);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testSize() throws Exception {
        Session s1 = new Session();
        assertEquals(0, s1.size());

        Path eventTemp = getTempFile(oneEvent);
        Path phaseTemp = getTempFile(onePhase);
        Session s2 = new Session(eventTemp.toString(), phaseTemp.toString());
        assertEquals(1, s2.size());
    }

    @Test
    public void testAddCycle() throws Exception {
        events.add(new Event(oneEvent));
        phases.add(new Phase(onePhase));

        Session s = new Session();
        assertTrue(s.addCycle(events, phases));
    }

    @Test
    public void testGetCycle() throws Exception {
        events.add(new Event(oneEvent));
        phases.add(new Phase(onePhase));

        Cycle c = new Cycle(events, phases);
        Session s = new Session();

        s.addCycle(events, phases);
        assertEquals(c.eventSize(), s.getCycle(0).eventSize());
        assertEquals(c.phaseSize(), s.getCycle(0).phaseSize());
    }

    @Test
    public void testEXP() throws Exception {
        Path eventTemp = getEmptyTempFile();
        Path phaseTemp = Paths.get("exp.json");

        Session s = new Session();
        s.parseFiles(eventTemp.toString(), phaseTemp.toString());
        assertEquals(5, s.size());
    }

    @Test
    public void testParseFiles() throws Exception {
        Path eventTemp = getEmptyTempFile();
        Path phaseTemp = getEmptyTempFile();

        FileIO.writeToFile(phaseTemp.toString(), "[{\"CycleType\":\"red\",\"CycleEnd\":\"344\",\"id\":\"0344\",\"CycleStart\":\"0\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"green\",\"CycleEnd\":\"381\",\"id\":\"345381\",\"CycleStart\":\"345\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"blue\",\"CycleEnd\":\"408\",\"id\":\"382408\",\"CycleStart\":\"382\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"red\",\"CycleEnd\":\"496\",\"id\":\"409496\",\"CycleStart\":\"409\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"green\",\"CycleEnd\":\"710\",\"id\":\"497710\",\"CycleStart\":\"497\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"red\",\"CycleEnd\":\"795\",\"id\":\"711795\",\"CycleStart\":\"711\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"green\",\"CycleEnd\":\"1038\",\"id\":\"7961038\",\"CycleStart\":\"796\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"blue\",\"CycleEnd\":\"1120\",\"id\":\"10391120\",\"CycleStart\":\"1039\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"red\",\"CycleEnd\":\"1168\",\"id\":\"11211168\",\"CycleStart\":\"1121\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"green\",\"CycleEnd\":\"1281\",\"id\":\"11691281\",\"CycleStart\":\"1169\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"red\",\"CycleEnd\":\"1374\",\"id\":\"12821374\",\"CycleStart\":\"1282\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"green\",\"CycleEnd\":\"1904\",\"id\":\"13751904\",\"CycleStart\":\"1375\"},");
        FileIO.writeToFile(phaseTemp.toString(), "{\"CycleType\":\"blue\",\"CycleEnd\":\"1947\",\"id\":\"19051947\",\"CycleStart\":\"1905\"}]");

        for (int i = 0; i < 1948; i++) {
            if (i == 0) {
                FileIO.writeToFile(phaseTemp.toString(), "[{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"},");
            }
            else if (i == 1947) {
                FileIO.writeToFile(phaseTemp.toString(), "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}]");
            }
            else {
                FileIO.writeToFile(phaseTemp.toString(), "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"},");
            }
        }

        Session s = new Session();
        s.parseFiles(eventTemp.toString(), phaseTemp.toString());

        assertEquals(5, s.size());
    }

    // utility method; only used for constructing temporary files
    private Path getTempFile(String data) {
        Path temp = getEmptyTempFile();
        List<String> content = Arrays.asList(data.split("\\r?\\n"));

        for (String s : content) {
            FileIO.writeToFile(temp.toString(), s);
        }

        return temp;
    }

    // utility method; only used for constructing temporary files
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
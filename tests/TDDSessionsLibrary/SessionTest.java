package TDDSessionsLibrary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SessionTest {

    List<Path> tempFiles;
    List<Event> events;
    List<Phase> phases;
    final String jsonEvent = "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}";
    final String jsonPhase = "{\"CycleType\":\"red\",\"CycleStart\":\"32\",\"CycleEnd\":\"41\"}";

    @Before
    public void setUp() throws Exception {
        tempFiles = new ArrayList<>();
        events = new ArrayList<>();
        phases = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
        for (Path p : tempFiles) {
            p.toFile().delete();
        }
    }

    @Test
    public void testConstructors() throws Exception {
        Path eventTemp = getTempFile(jsonEvent);
        Path phaseTemp = getTempFile(jsonPhase);

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

        Path eventTemp = getTempFile(jsonEvent);
        Path phaseTemp = getTempFile(jsonPhase);
        Session s2 = new Session(eventTemp.toString(), phaseTemp.toString());
        assertEquals(1, s2.size());
    }

    @Test
    public void testAddCycle() throws Exception {
        events.add(new Event(jsonEvent));
        phases.add(new Phase(jsonPhase));

        Session s = new Session();

        assertTrue(s.addCycle(events, phases));
    }

    @Test
    public void testGetCycle() throws Exception {
        events.add(new Event(jsonEvent));
        phases.add(new Phase(jsonPhase));

        Cycle c = new Cycle(events, phases);
        Session s = new Session();

        s.addCycle(events, phases);
        assertEquals(c.eventSize(), s.getCycle(0).eventSize());
        assertEquals(c.phaseSize(), s.getCycle(0).phaseSize());
    }

    @Test
    public void testProcessFiles() throws Exception {
        List<String> cycles = new ArrayList<>();
        cycles.add("[{\"CycleType\":\"red\",\"CycleEnd\":\"344\",\"id\":\"0344\",\"CycleStart\":\"0\"}");
        cycles.add("{\"CycleType\":\"green\",\"CycleEnd\":\"381\",\"id\":\"345381\",\"CycleStart\":\"345\"}");
        cycles.add("{\"CycleType\":\"blue\",\"CycleEnd\":408,\"id\":\"382408\",\"CycleStart\":\"382\"}");
        cycles.add("{\"CycleType\":\"red\",\"CycleEnd\":\"496\",\"id\":\"409496\",\"CycleStart\":\"409\"}");
        cycles.add("{\"CycleType\":\"green\",\"CycleEnd\":\"710\",\"id\":\"497710\",\"CycleStart\":\"497\"}");
        cycles.add("{\"CycleType\":\"red\",\"CycleEnd\":\"795\",\"id\":\"711795\",\"CycleStart\":\"711\"}");
        cycles.add("{\"CycleType\":\"green\",\"CycleEnd\":\"1038\",\"id\":\"7961038\",\"CycleStart\":\"796\"}");
        cycles.add("{\"CycleType\":\"blue\",\"CycleEnd\":1120,\"id\":\"10391120\",\"CycleStart\":\"1039\"}");
        cycles.add("{\"CycleType\":\"red\",\"CycleEnd\":\"1168\",\"id\":\"11211168\",\"CycleStart\":\"1121\"}");
        cycles.add("{\"CycleType\":\"green\",\"CycleEnd\":\"1281\",\"id\":\"11691281\",\"CycleStart\":\"1169\"}");
        cycles.add("{\"CycleType\":\"red\",\"CycleEnd\":\"1374\",\"id\":\"12821374\",\"CycleStart\":\"1282\"}");
        cycles.add("{\"CycleType\":\"green\",\"CycleEnd\":\"1904\",\"id\":\"13751904\",\"CycleStart\":\"1375\"}");
        cycles.add("{\"CycleType\":\"blue\",\"CycleEnd\":\"1947\",\"id\":\"19051947\",\"CycleStart\":\"1905\"}]");

        Path eventTemp = getEmptyTempFile();
        Path phaseTemp = getEmptyTempFile();
        FileIO.writeToFile(phaseTemp.toString(), cycles);

        Session s = new Session();
        s.processFiles(eventTemp.toString(), phaseTemp.toString());
        assertEquals(5, s.size());
    }

    private Path getTempFile(String data) {
        Path temp = getEmptyTempFile();
        List<String> content = Arrays.asList(data.split("\\r?\\n"));

        FileIO.writeToFile(temp.toString(), content);

        return temp;
    }

    private Path getEmptyTempFile() {
        Path temp = null;

        try {
            temp = Files.createTempFile(null, ".tmp");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        tempFiles.add(temp);
        System.out.println("created temporary file: " + temp.toString());

        return temp;
    }

}
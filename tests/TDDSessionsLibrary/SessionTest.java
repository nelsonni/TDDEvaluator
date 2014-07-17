package TDDSessionsLibrary;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.rules.TemporaryFolder;
import static org.junit.Assert.*;

public class SessionTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDefaultConstructor() throws Exception {
        assertTrue(new Session() instanceof Session);
    }

    @Test
    public void testParametersConstructor() throws Exception {
        File file1 = temp.newFile();
        File file2 = temp.newFile();
        assertTrue(new Session(file1.getPath(), file2.getPath()) instanceof Session);
    }

    @Test
    public void testSize() throws Exception {
        Session session = new Session();
        assertEquals(0, session.size());
    }

    @Test
    public void testAddCycle() throws Exception {
        Session session = new Session();
        List<Event> events = new ArrayList<Event>();
        List<Phase> phases = new ArrayList<Phase>();

        session.addCycle(events, phases);
        assertEquals(1, session.size());
    }

    @Test
    public void testGetCycle() throws Exception {
        Session session = new Session();
        List<Event> events = new ArrayList<Event>();
        List<Phase> phases = new ArrayList<Phase>();
        phases.add(new Phase("red", 1, 3));

        session.addCycle(events, phases);
        assertTrue(session.getCycle(0) instanceof Cycle);
    }

    @Test
    public void testProcessFiles() throws Exception {
        File file1 = temp.newFile();
        File file2 = temp.newFile();
        FileWriter fstream = new FileWriter(file1.getName(), true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write("[{\"CycleType\":\"red\",\"CycleEnd\":\"344\",\"id\":\"0344\",\"CycleStart\":\"0\"}");
        out.write("{\"CycleType\":\"green\",\"CycleEnd\":\"381\",\"id\":\"345381\",\"CycleStart\":\"345\"}");
        out.write("{\"CycleType\":\"blue\",\"CycleEnd\":408,\"id\":\"382408\",\"CycleStart\":\"382\"}");
        out.write("{\"CycleType\":\"red\",\"CycleEnd\":\"496\",\"id\":\"409496\",\"CycleStart\":\"409\"}");
        out.write("{\"CycleType\":\"green\",\"CycleEnd\":\"710\",\"id\":\"497710\",\"CycleStart\":\"497\"}");
        out.write("{\"CycleType\":\"red\",\"CycleEnd\":\"795\",\"id\":\"711795\",\"CycleStart\":\"711\"}");
        out.write("{\"CycleType\":\"green\",\"CycleEnd\":\"1038\",\"id\":\"7961038\",\"CycleStart\":\"796\"}");
        out.write("{\"CycleType\":\"blue\",\"CycleEnd\":1120,\"id\":\"10391120\",\"CycleStart\":\"1039\"}");
        out.write("{\"CycleType\":\"red\",\"CycleEnd\":\"1168\",\"id\":\"11211168\",\"CycleStart\":\"1121\"}");
        out.write("{\"CycleType\":\"green\",\"CycleEnd\":\"1281\",\"id\":\"11691281\",\"CycleStart\":\"1169\"}");
        out.write("{\"CycleType\":\"red\",\"CycleEnd\":\"1374\",\"id\":\"12821374\",\"CycleStart\":\"1282\"}");
        out.write("{\"CycleType\":\"green\",\"CycleEnd\":\"1904\",\"id\":\"13751904\",\"CycleStart\":\"1375\"}");
        out.write("{\"CycleType\":\"blue\",\"CycleEnd\":\"1947\",\"id\":\"19051947\",\"CycleStart\":\"1905\"}");
        out.close();

        Session session = new Session();
        session.processFiles(file1.getPath(), file2.getPath());
        assertEquals(5, session.size());
    }

}
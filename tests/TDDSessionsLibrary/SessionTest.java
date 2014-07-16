package TDDSessionsLibrary;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
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
        Session session = new Session();
        assertTrue(session instanceof Session);
    }

    @Test
    public void testParametersConstructor() throws Exception {
        File file1 = temp.newFile();
        File file2 = temp.newFile();
        Session session = new Session(file1.getName(), file2.getName());
        assertTrue(session instanceof Session);
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

        session.addCycle(events, phases);
        assertTrue(session.getCycle(0) instanceof Cycle);
    }
}
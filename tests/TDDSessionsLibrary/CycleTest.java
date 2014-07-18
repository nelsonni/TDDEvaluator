package TDDSessionsLibrary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CycleTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDefaultConstructor() throws Exception {
        Cycle cycle = new Cycle();
        assertTrue(cycle instanceof Cycle);
    }

    @Test
    public void testParameterConstructor() throws Exception {
        List<Event> allEvents = new ArrayList<Event>();
        List<Phase> allPhases = new ArrayList<Phase>();
        allPhases.add(new Phase("red", 10, 24));

        Cycle cycle = new Cycle(allEvents, allPhases);
        assertEquals(0, cycle.eventSize());
        assertEquals(1, cycle.phaseSize());
        assertEquals(10, cycle.start());
        assertEquals(24, cycle.end());
    }

    @Test
    public void testStart() throws Exception {

    }

    @Test
    public void testEnd() throws Exception {

    }

    @Test
    public void testAddEvent() throws Exception {

    }

    @Test
    public void testAddEvent1() throws Exception {

    }

    @Test
    public void testAddPhase() throws Exception {

    }

    @Test
    public void testAddPhase1() throws Exception {

    }

    @Test
    public void testGetEvent() throws Exception {

    }

    @Test
    public void testGetPhase() throws Exception {

    }

    @Test
    public void testGetEventsList() throws Exception {

    }

    @Test
    public void testGetPhasesList() throws Exception {

    }
}
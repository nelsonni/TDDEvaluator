package TDDSessionsLibrary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CycleTest {

    List<Event> events;
    List<Phase> phases;
    final String jsonEvent = "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}";
    final String jsonPhase = "{\"CycleType\":\"red\",\"CycleStart\":\"32\",\"CycleEnd\":\"41\"}";
    Cycle c1;
    Cycle c2;

    @Before
    public void setUp() throws Exception {
        events = new ArrayList<>();
        phases = new ArrayList<>();
        c1 = new Cycle();
        c2 = new Cycle(events, phases);
    }

    @Test
    public void testConstructors() throws Exception {
        System.out.println("CycleTest::testConstructors...");
        try {
            Cycle c1 = new Cycle();
            Cycle c2 = new Cycle(events, phases);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testAddEventObject() throws Exception {
        System.out.println("CycleTest::testAddEventObject...");
        Event e = new Event(jsonEvent);
        assertTrue(c1.addEvent(e));
        assertTrue(c2.addEvent(e));
    }

    @Test
    public void testAddEventString() throws Exception {
        System.out.println("CycleTest::testAddEventString...");
        assertTrue(c1.addEvent(jsonEvent));
        assertTrue(c2.addEvent(jsonEvent));
    }

    @Test
    public void testAddPhaseObject() throws Exception {
        System.out.println("CycleTest::testAddPhaseObject...");
        Phase p = new Phase(jsonPhase);
        assertTrue(c1.addPhase(p));
        assertTrue(c2.addPhase(p));
    }

    @Test
    public void testAddPhaseString() throws Exception {
        System.out.println("CycleTest::testAddPhaseString...");
        c1.addPhase(jsonPhase);
        c2.addPhase(jsonPhase);

        assertEquals(jsonPhase, c1.getPhase(0).toString());
        assertEquals(jsonPhase, c2.getPhase(0).toString());
    }

    @Test
    public void testEventSize() {
        System.out.println("CycleTest::testEventSize...");
        Event e = new Event(jsonEvent);
        c1.addEvent(e);
        c1.addEvent(e);

        assertEquals(2, c1.eventSize());
        assertEquals(0, c2.eventSize());
    }

    @Test
    public void testPhaseSize() {
        System.out.println("CycleTest::testPhaseSize...");
        Phase p = new Phase(jsonPhase);
        c1.addPhase(p);
        c2.addPhase(p);

        assertEquals(1, c1.phaseSize());
        assertEquals(1, c2.phaseSize());
    }

    @Test
    public void testGetEvent() throws Exception {
        System.out.println("CycleTest::testGetEvent...");
        Event e = new Event(jsonEvent);
        c1.addEvent(e);
        c2.addEvent(e);

        assertEquals(e, c1.getEvent(0));
        assertEquals(e, c2.getEvent(0));
    }

    @Test
    public void testGetPhase() throws Exception {
        System.out.println("CycleTest::testGetPhase...");
        Phase p = new Phase(jsonPhase);
        c1.addPhase(p);
        c2.addPhase(p);

        assertEquals(p, c1.getPhase(0));
        assertEquals(p, c2.getPhase(0));
    }

    @Test
    public void testParseEventList() throws Exception {
        System.out.println("CycleTest::testParseEventList...");
        List<String> events = new ArrayList<>();
        events.add(jsonEvent);
        events.add(jsonEvent);

        assertEquals(2, Cycle.parseEventList(events).size());
    }

    @Test
    public void testParsePhasesList() throws Exception {
        System.out.println("CycleTest::testParsePhasesList...");
        List<String> phases = new ArrayList<>();
        phases.add(jsonPhase);

        assertEquals(1, Cycle.parsePhaseList(phases).size());
    }
}
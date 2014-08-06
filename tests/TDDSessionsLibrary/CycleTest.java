package TDDSessionsLibrary;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CycleTest {

    Phase p1 = new Phase("{\"CycleType\":\"red\",\"CycleStart\":\"0\",\"CycleEnd\":\"10\"}");
    Phase p2 = new Phase("{\"CycleType\":\"green\",\"CycleStart\":\"11\",\"CycleEnd\":\"20\"}");
    Phase p3 = new Phase("{\"CycleType\":\"blue\",\"CycleStart\":\"21\",\"CycleEnd\":\"30\"}");

    @Test
    public void testConstructors() throws Exception {
        try {
            Cycle c1 = new Cycle();
            Cycle c2 = new Cycle(p1, p2);
            Cycle c3 = new Cycle(p1, p2, p3);
            assertNotNull(c1);
            assertNotNull(c2);
            assertNotNull(c3);
        }
        catch (IllegalArgumentException e) {
            fail();
        }
    }

    @Test
    public void testEqualsTrue() throws Exception {
        Cycle c1 = new Cycle(p1, p2, p3);
        Cycle c2 = new Cycle(p1, p2, p3);

        assertEquals(c1, c2);
    }

    @Test
    public void testEqualsFalse() throws Exception {
        Cycle c1 = new Cycle(p1, p2, p3);
        Cycle c2 = new Cycle(p1, p2);

        assertFalse(c1.equals(c2));
    }

    @Test
    public void testSize() throws Exception {
        Cycle c1 = new Cycle();
        Cycle c2 = new Cycle(p1, p2);
        Cycle c3 = new Cycle(p1, p2, p3);

        assertEquals(0, c1.size());
        assertEquals(2, c2.size());
        assertEquals(3, c3.size());
    }

    @Test
    public void testGet() throws Exception {
        Cycle c = new Cycle(p1, p2, p3);

        assertEquals(p1, c.get(0));
        assertEquals(p2, c.get(1));
        assertEquals(p3, c.get(2));
    }

    @Test
    public void testAddObject() throws Exception {
        Cycle c = new Cycle();

        assertTrue(c.add(p1));
        assertEquals(p1, c.get(0));
    }

    @Test
    public void testAddString() throws Exception {
        Cycle c = new Cycle();

        assertTrue(c.add(p1.toString()));
        assertEquals(p1.toString(), c.get(0).toString());

    }

    @Test
    public void testParsePhasesList() throws Exception {
        List<String> phaseList = new ArrayList<>();
        phaseList.add(p1.toString());
        phaseList.add(p2.toString());

        assertEquals(2, Cycle.parsePhaseList(phaseList).size());
    }
}
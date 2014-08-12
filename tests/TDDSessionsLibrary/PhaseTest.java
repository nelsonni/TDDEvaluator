package TDDSessionsLibrary;

import org.junit.Test;

import static org.junit.Assert.*;

public class PhaseTest {

    @Test
    public void testParameterConstructor() throws Exception {
        Phase p = new Phase("red", 12, 20);
        assertEquals("red", p.type);
        assertEquals(12, p.start);
        assertEquals(20, p.end);
    }

    @Test
    public void testGoodJSONStringConstructor() throws Exception {
        Phase p = new Phase("{\"CycleType\":\"blue\",\"CycleStart\":\"24\",\"CycleEnd\":\"32\"}");
        assertEquals("blue", p.type);
        assertEquals(24, p.start);
        assertEquals(32, p.end);
    }

    @Test
    public void testBadJSONStringConstructor() throws Exception {
        Phase p = new Phase("{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}");
        assertEquals("error", p.type);
        assertEquals(-1, p.start);
        assertEquals(-1, p.end);
    }

    @Test
    public void testEqualsTrue() throws Exception {
        Phase p1 = new Phase("{\"CycleType\":\"blue\",\"CycleStart\":\"24\",\"CycleEnd\":\"32\"}");
        Phase p2 = new Phase("{\"CycleType\":\"blue\",\"CycleStart\":\"24\",\"CycleEnd\":\"32\"}");
        assertTrue(p1.equals(p2));
    }

    @Test
    public void testEqualsFalse() throws Exception {
        Phase p1 = new Phase("{\"CycleType\":\"blue\",\"CycleStart\":\"24\",\"CycleEnd\":\"32\"}");
        Phase p2 = new Phase("{\"CycleType\":\"blue\",\"CycleStart\":\"19\",\"CycleEnd\":\"28\"}");
        assertFalse(p1.equals(p2));
    }

}
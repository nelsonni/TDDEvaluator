package TDDSessionsLibrary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PhaseTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testParameterConstructor() throws Exception {
        Phase phase = new Phase("red", 12, 20);

        assertTrue(phase instanceof Phase);
        assertEquals("red", phase.type);
        assertEquals(12, phase.start);
        assertEquals(20, phase.end);
    }

    @Test
    public void testGoodJSONStringConstructor() throws Exception {
        String json = "{\"CycleType\":\"blue\",\"CycleStart\":\"24\",\"CycleEnd\":\"32\"}";
        Phase phase = new Phase(json);

        assertTrue(phase instanceof Phase);
        assertEquals("blue", phase.type);
        assertEquals(24, phase.start);
        assertEquals(32, phase.end);
    }

    @Test
    public void testBadJSONStringConstructor() throws Exception {
        String json = "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}";
        Phase phase = new Phase(json);

        assertTrue(phase instanceof Phase);
        assertEquals(null, phase.type);
        assertEquals(-1, phase.start);
        assertEquals(-1, phase.end);
    }

}
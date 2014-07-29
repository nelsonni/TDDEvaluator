package TDDSessionsLibrary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhaseTest {

    @Test
    public void testParameterConstructor() throws Exception {
        System.out.println("PhaseTest::testParameterConstructor...");
        Phase phase = new Phase("red", 12, 20);

        assertEquals("red", phase.type);
        assertEquals(12, phase.start);
        assertEquals(20, phase.end);
    }

    @Test
    public void testGoodJSONStringConstructor() throws Exception {
        System.out.println("PhaseTest::testGoodJSONStringConstructor...");
        String json = "{\"CycleType\":\"blue\",\"CycleStart\":\"24\",\"CycleEnd\":\"32\"}";
        Phase phase = new Phase(json);

        assertEquals("blue", phase.type);
        assertEquals(24, phase.start);
        assertEquals(32, phase.end);
    }

    @Test
    public void testBadJSONStringConstructor() throws Exception {
        System.out.println("PhaseTest::testBadJSONStringConstructor...");
        String json = "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}";
        Phase phase = new Phase(json);

        assertEquals("error", phase.type);
        assertEquals(-1, phase.start);
        assertEquals(-1, phase.end);
    }

}
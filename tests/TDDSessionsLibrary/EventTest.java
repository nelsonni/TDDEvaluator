package TDDSessionsLibrary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventTest {

    @Test
    public void testSize() throws Exception {
        System.out.println("EventTest::testSize...");
        Event e = new Event("{\"timestamp\":\"1400549108894\",\"text\":\"some example\",\"changeOrigin\":\"user\"}");
        assertEquals(3, e.size());
    }

    @Test
    public void testToString() throws Exception {
        String json = "{\"timestamp\":\"1400549108894\",\"text\":\"some example\",\"changeOrigin\":\"user\"}";

        System.out.println("EventTest::testToString...");
        Event e = new Event(json);
        assertEquals(json, e.toString());
    }

    @Test
    public void testEqualsTrue() throws Exception {
        System.out.println("EventTest::testEqualsTrue...");
        Event e1 = new Event("{\"timestamp\":\"1400549108894\",\"text\":\"same text\",\"changeOrigin\":\"user\"}");
        Event e2 = new Event("{\"timestamp\":\"1400549108894\",\"text\":\"same text\",\"changeOrigin\":\"user\"}");
        assertTrue(e1.equals(e2));
    }

    @Test
    public void testEqualsFalse() throws Exception {
        System.out.println("EventTest::testEqualsFalse...");
        Event e1 = new Event("{\"timestamp\":\"1400549108894\",\"text\":\"some text\",\"changeOrigin\":\"user\"}");
        Event e2 = new Event("{\"timestamp\":\"1303750108894\",\"text\":\"different text\",\"changeOrigin\":\"user\"}");
        assertFalse(e1.equals(e2));
    }

}
package TDDSessionsLibrary;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EventTest {

    final String json = "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}";

    @Test
    public void testSize() throws Exception {
        System.out.println("EventTest::testSize...");
        Event event = new Event(json);
        assertEquals(3, event.size());
    }

    @Test
    public void testToString() throws Exception {
        System.out.println("EventTest::testToString...");
        Event event = new Event(json);
        assertEquals(json, event.toString());
    }

}
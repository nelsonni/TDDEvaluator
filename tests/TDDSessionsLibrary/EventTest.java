package TDDSessionsLibrary;

import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventTest {

    final String json = "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}";

    @Test
    public void testSize() throws Exception {
        Event event = new Event(json);
        assertEquals(3, event.size());
    }

    @Test
    public void testToString() throws Exception {
        Event event = new Event(json);
        assertEquals(json, event.toString());
    }

    @Test
    public void testParseJSONString() throws Exception {
        JSONObject jObj = Event.parseJSONString(json);
        assertEquals("1400549108894", jObj.get("timestamp").toString());
        assertEquals("example", jObj.get("text").toString());
        assertEquals("user", jObj.get("changeOrigin").toString());
    }

}
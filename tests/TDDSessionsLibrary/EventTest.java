package TDDSessionsLibrary;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventTest {

    String json;

    @Before
    public void setUp() throws Exception {
        json = "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}";
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() throws Exception {
        assertTrue(new Event(json) instanceof Event);
    }

    @Test
    public void testGetString() throws Exception {
        Event event = new Event(json);
        assertEquals(0, event.getString().compareTo(json));
    }

    @Test
    public void testParseJSONString() throws Exception {
        JSONObject jObj = Event.parseJSONString(json);
        assertEquals(0, jObj.get("timestamp").toString().compareTo("1400549108894"));
    }

}
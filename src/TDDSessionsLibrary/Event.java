package TDDSessionsLibrary;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/14/14.
 */
public class Event {

    private JSONObject operationEvent;

    public Event(String jString) {
        operationEvent = parseJSONString(jString);
    }

    public String getString() {
        return operationEvent.toString();
    }

    protected static JSONObject parseJSONString(String jsonString) {
        JSONParser parser = new JSONParser();
        JSONObject jObj = null;

        try {
            jObj = (JSONObject) parser.parse(jsonString);
        }
        catch (ParseException pe) {
            System.out.println(pe);
        }

        return jObj;
    }

}

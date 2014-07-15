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

    public String getEvent() {
        return operationEvent.toString();
    }

    protected JSONObject parseJSONString(String jsonString) {
        JSONParser parser = new JSONParser();
        JSONArray jArray;
        JSONObject jObj = null;

        try {
            jArray = (JSONArray) parser.parse(jsonString);
            jObj = (JSONObject) jArray.get(0);
        }
        catch (ParseException pe) {
            System.out.println("Exception caught at position: " + pe.getPosition());
            System.out.println(pe);
        }

        return jObj;
    }

}

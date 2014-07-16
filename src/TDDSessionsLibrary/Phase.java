package TDDSessionsLibrary;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/14/14.
 */
public class Phase {

    protected String type;
    protected int start;
    protected int end;

    public Phase(String phaseType, int phaseStart, int phaseEnd) {
        type = phaseType;
        start = phaseStart;
        end = phaseEnd;
    }

    public Phase(String jString) {
        JSONObject jObj = parseJSONString(jString);

        try {
            type = jObj.get("CycleType").toString();
            start = Integer.parseInt(jObj.get("CycleStart").toString());
            end = Integer.parseInt(jObj.get("CycleEnd").toString());
        }
        catch (ClassCastException cce) {
            System.out.println("Phase Exception: JSON Object does not contain phase interval data.");
            System.out.println(cce);
        }
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

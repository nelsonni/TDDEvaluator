package TDDSessionsLibrary;

import org.json.simple.JSONObject;

import java.util.List;

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

    public Phase(String jsonString) {

        try {
            JSONObject jObj = FileIO.parseJSONString(jsonString);
            type = jObj.get("CycleType").toString();
            start = Integer.parseInt(jObj.get("CycleStart").toString());
            end = Integer.parseInt(jObj.get("CycleEnd").toString());
        }
        catch (ClassCastException ce) {
            System.err.format("Malformed JSON: %s%n", ce);
        }
        catch (NullPointerException ne) {
            type = "error";
            start = -1;
            end = -1;
        }
    }

    public String toString() {
        return String.format("{\"CycleType\":\"%s\",\"CycleStart\":\"%d\",\"CycleEnd\":\"%d\"}", type, start, end);
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Phase p = (Phase) obj;
        if (!this.type.equals(p.type)) {
            return false;
        }
        if (this.start != p.start) {
            return false;
        }
        if (this.end != p.end) {
            return false;
        }
        return true;
    }

}

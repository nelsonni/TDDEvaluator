package TDDSessionsLibrary;

import org.json.simple.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/14/14.
 */
public class Event {

    protected JSONObject operationEvent;

    public Event(String jsonString) {
        operationEvent = FileIO.parseJSONString(jsonString);
    }

    /***
     * Returns the number of JSON properties (key/value pairs) in this Event instance. This
     * method utilizes the Interface JSON (net.sf.json) provided through implementation of
     * the JSONObject library (org.json.JSONObject).
     *
     * @return size     the number of JSON properties in the Event object
     */
    public int size() {
        return operationEvent.size();
    }

    @Override
    public String toString() {
        return operationEvent.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        Event e = (Event) obj;
        return this.operationEvent.equals(e.operationEvent);
    }
}

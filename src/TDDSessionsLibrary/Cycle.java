package TDDSessionsLibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/14/14.
 */
public class Cycle {

    private List<Event> events;
    private List<Phase> phases;

    public Cycle(List<String> allEvents, List<String> allPhases) {
        for (String data : allEvents) {
            events.add(new Event(data));
        }

        for (String data : allPhases) {
            phases.add(new Phase(data));
        }
    }

    public Event getEvent(int index) {
        return events.get(index);
    }

    public Phase getPhase(int index) {
        return phases.get(index);
    }

}

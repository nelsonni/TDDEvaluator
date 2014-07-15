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

    public Cycle() {
        events = new ArrayList<Event>();
        phases = new ArrayList<Phase>();
    }

    public Cycle(List<Event> newEvents, List<Phase> newPhases) {
        events.addAll(newEvents);
        phases.addAll(newPhases);
    }

}

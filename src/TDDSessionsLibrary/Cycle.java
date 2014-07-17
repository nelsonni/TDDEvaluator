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

    public Cycle () {
        events = new ArrayList<Event>();
        phases = new ArrayList<Phase>();
    }

    public Cycle(List<Event> allEvents, List<Phase> allPhases) {
        if (!allEvents.isEmpty()) {
            events.addAll(allEvents);
        }
        if (!allPhases.isEmpty()) {
            phases.addAll(allPhases);
        }
    }

    public int getStart() {
        if (!phases.isEmpty()) {
            return phases.get(0).start;
        }
        return 0;
    }

    public int getEnd() {
        if (!phases.isEmpty()) {
            return phases.get(phases.size()-1).end;
        }
        return 0;
    }

    public boolean addEvent(Event event) {
        return events.add(event);
    }

    public boolean addPhase(Phase phase) {
        return phases.add(phase);
    }

    public Event getEvent(int index) {
        return events.get(index);
    }

    public Phase getPhase(int index) {
        return phases.get(index);
    }

    protected static List<Event> getEventsList(List<String> eventFileContent) {
        List<Event> eventsList = new ArrayList<Event>();

        for (String contentLine : eventFileContent) {
            eventsList.add(new Event(contentLine));
        }

        return eventsList;
    }

    protected static List<Phase> getPhasesList(List<String> phaseFileContent) {
        List<Phase> phasesList = new ArrayList<Phase>();

        for (String contentLine : phaseFileContent) {
            phasesList.add(new Phase(contentLine));
        }

        return phasesList;
    }

}

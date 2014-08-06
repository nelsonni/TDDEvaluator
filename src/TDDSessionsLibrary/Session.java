package TDDSessionsLibrary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/14/14.
 */
public class Session {

    public String eventFilename;
    public String phaseFilename;
    private List<Cycle> cycles;
    private List<Event> timeline;

    public Session() {
        eventFilename = null;
        phaseFilename = null;
        cycles = new ArrayList<>();
        timeline = new ArrayList<>();
    }

    public Session(String eventFilePath, String phaseFilePath) {
        eventFilename = eventFilePath;
        phaseFilename = phaseFilePath;
        cycles = new ArrayList<>();
        timeline = new ArrayList<>();

        parseFiles(eventFilename, phaseFilename);
    }

    public boolean add(Event event) {
        return timeline.add(event);
    }

    public boolean add(Cycle cycle) {
        return cycles.add(cycle);
    }

    public boolean add(Phase first, Phase second) {
        return cycles.add(new Cycle(first, second));
    }

    public boolean add(Phase first, Phase second, Phase third) {
        return cycles.add(new Cycle(first, second, third));
    }

    public boolean addAll(List<Event> events) {
        return timeline.addAll(events);
    }

    /***
     * Returns the number of Event elements in the timeline list. If this list contains more than
     * Interger.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return size     the number of Event elements in the timeline list
     */
    public int numEvents() {
        return timeline.size();
    }

    /***
     * Returns the number of Cycle elements in the cycles list. If this list contains more than
     * Interger.MAX_VALUE elements, returns Integer.MAX_VALUE.
     *
     * @return size     the number of Cycle elements in the cycles list
     */
    public int numCycles() {
        return cycles.size();
    }

    /***
     * Returns the Cycle element at the specified position in the cycles list.
     *
     * @param index     index of the Cycle element to return
     * @return          the Cycle element at the specified position in the list
     * @throws IndexOutOfBoundsException    if the index is out of range (index < 0 || index >= size())
     */
    public Cycle getCycle(int index) throws IndexOutOfBoundsException {
        return cycles.get(index);
    }

    /***
     * Returns the Event object at the specified position in the timeline list.
     *
     * @param index     index of the Event element to return
     * @return          the Event element at the specified position in the list
     * @throws IndexOutOfBoundsException    if the index is out of range (index < 0 || index >= size())
     */
    public Event getEvent(int index) throws IndexOutOfBoundsException {
        return timeline.get(index);
    }




    public void parseFiles(String eventFilePath, String phaseFilePath) {

        // step 1: get file contents and save into lists (local)
        List<String> eventFileContent = FileIO.readFromFile(eventFilePath);
        List<String> phaseFileContent = FileIO.readFromFile(phaseFilePath);

        // step 2: convert Strings to Event and Phase objects in the lists (local)
        List<Event> eventsList = parseEventList(eventFileContent);
        List<Phase> phasesList = Cycle.parsePhaseList(phaseFileContent);

        // step 3: delineate cycles from phases, add to the cycles in this class
        processCycles(phasesList);

        // step 4: add events to the timeline in this class
        timeline.addAll(eventsList);

        // step 5: verify that all events referenced by cycles/phases are in the timeline of this class
        validateTimeline();
    }

    private void processCycles(List<Phase> phasesList) {
        Cycle currentCycle = new Cycle();

        // use a queue to evaluate phases in order
        Queue<Phase> queue = new LinkedList<>();
        for (Phase p : phasesList) {
            queue.add(p);
        }

        while (!queue.isEmpty()) {

            switch (queue.peek().type) {
                case "red":
                    if (currentCycle.size() > 0) {
                        cycles.add(currentCycle);
                    }
                    currentCycle = new Cycle();
                    currentCycle.add(queue.remove());
                    break;
                case "green":
                    currentCycle.add(queue.remove());
                    break;
                case "blue":
                    currentCycle.add(queue.remove());
                    break;
                default:
                    currentCycle = new Cycle();
                    currentCycle.add(queue.remove());
                    break;
            }
        }

        // handle the last cycle if it exists
        if (currentCycle.size() > 0) {
            cycles.add(currentCycle);
        }
    }

    private void validateTimeline() {
        for (Cycle c : cycles) {
            for (Phase p : c.phases) {
                if (p.start >= timeline.size() || p.end >= timeline.size()) {
                    // TODO Finish this code if it is necessary functionality, re-evaluation of purpose is required
                }
            }
        }
    }

    protected static List<Event> parseEventList(List<String> eventFileContent) {
        List<Event> eventsList = new ArrayList<>();

        for (String contentLine : eventFileContent) {
            eventsList.add(new Event(contentLine));
        }

        return eventsList;
    }

/*
    private void processEvents(List<Event> eventsList) {
        for (Cycle c : cycles) {
            for (int i = c.start(); i <= c.end(); i++) {
                try {
                    c.add(eventsList.get(i));
                }
                catch (IndexOutOfBoundsException e) {
                    System.err.format("Phases Missing Referenced Events: %s%n", e);
                }
            }
        }
    }
*/

}

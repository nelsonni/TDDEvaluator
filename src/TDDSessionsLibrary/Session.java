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
    private List<Event> timelineJSON;
    private List<Event> timelineAST;

    public Session() {
        eventFilename = null;
        phaseFilename = null;
        cycles = new ArrayList<>();
        timelineJSON = new ArrayList<>();
        timelineAST = new ArrayList<>();
    }

    public Session(String eventFilePath, String phaseFilePath) {
        this(); // run default constructor first
        parseFiles(eventFilename, phaseFilename);
    }

    public boolean add(Event event, String type) {
        switch (type) {
            case "JSON":
                return timelineJSON.add(event);
            case "AST":
                return timelineAST.add(event);
            default:
                return false;
        }
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
        return timelineJSON.addAll(events);
    }

    /***
     * Returns the number of Event elements in the list specified by type parameter. If this list contains more than
     * Interger.MAX_VALUE elements, returns Integer.MAX_VALUE. If no list with the specified type exists, returns -1.
     *
     * @return size     the number of Event elements in the timelineJSON list
     */
    public int numEvents(String type) {
        switch (type) {
            case "JSON":
                return timelineJSON.size();
            case "AST":
                return timelineAST.size();
            default:
                return -1;
        }
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
     * Returns the Event object at the specified position in the specified type list.
     *
     * @param index     index of the Event element to return
     * @param type      type of list to return Event element from (JSON, AST)
     * @return          the Event element at the specified position in the type list
     * @throws IndexOutOfBoundsException
     */
    public Event getEvent(int index, String type) throws IndexOutOfBoundsException {
        switch (type) {
            case "JSON":
                return timelineJSON.get(index);
            case "AST":
                return timelineAST.get(index);
            default:
                return null;
        }
    }

    /***
     * Returns a subset List containing all Event objects from the start position to the end position
     * within the specified type list.
     *
     * @param start     index of the starting Event element to return
     * @param end       index of the ending Event element to return
     * @param type      type of list to return Event elements from (JSON or AST)
     * @return          a list of Event elements from the specified interval positions within the type list
     * @throws IndexOutOfBoundsException
     */
    public List<Event> getEventsList(int start, int end, String type) throws IndexOutOfBoundsException {
        List<Event> subset = new ArrayList<>();
        if (start <= end) {
            return null;
        }

        for (int i = start; i < end; i++) {
            switch (type) {
                case "JSON":
                    subset.add(timelineJSON.get(i));
                    break;
                case "AST":
                    subset.add(timelineAST.get(i));
                    break;
                default:
                    return null;
            }
        }

        return subset;
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

        // step 4: add events to the timelineJSON in this class
        timelineJSON.addAll(eventsList);

        // step 5: verify that all events referenced by cycles/phases are in the timelineJSON of this class
        validateTimeline();
    }

    protected static List<Event> parseEventList(List<String> eventFileContent) {
        List<Event> eventsList = new ArrayList<>();

        for (String contentLine : eventFileContent) {
            eventsList.add(new Event(contentLine));
        }

        return eventsList;
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
                if (p.start >= timelineJSON.size() || p.end >= timelineJSON.size()) {
                    // TODO Finish this code if it is necessary functionality, re-evaluation of purpose is required
                }
            }
        }
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

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

    public String eventFile;
    public String phaseFile;
    private List<Cycle> cycles;

    public Session() {
        eventFile = null;
        phaseFile = null;
        cycles = new ArrayList<>();
    }

    public Session(String eventFilePath, String phaseFilePath) {
        eventFile = eventFilePath;
        phaseFile = phaseFilePath;
        cycles = new ArrayList<>();

        parseFiles(eventFile, phaseFile);
    }

    public int size() {
        return cycles.size();
    }

    public Cycle getCycle(int index) {
        return cycles.get(index);
    }

    public boolean addCycle(List<Event> eventsList, List<Phase> phasesList) {
        return cycles.add(new Cycle(eventsList, phasesList));
    }

    public void parseFiles(String eventFilePath, String phaseFilePath) {

        // step 1: get file contents
        List<String> eventFileContent = FileIO.readFromFile(eventFilePath);
        List<String> phaseFileContent = FileIO.readFromFile(phaseFilePath);

        // step 2: convert to Event and Phase lists
        List<Event> eventsList = Cycle.parseEventList(eventFileContent);
        List<Phase> phasesList = Cycle.parsePhaseList(phaseFileContent);

        // step 3: delineate cycles from phases, add to List<Cycle> cycles for this session
        processCycles(phasesList);

        // step 4: add events to appropriate Cycle object
        processEvents(eventsList);
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
                    if (currentCycle.phaseSize() > 0) {
                        cycles.add(currentCycle);
                    }
                    currentCycle = new Cycle();
                    currentCycle.addPhase(queue.remove());
                    break;
                case "green":
                    currentCycle.addPhase(queue.remove());
                    break;
                case "blue":
                    currentCycle.addPhase(queue.remove());
                    break;
                default:
                    currentCycle = new Cycle();
                    currentCycle.addPhase(queue.remove());
                    break;
            }

            // handle the last cycle if it exists
            if (currentCycle.phaseSize() > 0) {
                cycles.add(currentCycle);
            }
        }
    }

    private void processEvents(List<Event> eventsList) {
        for (Cycle c : cycles) {
            for (int i = c.start(); i <= c.end(); i++) {
                try {
                    c.addEvent(eventsList.get(i));
                }
                catch (IndexOutOfBoundsException e) {
                    System.err.format("Phases Missing Referenced Events: %s%n", e);
                }
            }
        }
    }

}

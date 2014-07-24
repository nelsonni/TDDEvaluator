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
        // TODO: Clean-up the logic within the processCycles method

        Cycle cycle = new Cycle();

        // use a queue to evaluate phases in order
        Queue<Phase> queue = new LinkedList<>();
        for (Phase p : phasesList) {
            queue.add(p);
        }

        for (Phase p : phasesList) {
            System.out.println("List: " + p.toString() + " || " + queue.remove().toString() + " :Queue");
        }

        // expand on the idea of using queues for processing the cycles in-stream style
        System.out.println("------------------------------------------------");

        Cycle newCycle = new Cycle();

        if (phasesList.size() == 1) {
            Phase current = phasesList.get(0);
            newCycle.addPhase(current);
            cycles.add(newCycle);
        }
        else {
            for (int i = 0; i < phasesList.size()-1; i++) {
                Phase current = phasesList.get(i);
                Phase next = phasesList.get(i+1);

                if (current.type.equals("blank")) {
                    newCycle = new Cycle();
                    newCycle.addPhase(current);
                }
                else if (current.type.equals("red")) {
                    newCycle = new Cycle();
                    newCycle.addPhase(current);
                }
                else if (current.type.equals("green")) {
                    newCycle.addPhase(current);
                }
                else if (current.type.equals("blue")) {
                    newCycle.addPhase(current);
                }

                if (next.type.equals("red") || (i+1) == phasesList.size()-1) {
                    cycles.add(newCycle);
                }
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
                    System.out.println("eventsList missing events included in phases of a cycle");
                    System.out.println(e);
                }
            }
        }
    }

}

package TDDSessionsLibrary;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        cycles = new ArrayList<Cycle>();
    }

    public Session(String eventFilePath, String phaseFilePath) {
        eventFile = eventFilePath;
        phaseFile = phaseFilePath;
        cycles = new ArrayList<Cycle>();

        processFiles(eventFile, phaseFile);
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

    public void processFiles(String eventsFilePath, String phasesFilePath) {
        // step 1: get file contents
        List<String> eventFileContent = FileIO.readFromFile(eventsFilePath);
        List<String> phaseFileContent = FileIO.readFromFile(phasesFilePath);

        // step 2: convert to Event and Phase lists
        List<Event> eventsList = Cycle.parseEventList(eventFileContent);
        List<Phase> phasesList = Cycle.parsePhasesList(phaseFileContent);

        // step 3: delineate cycles from phases, add to List<Cycle> cycles for this session
        processCycles(phasesList);

        // step 4: add events to appropriate Cycle object
        processEvents(eventsList);
    }

    public void printInfo() {
        System.out.println("-----Events File-----");
        System.out.println("FileName: " + Paths.get(eventFile).getFileName().toString());
        System.out.println("FilePath: " + eventFile);
        System.out.println("-----Phases File-----");
        System.out.println("FileName: " + Paths.get(phaseFile).getFileName().toString());
        System.out.println("FilePath: " + phaseFile);
    }



    private void processCycles(List<Phase> phasesList) {
        Cycle newCycle = new Cycle();

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
                System.out.println("ending cycle, adding to list");
                System.out.println();
                cycles.add(newCycle);
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

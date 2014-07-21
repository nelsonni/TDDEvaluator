package TDDSessionsLibrary;

import java.nio.file.Path;
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
        cycles = new ArrayList<>();
    }

    public Session(String eventFilePath, String phaseFilePath) {
        eventFile = eventFilePath;
        phaseFile = phaseFilePath;
        cycles = new ArrayList<>();

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
        System.out.println("\nstep 1: get file contents");
        List<String> eventFileContent = FileIO.readFromFile(eventsFilePath);
        List<String> phaseFileContent = FileIO.readFromFile(phasesFilePath);

        System.out.println("Events: " + eventsFilePath);
        Path x = Paths.get(eventsFilePath);
        System.out.println("file size: " + x.toFile().length() + ", content count: " + eventFileContent.size());
        System.out.println("Phases: " + phasesFilePath);
        Path z = Paths.get(phasesFilePath);
        System.out.println("file size: " + z.toFile().length() + ", content count: " + phaseFileContent.size());

        // step 2: convert to Event and Phase lists
        System.out.println("\nstep 2: convert to Event and Phase lists");
        List<Event> eventsList = Cycle.parseEventList(eventFileContent);
        List<Phase> phasesList = Cycle.parsePhaseList(phaseFileContent);

        System.out.println("Events List:");
        System.out.println("list size: " + eventsList.size());
        System.out.println("Phases List:");
        System.out.println("list size: " + phasesList.size());

        // step 3: delineate cycles from phases, add to List<Cycle> cycles for this session
        System.out.println("\nstep 3: delineate cycles from phases, add to List<Cycle> cycles");
        for (Phase p : phasesList) {
            System.out.println("\t" + p.toString());
        }
        processCycles(phasesList);

        // step 4: add events to appropriate Cycle object
        System.out.println("\nstep 4: add events to appropriate Cycle object");
        for (Event e : eventsList) {
            System.out.println("\t" + e.toString());
        }
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
        // TODO: Clean-up the logic within the processCycles method
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

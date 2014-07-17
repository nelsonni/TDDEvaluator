package TDDSessionsLibrary;

import org.json.simple.JSONArray;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/14/14.
 */
public class Session {

    public String eventFilePath;
    public String phaseFilePath;
    private List<Cycle> cycles;
    private FileIO io;

    public Session() {
        cycles = new ArrayList<Cycle>();
    }

    public Session(String eventRelativeFilePath, String phaseRelativeFilePath) {
        eventFilePath = eventRelativeFilePath;
        phaseFilePath = phaseRelativeFilePath;
        cycles = new ArrayList<Cycle>();

        processFiles(eventFilePath, phaseFilePath);
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

        // step 2: convert to List<Event> and List<Phase> objects
        List<Event> eventsList = Cycle.getEventsList(eventFileContent);
        List<Phase> phasesList = Cycle.getPhasesList(phaseFileContent);

        // step 3: delineate cycles from phases, add to List<Cycle> cycles for this session
        processCycles(phasesList);

        // step 4: add events to appropriate Cycle object
        processEvents(eventsList);
    }

    public void printInfo() {
        System.out.println("-----Events File-----");
        System.out.println("FileName: " + Paths.get(eventFilePath).getFileName().toString());
        System.out.println("FilePath: " + eventFilePath);
        System.out.println("-----Phases File-----");
        System.out.println("FileName: " + Paths.get(phaseFilePath).getFileName().toString());
        System.out.println("FilePath: " + phaseFilePath);
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
            int startIndex = c.getStart();
            int endIndex = c.getEnd();

            for (int i = c.getStart(); i <= c.getEnd(); i++) {
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

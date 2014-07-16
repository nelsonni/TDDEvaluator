package TDDSessionsLibrary;

import org.json.simple.JSONArray;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/14/14.
 */
public class Session {

    public String eventsFile;
    public String cyclesFile;
    private List<Cycle> cycles;

    public Session() {
        cycles = new ArrayList<Cycle>();
    }

    public Session(String eventFileName, String cycleFileName) {
        cycles = new ArrayList<Cycle>();
        eventsFile = Paths.get(eventFileName).getFileName().toString();
        cyclesFile = Paths.get(cycleFileName).getFileName().toString();
        List<String> eventContent = getFileContentsList(eventFileName);
        List<String> cycleContent = getFileContentsList(cycleFileName);
        addCycle(eventContent, cycleContent);
    }

    public int size() {
        return cycles.size();
    }

    public Cycle getCycle(int index) {
        return cycles.get(index);
    }

    public boolean addCycle(List<String> cycleEvents, List<String> cyclePhases) {
        boolean result = cycles.add(new Cycle(cycleEvents, cyclePhases));
        return result;
    }

    private List<String> getFileContentsList(String filePath) {
        Path path = Paths.get(filePath);
        List<String> allLines = null;

        try {
            allLines = Files.readAllLines(path, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allLines;
    }

}

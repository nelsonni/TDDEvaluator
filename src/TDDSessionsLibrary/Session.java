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

        parseJSONEvents(eventFileName);
        parseJSONCycles(cycleFileName);
    }

    public int size() {
        return cycles.size();
    }

    public Cycle getCycle(int index) {
        return cycles.get(index);
    }

    public boolean addCycle(List<Event> cycleEvents, List<Phase> cyclePhases) {
        return cycles.add(new Cycle(cycleEvents, cyclePhases));
    }

    private void parseJSONEvents(String fileName) {
        //JSONArray allJArray = new JSONArray();
        //List<String> fileContents = getFileContentsList(fileName);

        /*
        for (String line : fileContents) {
            allJArray.addAll(parseJSONString(line));
        }*/

        //return allJArray;
    }

    private void parseJSONCycles(String fileName) {

    }







    /*
    private JSONArray mapEvents(JSONArray events) {
        for (int i = 0; i < events.size(); i++) {
            JSONObject jObj = (JSONObject) events.get(i);
            String cycleType = TDDCycleRanges.get(i);

            if (cycleType == null) {
                jObj.put("TDDCycle", "null");
            }
            else {
                jObj.put("TDDCycle", cycleType);
            }
        }
        return events;
    }
    */




    private List<String> getFileContentsList(String fileName) {
        Path filePath = Paths.get(fileName);
        List<String> allLines = null;

        try {
            allLines = Files.readAllLines(filePath, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return allLines;
    }

}

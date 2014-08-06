package TDDSessionsLibrary;

import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FileIOTest {

    @Test
    public void testReadFromFile() throws Exception {
        Path temp = getTempFile("begin\n23434213423453254324\nend");

        List<String> content = FileIO.readFromFile(temp.toString());
        String output = FileIO.arrayToString(content);

        assertEquals("begin\n23434213423453254324\nend", output);
    }

    @Test
    public void testWriteToFile() throws Exception {
        Path temp = getEmptyTempFile();
        String writeContent = "begin\n23434213423453254324\nend";

        FileIO.writeToFile(temp.toString(), writeContent);

        List<String> readContent = FileIO.readFromFile(temp.toString());
        String output = FileIO.arrayToString(readContent);

        assertEquals("begin\n23434213423453254324\nend", output);
    }

    @Test
    public void testParseJSONString() throws Exception {
        String json = "{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}";
        JSONObject jObj = FileIO.parseJSONString(json);

        assertEquals("1400549108894", jObj.get("timestamp").toString());
        assertEquals("example", jObj.get("text").toString());
        assertEquals("user", jObj.get("changeOrigin").toString());
    }

    @Test
    public void testArrayToString() throws Exception {
        List<String> cycleLines = new ArrayList<>();
        cycleLines.add("[{\"CycleType\":\"red\",\"CycleEnd\":\"344\",\"id\":\"0344\",\"CycleStart\":\"0\"},");
        cycleLines.add("{\"CycleType\":\"green\",\"CycleEnd\":\"381\",\"id\":\"345381\",\"CycleStart\":\"345\"},");
        cycleLines.add("{\"CycleType\":\"blue\",\"CycleEnd\":\"408\",\"id\":\"382408\",\"CycleStart\":\"382\"},");
        cycleLines.add("{\"CycleType\":\"red\",\"CycleEnd\":\"496\",\"id\":\"409496\",\"CycleStart\":\"409\"},");
        cycleLines.add("{\"CycleType\":\"green\",\"CycleEnd\":\"710\",\"id\":\"497710\",\"CycleStart\":\"497\"},");
        cycleLines.add("{\"CycleType\":\"red\",\"CycleEnd\":\"795\",\"id\":\"711795\",\"CycleStart\":\"711\"},");
        cycleLines.add("{\"CycleType\":\"green\",\"CycleEnd\":\"1038\",\"id\":\"7961038\",\"CycleStart\":\"796\"},");
        cycleLines.add("{\"CycleType\":\"blue\",\"CycleEnd\":\"1120\",\"id\":\"10391120\",\"CycleStart\":\"1039\"},");
        cycleLines.add("{\"CycleType\":\"red\",\"CycleEnd\":\"1168\",\"id\":\"11211168\",\"CycleStart\":\"1121\"},");
        cycleLines.add("{\"CycleType\":\"green\",\"CycleEnd\":\"1281\",\"id\":\"11691281\",\"CycleStart\":\"1169\"},");
        cycleLines.add("{\"CycleType\":\"red\",\"CycleEnd\":\"1374\",\"id\":\"12821374\",\"CycleStart\":\"1282\"},");
        cycleLines.add("{\"CycleType\":\"green\",\"CycleEnd\":\"1904\",\"id\":\"13751904\",\"CycleStart\":\"1375\"},");
        cycleLines.add("{\"CycleType\":\"blue\",\"CycleEnd\":\"1947\",\"id\":\"19051947\",\"CycleStart\":\"1905\"}]");
        String convertedCycleLines = FileIO.arrayToString(cycleLines);

        String control = "[{\"CycleType\":\"red\",\"CycleEnd\":\"344\",\"id\":\"0344\",\"CycleStart\":\"0\"},\n" +
        "{\"CycleType\":\"green\",\"CycleEnd\":\"381\",\"id\":\"345381\",\"CycleStart\":\"345\"},\n" +
        "{\"CycleType\":\"blue\",\"CycleEnd\":\"408\",\"id\":\"382408\",\"CycleStart\":\"382\"},\n" +
        "{\"CycleType\":\"red\",\"CycleEnd\":\"496\",\"id\":\"409496\",\"CycleStart\":\"409\"},\n" +
        "{\"CycleType\":\"green\",\"CycleEnd\":\"710\",\"id\":\"497710\",\"CycleStart\":\"497\"},\n" +
        "{\"CycleType\":\"red\",\"CycleEnd\":\"795\",\"id\":\"711795\",\"CycleStart\":\"711\"},\n" +
        "{\"CycleType\":\"green\",\"CycleEnd\":\"1038\",\"id\":\"7961038\",\"CycleStart\":\"796\"},\n" +
        "{\"CycleType\":\"blue\",\"CycleEnd\":\"1120\",\"id\":\"10391120\",\"CycleStart\":\"1039\"},\n" +
        "{\"CycleType\":\"red\",\"CycleEnd\":\"1168\",\"id\":\"11211168\",\"CycleStart\":\"1121\"},\n" +
        "{\"CycleType\":\"green\",\"CycleEnd\":\"1281\",\"id\":\"11691281\",\"CycleStart\":\"1169\"},\n" +
        "{\"CycleType\":\"red\",\"CycleEnd\":\"1374\",\"id\":\"12821374\",\"CycleStart\":\"1282\"},\n" +
        "{\"CycleType\":\"green\",\"CycleEnd\":\"1904\",\"id\":\"13751904\",\"CycleStart\":\"1375\"},\n" +
        "{\"CycleType\":\"blue\",\"CycleEnd\":\"1947\",\"id\":\"19051947\",\"CycleStart\":\"1905\"}]";

        assertEquals(control, convertedCycleLines);
    }

    // utility method; only used for constructing temporary files with content
    private Path getTempFile(String data) {
        Path temp = getEmptyTempFile();
        List<String> content = Arrays.asList(data.split("\\r?\\n"));

        for (String s : content) {
            FileIO.writeToFile(temp.toString(), s);
            FileIO.writeToFile(temp.toString(), "\n");
        }

        return temp;
    }

    // utility method; only used for constructing empty temporary files
    private Path getEmptyTempFile() {
        Path temp = null;

        try {
            temp = Files.createTempFile(null, ".tmp");
            System.out.println("created temporary file: " + temp.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }

}
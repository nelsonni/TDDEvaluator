package TDDSessionsLibrary;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileIOTest {

    @Test
    public void testReadFromFile() throws Exception {
        Path temp = getTempFile();

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
        JSONObject jObj = FileIO.parseJSONString("{\"timestamp\":\"1400549108894\",\"text\":\"example\",\"changeOrigin\":\"user\"}");
        assertEquals("1400549108894", jObj.get("timestamp").toString());
        assertEquals("example", jObj.get("text").toString());
        assertEquals("user", jObj.get("changeOrigin").toString());
    }

    private Path getTempFile() {
        Path temp = getEmptyTempFile();

        try (BufferedWriter writer = Files.newBufferedWriter(temp, Charset.defaultCharset(), StandardOpenOption.APPEND)) {
            writer.write("begin\n23434213423453254324\nend");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return temp;
    }

    private Path getEmptyTempFile() {
        Path temp = null;

        try {
            temp = Files.createTempFile(null, ".tmp");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("created temporary file: " + temp.toString());

        return temp;
    }

}
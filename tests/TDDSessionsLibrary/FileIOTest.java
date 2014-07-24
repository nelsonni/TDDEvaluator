package TDDSessionsLibrary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileIOTest {

    @Test
    public void testReadFromFile() throws Exception {
        Path temp = getTempFile();

        List<String> content = FileIO.readFromFile(temp.toString());
        String output = arrayToString(content);

        assertEquals("begin\n23434213423453254324\nend", output);
    }

    @Test
    public void testWriteToFile() throws Exception {
        Path temp = getEmptyTempFile();
        String writeContent = "begin\n23434213423453254324\nend";

        FileIO.writeToFile(temp.toString(), writeContent);

        List<String> readContent = FileIO.readFromFile(temp.toString());
        String output = arrayToString(readContent);

        assertEquals("begin\n23434213423453254324\nend", output);
    }

    private String arrayToString(List<String> contentArray) {
        StringBuilder sb = new StringBuilder();

        for (String s : contentArray) {
            sb.append(s).append(System.lineSeparator());
        }
        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }

    private Path getTempFile() {
        Path temp = getEmptyTempFile();

        try (BufferedWriter writer = Files.newBufferedWriter(temp, Charset.defaultCharset())) {
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
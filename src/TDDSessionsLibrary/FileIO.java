package TDDSessionsLibrary;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Created by nelsonni@onid.oregonstate.edu on 7/16/14.
 */
public class FileIO {

    // TODO: Fix issues with the operation of FileIO utility class
    // this class should not be instantiated, and should instead be used as an interface
    // to file input/output functionality through the static methods below.

    public static List<String> readFromFile(String filePath) {
        Path path = Paths.get(filePath);
        List<String> content = new ArrayList<String>();

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                content.add(line);
            }
        }
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        catch (NullPointerException e) {
            System.err.format("NullPointerException: %s%n", e);
        }

        return content;
    }

    public static void writeToFile(String filePath, List<String> contentsList) {
        Path path = Paths.get(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {
            for (String s : contentsList) {
                writer.write(s, 0, s.length());
            }
            writer.close();
        }
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

}

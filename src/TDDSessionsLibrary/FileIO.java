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

    /***
     * Utility method to allow reading in all text/bytecode from a local file to be use by all methods
     * within the TDDSessionsLibrary. This method does not operate asynchronously, therefore it can result
     * in a blocked state if the content within the file is too large.
     *
     * The filePath parameter must be relative to the runtime directory of the TDDSessionsLibrary.
     *
     * @param filePath      a relative path to the destination input file (e.g. /local/usr/data.in)
     * @return contentsList a newline-delineated collection (ArrayList object) of text lines read from file
     */
    static List<String> readFromFile(String filePath) {
        Path path = Paths.get(filePath);
        List<String> content = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line;
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

    /***
     * Utility method to allow for fire-and-forget writing of content to file from all methods
     * within the TDDSessionsLibrary. This method does not operate asynchronously, therefore it can
     * result in a blocked state if the to-be-written content is too large.
     *
     * @param filePath      a relative path to the destination output file (e.g. /local/usr/data.out)
     * @param content  a newline-delineated collection of text lines to be written to file
     */
    static void writeToFile(String filePath, String content) {
        Path path = Paths.get(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset())) {
            writer.write(content, 0, content.length());
            writer.close();
        }
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

}

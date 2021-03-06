package TDDSessionsLibrary;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

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
     * @param filePath  a relative path to the destination input file (e.g. /local/usr/data.in)
     * @return  contentsList a newline-delineated collection (ArrayList object) of text lines read from file
     */
    public static List<String> readFromFile(String filePath) {
        Path path = Paths.get(filePath);

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset())) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        catch (NullPointerException e) {
            System.err.format("NullPointerException: %s%n", e);
        }

        return lines;
    }

    /***
     * Utility method to allow for fire-and-forget writing of content to file from all methods
     * within the TDDSessionsLibrary. This method does not operate asynchronously, therefore it can
     * result in a blocked state if the to-be-written content is too large.
     *
     * @param filePath  a relative path to the destination output file (e.g. /local/usr/data.out)
     * @param content   a newline-delineated collection of text lines to be written to file
     */
    public static void writeToFile(String filePath, String content) {
        Path path = Paths.get(filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.defaultCharset(), StandardOpenOption.APPEND)) {
            writer.write(content, 0, content.length());
            writer.close();
        }
        catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    /***
     * Utility method to parse and convert all JSON-compatible elements from java.lang.String format to a
     * org.json.JSONObject instance that can be used to access and iterate all JSON elements from files or streams.
     *
     * @param jsonString    a java.lang.String object containing newline delineated text to be converted
     * @return jObj     a org.json.JSONObject that contains all JSON-compatible elements from the
     *                  parameter java.lang.String object
     */
    public static JSONObject parseJSONString(String jsonString) {
        JSONParser parser = new JSONParser();
        JSONObject jObj = null;

        // sanitize JSON for parsing
        if (jsonString.contains("{") && jsonString.contains("}")) {
            int jsonOpenPos = jsonString.indexOf("{");
            int jsonClosePos = jsonString.lastIndexOf("}")+1;
            jsonString = jsonString.substring(jsonOpenPos, jsonClosePos);
        }

        try {
            jObj = (JSONObject) parser.parse(jsonString);
        }
        catch (ParseException pe) {
            System.err.format("JSON ParseException: %s%n", pe);
        }

        return jObj;
    }

    /***
     * Utility method to convert line-based text content from array to java.lang.String format in preparation
     * for writing to file using the writeToFile(String, String) method.
     *
     * @param contentArray  an instance of the java.util.List interface class that contains java.lang.String
     *                      objects in contextual order, with top line in position 0.
     * @return contentStr   a java.lang.String object containing newline delineated text from parameter
     *                      java.util.List object
     */
    public static String arrayToString(List<String> contentArray) {
        StringBuilder sb = new StringBuilder();

        for (String s : contentArray) {
            sb.append(s).append(System.lineSeparator());
        }
        sb.deleteCharAt(sb.length()-1);

        return sb.toString();
    }

}

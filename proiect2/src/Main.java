import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import data.Input;
import util.StartEngine;

import java.io.File;
import java.io.IOException;

public class Main {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {

        // reads the input
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputJson = objectMapper.readValue(new File(args[0]), Input.class);
        ArrayNode outputJson = objectMapper.createArrayNode();

        // starts the program
        StartEngine startEngine = new StartEngine(inputJson, outputJson);
        startEngine.start();

        // puts the output in the corresponding file
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), outputJson);
    }
}

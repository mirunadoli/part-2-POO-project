import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import data.Input;
import util.StartEngine;

import java.io.File;
import java.io.IOException;

public class Main {

    static int i = 1;
    /**
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {

        // reads the input
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputJson = objectMapper.readValue(new File(args[0]), Input.class);
        ArrayNode outputJson = objectMapper.createArrayNode();

        // the start of the program
        StartEngine startEngine = new StartEngine(inputJson, outputJson);
        startEngine.start();

        // puts the output in the corresponding file
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), outputJson);
        objectWriter.writeValue(new File("results/" + args[1] + i), outputJson);
        i++;
    }
}

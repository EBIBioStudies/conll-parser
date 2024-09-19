package uk.ac.ebi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for reading and parsing CoNLL-U files.
 */
public class ConlluReader {

    /**
     * Reads a CoNLL-U file and returns a list of tokens per sentence.
     *
     * @param filePath Path to the CoNLL-U file.
     * @return A list of sentences, where each sentence is a list of tokens.
     * @throws IOException If the file cannot be read.
     */
    private List<List<Token>> readConlluFile(String filePath) throws IOException {
        List<List<Token>> sentences = new ArrayList<>();
        List<Token> currentSentence = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    if (!currentSentence.isEmpty()) {
                        sentences.add(currentSentence);
                        currentSentence = new ArrayList<>();
                    }
                    continue;
                }

                // Skip comment lines
                if (line.startsWith("#")) {
                    continue;
                }

                Token token = parseToken(line);
                if (token == null) continue;

                // Add token to the current sentence
                currentSentence.add(token);
            }

            // Add the last sentence
            if (!currentSentence.isEmpty()) {
                sentences.add(currentSentence);
            }
        }

        return sentences;
    }

    private Token parseToken(String line) {
        // Split the line into fields by tab
        String[] fields = line.split("[ \t]+");

        if (fields.length != 10) {
            System.err.println("Invalid line format, skipping: " + line);
            return null;
        }

        // Create a token from the fields
        Token token = new Token(fields);

        if (token.id.contains("-")) {
            return null;
        }
        return token;
    }

    public List<DependencyGraph> parse(String filePath) throws IOException {
        List<List<Token>> sentences = readConlluFile(filePath);
        List<DependencyGraph> graphs = new ArrayList<>();
        for (List<Token> sentence : sentences) {
            graphs.add( DependencyGraph.buildGraphFromTokens(sentence));
        }
        return graphs;
    }


    public static void main(String[] args) throws IOException {
        ConlluReader reader = new ConlluReader();
        String filePath = "src/test/resources/quick-brown-fox.conll";  // Update this path
        List<DependencyGraph> graphs = reader.parse(filePath);
        for (DependencyGraph graph : graphs) {
            System.out.println(graph.extractNounChunks());
        }

    }
}

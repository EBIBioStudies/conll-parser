package uk.ac.ebi;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DependencyGraphTest {

    @Test
    void testToString() {
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(new String[] {"1", "I", "I", "PRON", "_", "_", "2", "nsubj", "_", "_"}));
        tokens.add(new Token(new String[] {"2", "see", "see", "VERB", "_", "_", "0", "root", "_", "_"}));
        tokens.add(new Token(new String[] {"3", "a", "a", "DET", "_", "_", "4", "det", "_", "_"}));
        tokens.add(new Token(new String[] {"4", "cat", "cat", "NOUN", "_", "_", "2", "obj", "_", "_"}));
        DependencyGraph graph = DependencyGraph.buildGraphFromTokens(tokens);
        assertEquals("I see a cat", graph.toString());
    }

}

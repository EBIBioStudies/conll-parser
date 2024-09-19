package uk.ac.ebi;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConlluReaderTest {

    @Test
    void testParsing() throws IOException {
        ConlluReader reader = new ConlluReader();
        URL resource = getClass().getClassLoader().getResource("two-sentences.conll");
        assert resource != null;
        List<DependencyGraph> graphs = reader.parse(resource.getFile());
        assertNotNull(graphs);
        assertEquals(2, graphs.size());
        DependencyGraph graph = graphs.get(0);
        assertNotNull(graph);
        assertEquals("I see a cat .", graph.toString());
        graph = graphs.get(1);
        assertNotNull(graph);
        assertEquals("You feed the dog .", graph.toString());
    }

    @Test
    void testCompoundChunking() throws IOException {
        ConlluReader reader = new ConlluReader();
        URL resource = getClass().getClassLoader().getResource("compound.conll");
        assert resource != null;
        List<DependencyGraph> graphs = reader.parse(resource.getFile());
        assertNotNull(graphs);
        assertEquals(1, graphs.size());
        DependencyGraph graph = graphs.get(0);
        assertNotNull(graph);
        List<String> chunks = graph.extractNounChunks();
        assertLinesMatch(List.of("black coffee cup", "a white stripe"), chunks);
    }
}

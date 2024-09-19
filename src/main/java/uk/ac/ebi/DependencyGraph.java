package uk.ac.ebi;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class representing the dependency graph of a sentence.
 */
public class DependencyGraph {


    private final Map<Integer, List<Integer>> dependents;

    // Map of node IDs to the actual tokens (for reference)
    private final Map<Integer, Token> nodes;

    public DependencyGraph() {
        this.dependents = new HashMap<>();
        this.nodes = new HashMap<>();
    }

    /**
     * Adds a directed edge from the head token to the dependent token.
     *
     * @param headId The ID of the head token.
     * @param depId  The ID of the dependent token.
     */
    public void addEdge(int headId, int depId) {
        dependents.putIfAbsent(headId, new ArrayList<>());
        dependents.get(headId).add(depId);
    }

    /**
     * Adds a node to the graph corresponding to a token.
     *
     * @param id    The ID of the token (node).
     * @param token The token to be stored.
     */
    public void addNode(int id, Token token) {
        nodes.put(id, token);
    }

    /**
     * Prints the dependency graph in a human-readable format.
     */
    @Override
    public String toString() {
        return nodes.values().stream().map(token -> token.form).collect(Collectors.joining(" "));
    }

    /**
     * Builds a dependency graph from a list of tokens.
     *
     * @param tokens The list of tokens in the sentence.
     * @return The constructed DependencyGraph.
     */
    public static DependencyGraph buildGraphFromTokens(List<Token> tokens) {
        DependencyGraph graph = new DependencyGraph();

        for (Token token : tokens) {
            try {
                int tokenId = Integer.parseInt(token.id);  // The token's ID
                int headId = Integer.parseInt(token.head); // The head ID of the token (0 for ROOT)

                // Add the token as a node
                graph.addNode(tokenId, token);

                // If the token has a head (non-zero), add an edge from the head to the token
                if (headId != 0) {
                    graph.addEdge(headId, tokenId);
                }
            } catch (NumberFormatException e) {
                // Issue: Malformed token ID or head ID, skip this token
                System.err.println("Skipping token due to invalid ID or head: " + token);
            }
        }

        return graph;
    }

    // Method to extract noun chunks
    public List<String> extractNounChunks() {
        List<String> chunks = new ArrayList<>();
        final var allowed = Set.of(Token.UPOS.NOUN, Token.UPOS.PROPN);
        for (Integer nodeId : nodes.keySet()) {
            Token token = nodes.get(nodeId);
            if (allowed.contains(token.tag)) {
                String chunk = collectNounChunk(nodeId);
                if (!chunk.isEmpty()) {
                    chunks.add(chunk);
                }
            }
        }

        return chunks;
    }

    // Helper method to collect a noun chunk given the starting node
    private String collectNounChunk(Integer nodeId) {

        final var allowed = Set.of("det","amod","compound");
        StringBuilder chunk = new StringBuilder();

        if (dependents.containsKey(nodeId)) {
         dependents.get(nodeId).forEach(id -> {
             var child = nodes.get(id);
             if (allowed.contains(child.deprel)) {
                 chunk.append(child.form).append(" ");
             }
         });
        }

        chunk.append(nodes.get(nodeId).form);
        return chunk.toString().trim();
    }
}


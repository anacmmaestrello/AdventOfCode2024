package day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PrintQueuePartTwo {
    public static void main(String[] args) throws IOException {
        // Path to the input file
        String filePath = "src/day05/input.txt";

        // Read the input
        String input = new String(Files.readAllBytes(Paths.get(filePath))).trim();

        // Split the input into rules and updates
        String[] sections = input.split("\\r?\\n\\r?\\n");
        String[] rules = sections[0].split("\\r?\\n");
        String[] updates = sections[1].split("\\r?\\n");

        // Parse the ordering rules into a directed graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (String rule : rules) {
            String[] parts = rule.split("\\|");
            int before = Integer.parseInt(parts[0]);
            int after = Integer.parseInt(parts[1]);
            graph.computeIfAbsent(before, k -> new ArrayList<>()).add(after);
        }

        // Identify and reorder the incorrectly ordered updates
        int sumOfMiddlePages = 0;
        for (String update : updates) {
            String[] pages = update.split(",");
            List<Integer> pageList = new ArrayList<>();
            for (String page : pages) {
                pageList.add(Integer.parseInt(page));
            }

            if (isValidOrder(pageList, graph)) {
                // Skip valid updates for Part Two
                continue;
            }

            // Reorder the update
            List<Integer> reordered = reorderPages(pageList, graph);

            // Find the middle page
            int middleIndex = reordered.size() / 2;
            int middlePage = reordered.get(middleIndex);
            sumOfMiddlePages += middlePage;
        }

        // Print the final result
        System.out.println("Sum of middle pages after reordering: " + sumOfMiddlePages);
    }

    // Check if a given order of pages is valid
    public static boolean isValidOrder(List<Integer> pages, Map<Integer, List<Integer>> graph) {
        for (int i = 0; i < pages.size(); i++) {
            for (int j = i + 1; j < pages.size(); j++) {
                int before = pages.get(i);
                int after = pages.get(j);

                // Check if there's a rule that is violated
                if (graph.containsKey(before) && graph.get(before).contains(after)) {
                    continue; // Rule is satisfied
                }
                if (graph.containsKey(after) && graph.get(after).contains(before)) {
                    return false; // Rule is violated
                }
            }
        }
        return true; // All rules satisfied
    }

    // Reorder pages to satisfy the rules
    public static List<Integer> reorderPages(List<Integer> pages, Map<Integer, List<Integer>> graph) {
        // Topological sorting to reorder the pages
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

        // Build the adjacency list and calculate in-degrees
        for (int page : pages) {
            inDegree.put(page, 0);
            adjacencyList.put(page, new ArrayList<>());
        }
        for (int before : graph.keySet()) {
            for (int after : graph.get(before)) {
                if (pages.contains(before) && pages.contains(after)) {
                    adjacencyList.get(before).add(after);
                    inDegree.put(after, inDegree.get(after) + 1);
                }
            }
        }

        // Perform topological sort using Kahn's algorithm
        Queue<Integer> queue = new LinkedList<>();
        for (int page : inDegree.keySet()) {
            if (inDegree.get(page) == 0) {
                queue.offer(page);
            }
        }

        List<Integer> sortedPages = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sortedPages.add(current);

            for (int neighbor : adjacencyList.get(current)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return sortedPages;
    }
}

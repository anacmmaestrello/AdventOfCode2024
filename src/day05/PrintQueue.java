package day05;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PrintQueue {

    public static void main(String[] args) {
        // Path to the input file
        String filePath = "src/day05/input.txt";

        try {
            // Read the input
            String input = new String(Files.readAllBytes(Paths.get(filePath))).trim();

            // Split rules and updates sections
            String[] sections = input.split("\\r?\\n\\r?\\n");

            if (sections.length < 2) {
                System.err.println("Input file is not in the correct format. Ensure rules and updates are separated by a blank line.");
                return;
            }

            // Parse rules and updates
            String[] rules = sections[0].split("\n");
            String[] updates = sections[1].split("\n");

            // Validate updates and calculate the sum of middle pages
            int sumOfMiddlePages = calculateMiddlePageSum(rules, updates);

            // Output the result
            System.out.println("Sum of middle pages: " + sumOfMiddlePages);

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    private static int calculateMiddlePageSum(String[] rules, String[] updates) {
        // Parse rules into a map for validation
        Map<Integer, Set<Integer>> precedenceMap = parseRules(rules);

        int sumOfMiddlePages = 0;

        // Validate each update
        for (String update : updates) {
            String[] pages = update.split(",");
            List<Integer> pageList = new ArrayList<>();

            for (String page : pages) {
                pageList.add(Integer.parseInt(page.trim()));
            }

            if (isValidUpdate(pageList, precedenceMap)) {
                // Find the middle page
                int middlePage = pageList.get(pageList.size() / 2);
                sumOfMiddlePages += middlePage;
            }
        }

        return sumOfMiddlePages;
    }

    private static Map<Integer, Set<Integer>> parseRules(String[] rules) {
        Map<Integer, Set<Integer>> precedenceMap = new HashMap<>();

        for (String rule : rules) {
            String[] parts = rule.split("\\|");
            if (parts.length != 2) continue; // Skip invalid rules

            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());

            precedenceMap.putIfAbsent(x, new HashSet<>());
            precedenceMap.get(x).add(y);
        }

        return precedenceMap;
    }

    private static boolean isValidUpdate(List<Integer> update, Map<Integer, Set<Integer>> precedenceMap) {
        // Create a position map to quickly find the position of each page in the update
        Map<Integer, Integer> positionMap = new HashMap<>();
        for (int i = 0; i < update.size(); i++) {
            positionMap.put(update.get(i), i);
        }

        // Check each rule
        for (Map.Entry<Integer, Set<Integer>> entry : precedenceMap.entrySet()) {
            int x = entry.getKey();
            Set<Integer> ySet = entry.getValue();

            if (positionMap.containsKey(x)) {
                int xPosition = positionMap.get(x);

                for (int y : ySet) {
                    if (positionMap.containsKey(y)) {
                        int yPosition = positionMap.get(y);

                        // If x is not before y, the update is invalid
                        if (xPosition >= yPosition) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }
}

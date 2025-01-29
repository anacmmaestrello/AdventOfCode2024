package day10;

import java.io.*;
import java.util.*;

public class HikingTrail {

    public static void main(String[] args) {
        String inputFile = "src/day10/input.txt";

        try {
            List<String> map = readInput(inputFile);
            int[][] topographicMap = parseMap(map);
            int totalScore = calculateTrailheadScores(topographicMap);
            System.out.println("Total score of all trailheads: " + totalScore);
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    /**
     * Reads the input file and returns a list of strings representing the map.
     */
    private static List<String> readInput(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Parses the map input into a 2D integer array.
     */
    private static int[][] parseMap(List<String> map) {
        int rows = map.size();
        int cols = map.get(0).length();
        int[][] topographicMap = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = map.get(i);
            for (int j = 0; j < cols; j++) {
                topographicMap[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }
        return topographicMap;
    }

    /**
     * Calculates the total score of all trailheads on the topographic map.
     */
    private static int calculateTrailheadScores(int[][] map) {
        int totalScore = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) { // Trailhead found
                    int score = calculateTrailheadScore(map, i, j);
                    totalScore += score;
                }
            }
        }

        return totalScore;
    }

    /**
     * Calculates the score for a specific trailhead (height 0 position).
     */
    private static int calculateTrailheadScore(int[][] map, int startRow, int startCol) {
        Set<String> reachableNines = new HashSet<>();
        boolean[][] visited = new boolean[map.length][map[0].length];
        dfs(map, startRow, startCol, visited, reachableNines, 0);
        return reachableNines.size();
    }

    /**
     * Depth-first search to find reachable positions from the trailhead.
     */
    private static void dfs(int[][] map, int row, int col, boolean[][] visited, Set<String> reachableNines, int currentHeight) {
        // Out of bounds or already visited
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length || visited[row][col]) {
            return;
        }

        int height = map[row][col];
        if (height != currentHeight) { // Must follow the strict height progression
            return;
        }

        // Mark as visited
        visited[row][col] = true;

        // If this is height 9, add it to reachableNines
        if (height == 9) {
            reachableNines.add(row + "," + col);
            return;
        }

        // Explore neighbors
        dfs(map, row - 1, col, visited, reachableNines, currentHeight + 1); // Up
        dfs(map, row + 1, col, visited, reachableNines, currentHeight + 1); // Down
        dfs(map, row, col - 1, visited, reachableNines, currentHeight + 1); // Left
        dfs(map, row, col + 1, visited, reachableNines, currentHeight + 1); // Right
    }
}

package day10;

import java.io.*;
import java.util.*;

public class HikingTrailPartTwo {

    public static void main(String[] args) {
        String inputFile = "src/day10/input.txt";

        try {
            List<String> map = readInput(inputFile);
            int[][] topographicMap = parseMap(map);

            // Part 1: Calculate the total score of all trailheads
            int totalScore = calculateTrailheadScores(topographicMap);
            System.out.println("Total score of all trailheads: " + totalScore);

            // Part 2: Calculate the total rating of all trailheads
            int totalRating = calculateTrailheadRatings(topographicMap);
            System.out.println("Total rating of all trailheads: " + totalRating);

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

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

    private static int calculateTrailheadScores(int[][] map) {
        int totalScore = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) {
                    int score = calculateTrailheadScore(map, i, j);
                    totalScore += score;
                }
            }
        }

        return totalScore;
    }

    private static int calculateTrailheadScore(int[][] map, int startRow, int startCol) {
        Set<String> reachableNines = new HashSet<>();
        boolean[][] visited = new boolean[map.length][map[0].length];
        dfsScore(map, startRow, startCol, visited, reachableNines, 0);
        return reachableNines.size();
    }

    private static void dfsScore(int[][] map, int row, int col, boolean[][] visited, Set<String> reachableNines, int currentHeight) {
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length || visited[row][col]) {
            return;
        }

        int height = map[row][col];
        if (height != currentHeight) {
            return;
        }

        visited[row][col] = true;

        if (height == 9) {
            reachableNines.add(row + "," + col);
            return;
        }

        dfsScore(map, row - 1, col, visited, reachableNines, currentHeight + 1);
        dfsScore(map, row + 1, col, visited, reachableNines, currentHeight + 1);
        dfsScore(map, row, col - 1, visited, reachableNines, currentHeight + 1);
        dfsScore(map, row, col + 1, visited, reachableNines, currentHeight + 1);

        visited[row][col] = false;
    }

    private static int calculateTrailheadRatings(int[][] map) {
        int totalRating = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) {
                    int rating = calculateTrailheadRating(map, i, j);
                    totalRating += rating;
                }
            }
        }

        return totalRating;
    }

    private static int calculateTrailheadRating(int[][] map, int startRow, int startCol) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        return dfsRating(map, startRow, startCol, visited, 0);
    }

    private static int dfsRating(int[][] map, int row, int col, boolean[][] visited, int currentHeight) {
        if (row < 0 || col < 0 || row >= map.length || col >= map[0].length || visited[row][col]) {
            return 0;
        }

        int height = map[row][col];
        if (height != currentHeight) {
            return 0;
        }

        visited[row][col] = true;

        if (height == 9) {
            visited[row][col] = false; // Backtrack
            return 1; // Count this trail
        }

        int trails = 0;
        trails += dfsRating(map, row - 1, col, visited, currentHeight + 1); // Up
        trails += dfsRating(map, row + 1, col, visited, currentHeight + 1); // Down
        trails += dfsRating(map, row, col - 1, visited, currentHeight + 1); // Left
        trails += dfsRating(map, row, col + 1, visited, currentHeight + 1); // Right

        visited[row][col] = false; // Backtrack to explore other paths
        return trails;
    }
}

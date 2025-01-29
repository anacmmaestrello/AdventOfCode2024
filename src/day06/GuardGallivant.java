package day06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class GuardGallivant {

    // Directions: UP, RIGHT, DOWN, LEFT
    private static final int[][] DIRECTIONS = {
            {-1, 0}, // UP
            {0, 1},  // RIGHT
            {1, 0},  // DOWN
            {0, -1}  // LEFT
    };

    public static void main(String[] args) {
        // Path to the input file
        String filePath = "src/day06/input.txt";

        try {
            // Read the input file and split it into lines
            String input = new String(Files.readAllBytes(Paths.get(filePath))).trim();
            String[] lines = input.split("\\r?\\n");

            // Process the input and calculate distinct positions visited
            int distinctPositions = predictGuardPath(lines);
            System.out.println("Distinct positions visited: " + distinctPositions);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static int predictGuardPath(String[] input) {
        int rows = input.length;
        int cols = input[0].length();
        char[][] map = new char[rows][cols];
        int guardRow = 0, guardCol = 0, direction = 0;

        // Load map and find guard's initial position and direction
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                map[r][c] = input[r].charAt(c);
                if (map[r][c] == '^' || map[r][c] == '>' || map[r][c] == 'v' || map[r][c] == '<') {
                    guardRow = r;
                    guardCol = c;
                    direction = "^>v<".indexOf(map[r][c]); // Determine direction
                    map[r][c] = '.';  // Clear the guard's initial position
                }
            }
        }

        Set<String> visited = new HashSet<>();
        visited.add(guardRow + "," + guardCol); // Add starting position to visited

        while (true) {
            int nextRow = guardRow + DIRECTIONS[direction][0];
            int nextCol = guardCol + DIRECTIONS[direction][1];

            // If guard moves out of bounds, stop simulation
            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) {
                break;
            }

            // If there's an obstacle, turn right
            if (map[nextRow][nextCol] == '#') {
                direction = (direction + 1) % 4; // Turn right 90 degrees
            } else {
                // Move forward
                guardRow = nextRow;
                guardCol = nextCol;
                visited.add(guardRow + "," + guardCol); // Mark as visited
            }
        }

        return visited.size();
    }
}

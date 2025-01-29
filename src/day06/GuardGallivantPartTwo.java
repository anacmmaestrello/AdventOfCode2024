package day06;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class GuardGallivantPartTwo {

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

            // Process the input and calculate possible obstruction positions
            int obstructionPositions = findObstructionPositions(lines);
            System.out.println("Number of possible obstruction positions: " + obstructionPositions);

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static int findObstructionPositions(String[] input) {
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

        int obstructionCount = 0;

        // Check every possible position for adding an obstruction
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Skip the starting position and existing obstacles
                if ((r == guardRow && c == guardCol) || map[r][c] == '#') {
                    continue;
                }

                // Simulate patrol with a new obstruction at (r, c)
                map[r][c] = '#'; // Temporarily place obstruction
                if (causesLoop(map, guardRow, guardCol, direction)) {
                    obstructionCount++;
                }
                map[r][c] = '.'; // Remove obstruction
            }
        }

        return obstructionCount;
    }

    private static boolean causesLoop(char[][] map, int startRow, int startCol, int startDirection) {
        int rows = map.length;
        int cols = map[0].length;

        int row = startRow;
        int col = startCol;
        int direction = startDirection;

        Set<String> visited = new HashSet<>();
        String initialState = row + "," + col + "," + direction;
        visited.add(initialState);

        while (true) {
            int nextRow = row + DIRECTIONS[direction][0];
            int nextCol = col + DIRECTIONS[direction][1];

            // If guard moves out of bounds, return false (no loop)
            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) {
                return false;
            }

            // If there's an obstacle, turn right
            if (map[nextRow][nextCol] == '#') {
                direction = (direction + 1) % 4; // Turn right 90 degrees
            } else {
                // Move forward
                row = nextRow;
                col = nextCol;
            }

            String currentState = row + "," + col + "," + direction;
            // If we revisit the same position with the same direction, a loop is detected
            if (visited.contains(currentState)) {
                return true;
            }

            visited.add(currentState);
        }
    }
}

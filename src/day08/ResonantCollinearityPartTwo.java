package day08;

import java.io.*;
import java.util.*;

public class ResonantCollinearityPartTwo {

    private static char[][] grid;
    private static int H, W;
    private static Set<Character> frequencies = new HashSet<>();

    public static void main(String[] args) {
        String filePath = "src/day08/input.txt";  // Update this path as needed

        try {
            // Read the input from file and initialize the grid
            grid = readAndParseInput(filePath);
            H = grid.length;
            W = grid[0].length;

            // Extract unique frequencies (excluding '.')
            extractFrequencies();

            // Part 2: Calculate antipodes (marking all points in line)
            System.out.println("Part 2: Total antipodes = " + sumAntipodes(2));

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    // Method to read the input file and parse the grid
    private static char[][] readAndParseInput(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();

        // Convert list of strings to a 2D char array (grid)
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    // Extract unique frequencies from the grid
    private static void extractFrequencies() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                char c = grid[i][j];
                if (c != '.') {
                    frequencies.add(c);
                }
            }
        }
    }

    // Method to check if a coordinate is within bounds
    private static boolean inbounds(int y, int x) {
        return y >= 0 && y < H && x >= 0 && x < W;
    }

    // Method to mark the antipodes (based on part 2 logic)
    private static void markPodes(int y, int x, int dy, int dx, int[][] antipodes) {
        // Part 2: Mark all points in a straight line
        while (inbounds(y, x)) {
            antipodes[y][x] = 1;
            y += dy;
            x += dx;
        }
    }

    // Method to sum antipodes for the given part
    private static int sumAntipodes(int part) {
        int[][] antipodes = new int[H][W];
        for (char freq : frequencies) {
            List<int[]> positions = new ArrayList<>();

            // Collect all positions for the current frequency
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (grid[i][j] == freq) {
                        positions.add(new int[]{i, j});
                    }
                }
            }

            // Process each combination of two antennas for the current frequency
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    int[] a = positions.get(i);
                    int[] b = positions.get(j);

                    // Mark row, column or diagonal between the two antennas
                    markPodes(a[0], a[1], b[0] - a[0], b[1] - a[1], antipodes);
                    markPodes(b[0], b[1], a[0] - b[0], a[1] - b[1], antipodes);
                }
            }
        }

        // Count the number of marked antipodes
        int count = 0;
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (antipodes[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
}

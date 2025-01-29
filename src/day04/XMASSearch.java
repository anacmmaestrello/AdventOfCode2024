package day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMASSearch {
    public static void main(String[] args) {
        // Path to the input file
        String filePath = "src/day04/input.txt";

        try {
            // Read the grid from the file
            String[] gridInput = Files.lines(Paths.get(filePath)).toArray(String[]::new);

            // Convert gridInput to a 2D char array
            char[][] grid = new char[gridInput.length][gridInput[0].length()];
            for (int i = 0; i < gridInput.length; i++) {
                grid[i] = gridInput[i].toCharArray();
            }

            // Find occurrences of X-MAS
            int count = findXMASSOccurrences(grid);
            System.out.println("Total occurrences of X-MAS: " + count);
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
    }

    public static int findXMASSOccurrences(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        // Traverse the grid to find all 'A' characters
        for (int i = 1; i < rows - 1; i++) { // Avoid edges (the center cannot be on the edge)
            for (int j = 1; j < cols - 1; j++) {
                if (grid[i][j] == 'A') {
                    // Log when an 'A' is found
                    System.out.println("Found 'A' at: (" + i + ", " + j + ")");

                    // Check if this 'A' forms a valid X-MAS shape
                    if (isXMAS(grid, i, j)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static boolean isXMAS(char[][] grid, int centerX, int centerY) {
        // Log the current center position
        System.out.println("Checking X-MAS for center: (" + centerX + ", " + centerY + ")");

        // Check both diagonals around the center (MAS in one, SAM in the other)
        boolean hasMAS1 = checkMAS(grid, centerX, centerY, -1, -1, 1, 1);  // Top-left to bottom-right diagonal (MAS)
        boolean hasMAS2 = checkMAS(grid, centerX, centerY, 1, -1, -1, 1);  // Bottom-left to top-right diagonal (SAM)

        System.out.println("MAS1: " + hasMAS1 + ", MAS2: " + hasMAS2); // Log the results of both diagonal checks

        return hasMAS1 && hasMAS2;
    }

    // Check if we have "MAS" or "SAM" along a diagonal
    public static boolean checkMAS(char[][] grid, int centerX, int centerY, int dx1, int dy1, int dx2, int dy2) {
        // Check for valid bounds for the two diagonal ends
        int startX = centerX + dx1;
        int startY = centerY + dy1;
        int endX = centerX + dx2;
        int endY = centerY + dy2;

        // Ensure all coordinates are within bounds
        if (startX < 0 || startY < 0 || endX < 0 || endY < 0 ||
                startX >= grid.length || startY >= grid[0].length ||
                endX >= grid.length || endY >= grid[0].length) {
            return false;
        }

        // Check for "MAS" or "SAM" diagonals
        String diagonal = "" + grid[startX][startY] + grid[centerX][centerY] + grid[endX][endY];

        // Log the diagonal being checked
        System.out.println("Diagonal: " + diagonal);

        return diagonal.equals("MAS") || diagonal.equals("SAM");
    }
}

package day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CeresSearch {
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

            // Word to search for
            String target = "XMAS";

            // Find occurrences of XMAS
            int count = findWordOccurrences(grid, target);
            System.out.println("Total occurrences of XMAS: " + count);
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
    }

    public static int findWordOccurrences(char[][] grid, String word) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        // Direction vectors for 8 directions
        int[][] directions = {
                {0, 1}, {0, -1}, {1, 0}, {-1, 0},   // Horizontal & Vertical
                {1, 1}, {-1, -1}, {1, -1}, {-1, 1}  // Diagonals
        };

        // Iterate through every cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Check if the current cell is the starting letter of the word
                if (grid[i][j] == word.charAt(0)) {
                    // Check all 8 directions
                    for (int[] dir : directions) {
                        int dx = dir[0], dy = dir[1];
                        if (checkWord(grid, word, i, j, dx, dy)) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    public static boolean checkWord(char[][] grid, String word, int startX, int startY, int dx, int dy) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Iterate through each character in the word
        for (int k = 0; k < word.length(); k++) {
            int x = startX + k * dx;
            int y = startY + k * dy;

            // Check if we're out of bounds
            if (x < 0 || x >= rows || y < 0 || y >= cols) {
                return false;
            }

            // Check if the character matches
            if (grid[x][y] != word.charAt(k)) {
                return false;
            }
        }

        return true;
    }
}

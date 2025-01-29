package day12;

import java.io.*;
import java.util.*;

public class GardenGroupsPartTwo {

    private static char[][] grid;
    private static Set<String> seen;
    private static int height, width;

    // Directions for up, right, down, left
    private static final int[] moveY = {-1, 0, 1, 0};
    private static final int[] moveX = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        // Read input and build the grid
        List<String> input = readInput("C:/Users/ana.maestrello/IdeaProjects/AdventOfCode2024/src/day12/input.txt");
        grid = buildGrid(input);

        height = grid.length;
        width = grid[0].length;
        seen = new HashSet<>();

        long part1 = 0;
        long part2 = 0;

        // Iterate over every cell to process the regions
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                String key = y + "," + x;
                if (!seen.contains(key)) {
                    Set<String> group = bfs(y, x);
                    seen.addAll(group);

                    int area = group.size();
                    int perimeter = calculatePerimeter(group);
                    int sides = calculateSides(group);

                    part1 += (long) area * perimeter;
                    part2 += (long) area * sides;
                }
            }
        }

        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);
    }

    // Read the input from the file
    private static List<String> readInput(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    // Convert input into a grid
    private static char[][] buildGrid(List<String> input) {
        int rows = input.size();
        int cols = input.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }

    // BFS function to find connected group of same type plants
    private static Set<String> bfs(int startY, int startX) {
        Set<String> group = new HashSet<>();
        char value = grid[startY][startX];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startY, startX});
        group.add(startY + "," + startX);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int cy = current[0], cx = current[1];

            for (int i = 0; i < 4; i++) {
                int ny = cy + moveY[i], nx = cx + moveX[i];
                if (loc(ny, nx) == value && !group.contains(ny + "," + nx)) {
                    group.add(ny + "," + nx);
                    queue.offer(new int[]{ny, nx});
                }
            }
        }
        return group;
    }

    // Helper function to get the grid value at (y, x), or return a null character if out of bounds
    private static char loc(int y, int x) {
        if (y < 0 || x < 0 || y >= height || x >= width) {
            return '\0';  // Out of bounds
        }
        return grid[y][x];
    }

    // Calculate the perimeter of the group (number of external sides)
    private static int calculatePerimeter(Set<String> group) {
        int total = 0;
        for (String cell : group) {
            String[] parts = cell.split(",");
            int y = Integer.parseInt(parts[0]), x = Integer.parseInt(parts[1]);

            for (int i = 0; i < 4; i++) {
                int ny = y + moveY[i], nx = x + moveX[i];
                if (loc(ny, nx) != grid[y][x]) {
                    total++;
                }
            }
        }
        return total;
    }

    // Calculate the sides of the group (special corner sides)
    private static int calculateSides(Set<String> group) {
        Set<String> seenSides = new HashSet<>();
        int cornerSides = 0;

        for (String cell : group) {
            String[] parts = cell.split(",");
            int y = Integer.parseInt(parts[0]), x = Integer.parseInt(parts[1]);

            for (int i = 0; i < 4; i++) {
                int ny = y + moveY[i], nx = x + moveX[i];

                if (loc(ny, nx) != grid[y][x]) {
                    int cy = y, cx = x;
                    while (loc(cy + moveX[i], cx + moveY[i]) == grid[y][x] &&
                            loc(cy + moveY[i], cx + moveX[i]) != grid[cy][cx]) {
                        cy += moveX[i];
                        cx += moveY[i];
                    }
                    String side = cy + "," + cx + "," + moveY[i] + "," + moveX[i];
                    if (!seenSides.contains(side)) {
                        seenSides.add(side);
                        cornerSides++;
                    }
                }
            }
        }
        return cornerSides;
    }
}

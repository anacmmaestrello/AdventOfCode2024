package day12;

import java.io.*;
import java.util.*;

public class GardenGroups {

    public static void main(String[] args) throws IOException {
        // Read the input map from a file
        BufferedReader br = new BufferedReader(new FileReader("src/day12/input.txt"));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();

        // Convert the input into a 2D character array
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] garden = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            garden[i] = lines.get(i).toCharArray();
        }

        // Variables to store total price
        long totalPrice = 0;
        boolean[][] visited = new boolean[rows][cols];

        // Directions for traversing neighbors (up, down, left, right)
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        // Flood fill each region
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    char plantType = garden[i][j];
                    int[] areaAndPerimeter = floodFill(garden, visited, i, j, plantType, dx, dy);
                    int area = areaAndPerimeter[0];
                    int perimeter = areaAndPerimeter[1];
                    totalPrice += (long) area * perimeter;
                }
            }
        }

        // Output the total price
        System.out.println("Total Price: " + totalPrice);
    }

    private static int[] floodFill(char[][] garden, boolean[][] visited, int x, int y, char plantType, int[] dx, int[] dy) {
        int rows = garden.length;
        int cols = garden[0].length;
        int area = 0;
        int perimeter = 0;

        // BFS Queue for flood fill
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            area++; // Increase area for each cell in the region

            // Check all four neighbors
            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];

                // If out of bounds or a different plant type, count as perimeter
                if (nx < 0 || ny < 0 || nx >= rows || ny >= cols || garden[nx][ny] != plantType) {
                    perimeter++;
                } else if (!visited[nx][ny]) {
                    // If not visited and same plant type, add to queue
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }

        return new int[]{area, perimeter};
    }
}

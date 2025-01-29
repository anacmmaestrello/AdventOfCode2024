package day01;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistorianHysteria {
    public static void main(String[] args) {
        try {
            // File path to the input file
            String filePath = "src/day01/input.txt";

            // Read and parse the data from the file
            List<int[]> parsedData = readAndParseInput(filePath);

            // Separate the data into two arrays
            int[] leftList = parsedData.get(0);
            int[] rightList = parsedData.get(1);

            // Calculate the total distance
            int totalDistance = calculateTotalDistance(leftList, rightList);

            // Print the result
            System.out.println("The total distance between the lists is: " + totalDistance);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Method to read and parse data from the file
    public static List<int[]> readAndParseInput(String filePath) throws IOException {
        // Open the file for reading
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        // Process each line in the file
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+"); // Split by whitespace
            leftList.add(Integer.parseInt(parts[0]));  // Add the first number to the left list
            rightList.add(Integer.parseInt(parts[1])); // Add the second number to the right list
        }
        reader.close();

        // Convert the lists to arrays
        int[] leftArray = leftList.stream().mapToInt(Integer::intValue).toArray();
        int[] rightArray = rightList.stream().mapToInt(Integer::intValue).toArray();

        // Return both arrays
        return Arrays.asList(leftArray, rightArray);
    }

    // Method to calculate the total distance between two lists
    public static int calculateTotalDistance(int[] leftList, int[] rightList) {
        // Ensure both lists are the same size
        if (leftList.length != rightList.length) {
            throw new IllegalArgumentException("The lists must have the same size!");
        }

        // Sort both lists
//        Arrays.sort(leftList);
//        Arrays.sort(rightList);
        countingSort(leftList);
        countingSort(rightList);

        // Calculate the total distance
        int totalDistance = 0;
        for (int i = 0; i < leftList.length; i++) {
            totalDistance += Math.abs(leftList[i] - rightList[i]);
        }

        return totalDistance;
    }

    //Counting sort method
    public static void countingSort(int[] array) {
        // Find the maximum element in the array
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        // Create a counting array
        int[] count = new int[max + 1];

        // Count the occurrences of each element
        for (int i = 0; i < array.length; i++) {
            count[array[i]]++;
        }

        // Build the sorted array
        int k = 0;
        for (int i = 0; i <= max; i++) {
            while (count[i] > 0) {
                array[k++] = i;
                count[i]--;
            }
        }
    }
}
package day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistorianHysteriaFromFile {
    public static void main(String[] args) {
        try {
            // File path to the input file
            String filePath = "src/day01/sample_input1.txt";

            // Read and parse the data from the file
            List<int[]> parsedData = readAndParseInput(filePath);

            // Separate the data into two arrays
            int[] leftList = parsedData.get(0);
            int[] rightList = parsedData.get(1);

            // Sort the arrays using selection sort
            selectionSort(leftList);
            selectionSort(rightList);

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

        return Arrays.asList(leftArray, rightArray);
    }

    // Method to sort an array using selection sort
    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the current element with the smallest element found
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    // Method to calculate the total distance between two lists
    public static int calculateTotalDistance(int[] leftList, int[] rightList) {
        // Ensure both lists are the same size
        if (leftList.length != rightList.length) {
            throw new IllegalArgumentException("The lists must have the same size!");
        }

        // Calculate the total distance
        int totalDistance = 0;
        for (int i = 0; i < leftList.length; i++) {
            totalDistance += Math.abs(leftList[i] - rightList[i]);
        }

        return totalDistance;
    }
}
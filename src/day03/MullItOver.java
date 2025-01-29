package day03;

import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class MullItOver {

    public static void main(String[] args) {
        // Path to the input file
        String filePath = "src/day03/input.txt";

        try {
            // Read the corrupted memory data from the file
            String corruptedMemory = new String(Files.readAllBytes(Paths.get(filePath)));

            // Calculate the sum of valid multiplication results
            int totalSum = calculateSumOfMultiplications(corruptedMemory);

            // Print the result
            System.out.println("Total sum of multiplications: " + totalSum);
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
    }

    // Function to calculate the sum of valid multiplications
    public static int calculateSumOfMultiplications(String corruptedMemory) {
        int sum = 0;

        // Regex to match valid 'mul(X, Y)' instructions
        String regex = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(corruptedMemory);

        // Find all matches
        while (matcher.find()) {
            // Extract the two numbers
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));

            // Multiply the numbers and add to the sum
            sum += x * y;
        }

        return sum;
    }
}

package day07;

import java.io.*;
import java.util.*;

public class BridgeRepairPartTwo {

    public static void main(String[] args) {
        String filePath = "src/day07/input.txt";

        try {
            // Read the input from file
            List<Equation> equations = readAndParseInput(filePath);

            // Calculate the total calibration result
            long totalCalibrationResult = 0;
            for (Equation equation : equations) {
                System.out.println("Processing equation with target: " + equation.target + " and numbers: " + equation.numbers);
                if (canProduceTarget(equation.numbers, equation.target)) {
                    System.out.println("Matched target: " + equation.target);
                    totalCalibrationResult += equation.target;
                } else {
                    System.out.println("No match for target: " + equation.target);
                }
            }

            // Print the total calibration result
            System.out.println("Total Calibration Result: " + totalCalibrationResult);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    static class Equation {
        long target;
        List<Integer> numbers;

        Equation(long target, List<Integer> numbers) {
            this.target = target;
            this.numbers = numbers;
        }
    }

    // Method to determine if the target value can be produced
    private static boolean canProduceTarget(List<Integer> numbers, long target) {
        // Start the recursive function with the first number
        return dfs(numbers, 1, numbers.get(0), target);
    }

    // Recursive function to try all operator combinations
    private static boolean dfs(List<Integer> numbers, int index, long currentValue, long target) {
        // Base case: If all numbers are used, check if the current value matches the target
        if (index == numbers.size()) {
//            System.out.println("End of recursion. CurrentValue: " + currentValue + ", Target: " + target);
            return currentValue == target;
        }

        // Recursive cases: Try +, *, and || operators
        int nextNumber = numbers.get(index);

        // Try addition
//        System.out.println("Trying addition: " + currentValue + " + " + nextNumber);
        if (dfs(numbers, index + 1, currentValue + nextNumber, target)) {
            return true;
        }

        // Try multiplication, with overflow check
        if (currentValue != 0 && nextNumber != 0 && currentValue > Long.MAX_VALUE / nextNumber) {
            System.out.println("Skipping multiplication due to overflow: " + currentValue + " * " + nextNumber);
        } else {
//            System.out.println("Trying multiplication: " + currentValue + " * " + nextNumber);
            if (dfs(numbers, index + 1, currentValue * nextNumber, target)) {
                return true;
            }
        }

        // Try concatenation (||)
        String concatenatedValue = String.valueOf(currentValue) + nextNumber;
        try {
            long concatenatedLong = Long.parseLong(concatenatedValue);
//            System.out.println("Trying concatenation: " + currentValue + " || " + nextNumber + " = " + concatenatedLong);
            if (dfs(numbers, index + 1, concatenatedLong, target)) {
                return true;
            }
        } catch (NumberFormatException e) {
//            System.out.println("Skipping concatenation due to overflow: " + concatenatedValue);
        }

        // If no operator works
        return false;
    }

    // Method to read and parse the input file
    private static List<Equation> readAndParseInput(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<Equation> equations = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            long target = Long.parseLong(parts[0].trim());
            List<Integer> numbers = new ArrayList<>();

            for (String num : parts[1].trim().split("\\s+")) {
                numbers.add(Integer.parseInt(num));
            }

//            System.out.println("Parsed equation: Target = " + target + ", Numbers = " + numbers);
            equations.add(new Equation(target, numbers));
        }

        reader.close();
        return equations;
    }
}

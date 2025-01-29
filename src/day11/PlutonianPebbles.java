package day11;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class PlutonianPebbles {

    public static void main(String[] args) {
        // Read input from a file
        String inputFile = "src/day11/input.txt"; // Adjust path as needed
        List<BigInteger> stones = readInputFromFile(inputFile);

        // Number of blinks (iterations)
        int blinks = 25;

        // Simulate the transformations
        long result = simulateStones(stones, blinks);

        // Output the number of stones after the given number of blinks
        System.out.println("Number of stones after " + blinks + " blinks: " + result);
    }

    /**
     * Simulates the transformations of the stones for the given number of blinks.
     *
     * @param stones Initial list of stones
     * @param blinks Number of iterations to simulate
     * @return Number of stones after all transformations
     */
    private static long simulateStones(List<BigInteger> stones, int blinks) {
        for (int blink = 0; blink < blinks; blink++) {
            List<BigInteger> newStones = new ArrayList<>();
            for (BigInteger stone : stones) {
                if (stone.equals(BigInteger.ZERO)) {
                    // Rule 1: Stone 0 becomes 1
                    newStones.add(BigInteger.ONE);
                } else if (hasEvenDigits(stone)) {
                    // Rule 2: Split stones with even digits
                    String stoneStr = stone.toString();
                    int mid = stoneStr.length() / 2;
                    BigInteger left = new BigInteger(stoneStr.substring(0, mid));
                    BigInteger right = new BigInteger(stoneStr.substring(mid));
                    newStones.add(left);
                    newStones.add(right);
                } else {
                    // Rule 3: Multiply by 2024
                    newStones.add(stone.multiply(BigInteger.valueOf(2024)));
                }
            }
            stones = newStones; // Update the stones for the next iteration
        }
        return stones.size();
    }

    /**
     * Checks if the number has an even number of digits.
     *
     * @param number The number to check
     * @return True if the number has an even number of digits, false otherwise
     */
    private static boolean hasEvenDigits(BigInteger number) {
        return number.toString().length() % 2 == 0;
    }

    /**
     * Reads the input from a file and parses it into a list of BigIntegers.
     *
     * @param filePath Path to the input file
     * @return List of BigIntegers representing the initial stones
     */
    private static List<BigInteger> readInputFromFile(String filePath) {
        List<BigInteger> stones = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                String[] parts = line.split("\\s+");
                for (String part : parts) {
                    stones.add(new BigInteger(part));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return stones;
    }
}

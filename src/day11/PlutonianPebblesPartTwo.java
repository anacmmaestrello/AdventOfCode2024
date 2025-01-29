package day11;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class PlutonianPebblesPartTwo {

    public static void main(String[] args) {
        // Read input from a file
        String inputFile = "src/day11/input.txt"; // Adjust path as needed
        List<BigInteger> initialStones = readInputFromFile(inputFile);

        // Number of blinks (iterations)
        int blinks = 75;

        // Simulate the transformations
        long result = simulateStonesEfficiently(initialStones, blinks);

        // Output the number of stones after the given number of blinks
        System.out.println("Number of stones after " + blinks + " blinks: " + result);
    }

    /**
     * Simulates the transformations of the stones for the given number of blinks using a frequency map.
     *
     * @param initialStones Initial list of stones
     * @param blinks Number of iterations to simulate
     * @return Number of stones after all transformations
     */
    private static long simulateStonesEfficiently(List<BigInteger> initialStones, int blinks) {
        // Frequency map to store the count of each stone
        Map<BigInteger, Long> stoneCounts = new HashMap<>();

        // Initialize the frequency map with the initial stones
        for (BigInteger stone : initialStones) {
            stoneCounts.put(stone, stoneCounts.getOrDefault(stone, 0L) + 1);
        }

        // Perform the simulation for the given number of blinks
        for (int blink = 0; blink < blinks; blink++) {
            Map<BigInteger, Long> newStoneCounts = new HashMap<>();

            for (Map.Entry<BigInteger, Long> entry : stoneCounts.entrySet()) {
                BigInteger stone = entry.getKey();
                long count = entry.getValue();

                if (stone.equals(BigInteger.ZERO)) {
                    // Rule 1: 0 → 1
                    newStoneCounts.put(BigInteger.ONE, newStoneCounts.getOrDefault(BigInteger.ONE, 0L) + count);
                } else if (hasEvenDigits(stone)) {
                    // Rule 2: Split stones with even digits
                    String stoneStr = stone.toString();
                    int mid = stoneStr.length() / 2;
                    BigInteger left = new BigInteger(stoneStr.substring(0, mid));
                    BigInteger right = new BigInteger(stoneStr.substring(mid));

                    newStoneCounts.put(left, newStoneCounts.getOrDefault(left, 0L) + count);
                    newStoneCounts.put(right, newStoneCounts.getOrDefault(right, 0L) + count);
                } else {
                    // Rule 3: Multiply by 2024
                    BigInteger newStone = stone.multiply(BigInteger.valueOf(2024));
                    newStoneCounts.put(newStone, newStoneCounts.getOrDefault(newStone, 0L) + count);
                }
            }

            // Update the frequency map for the next blink
            stoneCounts = newStoneCounts;
        }

        // Calculate the total number of stones after all transformations
        long totalStones = 0;
        for (long count : stoneCounts.values()) {
            totalStones += count;
        }
        return totalStones;
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

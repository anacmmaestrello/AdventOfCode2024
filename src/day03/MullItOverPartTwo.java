package day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MullItOverPartTwo {

    public static void main(String[] args) {
        // Path to the input file
        String filePath = "src/day03/input.txt";

        try {
            // Read the corrupted memory line data from the file
            String memory = new String(Files.readAllBytes(Paths.get(filePath)));

            // Calculate the total sum based on the rules
            int totalSum = calculateSumOfMultiplications(memory);

            // Print the result
            System.out.println("Total sum of enabled multiplications: " + totalSum);
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        }
    }

    /**
     * Executes the main logic, iterating over the memory and processing instructions.
     */
    public static int calculateSumOfMultiplications(String memory) {
        int sum = 0;               // Accumulator for the sum of multiplications
        boolean enabled = true;    // Tracks whether multiplication is enabled

        // Combined regex pattern for all instructions
        Pattern instructionPattern = Pattern.compile(
                "(do\\(\\))|(don't\\(\\))|mul\\((\\d+),(\\d+)\\)" // Combined regex
        );

        Matcher matcher = instructionPattern.matcher(memory);
        int i = 0; // Tracks where we are in the memory string

        // Process all matches from starting point - i
        while (matcher.find(i)) {
            // Move pointer to the end of the current match
            i = matcher.end();

            if (matcher.group(1) != null) {
                // Matches "do()"
                enabled = true;
            } else if (matcher.group(2) != null) {
                // Matches "don't()"
                enabled = false;
            } else if (enabled && matcher.group(3) != null && matcher.group(4) != null) {
                // Matches "mul(x, y)"
                int x = Integer.parseInt(matcher.group(3));
                int y = Integer.parseInt(matcher.group(4));
                // Multiplication is enabled
                sum += x * y;
            }
        }

        return sum;
    }
}

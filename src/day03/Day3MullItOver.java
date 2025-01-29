package day03;

import java.io.*;
import java.util.regex.*;

public class Day3MullItOver {
    public static void main(String[] args) {
        // File path to the input file
        String filePath = "src/day03/sample_input1.txt";

        // Step 1: Read the file content into a string
        String memory = readFile(filePath);

        if (memory == null) {
            System.out.println("Error reading file.");
            return;
        }

        // Step 2: Define regular expressions for instructions
        String mulRegex = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
        String doRegex = "do\\(\\)";
        String dontRegex = "don't\\(\\)";

        // Step 3: Create patterns
        Pattern mulPattern = Pattern.compile(mulRegex);
        Pattern doPattern = Pattern.compile(doRegex);
        Pattern dontPattern = Pattern.compile(dontRegex);

        // Step 4: Initialize variables
        int totalSum = 0;
        boolean isEnabled = false; // Default: mul instructions are disabled

        // Step 5: Sequentially parse the memory string
        int currentIndex = 0; // Start at the beginning of the string

        while (currentIndex < memory.length()) {
            // Try matching each instruction type at the current index
            Matcher mulMatcher = mulPattern.matcher(memory.substring(currentIndex));
            Matcher doMatcher = doPattern.matcher(memory.substring(currentIndex));
            Matcher dontMatcher = dontPattern.matcher(memory.substring(currentIndex));

            // Find the next match for each instruction type
            int nextMul = mulMatcher.find() ? currentIndex + mulMatcher.start() : -1;
            int nextDo = doMatcher.find() ? currentIndex + doMatcher.start() : -1;
            int nextDont = dontMatcher.find() ? currentIndex + dontMatcher.start() : -1;

            // Determine which instruction is the closest
            int nextInstruction = findClosestIndex(nextMul, nextDo, nextDont);

            if (nextInstruction == -1) {
                break; // No more instructions
            }

            if (nextInstruction == nextMul) {
                // Process a mul(X,Y) instruction if enabled
                mulMatcher = mulPattern.matcher(memory.substring(currentIndex));
                if (mulMatcher.find()) {
                    if (isEnabled) {
                        int x = Integer.parseInt(mulMatcher.group(1));
                        int y = Integer.parseInt(mulMatcher.group(2));
                        totalSum += x * y; // Add to the total sum
                    }
                    currentIndex += mulMatcher.end(); // Move past this instruction
                }
            } else if (nextInstruction == nextDo) {
                // Process a do() instruction
                isEnabled = true; // Enable mul instructions
                doMatcher = doPattern.matcher(memory.substring(currentIndex));
                if (doMatcher.find()) {
                    currentIndex += doMatcher.end(); // Move past this instruction
                }
            } else if (nextInstruction == nextDont) {
                // Process a don't() instruction
                isEnabled = false; // Disable mul instructions
                dontMatcher = dontPattern.matcher(memory.substring(currentIndex));
                if (dontMatcher.find()) {
                    currentIndex += dontMatcher.end(); // Move past this instruction
                }
            }
        }

        // Step 6: Output the total sum
        System.out.println("Total Sum: " + totalSum);
    }

    /**
     * Reads the content of a file into a string.
     *
     * @param filePath The path to the file
     * @return The file content as a string
     */
    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return content.toString();
    }

    /**
     * Finds the closest non-negative index among the given indices.
     *
     * @param indices The indices to check
     * @return The smallest non-negative index, or -1 if all are negative
     */
    private static int findClosestIndex(int... indices) {
        int closest = -1;
        for (int index : indices) {
            if (index >= 0 && (closest == -1 || index < closest)) {
                closest = index;
            }
        }
        return closest;
    }
}
